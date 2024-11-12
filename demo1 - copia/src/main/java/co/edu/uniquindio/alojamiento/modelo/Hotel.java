// Clase Hotel
package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class Hotel extends Alojamiento {
    private List<Habitacion> habitaciones;

    @Builder
    public Hotel(String idAlojamiento, String nombre, String ciudad, String descripcion, String imagenUrl,
                 float precioPorNoche, int capacidadMaxima, List<String> servicios, List<Habitacion> habitaciones) {
        super(idAlojamiento, nombre, ciudad, descripcion, imagenUrl, precioPorNoche, capacidadMaxima, servicios, TipoAlojamiento.HOTEL);
        this.habitaciones = habitaciones;
    }
}

