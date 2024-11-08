package org.lpodesta.springcloud.msvc.usuarios.services;

import org.lpodesta.springcloud.msvc.usuarios.clients.ICursoClient;
import org.lpodesta.springcloud.msvc.usuarios.models.entity.Usuario;
import org.lpodesta.springcloud.msvc.usuarios.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired //Inyeccion de dependencias
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ICursoClient cursoClient;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findUserById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario saveUser(Usuario u) {
        return usuarioRepository.save(u);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
        cursoClient.deleteCourseUserById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> showByIds(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }
}
