import { format, parse, differenceInMonths } from 'date-fns';
import { initializeApp } from "firebase/app";
import { getFirestore, collection, getDocs, addDoc, deleteDoc, updateDoc, doc, query, where } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyCkY1Ysfd41xuy6xUNUpGHChFNQrr1rgBk",
  authDomain: "appvideoclases.firebaseapp.com",
  projectId: "appvideoclases",
  storageBucket: "appvideoclases.firebasestorage.app",
  messagingSenderId: "965834988246",
  appId: "1:965834988246:web:fa293b5f0e2d50bd968777"
};

const app = initializeApp(firebaseConfig);
export const db = getFirestore(app);

export const cuentaCreada = async (usuario) => {
  const q = query(collection(db, "usuarios"), where("usuario", "==", usuario));
  const querySnapshot = await getDocs(q);
  return !querySnapshot.empty;
};

export const crearCuenta = async (nombreCompleto, usuario, contraseña) => {
  const regexUsuario = /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]{1,20}$/;
  const regexPassword = /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]{9,20}$/;
  const regexNombre = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,50}$/;

  if (!nombreCompleto || nombreCompleto.trim() === "") {
    return "El nombre y apellidos son obligatorios.";
  }

  if(!regexNombre.test(nombreCompleto)){
    return "El nombre y apellidos solo debe de incluir letras (Como máximo 50).";
  }

  if (!regexUsuario.test(usuario)) {
    return "El nombre de usuario solo permite letras y números. La longitud debe de ser mínimo 1 caracter y máximo 20.";
  }

  if (!regexPassword.test(contraseña)) {
    return "La contraseña solo permite letras y números. La longitud debe de ser mínimo 9 caracteres y máximo 20.";
  }

  const existe = await cuentaCreada(usuario);
  if (existe) {
    return "El usuario ya existe.";
  }

  let fecha = new Date();
  let fechaFormateada = format(fecha, 'dd-MM-yyyy');

  await addDoc(collection(db, "usuarios"), {
    nombreCompleto: nombreCompleto,
    usuario: usuario,
    password: contraseña,
    fecha: fechaFormateada,
    esAdmin: false
  });

  return "La cuenta se ha creado con éxito.";
}

export const esUnicoAdmin = async () => {
  const q = query(collection(db, "usuarios"), where("esAdmin", "==", true));
  const querySnapshot = await getDocs(q);
  return querySnapshot.size === 1;
}

export const borrarCuenta = async (usuario, contraseña) => {
  const q = query(collection(db, "usuarios"), where("usuario", "==", usuario), where("password", "==", contraseña));
  const querySnapshot = await getDocs(q);

  if (querySnapshot.empty) {
    return "No se encontró el usuario o la contraseña es incorrecta.";
  }

  let docId = null;
  let esAdmin = false;
  
  querySnapshot.forEach((doc) => {
    docId = doc.id;
    esAdmin = doc.data().esAdmin;
  });

  if (esAdmin) {
    const unico = await esUnicoAdmin();
    if (unico) {
      return "Eres el único admin, no puedes borrar tu cuenta.";
    }
  }

  await deleteDoc(doc(db, "usuarios", docId));
  return "Exito";
}

export const iniciarSesion = async (usuario, contraseña) => {
  const dbRef = collection(db, "usuarios");
  const snapshotAll = await getDocs(dbRef);
  
  if (snapshotAll.empty && usuario === 'admin' && contraseña === '123') {
     await addDoc(dbRef, {
        nombreCompleto: 'Admin Predeterminado',
        usuario: 'admin',
        password: '123',
        fecha: format(new Date(), 'dd-MM-yyyy'),
        esAdmin: true
     });
  }

  const q = query(dbRef, where("usuario", "==", usuario), where("password", "==", contraseña));
  const querySnapshot = await getDocs(q);

  if(querySnapshot.empty){
    const qUser = query(dbRef, where("usuario", "==", usuario));
    const userSnap = await getDocs(qUser);
    if(userSnap.empty) {
        return "El usuario o la contraseña son incorrectos. Inténtelo de nuevo.";
    }
    return "La contraseña es incorrecta. Inténtelo de nuevo.";
  }

  let userData = null;
  querySnapshot.forEach((doc) => {
    userData = doc.data();
  });

  let fechaActual = new Date();
  const fechaGuardada = parse(userData.fecha, 'dd-MM-yyyy', new Date());

  if (differenceInMonths(fechaActual, fechaGuardada) >= 1 && usuario !== 'admin') {
    return "Para inciar sesión debes de renovar tu suscripción.";
  }
  
  return { success: true, esAdmin: userData.esAdmin };
}

export const obtenerUsuarios = async () => {
  const querySnapshot = await getDocs(collection(db, "usuarios"));
  let usuarios = [];
  querySnapshot.forEach((doc) => {
    usuarios.push({ id: doc.id, ...doc.data() });
  });
  return usuarios;
}

export const borrarCuentaAdmin = async (usuario) => {
  const q = query(collection(db, "usuarios"), where("usuario", "==", usuario));
  const querySnapshot = await getDocs(q);
  
  const promesas = [];
  querySnapshot.forEach((documento) => {
    promesas.push(deleteDoc(doc(db, "usuarios", documento.id)));
  });
  
  await Promise.all(promesas);
}

export const hacerAdmin = async (usuario) => {
  const q = query(collection(db, "usuarios"), where("usuario", "==", usuario));
  const querySnapshot = await getDocs(q);
  
  const promesas = [];
  querySnapshot.forEach((documento) => {
    promesas.push(updateDoc(doc(db, "usuarios", documento.id), { esAdmin: true }));
  });
  
  await Promise.all(promesas);
}

export const obtenerEnlaces = async (usuario, esAdmin) => {
  let q;
  if (esAdmin) {
    q = collection(db, "enlaces");
  } else {
    q = query(collection(db, "enlaces"), where("usuario", "==", usuario));
  }
  const querySnapshot = await getDocs(q);
  let enlaces = [];
  querySnapshot.forEach((doc) => {
    enlaces.push({ id: doc.id, ...doc.data() });
  });
  return enlaces;
}

export const anadirEnlace = async (fecha, url, usuario) => {
  await addDoc(collection(db, "enlaces"), { fecha, url, usuario });
}

export const borrarEnlace = async (id) => {
  await deleteDoc(doc(db, "enlaces", id));
}

export const obtenerTests = async (usuario, esAdmin) => {
  let q;
  if (esAdmin) {
    q = collection(db, "tests");
  } else {
    q = query(collection(db, "tests"), where("usuario", "==", usuario));
  }
  const querySnapshot = await getDocs(q);
  let tests = [];
  querySnapshot.forEach((doc) => {
    tests.push({ id: doc.id, ...doc.data() });
  });
  return tests;
}

export const anadirTest = async (nombre, nota, usuario) => {
  await addDoc(collection(db, "tests"), { nombre, nota, usuario });
}

export const borrarTest = async (id) => {
  await deleteDoc(doc(db, "tests", id));
}

export const obtenerAnuncios = async () => {
  const querySnapshot = await getDocs(collection(db, "anuncios"));
  let anuncios = [];
  querySnapshot.forEach((doc) => {
    anuncios.push({ id: doc.id, ...doc.data() });
  });
  return anuncios;
}

export const crearAnuncio = async (mensaje) => {
  let fecha = new Date();
  let fechaFormateada = format(fecha, 'dd-MM-yyyy HH:mm');
  await addDoc(collection(db, "anuncios"), {
    mensaje: mensaje,
    fecha: fechaFormateada,
    timestamp: fecha.getTime()
  });
}

export const borrarAnuncio = async (id) => {
  await deleteDoc(doc(db, "anuncios", id));
}

/* PARA EL CHAT PRIVADO */

export const enviarMensaje = async (emisor, receptor, texto) => {
  await addDoc(collection(db, "chats"), {
    emisor,
    receptor,
    texto,
    fecha: new Date().getTime(),
    fechaLegible: format(new Date(), 'dd-MM-yyyy HH:mm')
  });
};

export const obtenerMensajes = async (usuario1, usuario2) => {
  const treintaDiasEnMs = 30 * 24 * 60 * 60 * 1000;
  const limiteFecha = new Date().getTime() - treintaDiasEnMs;

  const q = query(
    collection(db, "chats"),
    where("fecha", ">", limiteFecha)
  );

  const querySnapshot = await getDocs(q);
  let mensajes = [];
  
  querySnapshot.forEach((doc) => {
    const data = doc.data();
    // Filtramos manualmente para obtener solo la conversación entre estos dos usuarios
    if ((data.emisor === usuario1 && data.receptor === usuario2) || 
        (data.emisor === usuario2 && data.receptor === usuario1)) {
      mensajes.push({ id: doc.id, ...data });
    }
  });

  return mensajes.sort((a, b) => a.fecha - b.fecha);
};