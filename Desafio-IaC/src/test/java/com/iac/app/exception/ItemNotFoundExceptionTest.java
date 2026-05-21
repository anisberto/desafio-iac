package com.iac.app.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemNotFoundExceptionTest {

	@Test
	void shouldBuildMessageWithItemId() {
		ItemNotFoundException exception = new ItemNotFoundException(42L);

		assertThat(exception.getMessage()).isEqualTo("Item nao encontrado: 42");
	}

}
