package br.com.ufsm.usuario.api.controller.dto;

import br.com.ufsm.usuario.api.model.User;
import lombok.Data;

@Data
public class UserDto {

	private Long id;

	private String firstName;

	private String lastName;

	private String cpf;

	private String email;

	public UserDto(User usuario) {
		this.id = usuario.getId();
		this.firstName = usuario.getFirstName();
		this.lastName = usuario.getLastName();
		this.cpf = usuario.getCpf();
		this.email = usuario.getEmail();
	}

}
