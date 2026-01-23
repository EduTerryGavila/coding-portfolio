package com.miapp.flashcards.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    static class UsuarioConcreto extends Usuario {
    	
		private static final long serialVersionUID = 1L;

		public UsuarioConcreto(String nombre, String email, String password) {
            super(nombre, email, password);
        }
    }

    @Test
    void testConstructorYDatosBasicos() {
        Usuario usuario = new UsuarioConcreto("Juan", "juan@test.com", "1234");

        assertEquals("Juan", usuario.getNombre());
        assertEquals("juan@test.com", usuario.getEmail());
        assertEquals("1234", usuario.getPassword());
    }

    @Test
    void testGestionDeTiempo() {
        Usuario usuario = new UsuarioConcreto("Test", "t@t.com", "pass");
        
        assertEquals(0, usuario.getTiempoUsoSegundos());
        
        usuario.sumarTiempo(60);
        assertEquals(60, usuario.getTiempoUsoSegundos());
        
        usuario.sumarTiempo(3600);
        assertEquals(3660, usuario.getTiempoUsoSegundos());
    }

    @Test
    void testIgualdad() {
        Usuario u1 = new UsuarioConcreto("Ana", "ana@email.com", "abc");
        u1.setId(10L);
        
        Usuario u2 = new UsuarioConcreto("Ana Clon", "ana@email.com", "xyz");
        u2.setId(10L);
        
        assertEquals(u1, u2);
        
        assertEquals(u1.hashCode(), u2.hashCode());
    }
    
    @Test
    void testModificacionDatos() {
        Usuario usuario = new UsuarioConcreto("Old", "old@email.com", "oldPass");
        
        usuario.setNombre("New");
        usuario.setEmail("new@email.com");
        usuario.setPassword("newPass");
        usuario.setFechaRegistro("2026-05-05");
        
        assertEquals("New", usuario.getNombre());
        assertEquals("new@email.com", usuario.getEmail());
        assertEquals("newPass", usuario.getPassword());
        assertEquals("2026-05-05", usuario.getFechaRegistro());
    }
}