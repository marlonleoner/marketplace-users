package br.com.ufsm.usuario.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.com.ufsm.usuario.api.exceptions.ObjectNotFoundException;
import br.com.ufsm.usuario.api.model.User;
import br.com.ufsm.usuario.api.services.UserService;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(AuthRequestFilter.class);

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = recoverToken(request);

			if (tokenUtil.isValidToken(token)) {
				authenticate(token);
			}

			filterChain.doFilter(request, response);
		} catch (ObjectNotFoundException ex) {
			logger.error(" > doFilterInternal() - error: {}", ex.getMessage());
			resolver.resolveException(request, response, null, ex);
		}
	}

	private void authenticate(String token) throws ObjectNotFoundException {
		try {
			String id = tokenUtil.getIdFromToken(token);

			User user = userService.findById(Long.parseLong(id));

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (ObjectNotFoundException ex) {
			throw ex;
		}
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			logger.warn(" > recoverToken() - token does not exists");
			return null;
		}

		return token.substring(7, token.length());
	}

}