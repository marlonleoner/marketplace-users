package br.com.ufsm.usuarioApi.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "usuario")
@Getter @Setter @NoArgsConstructor
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "nome_usuario")
	private String nome;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "email_usuario")
	private String emailUsuario;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	public Usuario(String nome, String cpf, String emailUsuario, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.emailUsuario = emailUsuario;
		this.senha = senha;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Usuario)) return false;
		Usuario usuario = (Usuario) o;
		return Objects.equals(this.emailUsuario, usuario.emailUsuario);
	}

}
