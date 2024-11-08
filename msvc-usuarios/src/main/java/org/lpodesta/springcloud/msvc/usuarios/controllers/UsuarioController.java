package org.lpodesta.springcloud.msvc.usuarios.controllers;

import org.lpodesta.springcloud.msvc.usuarios.models.entity.Usuario;
import org.lpodesta.springcloud.msvc.usuarios.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Usuario saveUser(@RequestBody Usuario usuario){
        return usuarioService.saveUser(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modify(@RequestBody Usuario usuario,@PathVariable Long id){
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
}
