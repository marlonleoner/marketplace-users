package br.com.ufsm.usuarioApi.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.ufsm.usuarioApi.controller.dto.UsuarioDto;
import br.com.ufsm.usuarioApi.controller.form.AtualizacaoUsuarioForm;
import br.com.ufsm.usuarioApi.controller.form.UsuarioForm;
import br.com.ufsm.usuarioApi.modelo.Usuario;
import br.com.ufsm.usuarioApi.repository.UsuarioRepository;

@Service
public class UsuariosService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public List<Usuario> findAll(String nome){
		if (nome == null) {
			return usuarioRepository.findAll();
		} else {
			return usuarioRepository.findByNome(nome);
		}
	}
	
	public ResponseEntity<UsuarioDto> findByEmail(String email) {
		Optional<Usuario> usuario = usuarioRepository.findByEmailUsuario(email);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(new UsuarioDto(usuario.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	public Usuario create(UsuarioForm form) {
		Usuario usuario = form.converter();
		List<Usuario> usuarios = usuarioRepository.findAll();
		for (Usuario p : usuarios) {
			if (p.equals(usuario)) return null;
		}
		usuarioRepository.save(usuario);
		return usuario;
	}
	
	public UsuarioDto atualizarProduto(Long id, @Valid AtualizacaoUsuarioForm form) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if (optional.isPresent()) {
			Usuario u = optional.get();
			u.setEmailUsuario(form.getEmailUsuario());
			u.setSenha(form.getSenha());
			usuarioRepository.save(u);
			return new UsuarioDto(u);
		}
		return null;
	}

	public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if (optional.isPresent()) {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
