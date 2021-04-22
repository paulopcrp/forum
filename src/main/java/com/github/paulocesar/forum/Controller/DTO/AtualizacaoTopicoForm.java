package com.github.paulocesar.forum.Controller.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.github.paulocesar.forum.Topico;
import com.github.paulocesar.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm {
	
	@NotNull @NotEmpty @Length(min = 5)
 	private String titulo;
	
	@NotNull @NotEmpty @Length(max = 10)
	private String mensagem;

	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		
		Topico topico = topicoRepository.getOne(id);
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		
		return topico;
	}
	
	

}
