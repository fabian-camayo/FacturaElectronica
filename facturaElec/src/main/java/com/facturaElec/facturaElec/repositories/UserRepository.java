package com.facturaElec.facturaElec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.facturaElec.facturaElec.data.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Fabian Camayo
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    //Consultar usuario por email para la creación
    User findByEmail(String email);

    //Consultar usuarios por email
    @Query(value = "SELECT u FROM User u WHERE u.email =:email")
    List<User> buscarUsuarioEmail(@Param("email") String email);

    //Consulta utilizada para buscar un usuario por id
    @Query(value = "SELECT u FROM User u WHERE u.id =:id")
    User buscarUsuarioId(@Param("id") Integer id);

}
