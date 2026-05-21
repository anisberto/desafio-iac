package com.iac.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class InfoController {

	@Value("${spring.application.name}")
	private String applicationName;

	@GetMapping("/info")
	public ResponseEntity<Map<String, String>> info() {
		return ResponseEntity.ok(Map.of(
				"name", applicationName,
				"version", "0.0.1-SNAPSHOT",
				"description", "Aplicacao Spring Boot para Desafio IaC"
		));
	}

}
