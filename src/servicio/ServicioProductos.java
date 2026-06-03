package servicio;

import entidades.movimientos.Movimiento;
import entidades.productos.Cuenta;
import entidades.productos.ProductoAbstracto;
import entidades.productos.Tarjeta;
import entidades.usuarios.Cliente;
import entidades.usuarios.Usuario;
import persistencia.*;

import java.util.Collection;
import java.util.List;

public class ServicioProductos {
    private final ProductosCrudSQL productosCrudSQL = new ProductosCrudSQL();
    private final MovimientosCrudSQL movimientosCrudSQL = new MovimientosCrudSQL();

    public void escribirProducto(ProductoAbstracto producto) throws EscribirException {
        productosCrudSQL.escribir(producto);
    }

    public void cargarProductos(Cliente cliente) throws LeerException {
        cliente.clearProductos();
        for(ProductoAbstracto producto : productosCrudSQL.leerCuentas(cliente)){
            cliente.agregarProducto(producto);
        }

        for(ProductoAbstracto producto : productosCrudSQL.leerTarjetas(cliente)){
            cliente.agregarProducto(producto);
        }
    }

    public Cuenta buscarCuenta(String buscar) throws CuentaInexistente, LeerException {
        return productosCrudSQL.buscarCuenta(buscar);
    }

    public void cargarTransferencias(Cuenta cuenta) throws LeerException{
        List<Movimiento> movimientos = movimientosCrudSQL.leerMovimientos(cuenta);
        cuenta.getMovimientos().clear();
        cuenta.getMovimientos().addAll(movimientos);
    }
}
