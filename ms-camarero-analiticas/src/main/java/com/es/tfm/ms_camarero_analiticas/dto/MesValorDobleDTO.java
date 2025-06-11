package com.es.tfm.ms_camarero_analiticas.dto;

public class MesValorDobleDTO {
    private String periodo;
    private double comandas;
    private double facturas;

    public MesValorDobleDTO() {}
    public MesValorDobleDTO(String periodo, double comandas, double facturas) {
        this.periodo = periodo;
        this.comandas = comandas;
        this.facturas = facturas;
    }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public double getComandas() { return comandas; }
    public void setComandas(double comandas) { this.comandas = comandas; }
    public double getFacturas() { return facturas; }
    public void setFacturas(double facturas) { this.facturas = facturas; }
}
