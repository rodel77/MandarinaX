package vista;

import entidades.movimientos.Movimiento;
import entidades.productos.Cuenta;
import entidades.usuarios.Cliente;
import persistencia.LeerException;
import servicio.ServicioProductos;

import javax.swing.*;
import java.awt.*;

public class VistaTransacciones extends VistaAbstracta {
    private Cliente cliente;
    private Cuenta cuenta;

    private final ServicioProductos servicioProductos = new ServicioProductos();

    public VistaTransacciones(Cliente cliente, Cuenta cuenta) {
        this.cliente = cliente;
        this.cuenta = cuenta;
        try {
            servicioProductos.cargarTransferencias(cuenta);
        } catch (LeerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dimension getScreenSize() {
        return new Dimension(540, 700);
    }

    @Override
    public JPanel display() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel labelTitulo = new JLabel("Historial de Transacciones", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelTitulo, gbc);

        JLabel labelSubtitulo = new JLabel(cuenta.getNombre() + " - CBU: " + cuenta.getCBU() + " - Alias: " + cuenta.getAlias(), SwingConstants.CENTER);
        labelSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        labelSubtitulo.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(labelSubtitulo, gbc);

        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setOpaque(false);

        for(Movimiento movimiento : cuenta.getMovimientos()) {
            panelLista.add(createTarjetaTransaccion(movimiento));
            panelLista.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(panelLista);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(scrollPane, gbc);

        JButton btnVolver = crearBoton("Volver al Home");
        btnVolver.setBackground(new Color(100, 116, 139));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> transicionar(new VistaHomeCliente(cliente)));

        gbc.gridy = 3;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    private JPanel createTarjetaTransaccion(Movimiento movimiento) {
        JPanel card = new JPanel(new BorderLayout(15, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(241, 245, 249), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setMaximumSize(new Dimension(Short.MAX_VALUE, 65));

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setOpaque(false);

        JLabel lblMotivo = new JLabel("Transferencia");
        lblMotivo.setFont(fuenteLabels);
        lblMotivo.setForeground(new Color(15, 23, 42));
        panelIzquierdo.add(lblMotivo);

        panelIzquierdo.add(Box.createVerticalStrut(2));
        JLabel lblFecha = new JLabel(movimiento.getFecha().toString());
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFecha.setForeground(Color.GRAY);
        panelIzquierdo.add(lblFecha);

        JLabel lblMonto;
        if (movimiento.getTipo() == Movimiento.TipoMovimiento.CREDITO) {
            lblMonto = new JLabel("$"+movimiento.getCantidad());
            lblMonto.setForeground(new Color(21, 128, 61));
        } else {
            lblMonto = new JLabel("-$"+movimiento.getCantidad());
            lblMonto.setForeground(new Color(220, 38, 38));
        }
        lblMonto.setFont(fuenteLabels);

        card.add(panelIzquierdo, BorderLayout.WEST);
        card.add(lblMonto, BorderLayout.EAST);

        return card;
    }
}