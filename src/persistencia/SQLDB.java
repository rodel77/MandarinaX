package persistencia;

import java.sql.*;

public class SQLDB {
    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    private Connection connection;

    protected SQLDB() {
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/mandarinax?useSSL=false&allowPublicKeyRetrieval=true";
        username = "root";
        password = "";
    }

    public SQLDB(String driver, String url, String username, String password){
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private final void cargarDriver(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private final void obtenerConexion() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
    }

    private final void cerrarConexion() throws SQLException {
        connection.close();
    }

    private PreparedStatement preparedStatement(String query, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        int i = 1;
        for(Object param : params) {
            if(param instanceof Integer _param) stmt.setInt(i++, _param);
            else if(param instanceof String _param) stmt.setString(i++, _param);
            else if(param instanceof Double _param) stmt.setDouble(i++, _param);
            else if(param instanceof Float _param) stmt.setFloat(i++, _param);
            else if (param instanceof Long _param) stmt.setLong(i++, _param);
            else throw new IllegalArgumentException("Tipo invalido: "+param);
        }
        return stmt;
    }

    protected final ResultSet selectSql(String sql, Object... params) throws SQLException {
        ResultSet result;
        cargarDriver();
        obtenerConexion();
        PreparedStatement stmt = preparedStatement(sql, params);
        result = stmt.executeQuery();
        return result;
    }

    protected final int updateSql(String sql, Object... params) throws SQLException {
        cargarDriver();
        obtenerConexion();
        try (PreparedStatement stmt = preparedStatement(sql, params)) {
            return stmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }
}
