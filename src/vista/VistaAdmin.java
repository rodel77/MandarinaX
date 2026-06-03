package vista;

import javax.swing.*;
import java.awt.*;

public class VistaAdmin extends VistaAbstracta {
    @Override
    JPanel display() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel labelTitulo = createTitulo();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(labelTitulo, gbc);

        JLabel labelSubtitulo = new JLabel("Administración", SwingConstants.CENTER);
        labelSubtitulo.setFont(fuenteLabels);
        labelSubtitulo.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(labelSubtitulo, gbc);

        JButton btnUsuario = crearBoton("Agregar Usuario");
        btnUsuario.addActionListener(e -> transicionar(new VistaAltaUsuario()));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(btnUsuario, gbc);

        JButton btnCuenta = crearBoton("Agregar Producto");
        btnCuenta.addActionListener(e -> transicionar(new VistaAltaProducto()));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(btnCuenta, gbc);

        JButton btnVolver = crearBoton("Volver");
        btnVolver.setBackground(new Color(100, 116, 139));
        btnVolver.addActionListener(e -> transicionar(new VistaLogin()));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }
}
