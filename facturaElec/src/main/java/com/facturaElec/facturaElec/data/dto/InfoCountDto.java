package com.facturaElec.facturaElec.data.dto;

/**
 *
 * @author FabianCamayo
 */
public class InfoCountDto {
    Long factura;
    Long producto;
    Long cliente;
    Long empresa;
    Long usuario;

    public InfoCountDto() {
    }

    public InfoCountDto(Long factura, Long producto, Long cliente, Long empresa, Long usuario) {
        this.factura = factura;
        this.producto = producto;
        this.cliente = cliente;
        this.empresa = empresa;
        this.usuario = usuario;
    }

    
    public Long getFactura() {
        return factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public Long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }
    
}
