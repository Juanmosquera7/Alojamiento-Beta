package co.edu.uniquindio.alojamiento.modelo;

import lombok.Data;

import java.util.List;

@Data
public class Casa extends Alojamiento {
    private float costoAseo;

    public Casa(String idAlojamiento, String nombre, String ciudad, String descripcion, String imagenUrl,
                float precioPorNoche, int capacidadMaxima, List<String> servicios, float costoAseo) {
        // Llamamos al constructor de Alojamiento usando el builder
        super(idAlojamiento, nombre, ciudad, descripcion, imagenUrl, precioPorNoche, capacidadMaxima, servicios, TipoAlojamiento.CASA);
        this.costoAseo = costoAseo;
    }
}


