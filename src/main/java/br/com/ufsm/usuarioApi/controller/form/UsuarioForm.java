package br.com.ufsm.usuarioApi.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.ufsm.usuarioApi.modelo.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioForm {

	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @NotEmpty
	private String cpf;
	
	@NotNull @NotEmpty @Email
	private String emailUsuario;
	
	@NotNull @NotEmpty
	private String senha;

	public Usuario converter() {
		return new Usuario(nome, cpf, emailUsuario, senha);
	}
	
}
