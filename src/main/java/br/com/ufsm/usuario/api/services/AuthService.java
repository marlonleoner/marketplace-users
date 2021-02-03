package br.com.ufsm.usuario.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ufsm.usuario.api.controller.form.LoginForm;
import br.com.ufsm.usuario.api.model.User;
import br.com.ufsm.usuario.api.security.TokenUtil;

@Service
public class AuthService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenUtil tokenUtil;

	public String authenticate(LoginForm form) {
		UsernamePasswordAuthenticationToken data = form.converter();

		String token;
		try {
			Authentication authentication = authManager.authenticate(data);

			token = tokenUtil.generateToken(authentication);
		} catch (AuthenticationException ex) {
			logger.error(" > authenticate() - {}: {}", ex.getClass().getSimpleName(), ex.getMessage());

			throw new BadCredentialsException("Seu e-mail ou senha estão incorretos.");
		}

		return token;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Bad credentials");
		}

		return user;
	}

}