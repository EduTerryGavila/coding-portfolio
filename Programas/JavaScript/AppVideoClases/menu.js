import { obtenerUsuarios, borrarCuentaAdmin, hacerAdmin, borrarCuenta, crearCuenta, obtenerEnlaces, anadirEnlace, borrarEnlace, obtenerTests, anadirTest, borrarTest, obtenerAnuncios, crearAnuncio, borrarAnuncio, enviarMensaje, obtenerMensajes } from './login.js';

if (sessionStorage.getItem('esAdmin') === null) {
  window.location.replace('index.html');
}

const esAdmin = sessionStorage.getItem('esAdmin') === 'true';
const nombreGuardado = sessionStorage.getItem('nombreUsuario');
const elementoSaludo = document.getElementById('saludo-usuario');

if (elementoSaludo && nombreGuardado) {
    elementoSaludo.textContent = `¡BIENVENID@, ${nombreGuardado.toUpperCase()}!`;
}

const adminControls = document.getElementById('admin-controls');
const selectUsuarioEnlace = document.getElementById('select-usuario-enlace');
const selectUsuarioTest = document.getElementById('select-usuario-test');
const thAlumnoTabla = document.getElementById('th-alumno-tabla');
const thAlumnoTest = document.getElementById('th-alumno-test');
const adminControlsTest = document.getElementById('admin-controls-test');
const btnVerMatriculados = document.getElementById('btn-ver-matriculados');
const listaContenedor = document.getElementById('lista-matriculados');
const errorEliminarPropia = document.getElementById('error-eliminar-propia');
const btnEliminarCuenta = document.getElementById('btn-eliminar-cuenta');
const listaEliminarContenedor = document.getElementById('lista-eliminar-cuenta');

const btnCrearCuentaAdmin = document.getElementById('btn-crear-cuenta-admin');
const panelCrearCuenta = document.getElementById('panel-crear-cuenta');
const inputNuevoNombre = document.getElementById('input-nuevo-nombre');
const inputNuevoUsuario = document.getElementById('input-nuevo-usuario');
const inputNuevaPassword = document.getElementById('input-nueva-password');
const inputNuevaPasswordConfirm = document.getElementById('input-nueva-password-confirm');
const errorCrearCuenta = document.getElementById('error-crear-cuenta');
const btnConfirmarCrear = document.getElementById('btn-confirmar-crear');
const btnCancelarCrear = document.getElementById('btn-cancelar-crear');

const btnHacerAdmin = document.getElementById('btn-hacer-admin');
const listaHacerAdminContenedor = document.getElementById('lista-hacer-admin');

const btnEliminarPropiaCuenta = document.getElementById('btn-eliminar-propia-cuenta');
const confirmacionEliminarPropia = document.getElementById('confirmacion-eliminar-propia');
const inputEliminarUsuario = document.getElementById('input-eliminar-usuario');
const inputEliminarPassword = document.getElementById('input-eliminar-password');
const btnConfirmarEliminar = document.getElementById('btn-confirmar-eliminar');
const btnCancelarEliminar = document.getElementById('btn-cancelar-eliminar');

const panelConfirmacion = document.getElementById('confirmacion-accion');
const tituloConfirmacion = document.getElementById('titulo-confirmacion');
const textoConfirmacion = document.getElementById('texto-confirmacion');
const btnConfirmarAccion = document.getElementById('btn-confirmar-accion');
const btnCancelarAccion = document.getElementById('btn-cancelar-accion');

const btnTabla = document.getElementById('btn-tabla');
const seccionTabla = document.getElementById('seccion-tabla');
const cuerpoTabla = document.getElementById('cuerpo-tabla');
const inputFecha = document.getElementById('input-fecha');
const inputUrl = document.getElementById('input-url');
const btnAnadir = document.getElementById('btn-anadir');
const thAccionesTabla = document.getElementById('th-acciones-tabla');
const adminControlsAnuncios = document.getElementById('admin-controls-anuncios');
const inputNuevoAnuncio = document.getElementById('input-nuevo-anuncio');
const btnAnadirAnuncio = document.getElementById('btn-anadir-anuncio');
const listaAnuncios = document.getElementById('lista-anuncios');
const btnTest = document.getElementById('btn-test');
const seccionTest = document.getElementById('seccion-test');
const cuerpoTablaTest = document.getElementById('cuerpo-tabla-test');
const inputNombreTest = document.getElementById('input-nombre-test');
const inputNotaTest = document.getElementById('input-nota-test');
const btnAnadirTest = document.getElementById('btn-anadir-test');
const thAccionesTest = document.getElementById('th-acciones-test');

const seccionDudas = document.getElementById('seccion-dudas');
const inputDuda = document.getElementById('input-duda');
const btnEnviarDuda = document.getElementById('btn-enviar-duda');
const mensajeDudaEstado = document.getElementById('mensaje-duda-estado');

const btnCerrarSesion = document.getElementById('btn-cerrarSesion');

const btnAbrirChat = document.getElementById('btn-abrir-chat');
const seccionChat = document.getElementById('seccion-chat');
const cajaMensajes = document.getElementById('caja-mensajes');
const inputTextoChat = document.getElementById('input-texto-chat');
const btnEnviarChat = document.getElementById('btn-enviar-chat');
const selectUsuarioChat = document.getElementById('select-usuario-chat');
const adminSelectorChat = document.getElementById('admin-selector-chat');

let usuarioChatActivo = esAdmin ? "" : "admin"; 

let accionPendiente = null;
let usuarioObjetivo = null;

if (esAdmin) {
  if (adminControls) adminControls.classList.remove('oculto');
  if (adminControlsTest) adminControlsTest.classList.remove('oculto');
  if (btnVerMatriculados) btnVerMatriculados.classList.remove('oculto');
  if (btnEliminarCuenta) btnEliminarCuenta.classList.remove('oculto');
  if (btnCrearCuentaAdmin) btnCrearCuentaAdmin.classList.remove('oculto');
  if (btnHacerAdmin) btnHacerAdmin.classList.remove('oculto');
  if (btnEliminarPropiaCuenta) btnEliminarPropiaCuenta.classList.remove('oculto');
  if (thAccionesTabla) thAccionesTabla.classList.remove('oculto');
  if (thAccionesTest) thAccionesTest.classList.remove('oculto');
  if (adminControlsAnuncios) adminControlsAnuncios.classList.remove('oculto');
  if (thAlumnoTabla) thAlumnoTabla.classList.remove('oculto');
  if (thAlumnoTest) thAlumnoTest.classList.remove('oculto');

  obtenerUsuarios().then(usuarios => {
    let optionsHTML = '<option value="">-- Selecciona alumno --</option>';
    usuarios.forEach(u => {
      if (!u.esAdmin) {
        optionsHTML += `<option value="${u.usuario}">${u.nombreCompleto || u.usuario} (${u.usuario})</option>`;
      }
    });
    if (selectUsuarioEnlace) selectUsuarioEnlace.innerHTML = optionsHTML;
    if (selectUsuarioTest) selectUsuarioTest.innerHTML = optionsHTML;
  });
} else {
  if (seccionDudas) seccionDudas.classList.remove('oculto');
}

if (btnCrearCuentaAdmin) {
  btnCrearCuentaAdmin.addEventListener('click', () => {
    if (!panelCrearCuenta.classList.contains('oculto')) {
      panelCrearCuenta.classList.add('oculto');
      return;
    }
    listaContenedor.classList.add('oculto');
    listaEliminarContenedor.classList.add('oculto');
    listaHacerAdminContenedor.classList.add('oculto');
    panelConfirmacion.classList.add('oculto');
    confirmacionEliminarPropia.classList.add('oculto');

    inputNuevoNombre.value = '';
    inputNuevoUsuario.value = '';
    inputNuevaPassword.value = '';
    inputNuevaPasswordConfirm.value = '';
    errorCrearCuenta.classList.add('oculto');

    panelCrearCuenta.classList.remove('oculto');
  });
}

if (btnConfirmarCrear) {
  btnConfirmarCrear.addEventListener('click', async () => {
    const nombre = inputNuevoNombre.value;
    const usuario = inputNuevoUsuario.value;
    const password = inputNuevaPassword.value;
    const passwordConfirm = inputNuevaPasswordConfirm.value;

    if (password !== passwordConfirm) {
      errorCrearCuenta.style.color = "#e11d48";
      errorCrearCuenta.style.backgroundColor = "#fff1f2";
      errorCrearCuenta.style.borderColor = "#ffe4e6";
      errorCrearCuenta.textContent = "Las contraseñas no coinciden.";
      errorCrearCuenta.classList.remove('oculto');
      return;
    }

    const res = await crearCuenta(nombre, usuario, password);
    if (res === "La cuenta se ha creado con éxito.") {
      errorCrearCuenta.style.color = "#10b981"; 
      errorCrearCuenta.style.backgroundColor = "#d1fae5";
      errorCrearCuenta.style.borderColor = "#a7f3d0";
      errorCrearCuenta.textContent = "¡Alumno creado correctamente!";
      errorCrearCuenta.classList.remove('oculto');
      
      inputNuevoNombre.value = '';
      inputNuevoUsuario.value = '';
      inputNuevaPassword.value = '';
      inputNuevaPasswordConfirm.value = '';

      setTimeout(() => {
        panelCrearCuenta.classList.add('oculto');
        errorCrearCuenta.style.color = "#e11d48";
        errorCrearCuenta.style.backgroundColor = "#fff1f2";
        errorCrearCuenta.style.borderColor = "#ffe4e6";
        errorCrearCuenta.classList.add('oculto');
      }, 2000);
    } else {
      errorCrearCuenta.style.color = "#e11d48";
      errorCrearCuenta.style.backgroundColor = "#fff1f2";
      errorCrearCuenta.style.borderColor = "#ffe4e6";
      errorCrearCuenta.textContent = res;
      errorCrearCuenta.classList.remove('oculto');
    }
  });
}

if (btnCancelarCrear) {
  btnCancelarCrear.addEventListener('click', () => {
    panelCrearCuenta.classList.add('oculto');
  });
}

if (btnEliminarPropiaCuenta) {
  btnEliminarPropiaCuenta.addEventListener('click', () => {
    if (!confirmacionEliminarPropia.classList.contains('oculto')) {
      confirmacionEliminarPropia.classList.add('oculto');
      return;
    }
    listaContenedor.classList.add('oculto');
    listaEliminarContenedor.classList.add('oculto');
    listaHacerAdminContenedor.classList.add('oculto');
    panelConfirmacion.classList.add('oculto');
    panelCrearCuenta.classList.add('oculto');
    
    errorEliminarPropia.classList.add('oculto');
    inputEliminarUsuario.value = '';
    inputEliminarPassword.value = '';

    confirmacionEliminarPropia.classList.remove('oculto');
  });
}

if (btnConfirmarEliminar) {
  btnConfirmarEliminar.addEventListener('click', async () => {
    const usuario = inputEliminarUsuario.value;
    const password = inputEliminarPassword.value;
    
    if (usuario && password) {
      const res = await borrarCuenta(usuario, password);
      if (res === "Exito") {
        sessionStorage.removeItem('esAdmin');
        window.location.href = 'index.html';
      } else {
        errorEliminarPropia.textContent = res;
        errorEliminarPropia.classList.remove('oculto');
      }
    } else {
      errorEliminarPropia.textContent = "Por favor, introduce tu usuario y contraseña.";
      errorEliminarPropia.classList.remove('oculto');
    }
  });
}

if (btnCancelarEliminar) {
  btnCancelarEliminar.addEventListener('click', () => {
    confirmacionEliminarPropia.classList.add('oculto');
    inputEliminarUsuario.value = '';
    inputEliminarPassword.value = '';
    errorEliminarPropia.classList.add('oculto');
  });
}

if (btnConfirmarAccion) {
  btnConfirmarAccion.addEventListener('click', async () => {
    if (accionPendiente === 'eliminar') {
      await borrarCuentaAdmin(usuarioObjetivo);
      await renderizarListaEliminar();
      if (!listaHacerAdminContenedor.classList.contains('oculto')) await renderizarListaHacerAdmin();
      if (!listaContenedor.classList.contains('oculto')) await renderizarMatriculados();
      listaEliminarContenedor.classList.remove('oculto');
    } else if (accionPendiente === 'admin') {
      await hacerAdmin(usuarioObjetivo);
      await renderizarListaHacerAdmin();
      listaHacerAdminContenedor.classList.remove('oculto');
    }
    
    panelConfirmacion.classList.add('oculto');
    accionPendiente = null;
    usuarioObjetivo = null;
  });
}

if (btnCancelarAccion) {
  btnCancelarAccion.addEventListener('click', () => {
    panelConfirmacion.classList.add('oculto');
    
    if (accionPendiente === 'eliminar') {
      listaEliminarContenedor.classList.remove('oculto');
    } else if (accionPendiente === 'admin') {
      listaHacerAdminContenedor.classList.remove('oculto');
    }
    
    accionPendiente = null;
    usuarioObjetivo = null;
  });
}

const renderizarMatriculados = async () => {
  listaContenedor.innerHTML = '<h4 style="margin-top:0; color:#17a2b8;">Cargando...</h4>';
  const usuariosDb = await obtenerUsuarios();
  
  listaContenedor.innerHTML = '<h4 style="margin-top:0; color:#17a2b8;">Alumnos Matriculados</h4>';
  
  usuariosDb.forEach(user => {
    const nombre = user.nombreCompleto ? user.nombreCompleto : user.usuario;
    const div = document.createElement('div');
    div.className = 'item-matriculado';
    div.textContent = nombre;
    listaContenedor.appendChild(div);
  });
};

const renderizarListaEliminar = async () => {
  listaEliminarContenedor.innerHTML = '<h4 style="margin-top:0; color:#f43f5e;">Cargando...</h4>';
  const usuariosDb = await obtenerUsuarios();

  listaEliminarContenedor.innerHTML = '<h4 style="margin-top:0; color:#f43f5e;">Eliminar Usuarios</h4>';
  
  usuariosDb.forEach(user => {
    if (user.usuario !== 'admin') {
      const nombre = user.nombreCompleto ? user.nombreCompleto : user.usuario;
      const div = document.createElement('div');
      div.className = 'item-matriculado';
      div.textContent = `${nombre} (${user.usuario})`;

      const btnEliminar = document.createElement('button');
      btnEliminar.textContent = 'Eliminar';
      btnEliminar.className = 'btn-eliminar-item';
      
      btnEliminar.addEventListener('click', () => {
        accionPendiente = 'eliminar';
        usuarioObjetivo = user.usuario;
        tituloConfirmacion.textContent = 'Confirmar eliminación';
        tituloConfirmacion.style.color = '#f43f5e';
        textoConfirmacion.textContent = `¿Estás seguro de que quieres eliminar a ${nombre}?`;
        btnConfirmarAccion.style.backgroundColor = '#f43f5e';
        btnConfirmarAccion.textContent = 'Eliminar';
        
        listaEliminarContenedor.classList.add('oculto');
        panelConfirmacion.classList.remove('oculto');
      });

      div.appendChild(btnEliminar);
      listaEliminarContenedor.appendChild(div);
    }
  });
};

const renderizarListaHacerAdmin = async () => {
  listaHacerAdminContenedor.innerHTML = '<h4 style="margin-top:0; color:#10b981;">Cargando...</h4>';
  const usuariosDb = await obtenerUsuarios();

  listaHacerAdminContenedor.innerHTML = '<h4 style="margin-top:0; color:#10b981;">Añadir Administrador</h4>';
  
  usuariosDb.forEach(user => {
    if (!user.esAdmin) {
      const nombre = user.nombreCompleto ? user.nombreCompleto : user.usuario;
      const div = document.createElement('div');
      div.className = 'item-matriculado';
      div.textContent = `${nombre} (${user.usuario})`;

      const btnHacer = document.createElement('button');
      btnHacer.textContent = 'Hacer Admin';
      btnHacer.className = 'btn-hacer-admin-item';
      
      btnHacer.addEventListener('click', () => {
        accionPendiente = 'admin';
        usuarioObjetivo = user.usuario;
        tituloConfirmacion.textContent = 'Confirmar administrador';
        tituloConfirmacion.style.color = '#10b981';
        textoConfirmacion.textContent = `¿Estás seguro de que quieres hacer administrador a ${nombre}?`;
        btnConfirmarAccion.style.backgroundColor = '#10b981';
        btnConfirmarAccion.textContent = 'Hacer Admin';
        
        listaHacerAdminContenedor.classList.add('oculto');
        panelConfirmacion.classList.remove('oculto');
      });

      div.appendChild(btnHacer);
      listaHacerAdminContenedor.appendChild(div);
    }
  });
};

if (btnVerMatriculados) {
  btnVerMatriculados.addEventListener('click', async () => {
    if (!listaContenedor.classList.contains('oculto')) {
      listaContenedor.classList.add('oculto');
      return;
    }
    confirmacionEliminarPropia.classList.add('oculto');
    listaEliminarContenedor.classList.add('oculto');
    listaHacerAdminContenedor.classList.add('oculto');
    panelConfirmacion.classList.add('oculto');
    panelCrearCuenta.classList.add('oculto');
    
    listaContenedor.classList.remove('oculto');
    await renderizarMatriculados();
  });
}

if (btnEliminarCuenta) {
  btnEliminarCuenta.addEventListener('click', async () => {
    if (!listaEliminarContenedor.classList.contains('oculto')) {
      listaEliminarContenedor.classList.add('oculto');
      return;
    }
    confirmacionEliminarPropia.classList.add('oculto');
    listaContenedor.classList.add('oculto');
    listaHacerAdminContenedor.classList.add('oculto');
    panelConfirmacion.classList.add('oculto');
    panelCrearCuenta.classList.add('oculto');
    
    listaEliminarContenedor.classList.remove('oculto');
    await renderizarListaEliminar();
  });
}

if (btnHacerAdmin) {
  btnHacerAdmin.addEventListener('click', async () => {
    if (!listaHacerAdminContenedor.classList.contains('oculto')) {
      listaHacerAdminContenedor.classList.add('oculto');
      return;
    }
    confirmacionEliminarPropia.classList.add('oculto');
    listaContenedor.classList.add('oculto');
    listaEliminarContenedor.classList.add('oculto');
    panelConfirmacion.classList.add('oculto');
    panelCrearCuenta.classList.add('oculto');
    
    listaHacerAdminContenedor.classList.remove('oculto');
    await renderizarListaHacerAdmin();
  });
}

const renderizarAnuncios = async () => {
  if (!listaAnuncios) return;
  listaAnuncios.innerHTML = '<p style="text-align: center; color: #b45309; font-weight: bold;">Cargando anuncios...</p>';
  
  const anunciosDb = await obtenerAnuncios();
  anunciosDb.sort((a, b) => b.timestamp - a.timestamp);
  
  listaAnuncios.innerHTML = '';
  
  if (anunciosDb.length === 0) {
    listaAnuncios.innerHTML = '<p style="text-align: center; color: #92400e; font-style: italic;">No hay anuncios en este momento.</p>';
    return;
  }

  anunciosDb.forEach(anuncio => {
    const div = document.createElement('div');
    div.style.backgroundColor = 'white';
    div.style.padding = '15px';
    div.style.borderRadius = '12px';
    div.style.marginBottom = '10px';
    div.style.border = '1px solid #fde68a';
    div.style.boxShadow = '0 2px 4px rgba(245, 158, 11, 0.05)';
    div.style.display = 'flex';
    div.style.flexDirection = 'column';
    div.style.gap = '5px';

    const header = document.createElement('div');
    header.style.display = 'flex';
    header.style.justifyContent = 'space-between';
    header.style.alignItems = 'flex-start';

    const fechaP = document.createElement('small');
    fechaP.textContent = anuncio.fecha;
    fechaP.style.color = '#92400e';
    fechaP.style.fontWeight = 'bold';

    const textoP = document.createElement('p');
    textoP.textContent = anuncio.mensaje;
    textoP.style.margin = '5px 0 0 0';
    textoP.style.color = '#334155';
    textoP.style.fontSize = '15px';

    header.appendChild(fechaP);

    if (esAdmin) {
      const btnBorrar = document.createElement('button');
      btnBorrar.textContent = '✖';
      btnBorrar.style.backgroundColor = 'transparent';
      btnBorrar.style.color = '#ef4444';
      btnBorrar.style.border = 'none';
      btnBorrar.style.cursor = 'pointer';
      btnBorrar.style.padding = '0 5px';
      btnBorrar.style.fontSize = '16px';
      btnBorrar.style.boxShadow = 'none';
      btnBorrar.style.minWidth = 'auto';
      
      btnBorrar.addEventListener('click', async () => {
        await borrarAnuncio(anuncio.id);
        await renderizarAnuncios();
      });
      header.appendChild(btnBorrar);
    }

    div.appendChild(header);
    div.appendChild(textoP);
    listaAnuncios.appendChild(div);
  });
}

if (btnAnadirAnuncio) {
  btnAnadirAnuncio.addEventListener('click', async () => {
    const mensaje = inputNuevoAnuncio.value.trim();
    if (mensaje) {
      btnAnadirAnuncio.textContent = '...';
      btnAnadirAnuncio.disabled = true;
      await crearAnuncio(mensaje);
      await renderizarAnuncios();
      inputNuevoAnuncio.value = '';
      btnAnadirAnuncio.textContent = 'Publicar';
      btnAnadirAnuncio.disabled = false;
    }
  });
}

renderizarAnuncios();

const renderizarTabla = async () => {
  if (!cuerpoTabla) return;
  
  const colSpan = esAdmin ? 4 : 2;
  cuerpoTabla.innerHTML = `<tr><td colspan="${colSpan}" style="text-align:center; color:#6366f1; font-weight:bold;">Cargando información...</td></tr>`;
  
  const datosEnNube = await obtenerEnlaces(nombreGuardado, esAdmin);
  
  cuerpoTabla.innerHTML = '';
  
  if (datosEnNube.length === 0) {
    cuerpoTabla.innerHTML = `<tr><td colspan="${colSpan}" style="text-align:center; color:#94a3b8;">No hay enlaces registrados aún.</td></tr>`;
    return;
  }

  datosEnNube.forEach((enlace) => {
    const tr = document.createElement('tr');
    
    if (esAdmin) {
      const tdAlumno = document.createElement('td');
      tdAlumno.textContent = enlace.usuario || 'Antiguo/Sin asignar';
      tdAlumno.style.fontWeight = 'bold';
      tdAlumno.style.color = '#0ea5e9';
      tr.appendChild(tdAlumno);
    }

    const tdFecha = document.createElement('td');
    tdFecha.textContent = enlace.fecha;
    
    const tdUrl = document.createElement('td');
    const etiquetaEnlace = document.createElement('a');
    etiquetaEnlace.href = enlace.url;
    etiquetaEnlace.textContent = enlace.url;
    etiquetaEnlace.target = "_blank"; 
    
    tdUrl.appendChild(etiquetaEnlace);
    
    tr.appendChild(tdFecha);
    tr.appendChild(tdUrl);

    if (esAdmin) {
      const tdAccion = document.createElement('td');
      const btnEliminar = document.createElement('button');
      btnEliminar.textContent = 'Eliminar';
      btnEliminar.className = 'btn-eliminar-item';
      btnEliminar.style.margin = '0';
      
      btnEliminar.addEventListener('click', async () => {
        cuerpoTabla.innerHTML = `<tr><td colspan="${colSpan}" style="text-align:center; color:#f43f5e; font-weight:bold;">Borrando...</td></tr>`;
        await borrarEnlace(enlace.id);
        await renderizarTabla();
      });

      tdAccion.appendChild(btnEliminar);
      tr.appendChild(tdAccion);
    }
    
    cuerpoTabla.appendChild(tr);
  });
}

const renderizarTablaTest = async () => {
  if (!cuerpoTablaTest) return;
  
  const colSpan = esAdmin ? 4 : 2;
  cuerpoTablaTest.innerHTML = `<tr><td colspan="${colSpan}" style="text-align:center; color:#6366f1; font-weight:bold;">Cargando información...</td></tr>`;
  
  const datosEnNube = await obtenerTests(nombreGuardado, esAdmin);
  
  cuerpoTablaTest.innerHTML = '';

  if (datosEnNube.length === 0) {
    cuerpoTablaTest.innerHTML = `<tr><td colspan="${colSpan}" style="text-align:center; color:#94a3b8;">No hay tests registrados aún.</td></tr>`;
    return;
  }

  datosEnNube.forEach((test) => {
    const tr = document.createElement('tr');
    
    if (esAdmin) {
      const tdAlumno = document.createElement('td');
      tdAlumno.textContent = test.usuario || 'Antiguo/Sin asignar';
      tdAlumno.style.fontWeight = 'bold';
      tdAlumno.style.color = '#0ea5e9';
      tr.appendChild(tdAlumno);
    }

    const tdNombre = document.createElement('td');
    const etiquetaEnlace = document.createElement('a');
    etiquetaEnlace.href = test.nombre;
    etiquetaEnlace.textContent = "Ir al Test";
    etiquetaEnlace.target = "_blank";
    tdNombre.appendChild(etiquetaEnlace);
    
    const tdNota = document.createElement('td');
    tdNota.textContent = test.nota;
    
    tr.appendChild(tdNombre);
    tr.appendChild(tdNota);

    if (esAdmin) {
      const tdAccion = document.createElement('td');
      const btnEliminar = document.createElement('button');
      btnEliminar.textContent = 'Eliminar';
      btnEliminar.className = 'btn-eliminar-item';
      btnEliminar.style.margin = '0';
      
      btnEliminar.addEventListener('click', async () => {
        cuerpoTablaTest.innerHTML = `<tr><td colspan="${colSpan}" style="text-align:center; color:#f43f5e; font-weight:bold;">Borrando...</td></tr>`;
        await borrarTest(test.id);
        await renderizarTablaTest();
      });

      tdAccion.appendChild(btnEliminar);
      tr.appendChild(tdAccion);
    }
    
    cuerpoTablaTest.appendChild(tr);
  });
}

if (btnTabla) {
  btnTabla.addEventListener('click', async () => {
    if (seccionTabla) {
      seccionTabla.classList.toggle('oculto');
      if (!seccionTabla.classList.contains('oculto')) {
        seccionTest.classList.add('oculto');
      }
      
      await renderizarTabla();
    }
  });
}

if (btnTest) {
  btnTest.addEventListener('click', async () => {
    if (seccionTest) {
      seccionTest.classList.toggle('oculto');
      if (!seccionTest.classList.contains('oculto')) {
        seccionTabla.classList.add('oculto');
      }
      
      await renderizarTablaTest();
    }
  });
}

if (btnAnadir) {
  btnAnadir.addEventListener('click', async () => {
    const fecha = inputFecha.value;
    const url = inputUrl.value;
    const usuarioDestino = selectUsuarioEnlace ? selectUsuarioEnlace.value : '';
    
    if (!usuarioDestino) {
      alert("⚠️ Por favor, selecciona un alumno de la lista antes de asignar el enlace.");
      return;
    }

    if (fecha && url) {
      btnAnadir.textContent = '...';
      await anadirEnlace(fecha, url, usuarioDestino);
      await renderizarTabla();
      inputFecha.value = '';
      inputUrl.value = '';
      btnAnadir.textContent = 'Asignar';
    }
  });
}

if (btnAnadirTest) {
  btnAnadirTest.addEventListener('click', async () => {
    const nombre = inputNombreTest.value;
    const nota = inputNotaTest.value;
    const usuarioDestino = selectUsuarioTest ? selectUsuarioTest.value : '';
    
    if (!usuarioDestino) {
      alert("⚠️ Por favor, selecciona un alumno de la lista antes de asignar el test.");
      return;
    }

    if (nombre && nota) {
      btnAnadirTest.textContent = '...';
      await anadirTest(nombre, nota, usuarioDestino);
      await renderizarTablaTest();
      inputNombreTest.value = '';
      inputNotaTest.value = '';
      btnAnadirTest.textContent = 'Asignar';
    }
  });
}

if (btnEnviarDuda) {
  btnEnviarDuda.addEventListener('click', async () => {
    const duda = inputDuda.value.trim();
    if (!duda) return;

    mensajeDudaEstado.textContent = "Enviando mensaje...";
    mensajeDudaEstado.style.color = "#3b82f6";
    mensajeDudaEstado.classList.remove('oculto');
    btnEnviarDuda.disabled = true;

    try {
      const respuesta = await fetch("https://formsubmit.co/ajax/eduardo.terry.8@gmail.com", {
        method: "POST",
        headers: { 
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            Alumno: nombreGuardado || "Alumno",
            Mensaje: duda,
            _subject: "AppVideoClases: Nueva duda de " + (nombreGuardado || "un alumno")
        })
      });

      if (respuesta.ok) {
        mensajeDudaEstado.textContent = "¡Duda enviada correctamente a la profesora!";
        mensajeDudaEstado.style.color = "#10b981";
        inputDuda.value = '';
      } else {
        throw new Error("Error");
      }
    } catch (error) {
      mensajeDudaEstado.textContent = "Hubo un error al enviar. Inténtalo más tarde.";
      mensajeDudaEstado.style.color = "#e11d48";
    } finally {
      btnEnviarDuda.disabled = false;
      setTimeout(() => {
        mensajeDudaEstado.classList.add('oculto');
      }, 5000);
    }
  });
}

renderizarTabla();
renderizarTablaTest();

if (btnCerrarSesion) {
  btnCerrarSesion.addEventListener('click', () => {
    sessionStorage.removeItem('esAdmin');
    window.location.href = 'index.html';
  });
}

if (esAdmin) {
    adminSelectorChat.classList.remove('oculto');
    obtenerUsuarios().then(usuarios => {
        let optionsHTML = '<option value="">-- Selecciona alumno --</option>';
        usuarios.forEach(u => { if (!u.esAdmin) optionsHTML += `<option value="${u.usuario}">${u.nombreCompleto || u.usuario}</option>`; });
        selectUsuarioChat.innerHTML = optionsHTML;
    });
}

const cargarChat = async () => {
    if (!usuarioChatActivo) return;
    const mensajes = await obtenerMensajes(nombreGuardado, usuarioChatActivo);
    cajaMensajes.innerHTML = "";
    mensajes.forEach(m => {
        const div = document.createElement('div');
        const soyYo = m.emisor === nombreGuardado;
        div.style.alignSelf = soyYo ? 'flex-end' : 'flex-start';
        div.style.backgroundColor = soyYo ? '#ddd6fe' : '#f3f4f6';
        div.style.padding = '8px 12px';
        div.style.borderRadius = '12px';
        div.style.maxWidth = '80%';
        div.innerHTML = `<small style="display:block; font-size:10px; color:#666;">${m.fechaLegible}</small>${m.texto}`;
        cajaMensajes.appendChild(div);
    });
    cajaMensajes.scrollTop = cajaMensajes.scrollHeight;
};

btnAbrirChat.addEventListener('click', () => {
    seccionChat.classList.toggle('oculto');
    if (!seccionChat.classList.contains('oculto')) cargarChat();
});

selectUsuarioChat?.addEventListener('change', (e) => {
    usuarioChatActivo = e.target.value;
    cargarChat();
});

btnEnviarChat.addEventListener('click', async () => {
    const texto = inputTextoChat.value.trim();
    if (texto && usuarioChatActivo) {
        await enviarMensaje(nombreGuardado, usuarioChatActivo, texto);
        inputTextoChat.value = "";
        cargarChat();
    } else if (!usuarioChatActivo) {
        alert("Selecciona un alumno primero");
    }
});