package com.pratica.springboot.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.pratica.springboot.app.entity.Serie;

@Service
public class SeriesServicePl implements SerieRepositoryImpl {

	//private final String FETCH_SQL = "select * from serie where estreno=2017 AND temporadas=7 AND puntuacion=110";

	private final String FETCH_SQL = "select * from serie where estreno=2017 AND temporadas=(select MAX(temporadas) from serie) AND puntuacion=110";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<Serie> findAll() {

		return namedParameterJdbcTemplate.query(FETCH_SQL, new SerieMapper());
	}

}

@SuppressWarnings("rawtypes")
class SerieMapper implements RowMapper {

	@Override
	public Serie mapRow(ResultSet rs, int row) throws SQLException {
		Serie serie = new Serie();
		serie.setId(rs.getLong("id"));
		serie.setTitulo(rs.getString("titulo"));
		serie.setClasificacion(rs.getString("clasificacion"));
		serie.setPuntuacion(rs.getInt("puntuacion"));
		serie.setEstreno(rs.getInt("estreno"));
		serie.setTemporadas(rs.getInt("temporadas"));
		serie.setFecha(rs.getDate("fecha"));
		return serie;
	}

}
