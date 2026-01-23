package com.miapp.flashcards.controlador;

import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Curso;
import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Pregunta;
import com.miapp.flashcards.modelo.PreguntaAbierta;
import com.miapp.flashcards.modelo.Usuario;
import com.miapp.flashcards.persistencia.RepositorioCursos;
import com.miapp.flashcards.persistencia.RepositorioUsuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControladorPrincipalTest {

    private ControladorPrincipal controlador;

    @BeforeEach
    void setUp() {
        controlador = ControladorPrincipal.getInstance();
        controlador.cerrarSesion();
    }

    @Test
    void testSingleton() {
        ControladorPrincipal c1 = ControladorPrincipal.getInstance();
        ControladorPrincipal c2 = ControladorPrincipal.getInstance();
        assertSame(c1, c2, "Debe devolver siempre la misma instancia");
    }

    @Test
    void testRegistroEInicioSesionEstudiante() {
        String email = "est_" + System.currentTimeMillis() + "@test.com";
        String pass = "1234";

        boolean registro = controlador.registrarEstudiante("Pepe Estudiante", email, pass);
        assertTrue(registro, "El registro debería ser exitoso");

        boolean login = controlador.iniciarSesion(email, pass);
        assertTrue(login, "El login debería ser exitoso");

        Usuario actual = controlador.getUsuarioActual();
        assertNotNull(actual);
        assertTrue(actual instanceof Estudiante);
        assertEquals(email, actual.getEmail());
    }

    @Test
    void testRegistroEInicioSesionCreador() {
        String email = "cre_" + System.currentTimeMillis() + "@test.com";
        String pass = "abcd";

        boolean registro = controlador.registrarCreador("Ana Creadora", email, pass);
        assertTrue(registro);

        boolean login = controlador.iniciarSesion(email, pass);
        assertTrue(login);
        assertTrue(controlador.getUsuarioActual() instanceof Creador);
    }

    @Test
    void testRegistroDuplicadoFalla() {
        String email = "dup_" + System.currentTimeMillis() + "@test.com";
        
        controlador.registrarEstudiante("User 1", email, "pass");
        
        boolean duplicado = controlador.registrarEstudiante("User 2", email, "pass");
        assertFalse(duplicado, "No debería permitir registrar dos veces el mismo email");
    }

    @Test
    void testLoginFallido() {
        boolean resultado = controlador.iniciarSesion("email_falso@noexiste.com", "pass");
        assertFalse(resultado);
        assertNull(controlador.getUsuarioActual());
    }

    @Test
    void testCerrarSesion() {
        String email = "logout_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarEstudiante("Test Logout", email, "pass");
        controlador.iniciarSesion(email, "pass");
        
        assertNotNull(controlador.getUsuarioActual());
        
        controlador.cerrarSesion();
        
        assertNull(controlador.getUsuarioActual(), "El usuario actual debe ser null tras cerrar sesión");
    }

    @Test
    void testCrearCursoComoCreador() {
        String email = "prof_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarCreador("Profe", email, "pass");
        controlador.iniciarSesion(email, "pass");

        ArrayList<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaAbierta("P1", "R1"));
        
        boolean creado = controlador.crearCursoNuevo("Curso Java Test", preguntas);
        assertTrue(creado, "El curso debería crearse correctamente");

        List<Curso> misCursos = controlador.obtenerCursosDelUsuarioActual();
        boolean encontrado = misCursos.stream().anyMatch(c -> c.getNombre().equals("Curso Java Test"));
        assertTrue(encontrado);
    }

    @Test
    void testCrearCursoSinPermisos() {
        String email = "alum_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarEstudiante("Alumno", email, "pass");
        controlador.iniciarSesion(email, "pass");

        boolean creado = controlador.crearCursoNuevo("Curso Hackeado", new ArrayList<>());
        assertFalse(creado, "Un estudiante no debería poder crear cursos");
    }

    @Test
    void testClonarCurso() {
        String email = "clone_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarCreador("Profe Clone", email, "pass");
        controlador.iniciarSesion(email, "pass");

        Curso original = new Curso("Original", (Creador) controlador.getUsuarioActual());
        RepositorioCursos.getInstance().guardarCurso(original);

        controlador.clonarCurso(original);

        List<Curso> cursos = controlador.obtenerCursosDelUsuarioActual();
        boolean existeCopia = cursos.stream().anyMatch(c -> c.getNombre().equals("Original (Copia)"));
        assertTrue(existeCopia, "Debería existir una copia del curso");
    }

    @Test
    void testInscribirYDesinscribirEstudiante() {
        Creador creador = new Creador("Profe X", "x_" + System.currentTimeMillis() + "@t.com", "1", 0);
        creador.setId(System.currentTimeMillis());
        Curso curso = new Curso("Curso Matematicas", creador);
        RepositorioCursos.getInstance().guardarCurso(curso);

        String emailEst = "est_curso_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarEstudiante("Estudiante Curso", emailEst, "pass");
        controlador.iniciarSesion(emailEst, "pass");

        boolean inscrito = controlador.inscribirEstudianteEnCurso(curso);
        assertTrue(inscrito);

        List<Curso> misCursos = controlador.obtenerCursosDelUsuarioActual();
        assertTrue(misCursos.stream().anyMatch(c -> c.getId().equals(curso.getId())));

        boolean desinscrito = controlador.desinscribirEstudianteDeCurso(curso);
        assertTrue(desinscrito);

        misCursos = controlador.obtenerCursosDelUsuarioActual();
        assertFalse(misCursos.stream().anyMatch(c -> c.getId().equals(curso.getId())));
    }

    @Test
    void testBuscarCursos() {
        Creador c = new Creador(); 
        c.setId(99999L);
        
        Curso c1 = new Curso("Aprender Inglés", c);
        Curso c2 = new Curso("Aprender Francés", c);
        Curso c3 = new Curso("Cocina Básica", c);
        
        RepositorioCursos.getInstance().guardarCurso(c1);
        RepositorioCursos.getInstance().guardarCurso(c2);
        RepositorioCursos.getInstance().guardarCurso(c3);

        List<Curso> resultados = controlador.buscarCursosDisponibles("Aprender");
        
        assertTrue(resultados.stream().anyMatch(x -> x.getNombre().equals("Aprender Inglés")));
        assertTrue(resultados.stream().anyMatch(x -> x.getNombre().equals("Aprender Francés")));
        assertFalse(resultados.stream().anyMatch(x -> x.getNombre().equals("Cocina Básica")));
    }

    @Test
    void testCalculoRachaIncremento() {
        String email = "racha_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarEstudiante("Racha Man", email, "pass");
        
        Estudiante est = (Estudiante) RepositorioUsuarios.getInstance().buscarPorEmail(email);
        
        String ayer = LocalDate.now().minusDays(1).toString();
        est.setFechaUltimaActividad(ayer);
        est.setRachaActual(5);
        RepositorioUsuarios.getInstance().guardarCambios();

        controlador.iniciarSesion(email, "pass");
        
        Estudiante estLogueado = (Estudiante) controlador.getUsuarioActual();
        
        assertEquals(6, estLogueado.getRachaActual(), "Si entró ayer y entra hoy, la racha suma 1");
        assertEquals(LocalDate.now().toString(), estLogueado.getFechaUltimaActividad());
    }

    @Test
    void testCalculoRachaPerdida() {
        String email = "racha_fail_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarEstudiante("Racha Fail", email, "pass");
        
        Estudiante est = (Estudiante) RepositorioUsuarios.getInstance().buscarPorEmail(email);
        
        String anteayer = LocalDate.now().minusDays(2).toString();
        est.setFechaUltimaActividad(anteayer);
        est.setRachaActual(10);
        RepositorioUsuarios.getInstance().guardarCambios();

        controlador.iniciarSesion(email, "pass");
        
        Estudiante estLogueado = (Estudiante) controlador.getUsuarioActual();
        
        assertEquals(1, estLogueado.getRachaActual(), "Si faltó un día, la racha se reinicia a 1");
    }

    @Test
    void testEstadisticasCreador() {
        String email = "stat_" + System.currentTimeMillis() + "@test.com";
        controlador.registrarCreador("Stat Master", email, "pass");
        controlador.iniciarSesion(email, "pass");
        
        ArrayList<Pregunta> p1 = new ArrayList<>();
        p1.add(new PreguntaAbierta("Q1", "A1"));
        p1.add(new PreguntaAbierta("Q2", "A2"));
        controlador.crearCursoNuevo("C1", p1);
        
        ArrayList<Pregunta> p2 = new ArrayList<>();
        p2.add(new PreguntaAbierta("Q3", "A3"));
        controlador.crearCursoNuevo("C2", p2);
        
        Map<String, Number> stats = controlador.obtenerEstadisticasCreador();
        
        assertEquals(2, stats.get("cursos").intValue());
        assertEquals(3, stats.get("preguntas").intValue());
        assertEquals(1.5, stats.get("promedio").doubleValue(), 0.01);
    }
    
    @Test
    void testExportarEImportar(@TempDir Path tempDir) {
        Creador creador = new Creador();
        creador.setNombre("Creador Test");
        creador.setEmail("test@email.com");
        
        controlador.setUsuarioActual(creador); 

        Curso cursoOriginal = new Curso();
        cursoOriginal.setNombre("Curso Exportar");
        cursoOriginal.setCreador(creador);
        cursoOriginal.setPreguntas(new ArrayList<>());
        cursoOriginal.getPreguntas().add(new PreguntaAbierta("¿Test?", "Sí"));
        
        File archivo = tempDir.resolve("curso_test.json").toFile();
        
        boolean exportado = controlador.exportarCurso(cursoOriginal, archivo);
        assertTrue(exportado, "Debe devolver true al exportar");
        assertTrue(archivo.exists(), "El archivo debe crearse en disco");
        
        boolean importado = controlador.importarCurso(archivo);
        assertTrue(importado, "Debe devolver true al importar");
        
        List<Curso> todos = RepositorioCursos.getInstance().listarTodos();
        boolean existeImportado = todos.stream().anyMatch(c -> c.getNombre().equals("Curso Exportar"));
        assertTrue(existeImportado, "El curso importado debe estar en el repositorio");
    }
}