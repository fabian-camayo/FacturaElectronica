package com.facturaElec.facturaElec.controller;

import com.facturaElec.facturaElec.data.User;
import com.facturaElec.facturaElec.data.dto.InfoCountDto;
import com.facturaElec.facturaElec.repositories.UserRepository;
import com.facturaElec.facturaElec.repositories.clienteRepository;
import com.facturaElec.facturaElec.repositories.empresaRepository;
import com.facturaElec.facturaElec.repositories.facturaRepository;
import com.facturaElec.facturaElec.repositories.productoRepository;
import com.facturaElec.facturaElec.service.UserService;
import com.facturaElec.facturaElec.util.ResponseUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author FabianCamayo
 */
@RestController
@RequestMapping("/informacionController")
public class WebInformacionController {

    @Autowired
    protected clienteRepository clienteRepository;
    @Autowired
    protected empresaRepository empresaRepository;
    @Autowired
    protected facturaRepository facturaRepository;
    @Autowired
    protected productoRepository productoRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    private UserService userService;

    //Metodo utilizado para consultar el usuario en sesion
    public User usuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }

    //Metodo utilizado para estadistica de registros
    @GetMapping("/infoCount")
    public @ResponseBody
    Map<String, Object> infoCount(
    @RequestParam(value = "role", required = false) String role) {
        try {

            InfoCountDto respuesta = new InfoCountDto();
            if (role.equals("ADMIN")) {
                respuesta.setFactura(facturaRepository.contarFacturas());
                respuesta.setUsuario(userRepository.count());
                respuesta.setCliente(clienteRepository.count());
                respuesta.setEmpresa(empresaRepository.count());
                respuesta.setProducto(productoRepository.count());
            } else {
                respuesta.setFactura(facturaRepository.contarFacturasUsuario(usuario().getId()));
                respuesta.setCliente(clienteRepository.contarClientesUsuario(usuario().getId()));
                respuesta.setEmpresa(empresaRepository.contarEmpresaUsuario(usuario().getId()));
                respuesta.setProducto(productoRepository.contarProductoUsuario(usuario().getId()));
            }

            return ResponseUtil.mapOK(respuesta);
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }

    }

}
