package entidades.productos;

import entidades.movimientos.Movimiento;
import entidades.movimientos.SaldoInsuficienteException;

import java.util.UUID;

public class CajaAhorro extends Cuenta {
    private float saldo = 0.0f;

    public CajaAhorro(){
        super(UUID.randomUUID().toString());
    }

    public CajaAhorro(String CBU, float saldoInicial) {
        super(CBU);
        this.saldo = saldoInicial;
    }

    @Override
    public float getSaldo() {
        return saldo;
    }

    @Override
    public Movimiento debitar(float cantidad) throws SaldoInsuficienteException {
        if (saldo - cantidad < 0) throw new SaldoInsuficienteException();

        saldo -= cantidad;

        return super.debitar(cantidad);
    }

    @Override
    public Movimiento acreditar(float cantidad) {
        saldo += cantidad;

        return super.acreditar(cantidad);
    }

    @Override
    public String getNombre() {
        return "Caja de Ahorros";
    }
}
