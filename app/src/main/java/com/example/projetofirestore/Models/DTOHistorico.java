package com.example.projetofirestore.Models;

import com.google.firebase.Timestamp;
import com.google.type.Date;

public class DTOHistorico {
    private String nomeAnime;
    private String tituloEp;
    private String sinopse;
    private String saga;
    private String link;
    private String imagem;
    private long minutoAssistido;
    private String codigo;
    private String duracao;
    private Timestamp data;

    public DTOHistorico(String nomeAnime, String tituloEp, String sinopse, String saga, String link, String imagem, long minutoAssistido, String codigo, String duracao, Timestamp data) {
        this.nomeAnime = nomeAnime;
        this.tituloEp = tituloEp;
        this.sinopse = sinopse;
        this.saga = saga;
        this.link = link;
        this.imagem = imagem;
        this.minutoAssistido = minutoAssistido;
        this.codigo = codigo;
        this.duracao = duracao;
        this.data = data;
    }

    public DTOHistorico() {
    }

    public String getNomeAnime() {
        return nomeAnime;
    }

    public void setNomeAnime(String nomeAnime) {
        this.nomeAnime = nomeAnime;
    }

    public String getTituloEp() {
        return tituloEp;
    }

    public void setTituloEp(String tituloEp) {
        this.tituloEp = tituloEp;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getSaga() {
        return saga;
    }

    public void setSaga(String saga) {
        this.saga = saga;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public long getMinutoAssistido() {
        return minutoAssistido;
    }

    public void setMinutoAssistido(long minutoAssistido) {
        this.minutoAssistido = minutoAssistido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }
}

