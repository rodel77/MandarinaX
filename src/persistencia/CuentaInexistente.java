package persistencia;

public class CuentaInexistente extends RuntimeException {
    public CuentaInexistente(String message) {
        super(message);
    }
}
