package org.lpodesta.springcloud.msvc_cursos.clients;

import org.lpodesta.springcloud.msvc_cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url="localhost:8001")
public interface IUsuarioClientRest {

    @GetMapping("/{id}")
    Usuario findById(@PathVariable Long id);

    @PostMapping("/")
    Usuario save(@RequestBody Usuario usuario);

    @GetMapping("/users-courses")
    List<Usuario> getUsersByCourse(@RequestParam Iterable<Long> ids);
}
