package br.com.ufsm.usuarioApi.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.ufsm.usuarioApi.modelo.Usuario;
import lombok.Data;

@Data
public class UsuarioDto {

	private Long id;
	private String nome;
	private String cpf;
	private String emailUsuario;
	private String senha;
	
	
	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getIdUsuario();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.emailUsuario = usuario.getEmailUsuario();
		this.senha = usuario.getSenha();
	}

	public static List<UsuarioDto> converter(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList());
	}

}
