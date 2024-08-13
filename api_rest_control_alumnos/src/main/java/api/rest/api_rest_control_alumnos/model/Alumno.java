/**
 * Autor: Pablo Cesar Leyva Ramirez
 * Fecha: 2024-08-12
 * Descripcion: La clase Alumno es una entidad JPA que representa a los alumnos en la base de datos.
 * Define los atributos que se mapean a la tabla alumnos en la base de datos y proporciona
 * los métodos getter y setter para acceder a estos atributos.
 */

package api.rest.api_rest_control_alumnos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 10)
    private String telefono;

    // Constructor vacío
    public Alumno() {}

    // Constructor con todos los campos
    public Alumno(String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String direccion, String telefono) {
        this.nombre = Objects.requireNonNull(nombre, "Nombre no puede ser nulo");
        this.apellidoPaterno = Objects.requireNonNull(apellidoPaterno, "Apellido paterno no puede ser nulo");
        this.apellidoMaterno = Objects.requireNonNull(apellidoMaterno, "Apellido materno no puede ser nulo");
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "Nombre no puede ser nulo");
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = Objects.requireNonNull(apellidoPaterno, "Apellido paterno no puede ser nulo");
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = Objects.requireNonNull(apellidoMaterno, "Apellido materno no puede ser nulo");
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento =  Objects.requireNonNull(fechaNacimiento, "Fecha de nacimiento no puede ser nulo");;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    //Método para calcular la edad basada en la fecha de nacimiento
    @Transient // Indica que este campo no debe persistirse en la base de datos
    public int getEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(id, alumno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Alumno[id=%d, nombre='%s', apellidoPaterno='%s', apellidoMaterno='%s', fechaNacimiento='%s', telefono='%s, edad=%d']",
                id, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono,getEdad());
    }
}
