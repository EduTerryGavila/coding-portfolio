import { iniciarSesion } from './login.js';

const inputUsuario = document.getElementById('input-usuario');
const inputPassword = document.getElementById('input-password');
const checkMostrarPassword = document.getElementById('check-mostrar-password');
const btnIniciarSesion = document.getElementById('btn-iniciarSesion');
const cajaMensaje = document.getElementById('mensaje-salida');

const mostrarEnPantalla = (mensaje) => {
  cajaMensaje.textContent = mensaje;
  cajaMensaje.style.display = 'block';
};

checkMostrarPassword.addEventListener('change', () => {
  if (checkMostrarPassword.checked) {
    inputPassword.type = 'text';
  } else {
    inputPassword.type = 'password';
  }
});

btnIniciarSesion.addEventListener('click', async () => {
  const usuario = inputUsuario.value;
  const password = inputPassword.value;
  
  const resultado = await iniciarSesion(usuario, password);
  
  if (resultado && resultado.success) {
    sessionStorage.setItem('esAdmin', resultado.esAdmin);
    sessionStorage.setItem('nombreUsuario', inputUsuario.value);
    window.location.href = 'menu.html';
  } else {
    mostrarEnPantalla(resultado);
  }
});