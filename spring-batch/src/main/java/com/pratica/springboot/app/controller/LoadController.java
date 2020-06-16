package com.pratica.springboot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratica.springboot.app.entity.Serie;
import com.pratica.springboot.app.repository.SeriesServicePl;

@RestController
@RequestMapping("/load")
public class LoadController {
	
	

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;
	
	@Autowired
	SeriesServicePl service;

	@GetMapping
	public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException {
			Map<String, JobParameter> maps= new HashMap<>();
			maps.put("time", new JobParameter(System.currentTimeMillis()));
			JobParameters parameters= new JobParameters(maps);
			JobExecution jobExecution = jobLauncher.run(job, parameters);
			
			System.out.println("JobEecution: " + jobExecution.getStatus());
			System.out.println("Batch esta corriendo...");
			
			
		
				return jobExecution.getStatus();

	}
	@GetMapping("/listar")
	public List<Serie> getCustomers() { 
		
		return service.findAll(); 
	}
	
	
	
}
