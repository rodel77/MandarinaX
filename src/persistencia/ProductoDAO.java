package persistencia;

import entidades.productos.Cuenta;
import entidades.productos.ProductoAbstracto;
import entidades.productos.Tarjeta;
import entidades.usuarios.Cliente;

import java.util.Collection;
import java.util.List;

public interface ProductoDAO {
    void escribir(ProductoAbstracto producto) throws EscribirException;
    Collection<Tarjeta> leerTarjetas(Cliente cliente) throws LeerException;
    Collection<Cuenta> leerCuentas(Cliente cliente) throws LeerException;
    Cuenta buscarCuenta(String buscador) throws LeerException, CuentaInexistente;
    void actualizarCuenta(Cuenta cuenta) throws EscribirException;
}
