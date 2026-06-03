package entidades.productos;

import entidades.movimientos.Movimiento;
import entidades.movimientos.SaldoInsuficienteException;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta extends ProductoAbstracto {
    private List<Movimiento> movimientos = new ArrayList<>();
    private String CBU, alias;

    public Cuenta(String CBU) {
        this.CBU = CBU;
    }

    @Override
    public Movimiento acreditar(float cantidad) {
        Movimiento movimiento = new Movimiento(Movimiento.TipoMovimiento.CREDITO, cantidad);
        movimientos.add(movimiento);
        return movimiento;
    }

    @Override
    public Movimiento debitar(float cantidad) throws SaldoInsuficienteException {
        Movimiento movimiento = new Movimiento(Movimiento.TipoMovimiento.DEBITO, cantidad);
        movimientos.add(movimiento);
        return movimiento;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void printMovimientos() {
        for (Movimiento movimiento : movimientos) {
            movimiento.print();
        }
    }

    public String getCBU() {
        return CBU;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
