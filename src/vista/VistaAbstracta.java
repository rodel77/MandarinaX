package vista;

import javax.swing.*;
import java.awt.*;

public abstract class VistaAbstracta {
    private Ventana ventana;

    protected final Font fuenteLabels = new Font("Arial", Font.BOLD, 12);

    public Dimension getScreenSize(){
        return new Dimension(350, 420);
    }

    public void setVentana(Ventana ventana) {
        this.ventana = ventana;
    }

    void transicionar(VistaAbstracta vista){
        ventana.transicionar(vista);
    }

    JButton crearBoton(String nombre){
        JButton boton = new JButton(nombre);
        boton.setPreferredSize(new Dimension(0, 45));
        boton.setBackground(new Color(255, 120, 0));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        return boton;
    }

    JLabel createTitulo() {
        JLabel labelTitulo = new JLabel("MandarinaX", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        return labelTitulo;
    }

    abstract JPanel display();
}
