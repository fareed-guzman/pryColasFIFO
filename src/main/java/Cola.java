import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.HashMap; //
import java.util.Map; //

public class Cola {
    private final Queue<Pasajero> coleccion;

    public Cola() {
        coleccion = new LinkedList<>();
    }
    private final Map<Integer, String> claseDeAsiento = new HashMap<>(); //

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

        // 1b. Nombre sin números - R1
        for (char letra : p.getNombre().toCharArray()) {   // Recorro el nombre letra por letra.
            if (Character.isDigit(letra)) {                // Rechaza si alguna letra es un dígito.
                throw new IllegalArgumentException("NOMBRE NO VÁLIDO" +
                        "\nEl nombre no puede contener números." +
                        "\nIngrese un nombre sin números.");
            }
        }

        // 2. Asiento en rango del 1 al 30 - R1
        if (p.getNumeroAsiento() < 1 || p.getNumeroAsiento() > 30) { // Rechaza asientos fuera de 1-30.
            throw new IllegalArgumentException("ASIENTO NO VÁLIDO" +
                    "\nEl asiento " + p.getNumeroAsiento() + " no existe." +
                    "\nElija un asiento del 1 al 30.");
        }

        // 3. Asiento repetido - R1
        for (Pasajero x : coleccion) {
            if (p.getNumeroAsiento() == x.getNumeroAsiento()) { // Rechaza si alguien ya tiene asiento.
                throw new IllegalArgumentException("ASIENTO OCUPADO" +
                        "\nEl asiento " + p.getNumeroAsiento() + " ya está ocupado." +
                        "\nElija otro asiento.");
            }
        }

        // 4. Cupo por categoría - R1
        String tipo = p.getTipoAsiento(); // Guarda la categoría del pasajero para no repetir.
        int limite;

        if ("PRIMERA CLASE".equals(tipo)) {
            limite = 6; //4 antes
        } else if ("EJECUTIVA".equals(tipo)) {
            limite = 9; //6 antes
        } else if ("ECONOMICA".equals(tipo)) {
            limite = 15; //10 antes
        } else { // Otra categoría se rechaza.
            throw new IllegalArgumentException("CATEGORÍA NO VÁLIDA" +
                    "\nLa categoría no existe." +
                    "\nElija ECONOMICA, EJECUTIVA O PRIMERA CLASE.");
        }

        int ocupados = 0; // Pasajeros de esta categoría en la fila.
        for (Pasajero x : coleccion) {
            if (tipo.equals(x.getTipoAsiento())) { // Valida si es de la misma categoría que el nuevo.
                ocupados++;
            }
        }

        if (ocupados >= limite) { // Rechaza si ya se llegó al cupo máximo.
            throw new IllegalArgumentException("SIN CUPO EN '" + tipo +
                    "'\nEl máximo es " + limite + " pasajeros." +
                    "\nElija otra categoría.");
        }

        // 5. Un asiento conserva su clase - R1
        String claseFija = claseDeAsiento.get(p.getNumeroAsiento());   // Clase que ya tenía este asiento.
        if (claseFija != null && !claseFija.equals(tipo)) {            // Rechaza si ya tenía clase y es distinta.
            throw new IllegalArgumentException("CAMBIO DE CLASE NO PERMITIDO" +
                    "\nEl asiento " + p.getNumeroAsiento() + " pertenece a la categoría '" + claseFija + "'." +
                    "\nElija esa categoría u otro asiento.");
        }

        claseDeAsiento.put(p.getNumeroAsiento(), tipo);   // Recuerda la clase de este asiento.

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

    //Iveth Castro R2
    public Pasajero buscarPorAsiento(int asiento) {
        // TODO R2: validar rango y buscar sin retirar pasajeros.
        if(asiento<1 || asiento>30){
            throw new IllegalArgumentException("El asiento debe estar entre 1 y 30");
        }
        for (Pasajero p : coleccion) { //for - each

            if (p.getNumeroAsiento() == asiento) {
                return p;
            }

        }
        return null;
    }
    //Iveth Castro R2
     public int posicionPorAsiento(int asiento) {
        // TODO R2: posiciones desde 1; devolver -1 si no está en la fila.

        if (asiento < 1 || asiento > 30) {
            throw new IllegalArgumentException("El asiento debe estar entre 1 y 30");
        }
        int posicion=1;
        for (Pasajero p : coleccion) {
            if (p.getNumeroAsiento() == asiento) {
                return posicion;
            }
            posicion += 1;
        }
        return -1;
    }
    //Iveth Castro precios por clase
    public double obtenerPrecio(String tipoAsiento) {

        if ("PRIMERA CLASE".equals(tipoAsiento)) {
            return 450;
        } else if ("EJECUTIVA".equals(tipoAsiento)) {
            return 250;
        } else if ("ECONOMICA".equals(tipoAsiento)) {
            return 120;
        }

        throw new IllegalArgumentException("Categoría de asiento no válida");
    }

 /* Zaskia Sunta */
        // TODO R3: retirar únicamente al indicado y conservar el orden restante.
         

    public Pasajero cancelarPorAsiento(int asiento) {
        if (asiento < 1 || asiento > 30) {
            throw new IllegalArgumentException(
                    "El asiento debe estar entre 1 y 30"
            );
        }

        Pasajero cancelado = null;

        for (Pasajero p : coleccion) {
            if (p.getNumeroAsiento() == asiento) {
                cancelado = p;
                break;
            }
        }

        if (cancelado == null) {
            throw new NoSuchElementException(
                    "No existe un pasajero pendiente con el asiento " + asiento
            );
        }

        Queue<Pasajero> auxiliar = new LinkedList<>();

        while (!coleccion.isEmpty()) {
            Pasajero p = coleccion.poll();

            if (p.getNumeroAsiento() != asiento) {
                auxiliar.offer(p);
            }
        }

        coleccion.addAll(auxiliar);

        return cancelado;
    }
    /* R3 */

    //Iveth Castro
    @Override
    public String toString() {
        if (coleccion.isEmpty()) {
            return "Cola sin elementos";
        }
        StringBuilder sb = new StringBuilder();
        int posicion = 1;
        for (Pasajero p : coleccion) {
            sb.append(posicion++).append(". ").append(p)
                    .append(" | Precio: $").append(obtenerPrecio(p.getTipoAsiento()))
                    .append("\n");
        }
        return sb.toString();
    }
}
