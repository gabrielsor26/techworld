package com.techclothes.model;

public class Usuario {

    private String name, email, password;
    private int rol;

    public Usuario() {

    }

    public Usuario(String name, String email, String password, int rol) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.rol = rol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getRol() {
        return rol;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
}
