package br.com.ufsm.usuario.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufsm.usuario.api.controller.dto.TokenDto;
import br.com.ufsm.usuario.api.controller.form.LoginForm;
import br.com.ufsm.usuario.api.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginForm form) {
		String token = authService.authenticate(form);

		return ResponseEntity.ok(new TokenDto(token));
	}

}