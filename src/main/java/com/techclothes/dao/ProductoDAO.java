package com.techclothes.dao;

import com.techclothes.model.Producto;
import com.techclothes.model.Categoria;
import com.techclothes.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection connection;

    public ProductoDAO() {
        this.connection = Conexion.getInstance().getConexion();
    }

    // Listar todos los productos
    public List<Producto> listarProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT p.*, c.nombre AS categoriaNombre FROM productos p "
                + "JOIN categorias c ON p.categoria_id = c.id";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("categoria_id"), rs.getString("categoriaNombre"));
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("imagen_url"),
                        categoria, // Instancia de Categoria
                        rs.getInt("stock")
                );
                productos.add(producto);
            }
        }
        return productos;
    }

    // Obtener un producto por ID
    public Producto obtenerProducto(int id) throws SQLException {
        String query = "SELECT p.*, c.nombre AS categoriaNombre FROM productos p "
                + "JOIN categorias c ON p.categoria_id = c.id WHERE p.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt("categoria_id"), rs.getString("categoriaNombre"));
                    return new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("imagen_url"),
                            categoria, // Instancia de Categoria
                            rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    // Agregar un producto
    public void agregarProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO productos (nombre, descripcion, precio, imagen_url, categoria_id, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getImagenUrl());
            stmt.setInt(5, producto.getCategoria().getId());  // Usar el ID de la Categoria
            stmt.setInt(6, producto.getStock());
            stmt.executeUpdate();
        }
    }

    // Actualizar un producto
    public void actualizarProducto(Producto producto) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, imagen_url = ?, categoria_id = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getImagenUrl());
            stmt.setInt(5, producto.getCategoria().getId());  // Usar el ID de la Categoria
            stmt.setInt(6, producto.getStock());
            stmt.setInt(7, producto.getId());
            stmt.executeUpdate();
        }
    }

    // Eliminar un producto
    public void eliminarProducto(int id) throws SQLException {
        String query = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // ProductoDAO.java
    public List<Producto> listarProductosPorCategoria(String categoriaNombre) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos p JOIN categorias c ON p.categoria_id = c.id WHERE c.nombre = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categoriaNombre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("imagen_url"),
                            new Categoria(rs.getInt("categoria_id"), rs.getString("nombre")), // Crear la categoría
                            rs.getInt("stock")
                    );
                    productos.add(producto);
                }
            }
        }
        return productos;
    }

}
