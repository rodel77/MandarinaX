package entidades.productos;


import entidades.movimientos.Movimiento;

public class Tarjeta extends ProductoAbstracto {
    private String numeroTarjeta;
    private int expiracion;
    private int cvv;
    private float totalAPagar = 0f;
    private float disponible = 0f;

    public Tarjeta(String numeroTarjeta, int expiracion, int cvv){
        this.numeroTarjeta = numeroTarjeta;
        this.expiracion = expiracion;
        this.cvv = cvv;
    }

    public float getTotalAPagar(){
        return totalAPagar;
    }

    public void setTotalAPagar(float totalAPagar){
        this.totalAPagar = totalAPagar;
    }

    @Override
    public float getSaldo() {
        return disponible;
    }

    @Override
    public Movimiento debitar(float cantidad) {
        throw new RuntimeException("sin implementar");
    }

    @Override
    public Movimiento acreditar(float cantidad) {
        throw new RuntimeException("la tarjeta de crédito no puede tener acreditaciones");
    }

    @Override
    public String getNombre() {
        return "Tarjeta Mandarina Golden";
    }

    public int getCVV() {
        return cvv;
    }

    public int getExpiracion() {
        return expiracion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setSaldo(float saldo) {
        this.disponible = saldo;
    }
}
