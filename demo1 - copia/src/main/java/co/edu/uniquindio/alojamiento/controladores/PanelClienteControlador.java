package co.edu.uniquindio.alojamiento.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class PanelClienteControlador {

    private final ControladorPrincipal controladorPrincipal;

    @FXML
    private Button btnBuscarAlojamiento;

    @FXML
    private Button btnMisReservas;

    @FXML
    private Button btnRecargarBilletera;

    @FXML
    private Button btnEditarPerfil;

    @FXML
    private Button btnCerrarSesion;

    public PanelClienteControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private void buscarAlojamiento(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/buscarAlojamiento.fxml", "Buscar Alojamiento");
    }

    @FXML
    private void verMisReservas(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/misReservas.fxml", "Mis Reservas");
    }

    @FXML
    private void recargarBilletera(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/recargarBilletera.fxml", "Recargar Billetera");
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
        controladorPrincipal.navegarVentana("/vistas/editarPerfil.fxml", "Editar Perfil");
    }

    //@FXML
    //private void cerrarSesion(ActionEvent event) {
        //controladorPrincipal.cerrarSesion();
        //controladorPrincipal.navegarVentana("/vistas/login.fxml", "Iniciar Sesión");
    //}
}

