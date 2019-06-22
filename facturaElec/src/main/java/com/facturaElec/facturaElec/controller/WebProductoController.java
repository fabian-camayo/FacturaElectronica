package com.facturaElec.facturaElec.controller;

import com.facturaElec.facturaElec.data.Producto;
import com.facturaElec.facturaElec.data.User;
import com.facturaElec.facturaElec.repositories.productoRepository;
import com.facturaElec.facturaElec.service.UserService;
import com.facturaElec.facturaElec.util.ResponseUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/productoController")
public class WebProductoController {

    private static final Log log = LogFactory.getLog(WebProductoController.class);

    @Autowired
    protected productoRepository productoRepository;

    @Autowired
    private UserService userService;

    //Metodo utilizado para consultar el usuario en sesion
    public User usuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }

    //Metodo utilizado para listar productos
    @GetMapping("/listarProductos")
    public ResponseEntity<List<Producto>> getListarProductos(
            @RequestParam(value = "filtro", required = false) String filtro) {

        if (filtro == null || filtro.equals("")) {
            return new ResponseEntity<List<Producto>>(productoRepository.findAllProducto(usuario().getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Producto>>(productoRepository.findFiltroProducto(filtro, usuario().getId()), HttpStatus.OK);
        }

    }

    //Metodo utilizado para agregar un producto
    @PostMapping("/agregarProducto")
    public @ResponseBody
    Map<String, Object> agregarProducto(
            @RequestParam("codigo") String codigo,
            @RequestParam("marca") String marca,
            @RequestParam("nombre") String nombre,
            @RequestParam("garantia") String garantia,
            @RequestParam("precio") String precio,
            @RequestParam("descripcion") String descripcion,
            HttpServletRequest request) {
        try {

            Producto PO = new Producto();

            PO.setCodigo(codigo);
            PO.setMarca(marca);
            PO.setNombre(nombre);
            PO.setGarantia(garantia);
            PO.setPrecio(Integer.parseInt(precio));
            PO.setDescripcion(descripcion);
            PO.setUser(usuario());

            productoRepository.save(PO);

            return ResponseUtil.mapOK(PO);
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }
    }

    //Metodo utilizado para eliminar un producto
    @GetMapping("/eliminarProducto")
    public ResponseEntity<Void> eliminarProducto(
            @RequestParam("codigo") String codigo) {
        int id = productoRepository.buscarProductoIdCodigo(codigo, usuario().getId());
        if (productoRepository.existsById(id)) {
            productoRepository.delete(productoRepository.getOne(id));
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //Metodo utilizado para editar un producto
    @PostMapping("/actualizarProducto")
    public @ResponseBody
    Map<String, Object> actualizarProducto(
            @RequestParam("id") int id,
            @RequestParam("codigo") String codigo,
            @RequestParam("marca") String marca,
            @RequestParam("nombre") String nombre,
            @RequestParam("garantia") String garantia,
            @RequestParam("precio") Integer precio,
            @RequestParam("descripcion") String descripcion,
            HttpServletRequest request) {
        try {

            Producto PO = productoRepository.getOne(id);

            PO.setCodigo(codigo);
            PO.setMarca(marca);
            PO.setNombre(nombre);
            PO.setGarantia(garantia);
            PO.setPrecio(precio);
            PO.setDescripcion(descripcion);

            productoRepository.save(PO);

            return ResponseUtil.mapOK("Proceso exitoso");
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }
    }
}
