package com.luciano.exemplo.dao;

import com.luciano.exemplo.model.Pedido;
import com.luciano.exemplo.model.Produto;
import com.luciano.exemplo.vo.RelatorioDeVendasVO;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private final EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    public List<Object[]> relatorioVendas() {
        String jpql = """
                SELECT produto.nome,
                SUM(item.quantidade),
                MAX(pedido.data)
                FROM Pedido pedido
                JOIN pedido.itens item
                JOIN item.produto produto
                GROUP BY produto.nome
                ORDER BY item.quantidade DESC
                """;

        return em.createQuery(jpql, Object[].class).getResultList();
    }

    public List<RelatorioDeVendasVO> relatorioVendasV2() {
        String jpql = """
                SELECT new com.luciano.exemplo.vo.RelatorioDeVendasVO(
                produto.nome,
                SUM(item.quantidade),
                MAX(pedido.data))
                FROM Pedido pedido
                JOIN pedido.itens item
                JOIN item.produto produto
                GROUP BY produto.nome
                ORDER BY item.quantidade DESC
                """;

        return em.createQuery(jpql, RelatorioDeVendasVO.class).getResultList();
    }

}
