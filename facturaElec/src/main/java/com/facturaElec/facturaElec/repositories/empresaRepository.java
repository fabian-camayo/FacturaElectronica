package com.facturaElec.facturaElec.repositories;

import com.facturaElec.facturaElec.data.Empresa;
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
public interface empresaRepository extends JpaRepository<Empresa, Integer> {

    //Consulta utilizada para buscar una empresa de un usuario
    @Query(value = "SELECT e FROM Empresa e WHERE e.nit = :filtro AND e.user.id = :user")
    public List<Empresa> findFiltroEmpresa(@Param("filtro") String filtro, @Param("user") int user);

    //Consulta utilizada para buscar las empresas de un usuario
    @Query(value = "SELECT e FROM Empresa e WHERE e.user.id = :user")
    public List<Empresa> findAllEmpresa(@Param("user") int user);

    //Consulta utilizada para buscar una empresa de una factura
    @Query(value = "SELECT e FROM Empresa e WHERE e.nit = :nit AND e.user.id = :user")
    public Empresa buscarEmpresaNit(@Param("nit") String nit, @Param("user") int user);

    //Consulta utilizada para eliminar una empresa
    @Query(value = "SELECT DISTINCT e.id FROM Empresa e WHERE e.nit = :nit AND e.user.id = :user")
    public Integer buscarIdEmpresaNit(@Param("nit") String nit, @Param("user") int user);

    //Consulta utilizada para eliminar una empresa desde usuario
    @Query(value = "SELECT e.id FROM Empresa e "
            + "WHERE e.id = :user ")
    List<Integer> EmpresasUsuario(@Param("user") Integer user);

    //Consulta utilizada para eliminar una empresa desde usuario
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Empresa e WHERE e.user.id IN (:empresa)")
    public List<Integer> eliminarEmpresasUsuario(@Param("empresa") List<Integer> empresa);

    //Consulta utilizada para contar todas las empresas por usuario
    @Query(value = "SELECT COUNT(e.id) "
            + "FROM Empresa e WHERE e.user.id = :user")
    Long contarEmpresaUsuario(@Param("user") Integer user);
}
