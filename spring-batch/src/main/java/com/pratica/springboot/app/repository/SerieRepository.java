package com.pratica.springboot.app.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.pratica.springboot.app.entity.Serie;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long>{
	
	
	
}
