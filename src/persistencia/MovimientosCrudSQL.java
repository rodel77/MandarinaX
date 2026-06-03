package persistencia;

import entidades.movimientos.Movimiento;
import entidades.productos.Cuenta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimientosCrudSQL extends SQLDB implements MovimientoDAO {
    @Override
    public void escribirMovimiento(Movimiento movimiento) throws EscribirException {
        try {
            updateSql("insert into movimientos (origen, destino, cantidad, tipo) values (?, ?, ?, ?)",
                    movimiento.getOrigen().getId(),
                    movimiento.getDestino().getId(),
                    movimiento.getCantidad(),
                    movimiento.getTipo().toString()
            );
        } catch (SQLException e){
            throw new EscribirException(e.getMessage());
        }
    }

    private Movimiento crearMovimiento(ResultSet rs) throws SQLException {
        Movimiento movimiento = new Movimiento(
                Movimiento.TipoMovimiento.valueOf(rs.getString(5)),
                rs.getFloat(4)
        );

        movimiento.setFecha(rs.getTimestamp(6));

        return movimiento;
    }

    @Override
    public List<Movimiento> leerMovimientos(Cuenta cuenta) throws LeerException {
        List<Movimiento> movimientos = new ArrayList<>();
        int id = cuenta.getId();
        try(ResultSet rs = selectSql("select * from movimientos where origen = ? or destino = ? order by id desc",
                id,
                id
        )){
            while(rs.next()){
                movimientos.add(crearMovimiento(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movimientos;
    }
}
