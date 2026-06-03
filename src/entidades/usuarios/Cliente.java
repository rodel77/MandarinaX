package entidades.usuarios;

import entidades.productos.ProductoAbstracto;
import vista.VistaAbstracta;
import vista.VistaHomeCliente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cliente extends Usuario {
    private List<ProductoAbstracto> productos = new ArrayList<>();

    public Cliente(String nombre) {
        super(nombre);
    }

    public void agregarProducto(ProductoAbstracto producto) {
        producto.setCliente(this);
        productos.add(producto);
    }

    public List<ProductoAbstracto> getProductos() {
        return productos;
    }

    @Override
    public VistaAbstracta createVistaInicial() {
        return new VistaHomeCliente(this);
    }

    public void clearProductos() {
        productos.clear();
    }
}
