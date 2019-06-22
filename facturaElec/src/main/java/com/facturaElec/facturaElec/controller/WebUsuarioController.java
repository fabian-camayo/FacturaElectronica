package com.facturaElec.facturaElec.controller;

import com.facturaElec.facturaElec.data.Role;
import com.facturaElec.facturaElec.data.User;
import com.facturaElec.facturaElec.repositories.RoleRespository;
import com.facturaElec.facturaElec.repositories.UserRepository;
import com.facturaElec.facturaElec.repositories.clienteRepository;
import com.facturaElec.facturaElec.repositories.empresaRepository;
import com.facturaElec.facturaElec.repositories.facturaRepository;
import com.facturaElec.facturaElec.repositories.productoRepository;
import com.facturaElec.facturaElec.service.UserService;
import com.facturaElec.facturaElec.util.ResponseUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author FabianCamayo
 */
@RestController
@RequestMapping("/sesionUsuario")
public class WebUsuarioController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRespository roleRespository;
    @Autowired
    private facturaRepository facturaRepository;
    @Autowired
    private clienteRepository clienteRepository;
    @Autowired
    private empresaRepository empresaRepository;
    @Autowired
    private productoRepository productoRepository;

    //Metodo utilizado para consultar el usuario en sesion
    @GetMapping("/usuario")
    public @ResponseBody
    Map<String, Object> usuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return ResponseUtil.mapOK(user);
    }

    //Metodo utilizado para listar usuarios
    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<User>> getListarUsuarios(
            @RequestParam(value = "filtro", required = false) String filtro) {

        if (filtro == null || filtro.equals("")) {
            return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(userRepository.buscarUsuarioEmail(filtro), HttpStatus.OK);
        }
    }

    //Metodo utilizado para actualizar el usuario en sesion
    @PostMapping("/actualizarUsuario")
    public @ResponseBody
    Map<String, Object> actualizarUsuario(
            @RequestParam("id") int id,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("oldEmail") String oldEmail,
            @RequestParam("password") String password) {
        User userExists = userService.findUserByEmail(email);

        if (!email.toLowerCase().equals(oldEmail.toLowerCase()) && userExists != null) {
            return ResponseUtil.mapError("El correo escrito ya existe");
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            User user = userRepository.buscarUsuarioId(id);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email.toLowerCase());
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return ResponseUtil.mapOK("El usuario se actualizo con exito");
        }

    }

    //Metodo utilizado para agregar un usuario desde admin
    @PostMapping("/agregarUsuario")
    public @ResponseBody
    Map<String, Object> agregarUsuario(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("active") int active,
            @RequestParam("roles") String roles) {
        try {

            User userExists = userService.findUserByEmail(email);
            if (userExists != null) {
                return ResponseUtil.mapError("El correo escrito ya existe");
            } else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(password);
                User user = new User();
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email.toLowerCase());
                user.setPassword(hashedPassword);
                user.setActive(active);
                Role adminRole = roleRespository.findByRole(roles);
                user.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));
                userRepository.save(user);
                return ResponseUtil.mapOK("El usuario se creo con exito");
            }
        } catch (Exception e) {
            return ResponseUtil.mapError("Error al crear usuario" + e);
        }

    }

    //Metodo utilizado para actualizar un usuario desde admin
    @PostMapping("/actualizarUsuarios")
    public @ResponseBody
    Map<String, Object> actualizarUsuarios(
            @RequestParam("id") int id,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("oldEmail") String oldEmail,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam("active") int active,
            @RequestParam("roles") String roles) {
        try {

            User userExists = userService.findUserByEmail(email);
            if (!email.toLowerCase().equals(oldEmail.toLowerCase()) && userExists != null) {
                return ResponseUtil.mapError("El correo escrito ya existe");
            } else {
                User user = userRepository.buscarUsuarioId(id);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email.toLowerCase());
                if (password != null || !password.equals("")) {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String hashedPassword = passwordEncoder.encode(password);
                    user.setPassword(hashedPassword);
                }
                user.setActive(active);
                Role adminRole = roleRespository.findByRole(roles);
                user.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));
                userRepository.save(user);
                return ResponseUtil.mapOK("El usuario se actualizo con exito");
            }
        } catch (Exception e) {
            return ResponseUtil.mapError("Error al actualizar el usuario " + e);
        }

    }

    //Metodo utilizado para eliminar un usuario y sus derivados
    @GetMapping("/eliminarUsuario")
    public ResponseEntity<Void> eliminarCliente(
            @RequestParam("id") int id) {
        try {
            List<Integer> facturas = facturaRepository.FacturasUsuario(id);
            List<Integer> clientes = clienteRepository.ClientesUsuario(id);
            List<Integer> empresas = empresaRepository.EmpresasUsuario(id);
            List<Integer> productos = productoRepository.ProductosUsuario(id);

            if(facturas.size() > 0){
            facturaRepository.eliminarFacturasUsuario(facturas);
            }
            if(clientes.size() > 0){
            clienteRepository.eliminarClientesUsuario(clientes);
            }
            if(empresas.size() > 0){
            empresaRepository.eliminarEmpresasUsuario(empresas);
            }
            if(productos.size() > 0){
            productoRepository.eliminarProductosUsuario(productos);
            }
            userRepository.delete(new User(id));
                    
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

    }
}
