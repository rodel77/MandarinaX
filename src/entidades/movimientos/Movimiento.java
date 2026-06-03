package entidades.movimientos;

import entidades.productos.Cuenta;

import java.sql.Timestamp;

public class Movimiento {
    private Cuenta origen;
    private Cuenta destino;
    private TipoMovimiento tipo;
    private float cantidad;
    private Timestamp fecha;

    public Movimiento(TipoMovimiento tipo, float cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public void print(){
        System.out.println("Movimiento de " + tipo.name() + " ("+cantidad+")");
    }

    public Cuenta getOrigen() {
        return origen;
    }

    public void setOrigen(Cuenta origen) {
        this.origen = origen;
    }

    public Cuenta getDestino() {
        return destino;
    }

    public void setDestino(Cuenta destino) {
        this.destino = destino;
    }

    public float getCantidad(){
        return cantidad;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public enum TipoMovimiento {
        CREDITO, DEBITO
    }
}
