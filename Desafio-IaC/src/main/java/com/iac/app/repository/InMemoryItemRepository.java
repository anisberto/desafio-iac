package com.iac.app.repository;

import com.iac.app.model.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryItemRepository {

	private final Map<Long, Item> storage = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong(0);

	public List<Item> findAll() {
		return new ArrayList<>(storage.values());
	}

	public Optional<Item> findById(Long id) {
		return Optional.ofNullable(storage.get(id));
	}

	public Item save(Item item) {
		Long id = item.id() != null ? item.id() : sequence.incrementAndGet();
		Item persisted = new Item(id, item.name(), item.description(), item.quantity());
		storage.put(id, persisted);
		return persisted;
	}

	public boolean deleteById(Long id) {
		return storage.remove(id) != null;
	}

	public void clear() {
		storage.clear();
		sequence.set(0);
	}

}
