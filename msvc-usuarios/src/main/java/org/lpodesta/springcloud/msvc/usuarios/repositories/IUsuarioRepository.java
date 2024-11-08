package org.lpodesta.springcloud.msvc.usuarios.repositories;

import org.lpodesta.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> { //PAsamos la entity y el tipo de dato del id

}
