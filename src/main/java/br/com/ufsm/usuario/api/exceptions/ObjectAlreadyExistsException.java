package br.com.ufsm.usuario.api.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1731580886001447324L;

	public ObjectAlreadyExistsException(String message) {
		super(message);
	}

}
