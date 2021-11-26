package com.example.projetofirestore.Models;

import com.google.firebase.Timestamp;

public class DTOUsuario {
    private String nomeUsuario;
    private String sobrenome;
    private String emailUsuario;
    private String imagemUsuario;
    private Timestamp dataCadastro;
    private String sexo;
    private Boolean admin;

    public DTOUsuario(String nomeUsuario, String sobrenome, String emailUsuario, String imagemUsuario, Timestamp dataCadastro, String sexo, Boolean admin){
        this.nomeUsuario = nomeUsuario;
        this.sobrenome = sobrenome;
        this.emailUsuario = emailUsuario;
        this.imagemUsuario = imagemUsuario;
        this.dataCadastro = dataCadastro;
        this.sexo = sexo;
        this.admin = admin;
    }

    public DTOUsuario() {
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getImagemUsuario() {
        return imagemUsuario;
    }

    public void setImagemUsuario(String imagemUsuario) {
        this.imagemUsuario = imagemUsuario;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Timestamp dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
