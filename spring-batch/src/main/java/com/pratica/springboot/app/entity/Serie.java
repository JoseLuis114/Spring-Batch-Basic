package com.pratica.springboot.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
public class Serie implements Serializable{


	private static final long serialVersionUID = -1982879709326435722L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	private String titulo;
	
	private String clasificacion;
	
	
	//@Transient
	@JsonIgnore
	private String nivel_clasificacion;
	
	 int puntuacion;
	
	 int estreno;
	
	 int temporadas;
	 

	 private Date fecha;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getNivel_clasificacion() {
		return nivel_clasificacion;
	}

	public void setNivel_clasificacion(String nivel_clasificacion) {
		this.nivel_clasificacion = nivel_clasificacion;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public int getEstreno() {
		return estreno;
	}

	public void setEstreno(int estreno) {
		this.estreno = estreno;
	}

	public int getTemporadas() {
		return temporadas;
	}

	public void setTemporadas(int temporadas) {
		this.temporadas = temporadas;
	}
	@Override
	public String toString() {
		return "Serie [id=" + id + ", titulo=" + titulo + ", clasificacion=" + clasificacion + ", nivel_clasificacion="
				+ nivel_clasificacion + ", puntuacion=" + puntuacion + ", estreno=" + estreno + ", temporadas="
				+ temporadas + ", fecha=" + fecha + "]";
	}


	public Serie(Long id, String titulo, String clasificacion, String nivel_clasificacion, int puntuacion, int estreno,
			int temporadas, Date fecha) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.clasificacion = clasificacion;
		this.nivel_clasificacion = nivel_clasificacion;
		this.puntuacion = puntuacion;
		this.estreno = estreno;
		this.temporadas = temporadas;
		this.fecha = fecha;
	}

	public Serie() {
		
		
	}
	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}
