package com.iac.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iac.app.dto.ItemRequest;
import com.iac.app.repository.InMemoryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InMemoryItemRepository repository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		repository.clear();
	}

	@Test
	void shouldCreateAndListItems() throws Exception {
		ItemRequest request = new ItemRequest("Notebook", "Dell Inspiron", 2);

		mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Notebook"))
				.andExpect(jsonPath("$.quantity").value(2));

		mockMvc.perform(get("/api/items"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void shouldFindItemById() throws Exception {
		String response = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new ItemRequest("Mouse", "USB", 5))))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Long id = objectMapper.readTree(response).get("id").asLong();

		mockMvc.perform(get("/api/items/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Mouse"));
	}

	@Test
	void shouldUpdateItem() throws Exception {
		String response = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new ItemRequest("Teclado", "Mecanico", 1))))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Long id = objectMapper.readTree(response).get("id").asLong();
		ItemRequest update = new ItemRequest("Teclado Gamer", "RGB", 3);

		mockMvc.perform(put("/api/items/" + id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(update)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Teclado Gamer"))
				.andExpect(jsonPath("$.quantity").value(3));
	}

	@Test
	void shouldDeleteItem() throws Exception {
		String response = mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new ItemRequest("Headset", "Wireless", 1))))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Long id = objectMapper.readTree(response).get("id").asLong();

		mockMvc.perform(delete("/api/items/" + id))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/items/" + id))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldReturnNotFoundForUnknownItem() throws Exception {
		mockMvc.perform(get("/api/items/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Item nao encontrado"));
	}

	@Test
	void shouldReturnBadRequestForInvalidPayload() throws Exception {
		ItemRequest invalid = new ItemRequest("", "sem nome", -1);

		mockMvc.perform(post("/api/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(invalid)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Erro de validacao"));
	}

}
