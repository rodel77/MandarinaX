package vista;

import entidades.usuarios.Usuario;
import persistencia.LeerException;
import persistencia.UsuarioInexistente;
import servicio.InvalidPassword;
import servicio.ServicioUsuarios;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends VistaAbstracta {
    private ServicioUsuarios loginServicio = new ServicioUsuarios();

    @Override
    JPanel display() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel labelTitulo = createTitulo();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0); // Espacio grande abajo del título
        panel.add(labelTitulo, gbc);

        JLabel labelUsuario = new JLabel("Usuario");
        labelUsuario.setFont(fuenteLabels);
        labelUsuario.setForeground(Color.DARK_GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelUsuario, gbc);

        JTextField usuario = new JTextField(16);
        usuario.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 12, 0);
        panel.add(usuario, gbc);

        JLabel labelPassword = new JLabel("Password");
        labelPassword.setFont(fuenteLabels);
        labelPassword.setForeground(Color.DARK_GRAY);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelPassword, gbc);

        JPasswordField password = new JPasswordField(16);
        password.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(password, gbc);

        JLabel errorMsg = new JLabel("");
        errorMsg.setFont(fuenteLabels);
        errorMsg.setForeground(Color.RED);
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(errorMsg, gbc);

        JButton login = crearBoton("Login");
        login.addActionListener(e -> {
            try {
                Usuario user = loginServicio.login(usuario.getText(), password.getText());

                transicionar(user.createVistaInicial());
            } catch(UsuarioInexistente | InvalidPassword e2){
                errorMsg.setText("Usuario o contraseña invalidos");
            } catch (LeerException ex) {
                errorMsg.setText("Error inesperado");
                ex.printStackTrace();
            }
        });
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(login, gbc);

        return panel;
    }
}
