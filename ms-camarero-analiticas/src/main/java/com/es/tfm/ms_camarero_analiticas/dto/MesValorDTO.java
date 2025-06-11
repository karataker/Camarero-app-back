package com.es.tfm.ms_camarero_analiticas.dto;

public class MesValorDTO {
    private String periodo;
    private double valor;

    public MesValorDTO() {}
    public MesValorDTO(String periodo, double valor) {
        this.periodo = periodo;
        this.valor = valor;
    }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}