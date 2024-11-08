package org.lpodesta.springcloud.msvc.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.lpodesta.springcloud.msvc.usuarios.models.entity.Usuario;

public interface IUsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findUserById(Long id); //Puede ser null , evitamos las nullpointerexception
    Usuario saveUser(Usuario u);

    void delete(Long id);


}
