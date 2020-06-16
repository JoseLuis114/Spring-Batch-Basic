package com.pratica.springboot.app.listener;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pratica.springboot.app.entity.Serie;

@Component
public class JobListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JobListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void beforeJob(JobExecution jobExecution) {
		
		
		
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {


			/*jdbcTemplate.execute(
					"SELECT id,estreno from serie");*/

		
			/*
			
			
			List<Serie> series= jdbcTemplate.query("SELECT estreno from serie", new RowMapper<Serie>() {

				@Override
				public Serie mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					Serie serie=new Serie();
					serie.setEstreno(rs.getInt("estreno"));
					return serie;
				}
				
			});
			*/
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

			LOG.info("FINALIZADO EL JOB! Verifica los resultados:");

			
			jdbcTemplate.query(
					"SELECT id,titulo,clasificacion,nivel_clasificacion,puntuacion,estreno,temporadas,fecha from serie",
					(rs, row) -> {
						return new Serie(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
								rs.getInt(6), rs.getInt(7), rs.getDate(8));
					}).forEach(serie -> LOG.info("Registro" + serie));
			
		
			jdbcTemplate.execute("ALTER TABLE serie DROP COLUMN nivel_clasificacion");
			
		}

		}

	}

