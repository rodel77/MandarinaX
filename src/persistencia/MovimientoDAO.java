package persistencia;

import entidades.movimientos.Movimiento;
import entidades.productos.Cuenta;

import java.util.List;

public interface MovimientoDAO {
    void escribirMovimiento(Movimiento movimiento) throws EscribirException;
    List<Movimiento> leerMovimientos(Cuenta cuenta) throws LeerException;
}
