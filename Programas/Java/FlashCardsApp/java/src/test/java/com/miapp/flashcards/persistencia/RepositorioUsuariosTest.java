package com.miapp.flashcards.persistencia;

import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositorioUsuariosTest {

    @Test
    void testGuardarYRecuperarUsuario() {
        RepositorioUsuarios repo = RepositorioUsuarios.getInstance();
        
        String emailTest = "test_" + System.currentTimeMillis() + "@email.com";
        Estudiante est = new Estudiante("Usuario Test", emailTest, "1234", 0);
        
        repo.guardarNuevoUsuario(est);
        
        assertTrue(repo.existeEmail(emailTest), "El email debería existir en el repositorio");
        
        Usuario recuperado = repo.buscarPorEmail(emailTest);
        assertNotNull(recuperado);
        assertEquals("Usuario Test", recuperado.getNombre());
    }

    @Test
    void testIdentificarUsuario() {
        RepositorioUsuarios repo = RepositorioUsuarios.getInstance();
        String email = "login_" + System.currentTimeMillis() + "@test.com";
        String pass = "secreto";
        
        Estudiante est = new Estudiante("Login Man", email, pass, 0);
        repo.guardarNuevoUsuario(est);
        
        Usuario u = repo.identificarUsuario(email, pass);
        assertNotNull(u, "Debería devolver el usuario con credenciales correctas");
        
        Usuario uFail = repo.identificarUsuario(email, "incorrecto");
        assertNull(uFail, "Debería devolver null con password incorrecta");
        
        Usuario uNoExiste = repo.identificarUsuario("noexiste@nada.com", "123");
        assertNull(uNoExiste, "Debería devolver null si el usuario no existe");
    }
}