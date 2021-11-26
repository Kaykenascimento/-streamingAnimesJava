package com.example.projetofirestore.Models;

public class DTOGeneros {
    private String nomeGenero;
    private String imagem;

    public DTOGeneros(String nomeGenero, String imagem) {
        this.nomeGenero = nomeGenero;
        this.imagem = imagem;
    }

    public DTOGeneros() {
    }

    public String getNomeGenero() {
        return nomeGenero;
    }

    public void setNomeGenero(String nomeGenero) {
        this.nomeGenero = nomeGenero;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
