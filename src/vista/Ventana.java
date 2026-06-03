package vista;

import javax.swing.*;

public class Ventana {
    JFrame frame;

    public Ventana(){
        frame = new JFrame("MandarinaX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void transicionar(VistaAbstracta vista){
        setVista(vista);
    }

    public void setVista(VistaAbstracta vista){
        frame.setSize(vista.getScreenSize());
        frame.getContentPane().removeAll();
        vista.setVentana(this);
        JPanel panel = vista.display();
        frame.getContentPane().add(panel);
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }
}
