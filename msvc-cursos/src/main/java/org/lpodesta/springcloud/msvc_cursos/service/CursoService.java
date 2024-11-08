package org.lpodesta.springcloud.msvc_cursos.service;

import org.lpodesta.springcloud.msvc_cursos.clients.IUsuarioClientRest;
import org.lpodesta.springcloud.msvc_cursos.models.Usuario;
import org.lpodesta.springcloud.msvc_cursos.models.entity.Curso;
import org.lpodesta.springcloud.msvc_cursos.models.entity.CursoUsuario;
import org.lpodesta.springcloud.msvc_cursos.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService implements ICursoService{
    @Autowired
    private ICursoRepository cursoRepository;

    @Autowired
    private IUsuarioClientRest client;


    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findByIdWithUsers(Long id) {

        Optional<Curso> o = cursoRepository.findById(id);
        if(o.isPresent()){
            Curso curso = o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream().map(cu -> cu.getUsuarioId()).collect(Collectors.toList());

                List<Usuario> usuarios = client.getUsersByCourse(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return o;
    }

    @Override
    @Transactional
    public Curso save(Curso c) {
        return cursoRepository.save(c);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCourseUserById(Long userId) {
        cursoRepository.deleteByIdCourseUser(userId);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMsvc = client.findById(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMsvc = client.save(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMsvc = client.findById(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }


}
