package br.com.ufsm.usuario.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.ufsm.usuario.api.controller.form.NewUserForm;
import br.com.ufsm.usuario.api.controller.form.UpdateUserForm;
import br.com.ufsm.usuario.api.exceptions.ObjectAlreadyExistsException;
import br.com.ufsm.usuario.api.exceptions.ObjectNotFoundException;
import br.com.ufsm.usuario.api.model.User;
import br.com.ufsm.usuario.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll(String nome) {
		return userRepository.findByFirstNameContains(nome);
	}

	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new ObjectNotFoundException("Usuário não encontrado.");
		}

		return user.get();
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void verifyEmailAlreadyExists(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			throw new ObjectAlreadyExistsException("Email já cadastrado.");
		}
	}

	public User get() {
		return this.getAuthenticatedUser();
	}

	public User create(NewUserForm form) {
		this.verifyEmailAlreadyExists(form.getEmail());

		User user = form.convert();
		userRepository.save(user);

		return user;
	}

	public User update(UpdateUserForm form) {
		User user = this.getAuthenticatedUser();
		if (!user.getEmail().equals(form.getEmail())) {
			this.verifyEmailAlreadyExists(form.getEmail());
		}

		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setUpdatedAt(LocalDateTime.now());

		return user;
	}

	public void delete() {
		User user = this.getAuthenticatedUser();

		userRepository.deleteById(user.getId());
	}

	private User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return (User) authentication.getPrincipal();
	}

}
