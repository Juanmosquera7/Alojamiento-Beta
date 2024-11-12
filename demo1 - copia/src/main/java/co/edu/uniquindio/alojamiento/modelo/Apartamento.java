// Clase Apartamento
package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Apartamento extends Alojamiento {
    private float costoAseo;

    @Builder
    public Apartamento(String idAlojamiento, String nombre, String ciudad, String descripcion, String imagenUrl,
                       float precioPorNoche, int capacidadMaxima, List<String> servicios, float costoAseo) {
        super(idAlojamiento, nombre, ciudad, descripcion, imagenUrl, precioPorNoche, capacidadMaxima, servicios, TipoAlojamiento.APARTAMENTO);
        this.costoAseo = costoAseo;
    }
}

