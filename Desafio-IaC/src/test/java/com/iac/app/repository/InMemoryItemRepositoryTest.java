package com.iac.app.repository;

import com.iac.app.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryItemRepositoryTest {

	private InMemoryItemRepository repository;

	@BeforeEach
	void setUp() {
		repository = new InMemoryItemRepository();
	}

	@Test
	void shouldSaveAndFindItemById() {
		Item saved = repository.save(new Item(null, "Notebook", "Dell", 2));

		Optional<Item> found = repository.findById(saved.id());

		assertThat(found).isPresent();
		assertThat(found.get().name()).isEqualTo("Notebook");
		assertThat(found.get().quantity()).isEqualTo(2);
	}

	@Test
	void shouldReturnAllItems() {
		repository.save(new Item(null, "Mouse", "Logitech", 5));
		repository.save(new Item(null, "Teclado", "Redragon", 3));

		List<Item> items = repository.findAll();

		assertThat(items).hasSize(2);
	}

	@Test
	void shouldUpdateExistingItem() {
		Item saved = repository.save(new Item(null, "Monitor", "LG", 1));

		repository.save(new Item(saved.id(), "Monitor", "Samsung", 2));

		Optional<Item> updated = repository.findById(saved.id());
		assertThat(updated).isPresent();
		assertThat(updated.get().description()).isEqualTo("Samsung");
		assertThat(updated.get().quantity()).isEqualTo(2);
	}

	@Test
	void shouldDeleteItemById() {
		Item saved = repository.save(new Item(null, "Headset", "HyperX", 1));

		boolean deleted = repository.deleteById(saved.id());

		assertThat(deleted).isTrue();
		assertThat(repository.findById(saved.id())).isEmpty();
	}

	@Test
	void shouldReturnFalseWhenDeletingUnknownItem() {
		assertThat(repository.deleteById(999L)).isFalse();
	}

	@Test
	void shouldClearStorage() {
		repository.save(new Item(null, "Webcam", "Logitech", 1));
		repository.clear();

		assertThat(repository.findAll()).isEmpty();
	}

}
