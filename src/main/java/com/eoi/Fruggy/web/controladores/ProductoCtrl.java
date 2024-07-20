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
        // Manejo de la imagen
        if (file != null && !file.isEmpty()) {
            try {
                // Guardar la imagen en el servidor
                String directoryPath = "D:\\img";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs(); // Crear directorios si no existen
                }
                String fileName = file.getOriginalFilename();
                File targetFile = new File(directoryPath + File.separator + fileName);
                file.transferTo(targetFile);

                // Crear o actualizar la entidad Imagen
                Imagen imagen = new Imagen();
                imagen.setNombreArchivo(fileName);
                imagen.setRuta(directoryPath);
                imagen.setPathImagen("/images/" + fileName); // URL relativa para acceder a la imagen

                // Guardar la imagen
                imagenSrvc.guardar(imagen);
                producto.setImagen(imagen); // Asociar la imagen al producto
            } catch (Exception e) {
                // Manejar excepción si ocurre algún error
                e.printStackTrace();
                model.addAttribute("error", "Error al guardar la imagen.");
                return "create-productos";
            }
        } else {
            producto.setImagen(null);
        }

        // Crear o actualizar el precio
        Precio nuevoPrecio = new Precio();
        nuevoPrecio.setValor(precio);
        nuevoPrecio.setActivo(true);
        // Asegúrate de establecer otros campos si es necesario
        preciosSrvc.guardar(nuevoPrecio);

        // Asociar el precio con el producto
        producto.setProductoPrecios(nuevoPrecio);

        // Guardar el producto en la base de datos
        productosSrvc.guardar(producto);

        // Mensajes de depuración
        System.out.println("Producto guardado: " + producto);
        System.out.println("Precio asociado: " + producto.getProductoPrecios());

        return "redirect:/admin/productos";
    }


    @PostMapping("/producto/actualizar")
    public String actualizarProducto(@ModelAttribute Producto producto) throws Exception {
        productosSrvc.guardar(producto);
        return "redirect:/admin/productos";
    }
    @GetMapping("/producto/editar/{id}")
    public String editarProducto(@PathVariable("id") long id, Model model) {
        Optional<Producto> producto = productosSrvc.getRepo().findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
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
