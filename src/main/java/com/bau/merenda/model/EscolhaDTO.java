package com.bau.merenda.model;

public class EscolhaDTO {
 



    private String turno;
    private int quantidade;
    private Long itemId;

    public String getTurno() { return turno; }
    public int getQuantidade() { return quantidade; }
    public Long getItemId() { return itemId; }

    public void setTurno(String turno) { this.turno = turno; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
}

