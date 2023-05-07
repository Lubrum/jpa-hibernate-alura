package com.luciano.exemplo.dao;

import com.luciano.exemplo.model.Cliente;
import javax.persistence.EntityManager;

public class ClienteDao {

    private final EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Cliente cliente) {
        this.em.persist(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return em.find(Cliente.class, id);
    }

}
