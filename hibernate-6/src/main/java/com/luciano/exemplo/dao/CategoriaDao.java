package com.luciano.exemplo.dao;

import com.luciano.exemplo.model.Categoria;

import jakarta.persistence.EntityManager;

public class CategoriaDao {

    private final EntityManager em;

    public CategoriaDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Categoria categoria) {
        this.em.persist(categoria);
    }

    public void atualizar(Categoria categoria) {
        this.em.merge(categoria);
    }

    public void remove(Categoria categoria) {
        categoria = em.merge(categoria);
        this.em.remove(categoria);
    }
}