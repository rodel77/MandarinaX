package persistencia;

import entidades.productos.CajaAhorro;
import entidades.productos.Cuenta;
import entidades.productos.ProductoAbstracto;
import entidades.productos.Tarjeta;
import entidades.usuarios.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductosCrudSQL extends SQLDB implements ProductoDAO {
    @Override
    public void escribir(ProductoAbstracto producto) throws EscribirException {
        try {
            int usuarioId = producto.getCliente().getId();
            if(producto instanceof Cuenta cuenta) {
                if(!(producto instanceof CajaAhorro)) throw new EscribirException("tipo de cuenta invalido");

                updateSql("insert into cuentas (usuario, cbu, saldo, alias, tipo) values (?, ?, ?, ?, ?)",
                        usuarioId, cuenta.getCBU(), cuenta.getSaldo(), cuenta.getAlias(), "cajaAhorro");
            }else if(producto instanceof Tarjeta tarjeta){
                updateSql("insert into tarjetas (usuario, numeroTarjeta, expiracion, cvv, disponible, saldoPagar) values (?, ?, ?, ?, ?, ?)",
                        usuarioId, tarjeta.getNumeroTarjeta(), tarjeta.getExpiracion(), tarjeta.getCVV(), tarjeta.getSaldo(), tarjeta.getTotalAPagar());
            }
        } catch(SQLException e){
            throw new EscribirException(e.getMessage());
        }
    }

    @Override
    public Collection<Tarjeta> leerTarjetas(Cliente cliente) throws LeerException {
        try (ResultSet rs = selectSql("select numeroTarjeta, expiracion, cvv, disponible, saldoPagar, id from tarjetas where usuario = ?", cliente.getId())){
            List<Tarjeta> tarjetas = new ArrayList<>();
            while(rs.next()){
                Tarjeta tarjeta = new Tarjeta(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3)
                );
                tarjeta.setSaldo(rs.getFloat(4));
                tarjeta.setTotalAPagar(rs.getFloat(5));
                tarjeta.setId(rs.getInt(6));
                tarjetas.add(tarjeta);
            }
            return tarjetas;
        } catch(SQLException e){
            throw new LeerException(e.getMessage());
        }
    }

    private Cuenta crearCuenta(ResultSet rs) throws SQLException, LeerException {
        String tipoDeCuenta = rs.getString(5);
        if(tipoDeCuenta.equals("cajaAhorro")){
            CajaAhorro cajaAhorro = new CajaAhorro(rs.getString(2), rs.getFloat(4));
            cajaAhorro.setAlias(rs.getString(6));
            cajaAhorro.setId(rs.getInt(1));
            return cajaAhorro;
        }else{
            throw new LeerException("Tipo de cuenta "+tipoDeCuenta+" no encontrado");
        }
    }

    @Override
    public Collection<Cuenta> leerCuentas(Cliente cliente) throws LeerException {
        try (ResultSet rs = selectSql("select * from cuentas where usuario = ?", cliente.getId())){
            List<Cuenta> cuentas = new ArrayList<>();
            while(rs.next()){
                cuentas.add(crearCuenta(rs));
            }
            return cuentas;
        } catch(SQLException e){
            throw new LeerException(e.getMessage());
        }
    }

    @Override
    public Cuenta buscarCuenta(String buscador) throws LeerException, CuentaInexistente {
        try(ResultSet rs = selectSql("select * from cuentas where cbu = ? or id = ? or alias = ?",
                buscador,
                buscador,
                buscador
        )){
            if(rs.next()) {
                return crearCuenta(rs);
            }else{
                throw new CuentaInexistente("Cuenta "+buscador+" no encontrada");
            }
        }catch(SQLException ex){
            throw new LeerException(ex.getMessage());
        }
    }

    @Override
    public void actualizarCuenta(Cuenta cuenta) throws EscribirException {
        try {
            updateSql("update cuentas set saldo = ?, alias = ? where id = ?",
                    cuenta.getSaldo(),
                    cuenta.getAlias(),
                    cuenta.getId()
            );
        } catch(Exception e){
            throw new EscribirException(e.getMessage());
        }
    }
}
