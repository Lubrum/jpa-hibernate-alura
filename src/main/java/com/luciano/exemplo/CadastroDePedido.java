package com.luciano.exemplo;

import com.luciano.exemplo.dao.CategoriaDao;
import com.luciano.exemplo.dao.ClienteDao;
import com.luciano.exemplo.dao.PedidoDao;
import com.luciano.exemplo.dao.ProdutoDao;
import com.luciano.exemplo.model.*;
import com.luciano.exemplo.util.JPAUtil;
import com.luciano.exemplo.vo.RelatorioDeVendasVO;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        Produto produto = produtoDao.buscarPorId(1L);
        Produto produto2 = produtoDao.buscarPorId(2L);
        Produto produto3 = produtoDao.buscarPorId(3L);
        Cliente cliente = clienteDao.buscarPorId(1L);

        em.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));
        pedido.adicionarItem(new ItemPedido(10, pedido, produto2));

        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(10, pedido2, produto3));

        PedidoDao pedidoDao = new PedidoDao(em);
        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);

        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorTotalVendido();
        System.out.println("VALOR TOTAL: " + totalVendido);

//        List<Object[]> relatorio = pedidoDao.relatorioVendas();
//        for (var obj : relatorio) {
//            System.out.println(obj[0]);
//            System.out.println(obj[1]);
//            System.out.println(obj[2]);
//        }
        List<RelatorioDeVendasVO> relatorio = pedidoDao.relatorioVendasV2();
        relatorio.forEach(System.out::println);

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

        EntityManager em = JPAUtil.getEntityManager();
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogames);
        categoriaDao.cadastrar(macs);

        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(videogame);
        produtoDao.cadastrar(mac);

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
