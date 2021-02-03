package br.com.ufsm.usuario.api.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.ufsm.usuario.api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil implements Serializable {

	private Logger logger = LoggerFactory.getLogger(TokenUtil.class);

	private static final long serialVersionUID = -8831712574135119545L;

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	@Value("${jwt.expiration}")
	private String EXPIRES_IN;

	/**
	 * Gera e retorna um token JWT.
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		User user = (User) userDetails;
		logger.info(" > generateToken({}): {}", user.getId(), user.toString());

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		long expMillis = nowMillis + Long.parseLong(EXPIRES_IN);
		Date exp = new Date(expMillis);

		Map<String, Object> payload = new TreeMap<String, Object>();

		payload.put("id", user.getId().toString());
		payload.put("email", user.getEmail());
		payload.put("createdAt", now);
		payload.put("expiresIn", exp);

		// Building Token
		JwtBuilder builder = Jwts.builder().setClaims(payload).signWith(SignatureAlgorithm.HS512, SECRET_KEY);

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	public String generateToken(Authentication authentication) {
		return this.generateToken((UserDetails) authentication.getPrincipal());
	}

	/**
	 * Retorna o payload inserido no token JWT.
	 * 
	 * @param token - token JWT
	 * @return payload do token
	 */
	private Claims getPayload(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Retorna o email do usuário contido no token JWT.
	 * 
	 * @param token - token JWT
	 * @return email do usuario logado
	 */
	public String getEmailFromToken(String token) {
		Claims payload = this.getPayload(token);

		return payload.get("email").toString();
	}

	/**
	 * Retorna o identigicador do usuário contido no token JWT.
	 * 
	 * @param token - token JWT
	 * @return email do usuario logado
	 */
	public String getIdFromToken(String token) {
		Claims payload = this.getPayload(token);

		return payload.get("id").toString();
	}

	/**
	 * Retorna a data de expiração do token JWT.
	 * 
	 * @param token - token JWT
	 * @return data de expiração do token
	 */
	private Date getExpirationDateFromToken(String token) {
		Claims payload = this.getPayload(token);

		return new Date(Long.parseLong(payload.get("expiresIn").toString()));
	}

	/**
	 * Verifica se o token JWT está expirado
	 * 
	 * @param token
	 * @return true, se o token expirou
	 */
	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);

		return expiration.before(new Date());
	}

	/**
	 * Validação do token JWT.
	 * 
	 * @param token
	 * @param userDetails
	 * @return true, se o token é válido
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;

		String email = getEmailFromToken(token);
		String id = getIdFromToken(token);

		boolean isUserInfos = email.equals(user.getEmail()) && id.equals(user.getId().toString());

		return (isUserInfos && !isTokenExpired(token));
	}

	/**
	 * Validação do token JWT.
	 * 
	 * @param token
	 * @return true, se o token é válido
	 */
	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}