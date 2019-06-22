package com.facturaElec.facturaElec.controller;

import com.facturaElec.facturaElec.data.Empresa;
import com.facturaElec.facturaElec.data.User;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.facturaElec.facturaElec.repositories.empresaRepository;
import com.facturaElec.facturaElec.service.UserService;
import com.facturaElec.facturaElec.util.ResponseUtil;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author FabianCamayo
 */
@RestController
@RequestMapping("/empresaController")
public class WebEmpresaController {

    private static final Log log = LogFactory.getLog(WebEmpresaController.class);

    @Autowired
    protected empresaRepository empresaRepository;

    @Autowired
    private UserService userService;

    //Metodo utilizado para consultar el usuario en sesion
    public User usuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }

    //Metodo utilizado para listar empresas
    @GetMapping("/listarEmpresas")
    public ResponseEntity<List<Empresa>> getListarEmpresas(
            @RequestParam(value = "filtro", required = false) String filtro) {
        if (filtro == null || filtro.equals("")) {
            return new ResponseEntity<List<Empresa>>(empresaRepository.findAllEmpresa(usuario().getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Empresa>>(empresaRepository.findFiltroEmpresa(filtro, usuario().getId()), HttpStatus.OK);
        }
    }

    //Metodo utilizado para agregar una empresa
    @PostMapping("/agregarEmpresa")
    public @ResponseBody
    Map<String, Object> agregarEmpresa(
            @RequestParam("nit") String nit,
            @RequestParam("nombre") String nombre,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("email") String email) {
        try {

            Empresa EM = new Empresa();

            EM.setNit(nit);
            EM.setNombre(nombre);
            EM.setCiudad(ciudad);
            EM.setDireccion(direccion);
            EM.setTelefono(telefono);
            EM.setEmail(email.toLowerCase());
            EM.setUser(usuario());

            empresaRepository.save(EM);

            return ResponseUtil.mapOK(EM);
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }
    }

    //Metodo utilizado para eliminar una empresa
    @GetMapping("/eliminarEmpresa")
    public ResponseEntity<Void> eliminarEmpresa(
            @RequestParam("nit") String nit) {
        int id = empresaRepository.buscarIdEmpresaNit(nit, usuario().getId());
        if (empresaRepository.existsById(id)) {
            empresaRepository.delete(empresaRepository.getOne(id));
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //Metodo utilizado para editar una empresa
    @PostMapping("/actualizarEmpresa")
    public @ResponseBody
    Map<String, Object> actualizarEmpresa(
            @RequestParam("id") int id,
            @RequestParam("nit") String nit,
            @RequestParam("nombre") String nombre,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("email") String email) {
        try {

            Empresa EM = empresaRepository.getOne(id);

            EM.setNit(nit);
            EM.setNombre(nombre);
            EM.setCiudad(ciudad);
            EM.setDireccion(direccion);
            EM.setTelefono(telefono);
            EM.setEmail(email.toLowerCase());

            empresaRepository.save(EM);

            return ResponseUtil.mapOK("Proceso exitoso");
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }
    }
}
