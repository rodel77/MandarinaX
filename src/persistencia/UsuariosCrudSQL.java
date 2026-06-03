package persistencia;

import entidades.usuarios.Admin;
import entidades.usuarios.Cliente;
import entidades.usuarios.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosCrudSQL extends SQLDB implements UsuarioDAO {
    @Override
    public Usuario buscarPorNombre(String username) throws LeerException {
        try (ResultSet set = selectSql("select id,username,password,tipo from usuarios where username = ?", username)) {
            if (set.next()) {
                Usuario usuario;
                String tipo = set.getString(4);
                if(tipo.equals("admin")){
                    usuario = new Admin(set.getString(2));
                }else{
                    usuario = new Cliente(set.getString(2));
                }
                usuario.setId(set.getInt(1));
                usuario.setPassword(set.getString(3));
                return usuario;
            }else{
                throw new UsuarioInexistente("Usuario no encontrado");
            }
        } catch(SQLException e){
            throw new LeerException(e.getMessage());
        }
    }

    @Override
    public void escribir(Usuario usuario) throws EscribirException {
        if (usuario instanceof Admin) {
            throw new EscribirException("No se puede crear usuario tipo admin");
        }

        try {
            updateSql("insert into usuarios (username, password, tipo) values (?, ?, ?)", usuario.getUsername(), usuario.getPassword(), "cliente");
        } catch(SQLException e){
            throw new EscribirException(e.getMessage());
        }
    }
}
