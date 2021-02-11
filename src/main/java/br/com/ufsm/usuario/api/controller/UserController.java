package br.com.ufsm.usuario.api.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ufsm.usuario.api.controller.dto.UserDto;
import br.com.ufsm.usuario.api.controller.form.NewUserForm;
import br.com.ufsm.usuario.api.controller.form.UpdateUserForm;
import br.com.ufsm.usuario.api.services.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public UserDto get() {
		return new UserDto(userService.get());
	}

	@PostMapping
	public ResponseEntity<UserDto> create(@RequestBody @Valid NewUserForm form, UriComponentsBuilder uriBuilder) {
		UserDto user = new UserDto(userService.create(form));
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<UserDto> update(@RequestBody @Valid UpdateUserForm form) {
		return ResponseEntity.ok(new UserDto(userService.update(form)));
	}

	@DeleteMapping
	public ResponseEntity<?> delete() {
		userService.delete();
		return ResponseEntity.ok().build();
	}

}
