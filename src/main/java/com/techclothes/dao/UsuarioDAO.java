package com.techclothes.dao;
import com.techclothes.model.Categoria;
import com.techclothes.model.Producto;
import com.techclothes.model.Usuario;
import com.techclothes.util.Conexion;
import jakarta.servlet.jsp.tagext.TryCatchFinally;

import java.sql.*;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO() {
        this.connection = Conexion.getInstance().getConexion();
    }

    public Usuario verificarUsuario(String email, String password) throws SQLException{

        String query = "SELECT * FROM usuarios WHERE email = ? AND contraseña = ?";

        Usuario usuario = null;

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,email );
            stmt.setString(2,password );

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    usuario = new Usuario(rs.getString("nombre"),
                        rs.getString("email"),rs.getString("contraseña")
                    );

                }
            }
        }

        return  usuario;
    }
}
