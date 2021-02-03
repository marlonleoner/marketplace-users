package br.com.ufsm.usuarioApi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufsm.usuarioApi.modelo.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmailUsuario(String emailUsuario);
	List<Usuario> findByNome(String nome);
	
}
