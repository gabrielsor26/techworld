package com.techclothes.controller;


import com.techclothes.dao.UsuarioDAO;
import com.techclothes.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SignInController", urlPatterns = {"/SignInController"})
public class SignInController extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session = request.getSession(false); // Obtener la sesión sin crear una nueva

        if (session != null && session.getAttribute("nombre") != null) {
            // Si la sesión existe y hay un usuario autenticado
            response.sendRedirect("index.jsp"); // Redirigir al dashboard o a la página principal
        } else {
            // Si no hay sesión o usuario autenticado, mostrar la página de inicio de sesión
            handleRequest(request, response);
        }
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String view = request.getParameter("view");

        try {
            switch (action == null ? "" : action) {
                case "listar":
                default:
                    if ("SignIn".equals(view)) {
                        mostrarLogIn(request, response);
                    }
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar la solicitud de categorías", e);
        }
    }

    private void mostrarLogIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.getRequestDispatcher("views/signIn.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario;

        try {
            usuario = usuarioDAO.verificarUsuario(email, password);
            if (usuario != null) {
                HttpSession session = req.getSession();
                session.setAttribute("nombre", usuario.getName());
                session.setAttribute("rol", usuario.getRol());
                resp.sendRedirect("index.jsp");



            }else {
                req.setAttribute("error", "Credenciales incorrectas");
                req.getRequestDispatcher("/views/signIn.jsp").forward(req, resp);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
