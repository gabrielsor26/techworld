<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page session="true" %>
<%
    String nombre = (String) session.getAttribute("nombre");
%>
<!DOCTYPE html>
<html lang="es" data-theme="light">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>TechClothes - Catálogo</title>

        <!-- Tailwind CSS y DaisyUI CDN -->
        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css" rel="stylesheet" type="text/css" />
        <script>
            tailwind.config = {
                theme: {
                    extend: {
                        colors: {
                            background: '#FCFBFA',
                            primary: '#1F7BDD',
                            secondary: '#98C8ED',
                            accent: '#8595BF',
                            hoverAccent: '#7396D0',
                        }
                    }
                },
                daisyui: {
                    themes: ["light"],
                }
            };
        </script>
    </head>

    <body class="bg-background text-gray-900 font-sans">

        <!-- Header -->
        <nav class="bg-primary p-4 shadow-lg">
            <div class="container mx-auto flex justify-between items-center">
                <a href="index.jsp" class="text-3xl font-bold text-white">TechClothes</a>
                <div>
                    <a href="index.jsp" class="text-white mx-3 hover:text-secondary">Inicio</a>
                    <a href="ProductoController?action=listar&view=catalogo" class="text-white mx-3 hover:text-secondary">Catálogo</a>
                    <a href="carrito.html" class="text-white mx-3 hover:text-secondary">Carrito</a>
                    <a href="ProductoController?action=listar&view=mantenimiento" class="text-white mx-3 hover:text-secondary">Mantenimiento</a>
                    <%
                        if (nombre == null) {
                    %>
                    <!-- Mostrar "Iniciar sesión" si no hay usuario en sesión -->
                    <a href="SignInController?action=listar&view=SignIn" class="rounded p-2 text-black mx-3 hover:text-primary bg-background">Iniciar sesión</a>
                    <%
                    } else {
                    %>
                    <!-- Mostrar icono de usuario y opción para cerrar sesión -->
                    <a href="UserProfileController" class="text-white mx-3 hover:text-secondary">
                        <i class="fas fa-user"></i> <%= nombre %>
                    </a>
                    <a href="LogOutController" class="rounded p-2 text-black mx-3 hover:text-primary bg-background">Cerrar sesión</a>
                    <%
                        }
                    %>
                </div>
            </div>
        </nav>

        <section class="container mx-auto py-10">
            <div class="text-center mb-10">
                <h2 class="text-5xl font-bold text-primary mb-6">Catálogo de Productos</h2>
                <p class="text-lg text-gray-700">Explora nuestra colección y encuentra tu estilo ideal</p>
            </div>

            <!-- Filtro de Categorías con Enlaces -->
            <div class="flex justify-center space-x-6 overflow-x-auto mb-8 px-4 py-2">
                <a href="ProductoController?action=listar&view=catalogo&categoria=Todos" class="text-center focus:outline-none">
                    <img src="http://localhost:8080/imagenes/habilidadlogica.png" alt="Todos"
                         class="w-36 h-36 rounded-full mb-2 shadow-lg transition-transform transform hover:scale-110">
                    <p class="text-gray-700 font-semibold">Todos</p>
                </a>
                <a href="ProductoController?action=listar&view=catalogo&categoria=Polos" class="text-center focus:outline-none">
                    <img src="http://localhost:8080/imagenes/fisica.png" alt="Polos"
                         class="w-36 h-36 rounded-full mb-2 shadow-lg transition-transform transform hover:scale-110">
                    <p class="text-gray-700 font-semibold">Polos</p>
                </a>
                <a href="ProductoController?action=listar&view=catalogo&categoria=Poleras" class="text-center focus:outline-none">
                    <img src="http://localhost:8080/imagenes/habilidadlogica.png" alt="Poleras"
                         class="w-36 h-36 rounded-full mb-2 shadow-lg transition-transform transform hover:scale-110">
                    <p class="text-gray-700 font-semibold">Poleras</p>
                </a>
                <a href="ProductoController?action=listar&view=catalogo&categoria=Shorts" class="text-center focus:outline-none">
                    <img src="http://localhost:8080/imagenes/algebra.png" alt="Shorts"
                         class="w-36 h-36 rounded-full mb-2 shadow-lg transition-transform transform hover:scale-110">
                    <p class="text-gray-700 font-semibold">Shorts</p>
                </a>
                <a href="ProductoController?action=listar&view=catalogo&categoria=Pantalones" class="text-center focus:outline-none">
                    <img src="http://localhost:8080/imagenes/quimica.png" alt="Pantalones"
                         class="w-36 h-36 rounded-full mb-2 shadow-lg transition-transform transform hover:scale-110">
                    <p class="text-gray-700 font-semibold">Pantalones</p>
                </a>
                <a href="ProductoController?action=listar&view=catalogo&categoria=Gorras" class="text-center focus:outline-none">
                    <img src="http://localhost:8080/imagenes/habilidadverbal.png" alt="Gorras"
                         class="w-36 h-36 rounded-full mb-2 shadow-lg transition-transform transform hover:scale-110">
                    <p class="text-gray-700 font-semibold">Gorras</p>
                </a>
            </div>
        </section>


        <!-- Ordenar Productos -->
        <section class="container mx-auto px-4 mb-8">
            <label class="block text-gray-700 font-medium mb-2">Ordenar por:</label>
            <select id="ordenar" class="select select-bordered w-full max-w-xs" onchange="ordenarProductos()">
                <option value="recomendados">Recomendados</option>
                <option value="menor-mayor">Precio: Menor a Mayor</option>
                <option value="mayor-menor">Precio: Mayor a Menor</option>
            </select>
        </section>

        <!-- Sección de Productos -->
        <section class="container mx-auto pb-16 px-4">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8" id="productContainer">
                <c:forEach var="producto" items="${productos}">
                    <div class="producto-card ${producto.categoria.nombre} bg-white border rounded-lg overflow-hidden shadow-lg transition-transform transform duration-300 hover:scale-105">
                        <img src="${producto.imagenUrl}" alt="${producto.nombre}" class="w-full h-80 object-cover transition-transform transform hover:scale-110">
                        <div class="p-4">
                            <h3 class="text-lg font-semibold text-primary mb-1">${producto.nombre}</h3>
                            <p class="text-sm text-gray-600 mb-2">Por TechClothes</p>
                            <p class="text-gray-700 text-sm mb-4">${producto.descripcion}</p>
                            <p class="text-xl font-bold text-gray-900 mb-4">S/. ${producto.precio}</p>
                            <button class="w-full bg-primary hover:bg-hoverAccent text-white py-2 rounded-md font-medium shadow-lg transform transition-transform hover:scale-105 duration-300">
                                Añadir al carrito
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!-- Footer -->
        <footer class="bg-accent text-white py-8">
            <div class="container mx-auto text-center">
                <p class="text-lg">&copy; 2024 TechClothes. Todos los derechos reservados.</p>
            </div>
        </footer>

        <!-- JavaScript para Ordenar Productos -->
        <script>
            function ordenarProductos() {
                const contenedor = document.getElementById("productContainer");
                const productos = Array.from(contenedor.querySelectorAll(".producto-card"));
                const orden = document.getElementById("ordenar").value;

                productos.sort((a, b) => {
                    const precioA = parseFloat(a.querySelector(".text-xl").textContent.replace("S/. ", ""));
                    const precioB = parseFloat(b.querySelector(".text-xl").textContent.replace("S/. ", ""));

                    if (orden === "menor-mayor") {
                        return precioA - precioB;
                    } else if (orden === "mayor-menor") {
                        return precioB - precioA;
                    }
                    return 0;
                });

                productos.forEach(producto => contenedor.appendChild(producto));
            }
        </script>

    </body>
</html>
