package br.com.ufsm.usuario.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufsm.usuario.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	List<User> findByFirstNameContains(String nome);

}
