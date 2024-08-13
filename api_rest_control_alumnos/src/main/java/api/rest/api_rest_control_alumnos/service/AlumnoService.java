/**
 * Autor: Pablo Cesar Leyva Ramirez
 * Fecha: 2024-08-12
 */

package api.rest.api_rest_control_alumnos.service;

import api.rest.api_rest_control_alumnos.exception.InvalidRequestException;
import api.rest.api_rest_control_alumnos.exception.ResourceNotFoundException;
import api.rest.api_rest_control_alumnos.model.Alumno;
import api.rest.api_rest_control_alumnos.repo.AlumnoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    private final AlumnoRepo alumnoRepository;

    @Autowired
    public AlumnoService(AlumnoRepo alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    public List<Alumno> obtenerTodosAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno obtenerAlumnoPorId(Integer id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));
    }

    public String guardarAlumno(Alumno alumno) {
        validarAlumno(alumno);

                Optional<Alumno> alumnoExistente = alumnoRepository.findByNombreAndApellidoPaternoAndApellidoMaternoAndFechaNacimiento(
                alumno.getNombre(), alumno.getApellidoPaterno(), alumno.getApellidoMaterno(), alumno.getFechaNacimiento());

        if (alumnoExistente.isPresent()) {
            return "El alumno ya está registrado.";
        }

        // Verificar si el número telefónico ya está registrado
        Optional<Alumno> alumnoConMismoTelefono = alumnoRepository.findByTelefono(alumno.getTelefono());
        if (alumnoConMismoTelefono.isPresent()) {
            throw new InvalidRequestException("El número telefónico ya está registrado.");
        }

        alumnoRepository.save(alumno);
        return "Alumno registrado exitosamente.";
    }

    public String eliminarAlumno(Integer id) {
        if (!alumnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alumno no encontrado con id: " + id);
        }

        alumnoRepository.deleteById(id);
        return "Alumno eliminado exitosamente.";
    }

    private void validarAlumno(Alumno alumno) {
        if (alumno.getNombre() == null || alumno.getNombre().isEmpty()) {
            throw new InvalidRequestException("El nombre del alumno no puede estar vacío");
        }
        if (alumno.getTelefono() == null || alumno.getTelefono().isEmpty()) {
            throw new InvalidRequestException("El teléfono del alumno no puede estar vacío");
        }
        if (alumno.getTelefono().length() != 10) {
            throw new InvalidRequestException("El número telefónico del alumno debe tener exactamente 10 caracteres");
        }
    }

    public String actualizarAlumno(Integer id, Alumno alumno) {
        // Verifica si el alumno con el ID proporcionado existe
        Alumno alumnoExistente = obtenerAlumnoPorId(id);

        // Actualiza los detalles del alumno existente con los nuevos datos
        alumnoExistente.setNombre(alumno.getNombre());
        alumnoExistente.setApellidoPaterno(alumno.getApellidoPaterno());
        alumnoExistente.setApellidoMaterno(alumno.getApellidoMaterno());
        alumnoExistente.setFechaNacimiento(alumno.getFechaNacimiento());
        alumnoExistente.setTelefono(alumno.getTelefono());
        // Guarda el alumno actualizado en la base de datos
        alumnoRepository.save(alumnoExistente);
        // Devuelve un mensaje indicando que la actualización fue exitosa
        return "Alumno actualizado exitosamente.";
    }
}
