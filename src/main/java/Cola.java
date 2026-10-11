import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Cola {
    private final Queue<Pasajero> coleccion;

    public Cola() {
        coleccion = new LinkedList<>();
    }

    public void agregar(Pasajero p) {
        if (p == null) {
            throw new IllegalArgumentException("El pasajero no puede ser nulo");
        }

        //VALIDACIONES R1

        // 1. Nombre válido - R1
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            // Valida que el nombre ingresado no se null, o que esté vacío o formado por espacios.
            throw new IllegalArgumentException("NOMBRE NO VÁLIDO" +
                    "\nEl nombre es obligatorio y no puede estar vacío." +
                    "\nIngrese el nombre del pasajero.");
        }

        // 2. Asiento en rango del 1 al 30 - R1
        if (p.getNumeroAsiento() < 1 || p.getNumeroAsiento() > 30) {
            throw new IllegalArgumentException("ASIENTO NO VÁLIDO" +
                    "\nEl asiento " + p.getNumeroAsiento() + " no existe." +
                    "\nElija un asiento del 1 al 30.");
        }

        // 3. Asiento repetido - R1
        for (Pasajero x : coleccion) {
            if (p.getNumeroAsiento() == x.getNumeroAsiento()) {
                throw new IllegalArgumentException("ASIENTO OCUPADO" +
                        "\nEl asiento " + p.getNumeroAsiento() + " ya está ocupado." +
                        "\nElija otro asiento.");
            }
        }

        // 4. Cupo por categoría - R1
        String tipo = p.getTipoAsiento();
        int limite;

        if ("PRIMERA CLASE".equals(tipo)) {
            limite = 6; //4 antes
        } else if ("EJECUTIVA".equals(tipo)) {
            limite = 9; //6 antes
        } else if ("ECONOMICA".equals(tipo)) {
            limite = 15; //10 antes
        } else {
            throw new IllegalArgumentException("CATEGORÍA NO VÁLIDA" +
                    "\nLa categoría no existe." +
                    "\nElija ECONOMICA, EJECUTIVA O PRIMERA CLASE.");
        }

        int ocupados = 0;
        for (Pasajero x : coleccion) {
            if (tipo.equals(x.getTipoAsiento())) {
                ocupados++;
            }
        }

        if (ocupados >= limite) {
            throw new IllegalArgumentException("Sin cupo en " + tipo +
                    "\nEl máximo es " + limite + " pasajeros." +
                    "\nElija otra categoría.");
        }

        // R1.FAREED GUZMÁN

        coleccion.offer(p);
    }

    public Pasajero extraer() {
        if (coleccion.isEmpty()) {
            throw new NoSuchElementException("Cola sin elementos");
        }
        return coleccion.poll();
    }

    public Pasajero frente() {
        if (coleccion.isEmpty()) {
            throw new NoSuchElementException("Cola sin elementos");
        }
        return coleccion.peek();
    }

    public int size() {
        return coleccion.size();
    }

    public boolean estaVacia() {
        return coleccion.isEmpty();
    }

    public List<Pasajero> obtenerPendientes() {
        return Collections.unmodifiableList(new ArrayList<>(coleccion));
    }

    public Pasajero buscarPorAsiento(int asiento) {
        // TODO R2: validar rango y buscar sin retirar pasajeros.
        throw new UnsupportedOperationException("Pendiente implementar R2: búsqueda");
    }

    public int posicionPorAsiento(int asiento) {
        // TODO R2: posiciones desde 1; devolver -1 si no está en la fila.
        throw new UnsupportedOperationException("Pendiente implementar R2: posición");
    }

    public Pasajero cancelarPorAsiento(int asiento) {
        // TODO R3: retirar únicamente al indicado y conservar el orden restante.
        throw new UnsupportedOperationException("Pendiente implementar R3: cancelación");
    }

    @Override
    public String toString() {
        if (coleccion.isEmpty()) {
            return "Cola sin elementos";
        }
        StringBuilder sb = new StringBuilder();
        int posicion = 1;
        for (Pasajero p : coleccion) {
            sb.append(posicion++).append(". ").append(p).append("\n");
        }
        return sb.toString();
    }
}
