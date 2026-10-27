package ec.edu.taller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class GestorImpresiones {

    private final Queue<TrabajoImpresion> cola;
    private final int capacidadMaxima;

    public GestorImpresiones(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.cola = new PriorityQueue<>(Comparator.comparingInt(TrabajoImpresion::getPrioridad));
    }

    public boolean registrarTrabajo(TrabajoImpresion trabajo) {
        if (trabajo == null) {
            return false;
        }
        String codigo = trabajo.getCodigo();
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        if (existeTrabajo(codigo)) {
            return false;
        }
        if (cola.size() >= capacidadMaxima) {
            return false;
        }
        return cola.offer(trabajo);
    }

    public boolean existeTrabajo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        for (TrabajoImpresion trabajo : cola) {
            if (codigo.equals(trabajo.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public TrabajoImpresion consultarSiguienteTrabajo() {
        return cola.peek();
    }

    public TrabajoImpresion procesarSiguienteTrabajo() {
        TrabajoImpresion trabajo = cola.poll();
        if (trabajo != null) {
            trabajo.setEstado("PROCESADO");
        }
        return trabajo;
    }

    public int contarTrabajosPendientes() {
        return cola.size();
    }

    public int consultarEspaciosDisponibles() {
        return capacidadMaxima - cola.size();
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public List<TrabajoImpresion> listarTrabajos() {
        List<TrabajoImpresion> lista = new ArrayList<>(cola);
        lista.sort(Comparator.comparingInt(TrabajoImpresion::getPrioridad));
        return lista;
    }
}