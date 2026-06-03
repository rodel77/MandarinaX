package vista;

import entidades.usuarios.Cliente;
import servicio.ServicioUsuarios;

import javax.swing.*;
import java.awt.*;

public class VistaAltaUsuario extends VistaAbstracta {
    private final ServicioUsuarios servicioUsuarios = new ServicioUsuarios();

    @Override
    public JPanel display() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel labelTitulo = createTitulo();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(labelTitulo, gbc);

        JLabel labelSubtitulo = new JLabel("Alta usuario", SwingConstants.CENTER);
        labelSubtitulo.setFont(fuenteLabels);
        labelSubtitulo.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(labelSubtitulo, gbc);

        JLabel labelUsuario = new JLabel("Nuevo Usuario");
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        labelUsuario.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelUsuario, gbc);

        JTextField usuario = new JTextField(16);
        usuario.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(usuario, gbc);

        JLabel labelPassword = new JLabel("Contraseña");
        labelPassword.setFont(new Font("Arial", Font.BOLD, 12));
        labelPassword.setForeground(Color.DARK_GRAY);
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelPassword, gbc);

        JTextField password = new JTextField(16);
        password.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 25, 0);
        panel.add(password, gbc);

        JLabel estado = new JLabel("");
        estado.setFont(fuenteLabels);
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(estado, gbc);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBotones.setOpaque(false);

        JButton btnVolver = crearBoton("Volver");
        btnVolver.setBackground(new Color(100, 116, 139));
        btnVolver.addActionListener(e -> transicionar(new VistaAdmin()));

        JButton btnAceptar = crearBoton("Aceptar");
        btnAceptar.addActionListener(e -> {
            Cliente cliente = new Cliente(usuario.getText());
            cliente.setPassword(password.getText());
            try {
                servicioUsuarios.crearUsuario(cliente);
                estado.setText("Usuario "+cliente.getUsername()+" creado!");
                estado.setForeground(Color.GREEN);
            } catch (Exception e2) {
                estado.setText("Error: "+e2.getMessage());
                estado.setForeground(Color.RED);
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnAceptar);

        gbc.gridy = 7;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(panelBotones, gbc);

        return panel;
    }
}