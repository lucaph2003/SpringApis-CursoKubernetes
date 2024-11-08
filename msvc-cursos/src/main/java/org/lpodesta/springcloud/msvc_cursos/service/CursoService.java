package org.lpodesta.springcloud.msvc_cursos.service;

import org.lpodesta.springcloud.msvc_cursos.entity.Curso;
import org.lpodesta.springcloud.msvc_cursos.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService implements ICursoService{
    @Autowired
    private ICursoRepository cursoRepository;


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
    @Transactional
    public Curso save(Curso c) {
        return cursoRepository.save(c);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }
}
