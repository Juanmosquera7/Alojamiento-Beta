package co.edu.uniquindio.alojamiento.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class PanelAdminControlador {

    private final ControladorPrincipal controladorPrincipal;

    @FXML
    private StackPane contentArea;

    public PanelAdminControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private void initialize() {
        // Inicialización adicional si es necesaria
    }

    @FXML
    private void gestionarAlojamientos(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/gestionarAlojamientos.fxml", "Gestionar Alojamientos");
    }

    @FXML
    private void verOfertasEspeciales(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/ofertasEspeciales.fxml", "Ofertas Especiales");
    }

    @FXML
    private void verEstadisticas(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/estadisticas.fxml", "Estadísticas");
    }

    @FXML
    private void verAlojamientosPopulares(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/alojamientosPopulares.fxml", "Alojamientos Populares");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        controladorPrincipal.navegarVentana("/login.fxml", "Iniciar Sesión");
    }
}
