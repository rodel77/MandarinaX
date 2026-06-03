package servicio;

import entidades.movimientos.Movimiento;
import entidades.movimientos.SaldoInsuficienteException;
import entidades.productos.Cuenta;
import persistencia.*;

import java.sql.SQLException;

public class ServicioTransferencias {

    private final MovimientosCrudSQL movimientosCrudSQL = new MovimientosCrudSQL();
    private final ProductosCrudSQL productosCrudSQL = new ProductosCrudSQL();
    private final ServicioProductos servicioProductos = new ServicioProductos();

    public void transferir(Cuenta origen, String destino, float cantidad) throws TransaccionException, CuentaInexistente, SaldoInsuficienteException {
        try {
            Cuenta cuentaDestino = servicioProductos.buscarCuenta(destino);

            if(cuentaDestino.getId() == origen.getId()) throw new TransaccionException("No puedes transferirte a ti mismo");

            Movimiento movimientoOrigen = origen.debitar(cantidad);
            movimientoOrigen.setOrigen(origen);
            movimientoOrigen.setDestino(cuentaDestino);
            movimientosCrudSQL.escribirMovimiento(movimientoOrigen);

            Movimiento movimientoDestino = cuentaDestino.acreditar(cantidad);
            movimientoDestino.setOrigen(origen);
            movimientoDestino.setDestino(cuentaDestino);
            movimientosCrudSQL.escribirMovimiento(movimientoDestino);

            productosCrudSQL.actualizarCuenta(origen);
            productosCrudSQL.actualizarCuenta(cuentaDestino);
        } catch (EscribirException | LeerException e) {
            e.printStackTrace();
            throw new TransaccionException(e.getMessage());
        }
    }
}
