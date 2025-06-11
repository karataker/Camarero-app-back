package com.es.tfm.ms_camarero_analiticas.dto;

import java.util.List;

public class ResumenAnaliticoDTO {
    private List<MesValorDTO> ventasMensuales;
    private List<MesValorDTO> ventasRealesMensuales;
    private List<MesValorDTO> pedidosMensuales;
    private List<MesValorDTO> reservasMensuales;
    private List<MesValorDTO> carritoMedioEstimado;
    private List<MesValorDTO> carritoMedioReal;
    private List<ProductoRankingDTO> productosTop;

    public List<MesValorDTO> getVentasMensuales() { return ventasMensuales; }
    public void setVentasMensuales(List<MesValorDTO> ventasMensuales) { this.ventasMensuales = ventasMensuales; }
    public List<MesValorDTO> getVentasRealesMensuales() { return ventasRealesMensuales; }
    public void setVentasRealesMensuales(List<MesValorDTO> ventasRealesMensuales) { this.ventasRealesMensuales = ventasRealesMensuales; }
    public List<MesValorDTO> getPedidosMensuales() { return pedidosMensuales; }
    public void setPedidosMensuales(List<MesValorDTO> pedidosMensuales) { this.pedidosMensuales = pedidosMensuales; }
    public List<MesValorDTO> getReservasMensuales() { return reservasMensuales; }
    public void setReservasMensuales(List<MesValorDTO> reservasMensuales) { this.reservasMensuales = reservasMensuales; }
    public List<MesValorDTO> getCarritoMedioEstimado() { return carritoMedioEstimado; }
    public void setCarritoMedioEstimado(List<MesValorDTO> carritoMedioEstimado) { this.carritoMedioEstimado = carritoMedioEstimado; }
    public List<MesValorDTO> getCarritoMedioReal() { return carritoMedioReal; }
    public void setCarritoMedioReal(List<MesValorDTO> carritoMedioReal) { this.carritoMedioReal = carritoMedioReal; }
    public List<ProductoRankingDTO> getProductosTop() { return productosTop; }
    public void setProductosTop(List<ProductoRankingDTO> productosTop) { this.productosTop = productosTop; }
}
