package entidades.productos;

import entidades.movimientos.Movimiento;
import entidades.movimientos.SaldoInsuficienteException;
import entidades.usuarios.Cliente;

public abstract class ProductoAbstracto {
    private Cliente cliente;
    private int id;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public abstract float getSaldo();
    abstract Movimiento debitar(float cantidad) throws SaldoInsuficienteException;
    abstract Movimiento acreditar(float cantidad);
    public abstract String getNombre();
}
