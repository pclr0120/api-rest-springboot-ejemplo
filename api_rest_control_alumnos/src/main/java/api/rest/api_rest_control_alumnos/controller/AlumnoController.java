/**
 * Autor: Pablo Cesar Leyva Ramirez
 * Fecha: 2024-08-12
 */
package api.rest.api_rest_control_alumnos.controller;

import api.rest.api_rest_control_alumnos.model.Alumno;
import api.rest.api_rest_control_alumnos.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnoService.obtenerTodosAlumnos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Integer id) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        return ResponseEntity.ok(alumno);
    }

    @PostMapping
    public ResponseEntity<String> createAlumno(@RequestBody Alumno alumno) {
        String message = alumnoService.guardarAlumno(alumno);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAlumno(@PathVariable Integer id) {
        String message = alumnoService.eliminarAlumno(id);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAlumno(@PathVariable Integer id, @RequestBody Alumno alumno) {
        // Llama al servicio para actualizar el alumno
        String mensaje = alumnoService.actualizarAlumno(id, alumno);
        return ResponseEntity.ok(mensaje);
    }
}
