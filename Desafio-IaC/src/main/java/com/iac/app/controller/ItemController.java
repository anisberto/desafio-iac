package com.iac.app.controller;

import com.iac.app.dto.ItemRequest;
import com.iac.app.model.Item;
import com.iac.app.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public ResponseEntity<List<Item>> findAll() {
		return ResponseEntity.ok(itemService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> findById(@PathVariable Long id) {
		return ResponseEntity.ok(itemService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Item> create(@Valid @RequestBody ItemRequest request) {
		Item created = itemService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Item> update(@PathVariable Long id, @Valid @RequestBody ItemRequest request) {
		return ResponseEntity.ok(itemService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		itemService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
