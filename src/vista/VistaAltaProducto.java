package vista;

import entidades.productos.CajaAhorro;
import entidades.productos.ProductoAbstracto;
import entidades.productos.Tarjeta;
import entidades.usuarios.Cliente;
import entidades.usuarios.Usuario;
import persistencia.EscribirException;
import persistencia.UsuarioInexistente;
import servicio.ServicioProductos;
import servicio.ServicioUsuarios;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class VistaAltaProducto extends VistaAbstracta {

    private final ServicioUsuarios servicioUsuarios = new ServicioUsuarios();
    private final ServicioProductos servicioProductos = new ServicioProductos();
    private Cliente cliente;

    @Override
    public JPanel display() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        Font fuenteLabels = new Font("Arial", Font.BOLD, 12);

        JLabel labelTitulo = new JLabel("Alta de Producto", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add(labelTitulo, gbc);

        JLabel labelSubtitulo = new JLabel("MandarinaX", SwingConstants.CENTER);
        labelSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        labelSubtitulo.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(labelSubtitulo, gbc);

        JLabel labelBuscar = new JLabel("Buscar Usuario Asociado");
        labelBuscar.setFont(fuenteLabels);
        labelBuscar.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelBuscar, gbc);

        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        panelBusqueda.setOpaque(false);
        GridBagConstraints gbcBusqueda = new GridBagConstraints();
        gbcBusqueda.fill = GridBagConstraints.BOTH;

        JTextField txtBuscarUsuario = new JTextField();
        txtBuscarUsuario.setPreferredSize(new Dimension(0, 35));
        gbcBusqueda.gridx = 0;
        gbcBusqueda.gridy = 0;
        gbcBusqueda.weightx = 0.7;
        gbcBusqueda.insets = new Insets(0, 0, 0, 8);
        panelBusqueda.add(txtBuscarUsuario, gbcBusqueda);

        JButton btnBuscar = crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(0, 35));
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        gbcBusqueda.gridx = 1;
        gbcBusqueda.gridy = 0;
        gbcBusqueda.weightx = 0.3;
        gbcBusqueda.insets = new Insets(0, 0, 0, 0);
        panelBusqueda.add(btnBuscar, gbcBusqueda);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 4, 0);
        panel.add(panelBusqueda, gbc);

        JLabel estadoUsuario = new JLabel(" ");
        estadoUsuario.setFont(fuenteLabels);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 2, 12, 0);
        panel.add(estadoUsuario, gbc);

        JLabel labelProducto = new JLabel("Seleccionar Producto");
        labelProducto.setFont(fuenteLabels);
        labelProducto.setForeground(Color.DARK_GRAY);
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelProducto, gbc);

        String[] productosDisponibles = { "Seleccione un producto...", "Caja de Ahorros", "Tarjeta Mandarina Gold" };
        JComboBox<String> comboProductos = new JComboBox<>(productosDisponibles);
        comboProductos.setPreferredSize(new Dimension(0, 35));
        comboProductos.setBackground(Color.WHITE);
        comboProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(comboProductos, gbc);

        JLabel estadoAlta = new JLabel(" ");
        estadoAlta.setFont(fuenteLabels);
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 2, 12, 0);
        panel.add(estadoAlta, gbc);

        JLabel labelAlias = new JLabel("Alias");
        labelAlias.setFont(fuenteLabels);
        labelAlias.setForeground(Color.DARK_GRAY);
        labelAlias.setVisible(false);
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelAlias, gbc);

        JTextField textAlias = new JTextField(16);
        textAlias.setPreferredSize(new Dimension(0, 35));
        textAlias.setVisible(false);
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 25, 0);
        panel.add(textAlias, gbc);

        JLabel limiteLabel = new JLabel("Limite");
        limiteLabel.setVisible(false);
        limiteLabel.setFont(fuenteLabels);
        limiteLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 0, 2, 0);

        panel.add(limiteLabel, gbc);
        JSpinner limite = new JSpinner();
        limite.setVisible(false);
        limite.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 25, 0);
        panel.add(limite, gbc);

        comboProductos.addActionListener(e -> {
            boolean isCuenta = comboProductos.getSelectedIndex() == 1;
            textAlias.setVisible(isCuenta);
            labelAlias.setVisible(isCuenta);
            limite.setVisible(!isCuenta);
            limiteLabel.setVisible(!isCuenta);
        });

        btnBuscar.addActionListener(e -> {
            String user = txtBuscarUsuario.getText().trim();
            if (user.isEmpty()) {
                estadoUsuario.setText("Por favor, ingrese un usuario.");
                estadoUsuario.setForeground(Color.RED);
            } else {
                try {
                    Usuario usuario = servicioUsuarios.buscarPorNombre(user);
                    if(usuario instanceof Cliente cl) {
                        this.cliente = cl;
                        estadoUsuario.setText("Usuario encontrado");
                        estadoUsuario.setForeground(Color.GREEN);
                    }else{
                        estadoUsuario.setText("Este usuario no es un cliente");
                        estadoUsuario.setForeground(Color.RED);
                    }
                } catch (UsuarioInexistente ex) {
                    estadoUsuario.setText("Usuario no encontrado");
                    estadoUsuario.setForeground(Color.RED);
                } catch (Exception ex){
                    estadoUsuario.setText("Error inesperado: "+ex.getMessage());
                    estadoUsuario.setForeground(Color.RED);
                }
            }
        });

        JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelAcciones.setOpaque(false);

        JButton btnVolver = crearBoton("Volver");
        btnVolver.setBackground(new Color(100, 116, 139));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> transicionar(new VistaAdmin()));

        JButton btnAceptar = crearBoton("Aceptar");
        btnAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(e -> {
            if (cliente == null) {
                estadoAlta.setText("No se seleccionó un usuario");
                estadoAlta.setForeground(Color.RED);
                return;
            }

            switch (comboProductos.getSelectedIndex()) {
                case 0:
                    estadoAlta.setText("No se seleccionó un producto");
                    estadoAlta.setForeground(Color.RED);
                    return;
                case 1:
                    CajaAhorro cuenta = new CajaAhorro();
                    cuenta.setAlias(textAlias.getText());
                    cliente.agregarProducto(cuenta);
                    try {
                        servicioProductos.escribirProducto(cuenta);
                        estadoAlta.setText("Cuenta creada");
                        estadoAlta.setForeground(Color.GREEN);
                    } catch (EscribirException ex) {
                        estadoAlta.setText("Error escribiendo: "+ex.getMessage());
                        estadoAlta.setForeground(Color.RED);
                    }

                    break;
                case 2:
                    String numeroTarjeta = UUID.randomUUID().toString();
                    Tarjeta tarjeta = new Tarjeta(numeroTarjeta, 1235, 583);
                    tarjeta.acreditar((int)limite.getValue());
                    cliente.agregarProducto(tarjeta);
                    try {
                        servicioProductos.escribirProducto(tarjeta);
                        estadoAlta.setText("Tarjeta creada: "+numeroTarjeta);
                        estadoAlta.setForeground(Color.GREEN);
                    } catch (EscribirException ex) {
                        estadoAlta.setText("Error escribiendo: "+ex.getMessage());
                        estadoAlta.setForeground(Color.RED);
                    }
                    break;
            }
        });

        panelAcciones.add(btnVolver);
        panelAcciones.add(btnAceptar);

        gbc.gridy = 10;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(panelAcciones, gbc);

        return panel;
    }
}