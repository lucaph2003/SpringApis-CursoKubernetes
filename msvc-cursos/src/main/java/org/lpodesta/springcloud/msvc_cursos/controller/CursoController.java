package org.lpodesta.springcloud.msvc_cursos.controller;

import feign.FeignException;
import jakarta.validation.Valid;
import org.lpodesta.springcloud.msvc_cursos.models.Usuario;
import org.lpodesta.springcloud.msvc_cursos.models.entity.Curso;
import org.lpodesta.springcloud.msvc_cursos.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        Optional<Curso> o =cursoService.findByIdWithUsers(id);
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

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o ;
        try{
            o = cursoService.asignarUsuario(usuario,cursoId);
        }  catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje","ERROR " +e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o ;
        try{
            o = cursoService.crearUsuario(usuario,cursoId);
        }  catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje","ERROR " +e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove-usuario/{cursoId}")
    public ResponseEntity<?> removeUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o ;
        try{
            o = cursoService.eliminarUsuario(usuario,cursoId);
        }  catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje","ERROR " +e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-course-user/{userId}")
    public ResponseEntity<?> deleteCourseUserById(@PathVariable Long userId){
        cursoService.deleteCourseUserById(userId);
        return ResponseEntity.noContent().build();
    }


    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String,String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
