package com.bau.merenda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String senha;
    
    // Este campo não será persistido no banco de dados.
    @Transient 
    private String confirmaSenha;

    private String role = "MERENDEIRA";

    // Getters e Setters para todos os campos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getConfirmaSenha() { return confirmaSenha; }
    public void setConfirmaSenha(String confirmaSenha) { this.confirmaSenha = confirmaSenha; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}