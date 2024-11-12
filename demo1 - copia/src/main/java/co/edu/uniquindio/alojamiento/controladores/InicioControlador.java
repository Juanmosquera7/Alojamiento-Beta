package co.edu.uniquindio.alojamiento.controladores;

import javafx.event.ActionEvent;

public class InicioControlador {

    private final ControladorPrincipal controladorPrincipal;

    public InicioControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void irIniciarSesion(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/login.fxml", "Iniciar Sesión");
    }

    public void irRegistroCliente(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/registroCliente.fxml", "Registro Cliente");
    }

    public void verAlojamientos(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/vistas/listaAlojamientos.fxml", "Alojamientos Disponibles");
    }

    public void mostrarOfertas(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/vistas/ofertas.fxml", "Ofertas Especiales");
    }
}


