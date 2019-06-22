package com.facturaElec.facturaElec.controller;

import com.facturaElec.facturaElec.data.Cliente;
import com.facturaElec.facturaElec.data.Factura;
import com.facturaElec.facturaElec.data.Producto;
import com.facturaElec.facturaElec.data.Empresa;
import com.facturaElec.facturaElec.data.User;
import com.facturaElec.facturaElec.data.dto.ProductoDto;
import com.facturaElec.facturaElec.repositories.clienteRepository;
import com.facturaElec.facturaElec.repositories.empresaRepository;
import com.facturaElec.facturaElec.repositories.facturaRepository;
import com.facturaElec.facturaElec.repositories.productoRepository;
import com.facturaElec.facturaElec.service.UserService;
import com.facturaElec.facturaElec.util.ResponseUtil;
import com.google.gson.Gson;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author FabianCamayo
 */
@RestController
@RequestMapping("/facturaController")
public class WebFacturaController {

    private static final Log log = LogFactory.getLog(WebFacturaController.class);

    @Autowired
    protected facturaRepository facturaRepository;
    @Autowired
    protected productoRepository productoRepository;
    @Autowired
    protected clienteRepository clienteRepository;
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

    //Metodo utilizado para consultar facturas
    @GetMapping("/listarFacturas")
    public ResponseEntity<List<Factura>> getlistarFacturas(
            @RequestParam(value = "filtro", required = false) Long filtro) {

        if (filtro == null) {
            return new ResponseEntity<List<Factura>>(facturaRepository.buscarTodasFacturas(usuario().getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Factura>>(facturaRepository.buscarCodigoFacturas(filtro, usuario().getId()), HttpStatus.OK);
        }
    }

    //Metodo utilizado para consultar productos de la factura.
    @GetMapping("/listarProductosFactura")
    public ResponseEntity<List<Factura>> getlistarProductosFactura(
            @RequestParam(value = "filtro", required = false) Long filtro) {
        return new ResponseEntity<List<Factura>>(facturaRepository.buscarProductosFacturas(filtro, usuario().getId()), HttpStatus.OK);
    }

    //Metodo utilizado para consultar productos.
    @GetMapping("/listarProductos")
    public ResponseEntity<List<Factura>> getlistarProductos(
            @RequestParam(value = "codigo", required = false) Long codigo) {
        if (codigo != null) {
            return new ResponseEntity<List<Factura>>(facturaRepository.findCodigoProductos(codigo, usuario().getId()), HttpStatus.OK);
        } else {
            return null;
        }
    }

    //Metodo utilizado para agregar facturas.
    @PostMapping("/agregarFactura")
    public @ResponseBody
    Map<String, Object> agregarFactura(
            @RequestParam("documentoCliente") String clientes,
            @RequestParam(value = "codigo", required = false) Long codigo,
            @RequestParam("nitEmpresa") String empresas,
            @RequestParam("producto") String producto,
            @RequestParam("total") Integer total,
            HttpServletRequest request) {

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<ProductoDto>>() {
            }.getType();
            ArrayList<ProductoDto> productos = gson.fromJson(producto, listType);
            Long codigos;
            if (codigo == null || codigo == 0) {
                codigos = new Random().nextLong();
            } else {
                codigos = codigo;
                eliminarFactura(codigo);
            }
            Cliente cliente = clienteRepository.buscarClienteDocumento(clientes, usuario().getId());
            Empresa empresa = empresaRepository.buscarEmpresaNit(empresas, usuario().getId());
            for (int i = 0; i < productos.size(); i++) {
                Factura FE = new Factura();
                FE.setCodigo(codigos);
                FE.setFechaCreacion(new Date());
                FE.setCliente(cliente);
                FE.setEmpresa(empresa);
                FE.setProducto(new Producto(Integer.parseInt(productos.get(i).getId())));
                FE.setCantidad(productos.get(i).getCantidad());
                FE.setTotalProducto(productos.get(i).getTotalProducto());
                FE.setTotal(total);
                FE.setUser(usuario());
                facturaRepository.save(FE);
            }
            return ResponseUtil.mapOK("Proceso exitoso");
        } catch (Exception e) {
            return ResponseUtil.mapError("Error en el proceso " + e);
        }

    }

    //Metodo utilizado para eliminar facturas.
    @GetMapping("/eliminarFactura")
    public ResponseEntity<Void> eliminarFactura(
            @RequestParam("codigo") Long codigo) {
        try {
            List<Integer> facturas = facturaRepository.findIdFactura(codigo, usuario().getId());
            int id = 0;
            for (int i = 0; i < facturas.size(); i++) {
                id = facturas.get(i);
                facturaRepository.delete(facturaRepository.getOne(id));
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

    }
}
