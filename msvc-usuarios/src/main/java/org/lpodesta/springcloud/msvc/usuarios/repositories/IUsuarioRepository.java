package org.lpodesta.springcloud.msvc.usuarios.repositories;

import org.lpodesta.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> { //PAsamos la entity y el tipo de dato del id
    Optional<Usuario> findByEmail(String email);


    //Consulta personalizada
    @Query("select u from Usuario u where u.email=?1")
    Optional<Usuario> porEmail(String email);
}
