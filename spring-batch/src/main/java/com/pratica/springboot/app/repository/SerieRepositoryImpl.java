package com.pratica.springboot.app.repository;

import java.util.List;

import com.pratica.springboot.app.entity.Serie;


public interface SerieRepositoryImpl {
	
	public List<Serie> findAll();
	

}
