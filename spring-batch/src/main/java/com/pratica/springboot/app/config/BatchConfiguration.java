package com.pratica.springboot.app.config;

import java.util.List;

import javax.sql.DataSource;

import org.aspectj.apache.bcel.generic.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pratica.springboot.app.entity.Serie;
import com.pratica.springboot.app.listener.JobListener;
import com.pratica.springboot.app.process.ItemProcess;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	/*
	 * Realizamos las operciones y configuraciones del Reader dentro de esta clase
	 * 
	 * Realizamos las operciones y configuraciones del Processor dentro de esta
	 * clase
	 * 
	 * Realizamos las operciones y configuraciones del Writer dentro de esta clase
	 * 
	 * Realizamos las operciones y configuraciones del job dentro de esta clase
	 * 
	 * Realizamos las operciones y configuraciones del step dentro de esta clase
	 * 
	 */

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean
	public FlatFileItemReader<Serie> reader() {

		return new FlatFileItemReaderBuilder<Serie>().name("seriesItemReader")
				.resource(new FileSystemResource("src/main/resources/Series.csv")).delimited().delimiter(";")
				.names(new String[] { "titulo", "clasificacion", "nivel_clasificacion", "puntuacion", "estreno",
						"temporadas" })
				.targetType(Serie.class).build();

	}

	@Bean
	public ItemProcess processor() {
		return new ItemProcess();

	}

	@Bean
	public ItemWriter<Serie> writer() {

		/*
		 * JdbcBatchItemWriter<Serie> writer = new JdbcBatchItemWriter<Serie>();
		 * writer.setItemSqlParameterSourceProvider(new
		 * BeanPropertyItemSqlParameterSourceProvider<Serie>()); writer.setSql(
		 * "INSERT INTO serie (titulo,clasificacion,nivel_clasificacion,puntuacion,estreno,temporadas,fecha) VALUES (:titulo,:clasificacion,:nivel_clasificacion,:puntuacion,:estreno,:temporadas,:fecha)"
		 * ); writer.setDataSource(dataSource);
		 */
		return new ItemWriter<Serie>() {

			@Override
			public void write(List<? extends Serie> items) throws Exception {
				// TODO Auto-generated method stub

				/*
				 * for (int i = 0; i < items.size(); i++) {
				 * 
				 * if (items.get(i) == null) {
				 * 
				 * jdbcTemplate.update(
				 * "INSERT INTO serie (titulo,clasificacion,nivel_clasificacion,puntuacion,estreno,temporadas,fecha) VALUES (?,?,?,?,?,?,?)"
				 * , new Serie[] {titulo},new Serie[] {Type.STRING} ); }
				 * 
				 * }
				 */

				for (Serie serie : items) {

					if (serie.getTitulo() != null) {
						jdbcTemplate.update(
								"INSERT INTO serie (titulo,clasificacion,nivel_clasificacion,puntuacion,estreno,temporadas,fecha) VALUES (?,?,?,?,?,?,?)",
								serie.getTitulo(), serie.getClasificacion(), serie.getNivel_clasificacion(),
								serie.getPuntuacion(), serie.getEstreno(), serie.getTemporadas(), serie.getFecha());
					}
				}

			}

		};

	}

	@Bean
	public Job importSerieJob(JobListener listener) {

		return jobBuilderFactory.get("importSerieJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1()).end().build();

	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Serie, Serie>chunk(50).reader(reader()).processor(processor())
				.writer(writer()).faultTolerant().skipLimit(10000).skip(RuntimeException.class).build();
	}

}
