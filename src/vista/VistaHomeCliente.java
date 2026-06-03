package vista;

import entidades.productos.Cuenta;
import entidades.productos.ProductoAbstracto;
import entidades.productos.Tarjeta;
import entidades.usuarios.Cliente;
import persistencia.LeerException;
import servicio.ServicioProductos;

import javax.swing.*;
import java.awt.*;

public class VistaHomeCliente extends VistaAbstracta {
    private Cliente cliente;
    private final ServicioProductos servicioProductos = new ServicioProductos();

    public VistaHomeCliente(Cliente cliente){
        try {
            servicioProductos.cargarProductos(cliente);
        } catch (LeerException e) {
            throw new RuntimeException(e);
        }
        this.cliente = cliente;
    }

    @Override
    public Dimension getScreenSize() {
        return new Dimension(640, 680);
    }

    @Override
    public JPanel display() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);

        JPanel panelTextoTop = new JPanel();
        panelTextoTop.setLayout(new BoxLayout(panelTextoTop, BoxLayout.Y_AXIS));
        panelTextoTop.setOpaque(false);

        JLabel labelSaludo = new JLabel("¡Hola, " + cliente.getUsername() + "!", SwingConstants.LEFT);
        labelSaludo.setFont(new Font("Arial", Font.BOLD, 22));
        panelTextoTop.add(labelSaludo);

        panelTextoTop.add(Box.createVerticalStrut(2));
        JLabel labelSubtitulo = new JLabel("Bienvenido a tu MandarinaX", SwingConstants.LEFT);
        labelSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        labelSubtitulo.setForeground(Color.GRAY);
        panelTextoTop.add(labelSubtitulo);

        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 11));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(100, 116, 139));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            transicionar(new VistaLogin());
        });

        panelTop.add(panelTextoTop, BorderLayout.WEST);
        panelTop.add(btnLogout, BorderLayout.EAST);

        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 20, 0);
        panel.add(panelTop, gbc);

        JButton btnTransferir = crearBoton("Transferir Dinero");
        btnTransferir.setPreferredSize(new Dimension(0, 48));
        btnTransferir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTransferir.addActionListener(e -> {
            transicionar(new VistaTransferencia(this.cliente));
        });
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        panel.add(btnTransferir, gbc);

        JLabel labelSeccion = new JLabel("Mis Productos");
        labelSeccion.setFont(new Font("Arial", Font.BOLD, 14));
        labelSeccion.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 12, 0);
        panel.add(labelSeccion, gbc);

        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setOpaque(false);

        for (ProductoAbstracto productoAbstracto : cliente.getProductos()) {
            panelProductos.add(createProducto(productoAbstracto));
            panelProductos.add(Box.createVerticalStrut(12));
        }
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(panelProductos, gbc);

        return panel;
    }

    private JPanel createProducto(ProductoAbstracto producto) {
        JPanel card = new JPanel(new BorderLayout(15, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        card.setMaximumSize(new Dimension(Short.MAX_VALUE, 135));

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setOpaque(false);

        JLabel lblTipo = new JLabel(producto.getNombre());
        lblTipo.setFont(fuenteLabels);
        lblTipo.setForeground(new Color(15, 23, 42));
        panelIzquierdo.add(lblTipo);

        JLabel lblDisponible = new JLabel("$" + producto.getSaldo());
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setOpaque(false);

        if (producto instanceof Cuenta cuenta) {
            panelIzquierdo.add(Box.createVerticalStrut(4));
            JLabel lblCbu = new JLabel(cuenta.getCBU());
            lblCbu.setFont(fuenteLabels);
            lblCbu.setForeground(Color.GRAY);
            panelIzquierdo.add(lblCbu);

            panelIzquierdo.add(Box.createVerticalStrut(2));
            JLabel lblAlias = new JLabel(cuenta.getAlias());
            lblAlias.setFont(fuenteLabels);
            lblAlias.setForeground(Color.GRAY);
            panelIzquierdo.add(lblAlias);
        } else if (producto instanceof Tarjeta tarjeta) {
            lblDisponible = new JLabel("Disponible: $" + producto.getSaldo());
            panelIzquierdo.add(Box.createVerticalStrut(4));
            JLabel lblCbu = new JLabel(tarjeta.getNumeroTarjeta());
            lblCbu.setFont(fuenteLabels);
            lblCbu.setForeground(Color.GRAY);
            panelIzquierdo.add(lblCbu);

            JLabel lblTotalPagar = new JLabel("Total a pagar: $0.00");
            lblTotalPagar.setFont(fuenteLabels);
            lblTotalPagar.setForeground(Color.GRAY);
            lblTotalPagar.setAlignmentX(Component.RIGHT_ALIGNMENT);
            panelDerecho.add(lblTotalPagar);
        }

        panelIzquierdo.add(Box.createVerticalStrut(8));
        JPanel panelBotonesCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBotonesCard.setOpaque(false);

        if (producto instanceof Cuenta cuenta){
            JButton btnTransacciones = new JButton("Ver Transacciones");
            btnTransacciones.setFont(new Font("Arial", Font.BOLD, 11));
            btnTransacciones.setForeground(Color.WHITE);
            btnTransacciones.setBackground(new Color(100, 116, 139));
            btnTransacciones.setFocusPainted(false);
            btnTransacciones.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            btnTransacciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnTransacciones.addActionListener(e -> {
                transicionar(new VistaTransacciones(cliente, cuenta));
            });
            panelBotonesCard.add(btnTransacciones);
        }

        panelIzquierdo.add(panelBotonesCard);

        lblDisponible.setFont(fuenteLabels);
        lblDisponible.setAlignmentX(Component.RIGHT_ALIGNMENT);

        if (producto instanceof Cuenta) {
            lblDisponible.setForeground(new Color(21, 128, 61));
        } else {
            lblDisponible.setForeground(Color.DARK_GRAY);
        }
        panelDerecho.add(lblDisponible);

        panelDerecho.add(Box.createVerticalStrut(4));

        card.add(panelIzquierdo, BorderLayout.WEST);
        card.add(panelDerecho, BorderLayout.EAST);

        return card;
    }
}