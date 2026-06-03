package vista;

import entidades.movimientos.SaldoInsuficienteException;
import entidades.productos.Cuenta;
import entidades.productos.ProductoAbstracto;
import entidades.usuarios.Cliente;
import persistencia.CuentaInexistente;
import servicio.ServicioTransferencias;
import servicio.TransaccionException;

import javax.swing.*;
import java.awt.*;

public class VistaTransferencia extends VistaAbstracta {
    private Cliente cliente;

    private final ServicioTransferencias servicioTransferencias = new ServicioTransferencias();

    public VistaTransferencia(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public Dimension getScreenSize() {
        return new Dimension(520, 650);
    }

    @Override
    public JPanel display() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel labelTitulo = new JLabel("Transferir Dinero", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 2, 0);
        panel.add(labelTitulo, gbc);

        JLabel labelSubtitulo = new JLabel("MandarinaX", SwingConstants.CENTER);
        labelSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        labelSubtitulo.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        panel.add(labelSubtitulo, gbc);

        JLabel labelOrigen = new JLabel("Seleccionar Cuenta de Origen");
        labelOrigen.setFont(fuenteLabels);
        labelOrigen.setForeground(Color.DARK_GRAY);
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 4, 0);
        panel.add(labelOrigen, gbc);

        JComboBox<String> comboCuentas = new JComboBox<>();
        comboCuentas.setPreferredSize(new Dimension(0, 40));
        comboCuentas.setBackground(Color.WHITE);
        comboCuentas.setFont(new Font("Arial", Font.PLAIN, 13));

        for (ProductoAbstracto prod : cliente.getProductos()) {
            if (prod instanceof Cuenta cuenta) {
                comboCuentas.addItem(cuenta.getNombre() + " (" + cuenta.getCBU().substring(0, 4) + "...) - $" + cuenta.getSaldo());
            }
        }
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(comboCuentas, gbc);

        JLabel labelDestino = new JLabel("CBU, ID o alias");
        labelDestino.setFont(fuenteLabels);
        labelDestino.setForeground(Color.DARK_GRAY);
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 4, 0);
        panel.add(labelDestino, gbc);

        JTextField txtDestino = new JTextField();
        txtDestino.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(txtDestino, gbc);

        JLabel labelMonto = new JLabel("Monto a Transferir");
        labelMonto.setFont(fuenteLabels);
        labelMonto.setForeground(Color.DARK_GRAY);
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 0, 4, 0);
        panel.add(labelMonto, gbc);

        JTextField txtMonto = new JTextField();
        txtMonto.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(txtMonto, gbc);

        JLabel labelError = new JLabel(" ");
        labelError.setFont(new Font("Arial", Font.BOLD, 12));
        labelError.setForeground(new Color(220, 38, 38)); // Rojo para errores
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 2, 15, 0);
        panel.add(labelError, gbc);

        JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelAcciones.setOpaque(false);

        JButton btnVolver = crearBoton("Volver");
        btnVolver.setBackground(new Color(100, 116, 139));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> transicionar(new VistaHomeCliente(cliente)));

        JButton btnConfirmar = crearBoton("Confirmar");
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(e -> {
            String destino = txtDestino.getText().trim();
            String montoStr = txtMonto.getText().trim();

            try {
                if (comboCuentas.getSelectedItem() == null) throw new DatosIncorrectos("Cuenta invalida");
                if (destino.isEmpty()) throw new DatosIncorrectos("Destino invalido");
                if (montoStr.isEmpty()) throw new DatosIncorrectos("Monto vacio");

                try {
                    float monto = Float.parseFloat(montoStr);
                    if (monto < 0) {
                        throw new DatosIncorrectos("Monto menor a 0");
                    }
                    Cuenta origen = (Cuenta) cliente.getProductos().get(comboCuentas.getSelectedIndex());
                    servicioTransferencias.transferir(origen, destino, monto);

                    labelError.setForeground(Color.GREEN);
                    labelError.setText("Transferencia enviada exitosamente!");
                } catch (NumberFormatException ex2) {
                    throw new DatosIncorrectos("Monto invalido");
                } catch (SaldoInsuficienteException ex) {
                    throw new DatosIncorrectos("Fondos insuficientes");
                } catch (CuentaInexistente | TransaccionException ex) {
                    throw new DatosIncorrectos(ex.getMessage());
                }
            }catch (DatosIncorrectos ex){
                labelError.setForeground(Color.RED);
                labelError.setText("Error: "+ex.getMessage());
            }
        });

        panelAcciones.add(btnVolver);
        panelAcciones.add(btnConfirmar);

        gbc.gridy = 9;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(panelAcciones, gbc);

        return panel;
    }
}