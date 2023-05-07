package com.luciano.exemplo;

import com.luciano.exemplo.dao.CategoriaDao;
import com.luciano.exemplo.dao.ProdutoDao;
import com.luciano.exemplo.model.Categoria;
import com.luciano.exemplo.model.CategoriaId;
import com.luciano.exemplo.model.Produto;
import com.luciano.exemplo.util.JPAUtil;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.System.out;

public class CadastroDeProduto {

    public static void main(String[] args) {
        cadastrarProduto();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao dao = new ProdutoDao(em);

        Produto p = dao.buscarPorId(1L);
        out.println(p.getPreco());

        List<Produto> produtos = dao.buscarTodos();
        produtos.forEach(p1 -> System.out.println(p1.getNome()));

        List<Produto> produtos2 = dao.buscarPorNome("XIAomi Redmi");
        produtos2.forEach(out::println);

        List<Produto> produtos3 = dao.buscarPorNomeDaCategoria("CELULARES");
        produtos3.forEach(out::println);

        BigDecimal preco = dao.buscarPrecoPorNome("XIAomi Redmi");
        out.println("Preço = " + preco);
    }

    private static void cadastrarProduto() {

        Categoria categoria = new Categoria("CELULARES");
        Produto celular = new Produto("XIAomi Redmi", "Legal", new BigDecimal("800"), categoria);

        EntityManager em = JPAUtil.getEntityManager();
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(categoria);
        produtoDao.cadastrar(celular);
        em.getTransaction().commit();

        Categoria categoriaTeste = em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
        System.out.println(categoriaTeste);
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
