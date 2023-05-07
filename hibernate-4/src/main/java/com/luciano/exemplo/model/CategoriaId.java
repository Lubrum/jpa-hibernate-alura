package com.luciano.exemplo.model;

import javax.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CategoriaId implements Serializable {

    private String nome;

    private String tipo;

    public CategoriaId() {
    }

    public CategoriaId(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaId that = (CategoriaId) o;
        return Objects.equals(nome, that.nome) && Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, tipo);
    }
}
