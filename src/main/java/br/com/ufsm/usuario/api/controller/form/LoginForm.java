package br.com.ufsm.usuario.api.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginForm {

	private String email;

	private String password;

	public LoginForm(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(this.email, this.password);
	}
}
