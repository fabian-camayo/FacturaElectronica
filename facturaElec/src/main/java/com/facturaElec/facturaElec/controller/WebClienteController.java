package com.facturaElec.facturaElec.controller;

import com.facturaElec.facturaElec.data.Cliente;
import com.facturaElec.facturaElec.data.User;
import com.facturaElec.facturaElec.repositories.clienteRepository;
import com.facturaElec.facturaElec.service.UserService;
import com.facturaElec.facturaElec.util.ResponseUtil;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author FabianCamayo
 */
@RestController
@RequestMapping("/clienteController")
public class WebClienteController {

    private static final Log log = LogFactory.getLog(WebClienteController.class);

    @Autowired
    protected clienteRepository clienteRepository;
    @Autowired
    private UserService userService;

    //Metodo utilizado para consultar el usuario en sesion
    public User usuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }

    //Metodo utilizado para listar clientes
    @GetMapping("/listarClientes")
    public ResponseEntity<List<Cliente>> getListarClientes(
            @RequestParam(value = "filtro", required = false) String filtro) {

        if (filtro == null || filtro.equals("")) {
            return new ResponseEntity<List<Cliente>>(clienteRepository.findAllCliente(usuario().getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Cliente>>(clienteRepository.findFiltroCliente(filtro, usuario().getId()), HttpStatus.OK);
        }
    }

    //Metodo utilizado para agregar un cliente
    @PostMapping("/agregarCliente")
    public @ResponseBody
    Map<String, Object> agregarCliente(
            @RequestParam("tipoDocumento") String tipoDocumento,
            @RequestParam("documento") String documento,
            @RequestParam("nombre") String nombre,
            @RequestParam("pais") String pais,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo) {
        try {

            Cliente CL = new Cliente();

            CL.setTipoDocumento(tipoDocumento);
            CL.setDocumento(documento);
            CL.setNombre(nombre);
            CL.setPais(pais);
            CL.setCiudad(ciudad);
            CL.setDireccion(direccion);
            CL.setTelefono(telefono);
            CL.setCorreo(correo.toLowerCase());
            CL.setUser(usuario());

            clienteRepository.save(CL);

            return ResponseUtil.mapOK(CL);
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }
    }

    //Metodo utilizado para eliminar un cliente
    @GetMapping("/eliminarCliente")
    public ResponseEntity<Void> eliminarCliente(
            @RequestParam("documento") String documento) {
        int id = clienteRepository.buscarClienteIdDocumento(documento, usuario().getId());
        if (clienteRepository.existsById(id)) {
            clienteRepository.delete(clienteRepository.getOne(id));
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //Metodo utilizado para actualizar un cliente
    @PostMapping("/actualizarCliente")
    public @ResponseBody
    Map<String, Object> actualizarCliente(
            @RequestParam("id") int id,
            @RequestParam("tipoDocumento") String tipoDocumento,
            @RequestParam("documento") String documento,
            @RequestParam("nombre") String nombre,
            @RequestParam("pais") String pais,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo) {
        try {

            Cliente CL = clienteRepository.getOne(id);

            CL.setTipoDocumento(tipoDocumento);
            CL.setDocumento(documento);
            CL.setNombre(nombre);
            CL.setPais(pais);
            CL.setCiudad(ciudad);
            CL.setDireccion(direccion);
            CL.setTelefono(telefono);
            CL.setCorreo(correo.toLowerCase());

            clienteRepository.save(CL);

            return ResponseUtil.mapOK("Proceso exitoso");
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }
    }

}
