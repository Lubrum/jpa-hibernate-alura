package com.luciano.exemplo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DadosPessoais dadosPessoais;

    public Cliente() {
    }

    public Cliente(String nome, String cpf) {
        this.dadosPessoais = new DadosPessoais(nome, cpf);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return this.getDadosPessoais().getNome();
    }

    public String getCpf() {
        return this.getDadosPessoais().getCpf();
    }

    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }
}
