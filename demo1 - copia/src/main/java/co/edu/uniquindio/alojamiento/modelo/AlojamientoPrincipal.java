package co.edu.uniquindio.alojamiento.modelo;

import co.edu.uniquindio.alojamiento.servicio.ServiciosAlojamientos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class AlojamientoPrincipal implements ServiciosAlojamientos {
    // Listas para almacenar los objetos
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Alojamiento> alojamientos = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Oferta> ofertas = new ArrayList<>();
    private final List<Resena> resenas = new ArrayList<>();

    // Métodos de cliente

    @Override
    public Cliente loginCliente(String email, String contrasena) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email) && cliente.getContrasena().equals(contrasena)) {
                return cliente;
            }
        }
        throw new Exception("Usuario o contraseña incorrectos.");
    }

    @Override
    public void registrarCliente(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        // Verificar si el cliente ya está registrado con el mismo correo
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                throw new Exception("El cliente ya está registrado con este correo.");
            }
        }

        // Crear un nuevo cliente utilizando el patrón Builder de Lombok
        Cliente nuevoCliente = Cliente.builder()
                .cedula(cedula)
                .nombreCompleto(nombreCompleto)
                .telefono(telefono)
                .email(email)
                .contrasena(contrasena)
                .cuentaActivada(true)  // La cuenta no está activada al registrarse
                .saldoBilletera(0)      // El saldo inicial es 0
                .build();

        // Agregar el cliente a la lista
        clientes.add(nuevoCliente);
    }


    @Override
    public void activarCuentaCliente(String email, String codigoActivacion) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                cliente.setCuentaActiva(true);
                return;
            }
        }
        throw new Exception("Cliente no encontrado.");
    }

    @Override
    public void actualizarCliente(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                cliente.setNombreCompleto(nombreCompleto);
                cliente.setTelefono(telefono);
                cliente.setEmail(email);
                cliente.setContrasena(contrasena);
                return;
            }
        }
        throw new Exception("Cliente no encontrado.");
    }

    @Override
    public void eliminarCuentaCliente(String cedula) throws Exception {
        for (Iterator<Cliente> iterator = clientes.iterator(); iterator.hasNext();) {
            Cliente cliente = iterator.next();
            if (cliente.getCedula().equals(cedula)) {
                iterator.remove();
                return;
            }
        }
        throw new Exception("Cliente no encontrado.");
    }

    @Override
    public void solicitarCambioContrasena(String email) throws Exception {
        Cliente cliente = clientes.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente no encontrado."));
    }

    @Override
    public void cambiarContrasena(String email, String codigo, String nuevaContrasena) throws Exception {
        Cliente cliente = clientes.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente no encontrado."));
        cliente.setContrasena(nuevaContrasena);
    }

    // Métodos de alojamiento

    @Override
    public void crearAlojamiento(String nombre, String ciudad, String descripcion, String imagenUrl, float precioPorNoche, int capacidadMaxima, List<String> servicios, TipoAlojamiento tipo) throws Exception {
        String idAlojamiento = UUID.randomUUID().toString();
        Alojamiento alojamiento = new Alojamiento(idAlojamiento, nombre, ciudad, descripcion, imagenUrl, precioPorNoche, capacidadMaxima, servicios, tipo);
        alojamientos.add(alojamiento);
    }

    @Override
    public void modificarAlojamiento(String idAlojamiento, String nombre, String ciudad, String descripcion, String imagenUrl, float precioPorNoche, int capacidadMaxima, List<String> servicios) throws Exception {
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));

        alojamiento.setNombre(nombre);
        alojamiento.setCiudad(ciudad);
        alojamiento.setDescripcion(descripcion);
        alojamiento.setImagenUrl(imagenUrl);
        alojamiento.setPrecioPorNoche(precioPorNoche);
        alojamiento.setCapacidadMaxima(capacidadMaxima);
        alojamiento.setServicios(servicios);
    }

    @Override
    public void eliminarAlojamiento(String idAlojamiento) throws Exception {
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));
        alojamientos.remove(alojamiento);
    }

    @Override
    public List<Alojamiento> buscarAlojamiento(String nombre, String tipo, String ciudad, float precioMin, float precioMax) {
        List<Alojamiento> resultado = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos) {
            if ((nombre == null || alojamiento.getNombre().contains(nombre)) &&
                    (tipo == null || alojamiento.getTipo().toString().equals(tipo)) &&
                    (ciudad == null || alojamiento.getCiudad().equals(ciudad)) &&
                    alojamiento.getPrecioPorNoche() >= precioMin && alojamiento.getPrecioPorNoche() <= precioMax) {
                resultado.add(alojamiento);
            }
        }
        return resultado;
    }

    // Métodos de oferta

    @Override
    public Reserva crearReserva(String idAlojamiento, String cedulaCliente, LocalDate fechaInicio, LocalDate fechaFin, int numHuespedes) throws Exception {
        // 1. Buscar al cliente usando su cédula
        Cliente cliente = clientes.stream()
                .filter(c -> c.getCedula().equals(cedulaCliente))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente no encontrado."));

        // 2. Buscar el alojamiento usando su ID
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));

        // 3. Verificar si la capacidad máxima del alojamiento es suficiente para el número de huéspedes
        if (alojamiento.getCapacidadMaxima() < numHuespedes) {
            throw new Exception("Número de huéspedes excede la capacidad del alojamiento.");
        }

        // 4. Verificar que las fechas de inicio y fin sean válidas
        if (fechaInicio.isAfter(fechaFin)) {
            throw new Exception("La fecha de inicio no puede ser después de la fecha de fin.");
        }

        // 5. Crear la reserva
        Reserva reserva = Reserva.builder()
                .idReserva(UUID.randomUUID())  // Generar un ID único para la reserva
                .cliente(cliente)
                .alojamiento(alojamiento)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .numHuespedes(numHuespedes)
                .factura(null)  // Inicialmente no hay factura generada
                .build();

        // 6. Agregar la reserva a la lista de reservas
        reservas.add(reserva);

        // 7. Retornar la reserva creada
        return reserva;
    }



    @Override
    public void modificarOferta(String idOferta, float descuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        Oferta oferta = ofertas.stream()
                .filter(o -> o.getIdOferta().equals(idOferta))
                .findFirst()
                .orElseThrow(() -> new Exception("Oferta no encontrada."));

        oferta.setDescuento(descuento);
        oferta.setFechaInicio(fechaInicio);
        oferta.setFechaFin(fechaFin);
    }

    @Override
    public void eliminarOferta(String idOferta) throws Exception {
        Oferta oferta = ofertas.stream()
                .filter(o -> o.getIdOferta().equals(idOferta))
                .findFirst()
                .orElseThrow(() -> new Exception("Oferta no encontrada."));
        ofertas.remove(oferta);
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosPopulares(String ciudad) {
        return List.of();
    }

    @Override
    public List<TipoAlojamiento> obtenerTiposAlojamientoRentables() {
        return List.of();
    }

    @Override
    public float obtenerOcupacionAlojamiento(String idAlojamiento) {
        return 0;
    }

    @Override
    public float obtenerGananciasAlojamiento(String idAlojamiento) {
        return 0;
    }

    // Métodos de reservas

    @Override
    public void crearOferta(String idAlojamiento, float descuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));

        // Validación de descuento
        if (descuento < 0 || descuento > 100) {
            throw new Exception("El descuento debe ser entre 0 y 100.");
        }

        // Validación de fechas
        if (fechaInicio.isAfter(fechaFin)) {
            throw new Exception("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        // Crear la nueva oferta para el alojamiento
        Oferta nuevaOferta = Oferta.builder()
                //.idAlojamiento(idAlojamiento)
                .descuento(descuento)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .build();

        ofertas.add(nuevaOferta);
    }



    @Override
    public void cancelarReserva(UUID idReserva) throws Exception {
        Reserva reserva = reservas.stream()
                .filter(r -> r.getIdReserva().equals(idReserva))
                .findFirst()
                .orElseThrow(() -> new Exception("Reserva no encontrada."));
        reservas.remove(reserva);
    }

    @Override
    public List<Reserva> listarReservasPorCliente(String cedulaCliente) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().getCedula().equals(cedulaCliente)) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }

    @Override
    public List<Reserva> listarReservasPorAlojamiento(String idAlojamiento) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().getIdAlojamiento().equals(idAlojamiento)) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }

    @Override
    public void recargarBilletera(String cedulaCliente, float monto) throws Exception {

    }

    @Override
    public float consultarSaldoBilletera(String cedulaCliente) {
        return 0;
    }

    @Override
    public void agregarResena(String idAlojamiento, String cedulaCliente, String comentario, int valoracion) throws Exception {

    }

    @Override
    public List<Resena> listarResenasPorAlojamiento(String idAlojamiento) {
        return List.of();
    }

    @Override
    public Factura generarFactura(UUID idReserva) throws Exception {
        return null;
    }

    @Override
    public String generarCodigoQR(Factura factura) throws Exception {
        return "";
    }

    @Override
    public void enviarCorreoConQR(String emailCliente, String codigoQR, String detallesReserva) throws Exception {

    }

    // Métodos de reseñas


}
