/**
 * Autor: Pablo Cesar Leyva Ramirez
 * Fecha: 2024-08-12
 */
package api.rest.api_rest_control_alumnos.service;

import api.rest.api_rest_control_alumnos.exception.InvalidRequestException;
import api.rest.api_rest_control_alumnos.model.Alumno;
import api.rest.api_rest_control_alumnos.repo.AlumnoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AlumnoServiceTest {

    @Mock
    private AlumnoRepo alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno alumno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alumno = new Alumno("Juan", "Pérez", "Gómez", LocalDate.of(2000, 1, 1), "Dirección 123", "123456789");
        alumno.setId(1);
    }

    @Test
    void testObtenerAlumnoPorId_Encontrado() {
        when(alumnoRepository.findById(1)).thenReturn(Optional.of(alumno));

        Alumno result = alumnoService.obtenerAlumnoPorId(1);
        assertEquals(alumno, result);
        verify(alumnoRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerAlumnoPorId_NoEncontrado() {
        when(alumnoRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            alumnoService.obtenerAlumnoPorId(1);
        });

        assertEquals("Alumno no encontrado con id: 1", thrown.getMessage());
    }

    @Test
    void testGuardarAlumno_Valido() {
        when(alumnoRepository.save(alumno)).thenReturn(alumno);
        alumno.setTelefono("6681010012");
        String result = alumnoService.guardarAlumno(alumno);
        assertEquals("Alumno registrado exitosamente.", result);
        verify(alumnoRepository, times(1)).save(alumno);
    }

    @Test
    void testGuardarAlumno_NombreNulo() {
        try {
            alumno.setNombre(null);
            alumnoService.guardarAlumno(alumno);
        } catch (NullPointerException e) {
            // Verifica que el mensaje de la excepción sea el esperado
            assertEquals("Nombre no puede ser nulo", e.getMessage());
        }
    }
    @Test
    void testGuardarAlumno_ApellidoPaterNoNulo() {
        try {
            alumno.setApellidoPaterno(null);
            alumnoService.guardarAlumno(alumno);
        } catch (NullPointerException e) {
            // Verifica que el mensaje de la excepción sea el esperado
            assertEquals("Apellido paterno no puede ser nulo", e.getMessage());
        }
    }
    @Test
    void testGuardarAlumno_ApellidoMaternoNoNulo() {
        try {
            alumno.setApellidoMaterno(null);
            alumnoService.guardarAlumno(alumno);
        } catch (NullPointerException e) {
            // Verifica que el mensaje de la excepción sea el esperado
            assertEquals("Apellido materno no puede ser nulo", e.getMessage());
        }
    }
    @Test
    void testGuardarAlumno_TelefonoNoNulo() {
        try {
            alumno.setTelefono(null);
            alumnoService.guardarAlumno(alumno);
        } catch (InvalidRequestException e) {
            // Verifica que el mensaje de la excepción sea el esperado
            assertEquals("El teléfono del alumno no puede estar vacío", e.getMessage());
        }
    }
    @Test
    void testGuardarAlumno_TelefonoValidaLongitud() {
        try {
            alumno.setTelefono("668101001212");
            alumnoService.guardarAlumno(alumno);
        } catch (InvalidRequestException e) {
            // Verifica que el mensaje de la excepción sea el esperado
            assertEquals("El número telefónico del alumno debe tener exactamente 10 caracteres", e.getMessage());
        }
    }

    @Test
    void testEliminarAlumno_Existente() {
        when(alumnoRepository.existsById(7)).thenReturn(true);

        String result = alumnoService.eliminarAlumno(7);
        assertEquals("Alumno eliminado exitosamente.", result);
        verify(alumnoRepository, times(1)).deleteById(7);
    }

    @Test
    void testEliminarAlumno_NoExistente() {
        when(alumnoRepository.existsById(1)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            alumnoService.eliminarAlumno(1);
        });

        assertEquals("Alumno no encontrado con id: 1", thrown.getMessage());
    }

    @Test
    void testGuardarAlumno_fechaNacimientoNoNull() {
        try {
            alumno.setFechaNacimiento(null);
            alumnoService.guardarAlumno(alumno);
        } catch (NullPointerException e) {
            // Verifica que el mensaje de la excepción sea el esperado
            assertEquals("Fecha de nacimiento no puede ser nulo", e.getMessage());
        }
    }

}
