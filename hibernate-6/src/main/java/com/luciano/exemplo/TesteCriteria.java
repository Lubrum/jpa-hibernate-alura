package com.luciano.exemplo;

import com.luciano.exemplo.dao.CategoriaDao;
import com.luciano.exemplo.dao.ClienteDao;
import com.luciano.exemplo.dao.PedidoDao;
import com.luciano.exemplo.dao.ProdutoDao;
import com.luciano.exemplo.model.*;
import com.luciano.exemplo.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TesteCriteria {

    public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao dao = new ProdutoDao(em);
        List<Produto> produto = dao.buscarPorParametrosCriteriaAPI("PS5", null, null);
        produto.forEach(System.out::println);

        List<Produto> produto2 = dao.buscarPorParametrosCriteriaAPI("PS5", null, LocalDate.now());
        produto2.forEach(System.out::println);

        List<Produto> produto3 = dao.buscarPorParametrosCriteriaAPI(null, null, LocalDate.now());
        produto3.forEach(System.out::println);

        em.close();
    }

    private static void popularBancoDeDados() {

        Categoria celulares = new Categoria("CELULARES");
        Categoria videogames = new Categoria("VIDEOGAMES");
        Categoria macs = new Categoria("INFORMATICA");

        Produto celular = new Produto("XIAomi Redmi", "Legal", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("PS5", "www", new BigDecimal("450"), videogames);
        Produto mac = new Produto("Macbook", "pro", new BigDecimal("777"), macs);

        Cliente cliente = new Cliente("Rodrigo", "123456");

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, celular));
        pedido.adicionarItem(new ItemPedido(40, pedido, videogame));

        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(2, pedido2, mac));

        EntityManager em = JPAUtil.getEntityManager();

        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);
        PedidoDao pedidoDao = new PedidoDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogames);
        categoriaDao.cadastrar(macs);

        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(videogame);
        produtoDao.cadastrar(mac);

        clienteDao.cadastrar(cliente);

        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);

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
