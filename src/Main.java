import vista.Ventana;
import vista.VistaLogin;

public class Main {
    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        VistaLogin login = new VistaLogin();
        ventana.setVista(login);

//        PantallaInicial pantalla = new PantallaInicial();
//        pantalla.crearPantalla();
//        Cliente usuario = new Cliente("rodel");
//
//        Cuenta cajaAhorro = new CajaAhorro("1234");
//        usuario.agregarProducto(cajaAhorro);
//        cajaAhorro.acreditar(20.0f);
//
//        try {
//            cajaAhorro.debitar(40.0f);
//        } catch (SaldoInsuficienteException e){
//            System.out.println("Saldo insuficiente");
//        }
//
//        try {
//            cajaAhorro.debitar(10.0f);
//        } catch (SaldoInsuficienteException e2){
//            System.out.println("Saldo insuficiente");
//        }
//
//        System.out.println("Saldo "+ cajaAhorro.getSaldo());
//
//        cajaAhorro.printMovimientos();
    }
}