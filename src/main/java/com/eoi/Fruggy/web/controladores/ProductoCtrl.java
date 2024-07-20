package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcPrecio;
import com.eoi.Fruggy.servicios.SrvcProducto;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductoCtrl {

    @Autowired
    private SrvcProducto productosSrvc;
    @Autowired
    private SrvcPrecio preciosSrvc;
    @Autowired
    private SrvcUsuario usuariosSrvc;
    @Autowired
    private SrvcImagen imagenSrvc;

    @GetMapping("/productos")
    public String producto(Model model) {
        List<Producto> listaProductos = productosSrvc.getRepo().findAll();
        System.out.println("Lista de productos: " + listaProductos); // Verifica que la lista no esté vacía
        model.addAttribute("listaProducto", listaProductos);
        return "productos";
    }

    @GetMapping("/producto/agregar")
    public String agregarProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "create-productos";
    }

    @PostMapping("/producto/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                  @RequestParam("precio") Double precio,
                                  Model model) throws Exception {
        try {
            // Manejo de la imagen
            if (file != null && !file.isEmpty()) {
                String directoryPath = "D:\\img";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String fileName = file.getOriginalFilename();
                File targetFile = new File(directoryPath + File.separator + fileName);
                file.transferTo(targetFile);

                Imagen imagen = new Imagen();
                imagen.setNombreArchivo(fileName);
                imagen.setRuta(directoryPath);
                imagen.setPathImagen("/images/" + fileName);

                imagenSrvc.guardar(imagen);
                producto.setImagen(imagen);
            } else {
                producto.setImagen(null);
            }

            // Crear o actualizar el precio
            Precio nuevoPrecio = new Precio();
            nuevoPrecio.setValor(precio);
            nuevoPrecio.setActivo(true); // Ajusta esto según tu lógica
            preciosSrvc.guardar(nuevoPrecio);

            producto.setProductoPrecios(nuevoPrecio);

            productosSrvc.guardar(producto);

            return "redirect:/productos";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar el producto.");
            return "create-productos";
        }
    }


    @PostMapping("/producto/actualizar")
    public String actualizarProducto(@ModelAttribute Producto producto) throws Exception {
        productosSrvc.guardar(producto);
        return "redirect:/productos";
    }

    @PostMapping("/producto/eliminar/{id}")
    public String eliminarProducto(@PathVariable int id) throws Exception {
        productosSrvc.eliminarPorId(id);
        return "redirect:/productos";
    }

}
