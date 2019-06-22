package com.facturaElec.facturaElec.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author FabianCamayo
 */
@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    private Integer id;
    private Long codigo;
    private Date fechaCreacion;
    private Cliente cliente;
    private Empresa empresa;
    private Producto producto;
    private Integer cantidad;
    private Integer totalProducto;
    private Integer total;
    private User user;

    public Factura() {
    }

    public Factura(Integer id) {
        this.id = id;
    }

    public Factura(Integer id, Long codigo, Date fechaCreacion, Cliente cliente, Empresa empresa, Producto producto, Integer cantidad, Integer totalProducto, Integer total, User user) {
        this.id = id;
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.empresa = empresa;
        this.producto = producto;
        this.cantidad = cantidad;
        this.totalProducto = totalProducto;
        this.total = total;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "codigo", nullable = false)
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Column(name = "fecha_creacion", nullable = false, length = 100)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente", nullable = false)
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa", nullable = false)
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto", nullable = false)
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Column(name = "cantidad", nullable = false)
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Column(name = "total_producto", nullable = false)
    public Integer getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(Integer totalProducto) {
        this.totalProducto = totalProducto;
    }

    @Column(name = "total", nullable = false)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
