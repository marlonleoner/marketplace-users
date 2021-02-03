package br.com.ufsm.usuario.api.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class User implements UserDetails {

	private static final long serialVersionUID = -4119650946429164320L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_ID")
	@TableGenerator(name = "USER_ID", table = "GENERATOR_TABLE", pkColumnName = "USER_KEY", valueColumnName = "USER_KEY_NEXT", pkColumnValue = "user", allocationSize = 1)
	@Getter
	private Long id;

	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private String lastName;

	@Getter
	@Setter
	private String cpf;

	@Getter
	@Setter
	private String email;

	@Getter
	private String password;

	@Getter
	private LocalDateTime createdAt = LocalDateTime.now();

	@Getter
	@Setter
	private LocalDateTime updatedAt = LocalDateTime.now();

	public User(String firstName, String lastName, String cpf, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User[" + this.id + "] " + this.firstName + " " + this.lastName + ": " + this.email + "//<password>";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}