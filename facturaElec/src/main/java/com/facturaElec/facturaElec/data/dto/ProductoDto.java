package com.facturaElec.facturaElec.data.dto;

/**
 *
 * @author FabianCamayo
 */
public class ProductoDto {

    private String id;
    private String codigo;
    private String marca;
    private String nombre;
    private String garantia;
    private Integer precio;
    private String descripcion;
    private Integer cantidad;
    private Integer totalProducto;

    public ProductoDto() {
    }

    public ProductoDto(String id, String codigo, String marca, String nombre, String garantia, Integer precio, String descripcion,Integer cantidad,Integer totalProducto) {
        this.id = id;
        this.codigo = codigo;
        this.marca = marca;
        this.nombre = nombre;
        this.garantia = garantia;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.totalProducto =  totalProducto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(Integer totalProducto) {
        this.totalProducto = totalProducto;
    }

}
