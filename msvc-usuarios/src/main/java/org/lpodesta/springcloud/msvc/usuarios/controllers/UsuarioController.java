package org.lpodesta.springcloud.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.lpodesta.springcloud.msvc.usuarios.models.entity.Usuario;
import org.lpodesta.springcloud.msvc.usuarios.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController // hay dos tipos generalmente , controller para mvc  y restcontrolller para api rest
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){ //Inyecta el valor siempre y cuando el valor de la variable es igual al del argumento de la url
        Optional<Usuario> optionalUser = usuarioService.findUserById(id);
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUser(@Valid @RequestBody Usuario usuario, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }

        if(usuarioService.findByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("Error","Ya existe un usuario con ese correo electronico"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUser(usuario));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> modify(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validate(result);
        }

        Optional<Usuario> optionalUser = usuarioService.findUserById(id);
        if(optionalUser.isPresent()){
            Usuario usuarioDb = optionalUser.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUser(usuarioDb)); // Hace un update porque ya existe
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Usuario> optionalUser = usuarioService.findUserById(id);
        if(optionalUser.isPresent()){
            usuarioService.delete(id);
            return ResponseEntity.noContent().build(); // Hace un update porque ya existe
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/users-courses")
    public ResponseEntity<?> getUsersByCourse(@RequestParam List<Long> ids ){
        return ResponseEntity.ok(usuarioService.showByIds(ids));
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String,String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
