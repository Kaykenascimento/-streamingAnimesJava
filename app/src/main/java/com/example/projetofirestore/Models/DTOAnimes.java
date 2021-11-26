package com.example.projetofirestore.Models;

public class DTOAnimes {
    private String nomeAnime;
    private String imagem;
    private String ano;
    private String sinopse;
    private String estudio;
    private String genero;
    private String codigo;
    private String nomeAnimeInsensitive;

    public DTOAnimes(String nomeAnime, String imagem, String ano, String sinopse, String estudio, String genero, String codigo, String nomeAnimeInsensitive) {
        this.nomeAnime = nomeAnime;
        this.imagem = imagem;
        this.ano = ano;
        this.sinopse = sinopse;
        this.estudio = estudio;
        this.genero = genero;
        this.codigo = codigo;
        this.nomeAnimeInsensitive = nomeAnimeInsensitive;
    }

    public DTOAnimes() {
    }

    public String getNomeAnime() {
        return nomeAnime;
    }

    public void setNomeAnime(String nomeAnime) {
        this.nomeAnime = nomeAnime;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNomeAnimeInsensitive() {
        return nomeAnimeInsensitive;
    }

    public void setNomeAnimeInsensitive(String nomeAnimeInsensitive) {
        this.nomeAnimeInsensitive = nomeAnimeInsensitive;
    }
}
