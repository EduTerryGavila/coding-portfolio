package analizadorMensajes;
import java.util.*;


public class Rastreador {
    private List<Mensaje> mensajes;
    private Set<String> autores;
    private Set<String> temas;
    private Map<String, List<Mensaje>> mapaAutores;
    private Map<String, List<Mensaje>> mapaTemas;

    public Rastreador() {
        mensajes = new ArrayList<>();
        autores = new TreeSet<>();
        temas = new TreeSet<>();
        mapaAutores = new TreeMap<>();
        mapaTemas = new TreeMap<>();
    }

    public void registrarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        autores.add(mensaje.getAutor());

        mapaAutores.putIfAbsent(mensaje.getAutor(), new ArrayList<>());
        mapaAutores.get(mensaje.getAutor()).add(mensaje);

        for (String tema : mensaje.getTemas()) {
            temas.add(tema);
            mapaTemas.putIfAbsent(tema, new ArrayList<>());
            mapaTemas.get(tema).add(mensaje);
        }
    }

    public List<Mensaje> obtenerMensajesPorAutor(String autor) {
        return mapaAutores.getOrDefault(autor, new ArrayList<>());
    }

    public List<Mensaje> obtenerMensajesPorTema(String tema) {
        return mapaTemas.getOrDefault(tema, new ArrayList<>());
    }

    public List<Mensaje> obtenerMensajesOrdenados() {
        List<Mensaje> ordenados = new ArrayList<>(mensajes);
        Collections.sort(ordenados);
        return ordenados;
    }

    public List<Mensaje> obtenerMensajesOrdenados(Comparator<Mensaje> criterio) {
        List<Mensaje> ordenados = new ArrayList<>(mensajes);
        ordenados.sort(criterio);
        return ordenados;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public Set<String> getAutores() {
        return autores;
    }

    public Set<String> getTemas() {
        return temas;
    }
}