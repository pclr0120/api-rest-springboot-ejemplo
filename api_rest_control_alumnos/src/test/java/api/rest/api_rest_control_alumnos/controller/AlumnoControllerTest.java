/**
 * Autor: Pablo Cesar Leyva Ramirez
 * Fecha: 2024-08-12
 */
package api.rest.api_rest_control_alumnos.controller;

import api.rest.api_rest_control_alumnos.exception.ResourceNotFoundException;
import api.rest.api_rest_control_alumnos.model.Alumno;
import api.rest.api_rest_control_alumnos.service.AlumnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlumnoController.class)
@WithMockUser(username = "admin", roles = {"ADMIN"}) // Simular usuario con rol "ADMIN"
class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoService alumnoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Alumno alumno;
    @Test
    void testEliminarAlumnoNoExistente() throws Exception {
        int alumnoId = 1;

        // Simulamos que el servicio arroja una excepción de recurso no encontrado
        doThrow(new ResourceNotFoundException("Alumno no encontrado con id: " + alumnoId))
                .when(alumnoService).eliminarAlumno(alumnoId);

        // Realizamos la solicitud DELETE asegurándonos de que se autentica correctamente
        mockMvc.perform(delete("/api/v1/alumnos/{id}", alumnoId)
                        .with(csrf()))  // Incluye un token CSRF válido
                .andExpect(status().isNotFound());  // Verificamos que el estatus sea 404 Not Found
    }
    @BeforeEach
    void setUp() {
        alumno = new Alumno("Juan", "Pérez", "Gómez", LocalDate.of(2000, 1, 1), "Dirección 123", "123456789");
        alumno.setId(1);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetAlumnoById() throws Exception {
        when(alumnoService.obtenerAlumnoPorId(1)).thenReturn(alumno);

        mockMvc.perform(get("/api/v1/alumnos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetAllAlumnos() throws Exception {
        List<Alumno> alumnos = Arrays.asList(alumno);
        when(alumnoService.obtenerTodosAlumnos()).thenReturn(alumnos);

        mockMvc.perform(get("/api/v1/alumnos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateAlumno() throws Exception {
        when(alumnoService.guardarAlumno(any(Alumno.class))).thenReturn(String.valueOf(alumno));

        mockMvc.perform(post("/api/v1/alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumno)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan")));
    }

    @Test
    @WithMockUser(username = "admin",password = "admin123", roles = {"ADMIN"})
    void testDeleteAlumno() throws Exception {
        // Simula la eliminación exitosa
        when(alumnoService.eliminarAlumno(4)).thenReturn("Alumno eliminado exitosamente");

        mockMvc.perform(delete("/api/v1/alumnos/delete/4"))
                .andExpect(status().isNoContent());

        verify(alumnoService, times(1)).eliminarAlumno(4);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = {"ADMIN"})
    void testDeleteAlumno_2() throws Exception {
        // ID del alumno a eliminar
        final int alumnoId = 4;

        // Simula la eliminación exitosa
        when(alumnoService.eliminarAlumno(alumnoId)).thenReturn("Alumno eliminado exitosamente");

        // Realiza la solicitud DELETE
        mockMvc.perform(delete("/api/v1/alumnos/delete/{id}", alumnoId))
                .andExpect(status().isNoContent()) // Verifica que el estatus sea 204 No Content
                .andExpect(content().string(""));  // Verifica que no hay contenido en la respuesta

        verify(alumnoService, times(1)).eliminarAlumno(alumnoId);
    }
    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = {"ADMIN"})
    void testActualizarAlumno() throws Exception {
        Alumno alumnoActualizado = new Alumno("NuevoNombre", "NuevoApellidoPaterno", "NuevoApellidoMaterno", LocalDate.of(2000, 1, 1), "NuevaDireccion", "1234567890");

        // Simula la actualización exitosa
        when(alumnoService.actualizarAlumno(4, alumnoActualizado)).thenReturn("Alumno actualizado exitosamente");

        // Realiza la solicitud PUT
        mockMvc.perform(put("/api/v1/alumnos/update/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(alumnoActualizado)))
                .andExpect(status().isOk())
                .andExpect(content().string("Alumno actualizado exitosamente")); // Verifica el mensaje de éxito
         verify(alumnoService, times(1)).actualizarAlumno(4, alumnoActualizado);
    }


}
