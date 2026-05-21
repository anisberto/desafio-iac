package com.iac.app.service;

import com.iac.app.dto.ItemRequest;
import com.iac.app.exception.ItemNotFoundException;
import com.iac.app.model.Item;
import com.iac.app.repository.InMemoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

	private final InMemoryItemRepository repository;

	public ItemService(InMemoryItemRepository repository) {
		this.repository = repository;
	}

	public List<Item> findAll() {
		return repository.findAll();
	}

	public Item findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException(id));
	}

	public Item create(ItemRequest request) {
		Item item = new Item(null, request.name(), request.description(), request.quantity());
		return repository.save(item);
	}

	public Item update(Long id, ItemRequest request) {
		findById(id);
		Item updated = new Item(id, request.name(), request.description(), request.quantity());
		return repository.save(updated);
	}

	public void delete(Long id) {
		if (!repository.deleteById(id)) {
			throw new ItemNotFoundException(id);
		}
	}

}
