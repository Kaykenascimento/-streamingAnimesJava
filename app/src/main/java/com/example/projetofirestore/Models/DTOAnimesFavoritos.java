package com.example.projetofirestore.Models;

public class DTOAnimesFavoritos {
    private String nomeAnime;
    private String imagem;
    private String dataAdicionado;
    private String emailUsuario;
    private String sinopse;
    private String genero;
    private String estudio;
    private String ano;
    private String codigo;
    private String codigoAnime;

    public DTOAnimesFavoritos(String nomeAnime, String imagem, String dataAdicionado, String emailUsuario, String sinopse, String genero, String estudio, String ano, String codigo, String codigoAnime) {
        this.nomeAnime = nomeAnime;
        this.imagem = imagem;
        this.dataAdicionado = dataAdicionado;
        this.emailUsuario = emailUsuario;
        this.sinopse = sinopse;
        this.genero = genero;
        this.estudio = estudio;
        this.ano = ano;
        this.codigo = codigo;
        this.codigoAnime = codigoAnime;
    }

    public DTOAnimesFavoritos() {
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

    public String getDataAdicionado() {
        return dataAdicionado;
    }

    public void setDataAdicionado(String dataAdicionado) {
        this.dataAdicionado = dataAdicionado;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoAnime() {
        return codigoAnime;
    }

    public void setCodigoAnime(String codigoAnime) {
        this.codigoAnime = codigoAnime;
    }
}


