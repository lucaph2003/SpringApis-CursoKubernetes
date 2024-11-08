package org.lpodesta.springcloud.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity //Le decimos que es una tabla
@Table(name="usuarios") //Por defecto si no le ponemos es el nombre de la clase
public class Usuario {

    @Id //Le decimos quye va a ser el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Le decimos que es autoincremental
    private Long id;

    @Column(length = 100) //Con column podemos cambiar la metadata
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String nombre;

    @Column(unique = true)
    @Email
    @NotEmpty(message = "El email no puede estar vacio")
    private String email;

    @NotBlank //no acepta los espacione en blanco
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
