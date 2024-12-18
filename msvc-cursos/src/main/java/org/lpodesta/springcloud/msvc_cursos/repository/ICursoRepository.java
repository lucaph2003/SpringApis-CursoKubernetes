package org.lpodesta.springcloud.msvc_cursos.repository;

import org.lpodesta.springcloud.msvc_cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long> {

    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    void deleteByIdCourseUser(Long userId);
}
