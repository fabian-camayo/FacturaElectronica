package com.facturaElec.facturaElec.repositories;

import com.facturaElec.facturaElec.data.Factura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author FabianCamayo
 */
public interface facturaRepository extends JpaRepository<Factura, Integer> {

    //Consulta utilizada para eliminar una factura
    @Query(value = "SELECT f.id FROM Factura f WHERE f.codigo = :codigo AND f.user.id = :user")
    public List<Integer> findIdFactura(@Param("codigo") Long codigo, @Param("user") int user);

    //Consulta utilizada para buscar productos por codigo de factura
    @Query(value = "SELECT f.producto.id, f.producto.codigo, f.producto.marca, f.producto.nombre, f.producto.garantia, f.producto.precio, f.producto.descripcion, f.cantidad, f.totalProducto "
            + "FROM Factura f WHERE f.codigo = :codigo AND f.user.id = :user ORDER BY f.producto.codigo ASC")
    public List<Factura> findCodigoProductos(@Param("codigo") Long codigo, @Param("user") int user);

    //Consulta utilizada para buscar todas las facturas de un usuario
    @Query(value = "SELECT  DISTINCT  f.codigo, f.fechaCreacion, "
            + "f.cliente.documento AS documentoCliente, f.cliente.nombre AS nombreCliente, "
            + "f.empresa.nit AS nitEmpresa, f.empresa.nombre AS nombreEmpresa, f.total "
            + "FROM Factura f WHERE f.user.id = :user ORDER BY f.codigo ASC")
    public List<Factura> buscarTodasFacturas(@Param("user") int user);

    //Consulta utilizada para buscar una factura de un usuario
    @Query(value = "SELECT  DISTINCT  f.codigo, f.fechaCreacion, "
            + "f.cliente.documento AS documentoCliente, f.cliente.nombre AS nombreCliente, "
            + "f.empresa.nit AS nitEmpresa, f.empresa.nombre AS nombreEmpresa, f.total "
            + "FROM Factura f WHERE f.codigo = :filtro AND f.user.id = :user ORDER BY f.codigo ASC")
    public List<Factura> buscarCodigoFacturas(@Param("filtro") Long filtro, @Param("user") int user);

    //Consulta utilizada para buscar los productos de una factura de un usuario
    @Query(value = "SELECT p FROM Factura f "
            + "JOIN Producto p ON f.producto = p.id WHERE f.codigo = :filtro AND f.user.id = :user")
    public List<Factura> buscarProductosFacturas(@Param("filtro") Long filtro, @Param("user") int user);

    //Consulta utilizada para eliminar una factura desde usuario
    @Query(value = "SELECT f.id FROM Factura f "
            + "WHERE f.id = :user")
    List<Integer> FacturasUsuario(@Param("user") Integer user);

    //Consulta utilizada para eliminar una factura desde usuario
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Factura f WHERE f.user.id IN (:factura)")
    public List<Integer> eliminarFacturasUsuario(@Param("factura") List<Integer> factura);

    //Consulta utilizada para contar todas las factura
    @Query(value = "SELECT COUNT(DISTINCT f.codigo) "
            + "FROM Factura f")
    Long contarFacturas();

    //Consulta utilizada para contar todas las factura por usuario
    @Query(value = "SELECT COUNT(DISTINCT f.codigo) "
            + "FROM Factura f WHERE f.user.id = :user")
    Long contarFacturasUsuario(@Param("user") Integer user);
}
