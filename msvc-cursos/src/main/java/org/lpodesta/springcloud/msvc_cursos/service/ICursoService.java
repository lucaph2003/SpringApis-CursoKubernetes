package org.lpodesta.springcloud.msvc_cursos.service;

import org.lpodesta.springcloud.msvc_cursos.models.Usuario;
import org.lpodesta.springcloud.msvc_cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {
    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Optional<Curso> findByIdWithUsers(Long id);
    Curso save(Curso c);
    void delete(Long id);
    void deleteCourseUserById(Long userId);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);


}
