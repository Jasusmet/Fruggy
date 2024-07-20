package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcPrecio;
import com.eoi.Fruggy.servicios.SrvcProducto;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/admin/productos")
    public String adminListarProductos(Model model) {
        List<Producto> listaProductosAdmin = productosSrvc.getRepo().findAll();
        model.addAttribute("listaProductosAdmin", listaProductosAdmin);
        return "adminProductos";
    }


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
        if (producto.getId() != null) {
            // Actualización de un producto existente
            Optional<Producto> productoExistente = productosSrvc.encuentraPorId(producto.getId());
            if (productoExistente.isPresent()) {
                Producto productoActualizado = productoExistente.get();
                productoActualizado.setNombreProducto(producto.getNombreProducto());
                productoActualizado.setDescripcion(producto.getDescripcion());
                productoActualizado.setActivo(producto.getActivo());

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
                    productoActualizado.setImagen(imagen);
                }

                Precio nuevoPrecio = productoActualizado.getProductoPrecios();
                nuevoPrecio.setValor(precio);
                preciosSrvc.guardar(nuevoPrecio);

                productoActualizado.setProductoPrecios(nuevoPrecio);
                productosSrvc.guardar(productoActualizado);

            } else {
                model.addAttribute("error", "Producto no encontrado");
                return "error";
            }
        } else {
            // Creación de un nuevo producto
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
            }

            Precio nuevoPrecio = new Precio();
            nuevoPrecio.setValor(precio);
            nuevoPrecio.setActivo(true);
            preciosSrvc.guardar(nuevoPrecio);

            producto.setProductoPrecios(nuevoPrecio);
            productosSrvc.guardar(producto);
        }

        return "redirect:/admin/productos";
    }

    @GetMapping("/producto/editar/{id}")
    public String editarProducto(@PathVariable("id") long id, Model model) {
        Optional<Producto> producto = productosSrvc.encuentraPorId(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            model.addAttribute("precio", producto.get().getPrecio()); // no es necesario ponerlo, pero para que me ayude a entenderlo
            return "create-productos";
        } else {
            model.addAttribute("error", "Producto no encontrado");
            return "error";
        }
    }

    @PostMapping("/producto/eliminar/{id}")
    public String eliminarProducto(@PathVariable int id) throws Exception {
        productosSrvc.eliminarPorId(id);
        return "redirect:/admin/productos";
    }

}
