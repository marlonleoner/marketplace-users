package br.com.ufsm.usuario.api.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4395732112999769101L;

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
