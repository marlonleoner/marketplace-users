package br.com.ufsm.usuarioApi.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AtualizacaoUsuarioForm {

	@NotNull @NotEmpty @Email
	private String emailUsuario;
	
	@NotNull @NotEmpty
	private String senha;
	
}
