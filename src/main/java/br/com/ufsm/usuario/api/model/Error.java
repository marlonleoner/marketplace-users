package br.com.ufsm.usuario.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error {

	private String id;

	private Integer status;

	private String message;

}
