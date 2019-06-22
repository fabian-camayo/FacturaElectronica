package com.facturaElec.facturaElec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturaElec.facturaElec.data.Role;
/**
 *
 * @author FabianCamayo
 */
@Repository("roleRepository")
public interface RoleRespository extends JpaRepository<Role, Integer> {

 Role findByRole(String role);
}