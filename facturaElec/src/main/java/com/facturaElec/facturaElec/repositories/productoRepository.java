package com.facturaElec.facturaElec.repositories;

import com.facturaElec.facturaElec.data.Producto;
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
public interface productoRepository extends JpaRepository<Producto, Integer> {

    //Consulta utilizada para buscar un producto de un usuario
    @Query(value = "SELECT p FROM Producto p WHERE p.codigo = :filtro AND p.user.id = :user")
    public List<Producto> findFiltroProducto(@Param("filtro") String filtro, @Param("user") int user);

    //Consulta utilizada para buscar todos los productos de un usuario
    @Query(value = "SELECT p FROM Producto p WHERE p.user.id = :user")
    public List<Producto> findAllProducto(@Param("user") int user);

    //Consulta utilizada para eliminar un producto
    @Query(value = "SELECT DISTINCT p.id FROM Producto p WHERE p.codigo = :codigo AND p.user.id = :user")
    public Integer buscarProductoIdCodigo(@Param("codigo") String codigo, @Param("user") int user);

    //Consulta utilizada para eliminar un producto desde usuario
    @Query(value = "SELECT p.id FROM Producto p "
            + "WHERE p.id = :user ")
    List<Integer> ProductosUsuario(@Param("user") Integer user);

    //Consulta utilizada para eliminar un producto desde usuario
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Producto p WHERE p.user.id IN (:producto)")
    public List<Integer> eliminarProductosUsuario(@Param("producto") List<Integer> producto);

    //Consulta utilizada para contar todas los productos por usuario
    @Query(value = "SELECT COUNT(p.id) "
            + "FROM Producto p WHERE p.user.id = :user")
    Long contarProductoUsuario(@Param("user") Integer user);
}
