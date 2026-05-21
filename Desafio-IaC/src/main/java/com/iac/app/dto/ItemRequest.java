package com.iac.app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequest(
		@NotBlank(message = "Nome e obrigatorio")
		String name,

		String description,

		@NotNull(message = "Quantidade e obrigatoria")
		@Min(value = 0, message = "Quantidade deve ser maior ou igual a zero")
		Integer quantity
) {
}
