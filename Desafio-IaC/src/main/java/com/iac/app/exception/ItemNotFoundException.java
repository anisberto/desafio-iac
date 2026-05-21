package com.iac.app.exception;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(Long id) {
		super("Item nao encontrado: " + id);
	}

}
