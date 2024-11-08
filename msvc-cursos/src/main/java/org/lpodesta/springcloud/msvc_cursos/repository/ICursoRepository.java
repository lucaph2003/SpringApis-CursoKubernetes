package org.lpodesta.springcloud.msvc_cursos.repository;

import org.lpodesta.springcloud.msvc_cursos.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long> {
}
