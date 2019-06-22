package com.facturaElec.facturaElec.repositories;

import com.facturaElec.facturaElec.data.Cliente;
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
public interface clienteRepository extends JpaRepository<Cliente, Integer> {

    //Consulta utilizada para buscar un cliente de un usuario
    @Query(value = "SELECT c FROM Cliente c WHERE c.documento = :filtro AND c.user.id = :user")
    public List<Cliente> findFiltroCliente(@Param("filtro") String filtro, @Param("user") int user);

    //Consulta utilizada buscar todos los clientes de un usuario
    @Query(value = "SELECT c FROM Cliente c WHERE c.user.id = :user")
    public List<Cliente> findAllCliente(@Param("user") int user);

    //Consulta utilizada par consultar un cliente de una factura
    @Query(value = "SELECT c FROM Cliente c WHERE c.documento = :documento AND c.user.id = :user")
    public Cliente buscarClienteDocumento(@Param("documento") String documento, @Param("user") int user);

    //Consulta utilizada para eliminar un cliente por documento y usuario
    @Query(value = "SELECT DISTINCT c.id FROM Cliente c WHERE c.documento = :documento AND c.user.id = :user")
    public Integer buscarClienteIdDocumento(@Param("documento") String documento, @Param("user") int user);

    //Consulta utilizada para eliminar un cliente desde usuario
    @Query(value = "SELECT c.id FROM Cliente c "
            + "WHERE c.id = :user ")
    List<Integer> ClientesUsuario(@Param("user") Integer user);

    //Consulta utilizada para eliminar un cliente desde usuario
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Cliente c WHERE c.user.id IN (:cliente)")
    public List<Integer> eliminarClientesUsuario(@Param("cliente") List<Integer> cliente);

    //Consulta utilizada para contar todas los clientes por usuario
    @Query(value = "SELECT COUNT(c.id) "
            + "FROM Cliente c WHERE c.user.id = :user")
    Long contarClientesUsuario(@Param("user") Integer user);
}
