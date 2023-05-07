package com.luciano.exemplo;

import com.luciano.exemplo.dao.CategoriaDao;
import com.luciano.exemplo.dao.ClienteDao;
import com.luciano.exemplo.dao.PedidoDao;
import com.luciano.exemplo.dao.ProdutoDao;
import com.luciano.exemplo.model.*;
import com.luciano.exemplo.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        Produto produto = produtoDao.buscarPorId(1L);
        Cliente cliente = clienteDao.buscarPorId(1L);

        em.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));

        PedidoDao pedidoDao = new PedidoDao(em);
        pedidoDao.cadastrar(pedido);

        em.getTransaction().commit();
        em.close();
    }

    private static void popularBancoDeDados() {

        Categoria categoria = new Categoria("CELULARES");
        Produto celular = new Produto("XIAomi Redmi", "Legal", new BigDecimal("800"), categoria);
        Cliente cliente = new Cliente("Rodrigo", "123456");

        EntityManager em = JPAUtil.getEntityManager();
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(categoria);
        produtoDao.cadastrar(celular);
        clienteDao.cadastrar(cliente);

        em.getTransaction().commit();
        em.close();

        // transient, managed, detached e removed, estados de entidade JPA/Hibernate
        // transient: antes de persistir (nova entidade), managed: após dar um persist(), detached: após o clear() ou close()
        // no entity manager, removed: entidade removida após delete, commit/flush, p. salvar no banco commit() ou flush(), e merge() para voltar entidade de detached
        // para managed, find() e createQuery() para trazer do banco para 'managed'

//        Produto celular2 = new Produto("teste Redmi", "Legal", new BigDecimal("800"), categoria);
//        produtoDao.cadastrar(celular2);
//        celular2.setNome("teste");
//        em.flush();
//        em.clear();
//
//        celular2 = em.merge(celular2);
//        celular2.setNome("teste2");
//        em.flush();
//        em.remove(celular2);
//        em.flush();
//        em.close();
    }
}
