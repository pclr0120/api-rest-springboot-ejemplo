/**
 * Autor: Pablo Cesar Leyva Ramirez
 * Fecha: 2024-08-12
 * Descripcion: La interfaz AlumnoRepo extiende JpaRepository y proporciona métodos
 * para interactuar con la base de datos. Esta interfaz permite realizar operaciones CRUD
 * básicas sin necesidad de implementar métodos manualmente
 */
package api.rest.api_rest_control_alumnos.repo;

import api.rest.api_rest_control_alumnos.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AlumnoRepo extends JpaRepository<Alumno, Integer> {
    Optional<Alumno> findByNombreAndApellidoPaternoAndApellidoMaternoAndFechaNacimiento(
            String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento);

    Optional<Alumno> findByTelefono(String telefono);
}
