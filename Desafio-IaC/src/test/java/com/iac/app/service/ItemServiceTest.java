package com.iac.app.service;

import com.iac.app.dto.ItemRequest;
import com.iac.app.exception.ItemNotFoundException;
import com.iac.app.model.Item;
import com.iac.app.repository.InMemoryItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

	@Mock
	private InMemoryItemRepository repository;

	@InjectMocks
	private ItemService itemService;

	@Test
	void shouldReturnAllItems() {
		when(repository.findAll()).thenReturn(List.of(
				new Item(1L, "A", "desc A", 1),
				new Item(2L, "B", "desc B", 2)
		));

		List<Item> items = itemService.findAll();

		assertThat(items).hasSize(2);
		verify(repository).findAll();
	}

	@Test
	void shouldFindItemById() {
		when(repository.findById(1L)).thenReturn(Optional.of(new Item(1L, "A", "desc", 1)));

		Item item = itemService.findById(1L);

		assertThat(item.name()).isEqualTo("A");
	}

	@Test
	void shouldThrowWhenItemNotFound() {
		when(repository.findById(99L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> itemService.findById(99L))
				.isInstanceOf(ItemNotFoundException.class)
				.hasMessageContaining("99");
	}

	@Test
	void shouldCreateItem() {
		ItemRequest request = new ItemRequest("Notebook", "Dell", 3);
		when(repository.save(any(Item.class))).thenAnswer(invocation -> {
			Item item = invocation.getArgument(0);
			return new Item(1L, item.name(), item.description(), item.quantity());
		});

		Item created = itemService.create(request);

		assertThat(created.id()).isEqualTo(1L);
		assertThat(created.name()).isEqualTo("Notebook");
		verify(repository).save(any(Item.class));
	}

	@Test
	void shouldUpdateItem() {
		ItemRequest request = new ItemRequest("Mouse", "Logitech", 10);
		when(repository.findById(1L)).thenReturn(Optional.of(new Item(1L, "Old", "old", 1)));
		when(repository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Item updated = itemService.update(1L, request);

		assertThat(updated.id()).isEqualTo(1L);
		assertThat(updated.name()).isEqualTo("Mouse");
		assertThat(updated.quantity()).isEqualTo(10);
	}

	@Test
	void shouldDeleteItem() {
		when(repository.deleteById(1L)).thenReturn(true);

		itemService.delete(1L);

		verify(repository).deleteById(1L);
	}

	@Test
	void shouldThrowWhenDeletingUnknownItem() {
		when(repository.deleteById(99L)).thenReturn(false);

		assertThatThrownBy(() -> itemService.delete(99L))
				.isInstanceOf(ItemNotFoundException.class);
	}

}
