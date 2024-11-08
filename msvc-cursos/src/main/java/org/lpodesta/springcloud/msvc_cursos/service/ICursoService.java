package org.lpodesta.springcloud.msvc_cursos.service;

import org.lpodesta.springcloud.msvc_cursos.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {
    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Curso save(Curso c);
    void delete(Long id);
}
