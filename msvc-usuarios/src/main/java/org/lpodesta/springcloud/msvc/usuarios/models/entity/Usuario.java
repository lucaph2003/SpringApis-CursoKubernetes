package org.lpodesta.springcloud.msvc.usuarios.models.entity;

import jakarta.persistence.*;

@Entity //Le decimos que es una tabla
@Table(name="usuarios") //Por defecto si no le ponemos es el nombre de la clase
public class Usuario {

    @Id //Le decimos quye va a ser el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Le decimos que es autoincremental
    private Long id;

    @Column(length = 100) //Con column podemos cambiar la metadata
    private String nombre;

    @Column(unique = true)
    private String email;

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
