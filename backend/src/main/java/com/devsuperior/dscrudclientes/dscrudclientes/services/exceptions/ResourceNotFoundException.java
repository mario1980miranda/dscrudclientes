package com.devsuperior.dscrudclientes.dscrudclientes.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9054786409072970696L;

	public ResourceNotFoundException(final String msg) {
		super(msg);
	}
}
