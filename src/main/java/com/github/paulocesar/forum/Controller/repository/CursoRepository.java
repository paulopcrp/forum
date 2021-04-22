package com.github.paulocesar.forum.Controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.paulocesar.forum.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Curso findByNome(String nomeCurso);

}
