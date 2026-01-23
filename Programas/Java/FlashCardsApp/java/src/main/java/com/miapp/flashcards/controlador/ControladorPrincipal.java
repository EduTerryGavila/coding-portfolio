package com.miapp.flashcards.controlador;

import com.miapp.flashcards.estrategiafactExpImp.EstrategiaExpImp;
import com.miapp.flashcards.estrategiafactExpImp.FactoriaExpImp;
import com.miapp.flashcards.estrategias.Estrategia;
import com.miapp.flashcards.modelo.Creador;
import com.miapp.flashcards.modelo.Curso;
import com.miapp.flashcards.modelo.Estudiante;
import com.miapp.flashcards.modelo.Pregunta;
import com.miapp.flashcards.modelo.Usuario;
import com.miapp.flashcards.persistencia.RepositorioCursos;
import com.miapp.flashcards.persistencia.RepositorioUsuarios;
import com.miapp.flashcards.vista.ControladorCrearCurso;
import com.miapp.flashcards.vista.ControladorCursar;
import com.miapp.flashcards.vista.ControladorLoginYRegistro;
import com.miapp.flashcards.vista.ControladorMenuPrincipal;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set; 
import java.util.stream.Collectors;

public class ControladorPrincipal {

    // ====================================
    //        SINGLETON Y ATRIBUTOS
    // ====================================
    private static ControladorPrincipal instance;
    private Usuario usuarioActual;
    private Stage primaryStage;

    private ControladorPrincipal() {
    }

    public static ControladorPrincipal getInstance() {
        if (instance == null) {
            instance = new ControladorPrincipal();
        }
        return instance;
    }
    
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    // ====================================
    //        GESTIÓN DE SESIÓN
    // ====================================

    private long inicioSesion; 
    
    public boolean iniciarSesion(String email, String password) {
        Usuario usuario = RepositorioUsuarios.getInstance().identificarUsuario(email, password);
        
        if (usuario != null) {
            this.usuarioActual = usuario;
            
            this.inicioSesion = System.currentTimeMillis(); 
            
            if (usuario instanceof Estudiante) {
                actualizarRacha((Estudiante) usuario);
            }
            
            return true;
        }
        return false;
    }
    
    //Actualiza los dias consecutivos estudiando para la racha y guarda la fecha de hoy
    private void actualizarRacha(Estudiante est) {
        String hoy = LocalDate.now().toString();
        String ultima = est.getFechaUltimaActividad();

        if (ultima == null || ultima.isEmpty()) {
            est.setRachaActual(1);
        } else if (ultima.equals(hoy)) {
        } else {
            LocalDate fechaUltima = LocalDate.parse(ultima);
            LocalDate fechaHoy = LocalDate.now();
            
            if (fechaUltima.plusDays(1).equals(fechaHoy)) {
                est.setRachaActual(est.getRachaActual() + 1);
            } else {
                est.setRachaActual(1);
            }
        }
        
        if (est.getRachaActual() > est.getMejorRacha()) {
            est.setMejorRacha(est.getRachaActual());
        }
        est.setFechaUltimaActividad(hoy);
        
        RepositorioUsuarios.getInstance().guardarCambios();
    }

    //Cierra sesion guardando el tiempo para las estadisticas
    public void cerrarSesion() {
        if (usuarioActual != null) {
            long finSesion = System.currentTimeMillis();
            long diferenciaMilis = finSesion - inicioSesion;
            long segundosSesion = diferenciaMilis / 1000;
            
            usuarioActual.sumarTiempo(segundosSesion);
            
            RepositorioUsuarios.getInstance().guardarCambios();
            
            this.usuarioActual = null;
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // ====================================
    //        LÓGICA DE REGISTRO
    // ====================================

    public boolean registrarEstudiante(String nombre, String email, String password) {
        if (RepositorioUsuarios.getInstance().existeEmail(email)) {
            return false;
        }

        RepositorioUsuarios.getInstance().crearEstudiante(nombre, email, password);
        
        return true;
    }

    public boolean registrarCreador(String nombre, String email, String password) {
        if (RepositorioUsuarios.getInstance().existeEmail(email)) {
            return false;
        }

        RepositorioUsuarios.getInstance().crearCreador(nombre, email, password);
        
        return true;
    }

    // ====================================
    //        NAVEGACIÓN DE VENTANAS
    // ====================================

    //Guarda la referencia a la ventana principal de la aplicacion
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    //Metodos para mostrar el resto de ventanas
    public void mostrarLogin() {
        ControladorLoginYRegistro.abrir(this.primaryStage);
    }

    public void mostrarMenuPrincipal() {
        ControladorMenuPrincipal.abrir(this.primaryStage);
    }
    
    public void mostrarVentanaCursar(Curso curso, Estrategia estrategia) {
        ControladorCursar.abrirSesion(this.primaryStage, curso, estrategia);
    }
    
    public void mostrarEditarCurso(Curso cursoAEditar) {
        ControladorCrearCurso.abrirParaEditar(this.primaryStage, cursoAEditar);
    }
    
    // ====================================
    //        GESTIÓN DE CURSOS
    // ====================================
    
    //Comprueba que el usuario es creador o existe para crear un curso
    public boolean crearCursoNuevo(String nombre, ArrayList<Pregunta> preguntas) {
        if (usuarioActual == null || !(usuarioActual instanceof Creador)) {
            System.err.println("Error: No hay un creador logueado.");
            return false;
        }
        
        RepositorioCursos.getInstance().crearCurso(nombre, usuarioActual, preguntas);
                
        System.out.println("Curso guardado exitosamente: " + nombre);
        return true;
    }
    
    public List<Curso> obtenerCursosDelUsuarioActual() {
        if (usuarioActual == null) return Collections.emptyList();

        if (usuarioActual instanceof Creador) {
            return RepositorioCursos.getInstance().listarPorCreador(usuarioActual);
        }
        
        if (usuarioActual instanceof Estudiante) {
            Estudiante est = (Estudiante) usuarioActual;
            Set<Long> idsSuscritos = est.getCursosSuscritosIds();
            
            return RepositorioCursos.getInstance().listarTodos().stream()
                    .filter(c -> idsSuscritos.contains(c.getId()))
                    .collect(Collectors.toList());
        }
        
        return Collections.emptyList();
    }
    
    public boolean inscribirEstudianteEnCurso(Curso curso) {
        if (usuarioActual instanceof Estudiante) {
            Estudiante est = (Estudiante) usuarioActual;
            est.getCursosSuscritosIds().add(curso.getId());
            RepositorioUsuarios.getInstance().guardarCambios();
            return true;
        }
        return false;
    }

    public boolean desinscribirEstudianteDeCurso(Curso curso) {
        if (usuarioActual instanceof Estudiante) {
            Estudiante est = (Estudiante) usuarioActual;
            est.getCursosSuscritosIds().remove(curso.getId());
            RepositorioUsuarios.getInstance().guardarCambios();
            return true;
        }
        return false;
    }
    
    public void eliminarCurso(Curso curso) {
        if (curso != null) {
            RepositorioCursos.getInstance().eliminarCurso(curso);
            System.out.println("Curso eliminado: " + curso.getNombre());
        }
    }
   
    public boolean guardarCurso(Curso curso) {
        RepositorioCursos.getInstance().guardarCurso(curso);
        return true;
    }
    
    //Busca cursos por nombre, excluyendo los que haya creado el usuario actual.
    public List<Curso> buscarCursosDisponibles(String textoBusqueda) {
        List<Curso> todos = RepositorioCursos.getInstance().listarTodos();
        
        String filtro = (textoBusqueda == null) ? "" : textoBusqueda.toLowerCase();
        
        return todos.stream()
            .filter(c -> {
                if (usuarioActual != null && usuarioActual instanceof Creador) {
                    if (c.getCreador() != null && c.getCreador().getId() != null) {
                        return !c.getCreador().getId().equals(usuarioActual.getId());
                    }
                }
                return true; 
            })
            .filter(c -> c.getNombre() != null && c.getNombre().toLowerCase().contains(filtro))
            .collect(Collectors.toList());
    }
    
    public void clonarCurso(Curso cursoOriginal) {
        if (cursoOriginal == null) return;

        Curso copia = new Curso();
        
        copia.setNombre(cursoOriginal.getNombre() + " (Copia)");
        
        if (cursoOriginal.getPreguntas() != null) {
            copia.setPreguntas(new java.util.ArrayList<>(cursoOriginal.getPreguntas()));
        }
        
        Usuario usuarioActual = getUsuarioActual();
        if (usuarioActual instanceof Creador) {
            copia.setCreador((Creador) usuarioActual);
        }
        
        RepositorioCursos.getInstance().guardarCurso(copia);
    }
    
    // ====================================
    //        ESTADISTICAS
    // ====================================

    //Calcula estadisticas generales del creador como la cantidad de cursos, suma total de preguntas y promedio de preguntas por curso
    public Map<String, Number> obtenerEstadisticasCreador() {
        if (!(usuarioActual instanceof Creador)) return null;

        List<Curso> misCursos = obtenerCursosDelUsuarioActual();
        
        int totalCursos = misCursos.size();
        
        int totalPreguntas = misCursos.stream()
                                      .mapToInt(c -> c.getPreguntas().size())
                                      .sum();
                                      
        double promedio = (totalCursos > 0) ? (double) totalPreguntas / totalCursos : 0.0;

        Map<String, Number> stats = new HashMap<>();
        stats.put("cursos", totalCursos);
        stats.put("preguntas", totalPreguntas);
        stats.put("promedio", promedio);
        
        return stats;
    }
    
    
    // ====================================
    //        IMPORTACION/EXPORTACION
    // ====================================    
    
    //Exporta el curso a un archivo creando una copia con autor "Desconocido" para evitar enlazamientos no deseados
    public boolean exportarCurso(Curso cursoOriginal, File archivoDestino) {
    EstrategiaExpImp estrategia = FactoriaExpImp.obtenerEstrategia(archivoDestino);
    
    if (estrategia == null) {
        System.err.println("Error: Formato de archivo no soportado.");
        return false;
    }

    try {
        Curso copiaAnonima = new Curso();
        copiaAnonima.setNombre(cursoOriginal.getNombre());
        
        copiaAnonima.setPreguntas(new ArrayList<>(cursoOriginal.getPreguntas()));
        
        Creador autorDesconocido = new Creador("Desconocido", "anonimo@export.com", "");
        autorDesconocido.setId(null);
        
        copiaAnonima.setCreador(autorDesconocido);

        estrategia.guardarCurso(copiaAnonima, archivoDestino);
        return true;
        
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    
    //Importa un curso desde un archivo. Si es un creador, se lo apropia evitando duplicados
    //si es un estudiante, busca el curso existente para inscribirse o lo crea como nuevo si no existe
    public boolean importarCurso(File archivoOrigen) {
        EstrategiaExpImp estrategia = FactoriaExpImp.obtenerEstrategia(archivoOrigen);
        
        if (estrategia == null) {
            System.err.println("Formato no soportado.");
            return false;
        }

        try {
            Curso cursoImportado = estrategia.cargarCurso(archivoOrigen);
            
            if (usuarioActual instanceof Creador) {
                cursoImportado.setCreador((Creador) usuarioActual);
                
                List<Curso> misCursos = RepositorioCursos.getInstance().listarPorCreador(usuarioActual);
                for (Curso c : misCursos) {
                    if (c.getNombre() != null && c.getNombre().equalsIgnoreCase(cursoImportado.getNombre())) {
                         System.out.println("El curso ya existe en tus cursos. Se omite.");
                         return true; 
                    }
                }
                
                cursoImportado.setId(null);
                RepositorioCursos.getInstance().guardarCurso(cursoImportado);
            }
            
            else if (usuarioActual instanceof Estudiante) {
                List<Curso> todos = RepositorioCursos.getInstance().listarTodos();
                Curso cursoExistente = null;
                
                String nombreNuevo = (cursoImportado.getNombre() != null) ? cursoImportado.getNombre() : "";
                
                String creadorNuevo = "";
                if (cursoImportado.getCreador() != null && cursoImportado.getCreador().getNombre() != null) {
                    creadorNuevo = cursoImportado.getCreador().getNombre();
                }

                for (Curso c : todos) {
                     String nombreC = (c.getNombre() != null) ? c.getNombre() : "";
                     String creadorC = "";
                     if (c.getCreador() != null && c.getCreador().getNombre() != null) {
                         creadorC = c.getCreador().getNombre();
                     }
                     
                     if (nombreC.equalsIgnoreCase(nombreNuevo) && creadorC.equals(creadorNuevo)) {
                         cursoExistente = c;
                         break;
                     }
                }
                
                if (cursoExistente != null) {
                    inscribirEstudianteEnCurso(cursoExistente);
                } else {
                    cursoImportado.setId(null);
                    cursoImportado.setCreador(null); 
                    
                    RepositorioCursos.getInstance().guardarCurso(cursoImportado);
                    inscribirEstudianteEnCurso(cursoImportado);
                }
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}