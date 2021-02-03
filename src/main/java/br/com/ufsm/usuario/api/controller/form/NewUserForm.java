package br.com.ufsm.usuario.api.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.mindrot.jbcrypt.BCrypt;

import br.com.ufsm.usuario.api.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserForm {

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@NotNull
	@NotEmpty
	private String cpf;

	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String password;

	public User convert() {
		String hashedPassword = BCrypt.hashpw(this.password, BCrypt.gensalt());
		return new User(this.firstName, this.lastName, this.cpf, this.email, hashedPassword);
	}

}
