import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pasajero {
    private final String nombre;
    private final int numeroAsiento;
    private final String tipoAsiento;
    private boolean checkin;
    private LocalDateTime fechaHoraCheckIn;
    private final DateTimeFormatter formatoHora =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Pasajero(String nombre, int numeroAsiento, String tipoAsiento) {
        this.nombre = nombre;
        this.numeroAsiento = numeroAsiento;
        this.tipoAsiento = tipoAsiento;
        checkin = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public String getTipoAsiento() {
        return tipoAsiento;
    }

    public boolean isCheckin() {
        return checkin;
    }

    public void setCheckin(boolean checkin) {
        this.checkin = checkin;

        if (checkin) {
            this.fechaHoraCheckIn = LocalDateTime.now();
        } else {
            this.fechaHoraCheckIn = null;
        }
    }

  
@Override
public String toString() {
    String resultado = nombre + " | Asiento: " + numeroAsiento
            + " | " + tipoAsiento
            + " | Check-in: " + checkin;

    if (fechaHoraCheckIn != null) {
        resultado += " | Fecha y hora: "
                + fechaHoraCheckIn.format(formatoHora);
    }

    return resultado;
}
}
