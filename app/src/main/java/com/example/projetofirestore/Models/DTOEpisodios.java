package com.example.projetofirestore.Models;

public class DTOEpisodios {
    private String titulo;
    private String sinopse;
    private String imagem;
    private String duracao;
    private String ano;
    private String link;
    private String saga;
    private boolean filler;
    private String codigo;

    public DTOEpisodios(String titulo, String sinopse, String imagem, String duracao, String ano, String link, String saga, Boolean filler, String codigo) {
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.imagem = imagem;
        this.duracao = duracao;
        this.ano = ano;
        this.link = link;
        this.saga = saga;
        this.filler = filler;
        this.codigo = codigo;
    }

    public DTOEpisodios() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSaga() {
        return saga;
    }

    public void setSaga(String saga) {
        this.saga = saga;
    }

    public boolean isFiller() {
        return filler;
    }

    public void setFiller(boolean filler) {
        this.filler = filler;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
