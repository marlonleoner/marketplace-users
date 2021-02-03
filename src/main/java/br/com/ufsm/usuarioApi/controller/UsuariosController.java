package br.com.ufsm.usuarioApi.controller;

import java.net.URI;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ufsm.usuarioApi.controller.dto.UsuarioDto;
import br.com.ufsm.usuarioApi.controller.form.AtualizacaoUsuarioForm;
import br.com.ufsm.usuarioApi.controller.form.UsuarioForm;
import br.com.ufsm.usuarioApi.modelo.Usuario;
import br.com.ufsm.usuarioApi.services.UsuariosService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private UsuariosService usuariosService;
	
	
	@GetMapping
	public List<UsuarioDto> findAll(String nome) {
		return UsuarioDto.converter(usuariosService.findAll(nome));
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<UsuarioDto> usuarioPorEmail(@PathVariable String email) {
		return usuariosService.findByEmail(email);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> create(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {
		Usuario usuario = usuariosService.create(form);
		if (usuario == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getIdUsuario()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoUsuarioForm form) {
		UsuarioDto usuarioDto = usuariosService.atualizarProduto(id, form);
		if (usuarioDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(usuarioDto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		return usuariosService.removerUsuario(id);
	}
	
}
