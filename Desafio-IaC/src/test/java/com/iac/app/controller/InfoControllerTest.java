package com.iac.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnApplicationInfo() throws Exception {
		mockMvc.perform(get("/api/info"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Desafio-IaC"))
				.andExpect(jsonPath("$.version").value("0.0.1-SNAPSHOT"))
				.andExpect(jsonPath("$.description").exists());
	}

}
