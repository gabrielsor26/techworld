package com.techclothes.dao;

import com.techclothes.model.Categoria;
import com.techclothes.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection connection;

    public CategoriaDAO() {
        this.connection = Conexion.getInstance().getConexion();
    }

    // Listar todas las categorías
    public List<Categoria> listarCategorias() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String query = "SELECT * FROM categorias";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    // Obtener una categoría por su ID
    public Categoria obtenerCategoria(int id) throws SQLException {
        Categoria categoria = null;
        String query = "SELECT * FROM categorias WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria(
                            rs.getInt("id"),
                            rs.getString("nombre")
                    );
                }
            }
        }
        return categoria;
    }

    // Agregar una nueva categoría
    public void agregarCategoria(Categoria categoria) throws SQLException {
        String query = "INSERT INTO categorias (nombre) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categoria.getNombre());
            stmt.executeUpdate();
        }
    }

    // Actualizar una categoría existente
    public void actualizarCategoria(Categoria categoria) throws SQLException {
        String query = "UPDATE categorias SET nombre = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());
            stmt.executeUpdate();
        }
    }

    // Eliminar una categoría por ID
    public void eliminarCategoria(int id) throws SQLException {
        String query = "DELETE FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
