package org.lpodesta.springcloud.msvc_cursos.controller;

import jakarta.validation.Valid;
import org.lpodesta.springcloud.msvc_cursos.entity.Curso;
import org.lpodesta.springcloud.msvc_cursos.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

    @Autowired
    private ICursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> findAll(){
        return ResponseEntity.ok(cursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Curso> o =cursoService.findById(id);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Curso c, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }
        Curso cursoDb = cursoService.save(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> modify(@PathVariable Long id,@RequestBody Curso c){
        Optional<Curso> o =cursoService.findById(id);
        if(o.isPresent()){
            Curso cursoDb = o.get();
            cursoDb.setNombre(c.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Curso> o =cursoService.findById(id);
        if(o.isPresent()){
            cursoService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String,String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
