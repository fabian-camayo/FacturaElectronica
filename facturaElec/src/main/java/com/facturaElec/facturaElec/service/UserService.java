package com.facturaElec.facturaElec.service;


import com.facturaElec.facturaElec.data.User;
/**
 *
 * @author FabianCamayo
 */
public interface UserService {
  
 public User findUserByEmail(String email);
 
 public void saveUser(User user);
}
