package com.bau.merenda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "escolha_aluno")
public class EscolhaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String turno;
    private int quantidade;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public EscolhaAluno() {
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTurno() { return turno; }
    public int getQuantidade() { return quantidade; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Item getItem() { return item; }

    public void setId(Long id) { this.id = id; }
    public void setTurno(String turno) { this.turno = turno; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setItem(Item item) { this.item = item; }
}
