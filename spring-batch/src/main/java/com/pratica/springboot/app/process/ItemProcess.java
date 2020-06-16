package com.pratica.springboot.app.process;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.pratica.springboot.app.entity.Serie;

public class ItemProcess implements ItemProcessor<Serie, Serie> {

	private static final Logger LOG = LoggerFactory.getLogger(ItemProcess.class);

	private static Map<String, Serie> duplicados = new HashMap<String, Serie>();

	@Override
	public Serie process(Serie series) throws Exception {

		Long id = series.getId();
		String titulo = series.getTitulo().toUpperCase();
		
		
		if (!duplicados.containsKey(titulo)) {

			String clasificacion = series.getClasificacion();

			switch (series.getClasificacion()) {
			case "G":
				clasificacion = series.getClasificacion().replaceAll("G", "Todos los publicos");
				break;
			case "PG":
				clasificacion = series.getClasificacion().replaceAll("PG", "Todos los publicos");
				break;
			case "TV-Y7":
				clasificacion = series.getClasificacion().replaceAll("TV-Y7", "Mayores de 7 años");
				break;
			case "TV-Y":
				clasificacion = series.getClasificacion().replaceAll("TV-Y", "Todos los publicos");
				break;
			case "TV-Y7-FV":
				clasificacion = series.getClasificacion().replaceAll("TV-Y7-FV", "Mayores de 7 años.Fantasia violenta");
				break;
			case "PG-13":
				clasificacion = series.getClasificacion().replaceAll("PG-13", "Mayores de 13 años");
				break;
			case "TV-G":
				clasificacion = series.getClasificacion().replaceAll("TV-G", "Todos los publicos");
				break;
			case "R":
				clasificacion = series.getClasificacion().replaceAll("R", "Lenguaje obsceno y violencia");
				break;
			case "TV-14":
				clasificacion = series.getClasificacion().replaceAll("TV-14", "Mayores de 14 años");
				break;
			case "TV-PG":
				clasificacion = series.getClasificacion().replaceAll("TV-PG", "Bajo supervision parental");
				break;
			case "TV-MA":
				clasificacion = series.getClasificacion().replaceAll("TV-MA", "Mayores de 17 años");
				break;
			case "NR":
				clasificacion = series.getClasificacion().replaceAll("NR", "Sin clasificar");
				break;
			case "UR":
				clasificacion = series.getClasificacion().replaceAll("UR", "Sin clasificar. Solo adultos");
				break;
			default:
				break;
			}
			String nivel_clasificacion = series.getNivel_clasificacion().toUpperCase();
			int puntuacion = series.getPuntuacion();
			int estreno = series.getEstreno();
			int temporadas = series.getTemporadas();
			Date fecha = new Date();

			series = new Serie(id, titulo, clasificacion, nivel_clasificacion, puntuacion, estreno, temporadas, fecha);

			LOG.info("Convirtiendo (" + series + ") a (" + series + ")");

			duplicados.put(titulo, series);
		} else {

			series = new Serie();

		}

		return series;
	}

}
