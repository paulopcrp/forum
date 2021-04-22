package com.github.paulocesar.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.paulocesar.forum.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	List<Topico> findByCursoNome(String nomeCurso);
	
	//para fazer o jpqa e não seguir o padrão spring boot
	//@Query("SELECT T FROM topico t WHERE t.curso.nome = :nomeCurso")
	//List<Topico> carregarPorNomedoCurso /*coloco o nome que eu quiser*/ (@Param("nomeCurso")String nomeCurso);
	

}
