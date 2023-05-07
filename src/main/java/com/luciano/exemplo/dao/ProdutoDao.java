package com.luciano.exemplo.dao;

import com.luciano.exemplo.model.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDao {

    private final EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public void atualizar(Produto produto) {
        this.em.merge(produto);
    }

    public void remove(Produto produto) {
        produto = em.merge(produto);
        this.em.remove(produto);
    }

    public Produto buscarPorId(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> buscarTodos() {
        String jpql = "SELECT p FROM Produto p";
        return em.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
        return em.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPorNomeDaCategoria(String nome) {
        String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
        return em.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPorNomeDaCategoriaV2(String nome) {
        return em.createNamedQuery("Produto.buscarPorNomeDaCategoria", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoPorNome(String nome) {
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
        return em.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
        String jpql = "SELECT p FROM Produto p WHERE 1=1 ";

        if (nome != null && !nome.isBlank()) {
            jpql += " AND p.nome = :nome";
        }

        if (preco != null) {
            jpql += " AND p.preco = :preco";
        }

        if (dataCadastro != null) {
            jpql += " AND p.dataCadastro = :dataCadastro";
        }

        TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);
        if (nome != null && !nome.isBlank()) {
            typedQuery.setParameter("nome", nome);
        }

        if (preco != null) {
            typedQuery.setParameter("preco", preco);
        }

        if (dataCadastro != null) {
            typedQuery.setParameter("dataCadastro", dataCadastro);
        }

        return typedQuery.getResultList();
    }

    public List<Produto> buscarPorParametrosCriteriaAPI(String nome, BigDecimal preco, LocalDate dataCadastro) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = builder.createQuery(Produto.class);
        Root<Produto> from = criteriaQuery.from(Produto.class);

        Predicate filtros = builder.and();

        if (nome != null && !nome.isBlank()) {
            filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
        }

        if (preco != null) {
            filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
        }

        if (dataCadastro != null) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
        }
        criteriaQuery.where(filtros);

        return em.createQuery(criteriaQuery).getResultList();
    }
}
