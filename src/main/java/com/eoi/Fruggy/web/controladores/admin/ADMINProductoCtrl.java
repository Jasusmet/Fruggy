package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/admin/productos")
public class ADMINProductoCtrl {

    private final SrvcProducto productosSrvc;
    private final SrvcPrecio precioSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcSubcategoria subcategoriasSrvc;
    private final SrvcCategoria categoriasSrvc;
    private final SrvcSupermercado supermercadoSrvc;
    private final SrvcDescuento descuentoSrvc;
    private final SrvcTipodescuento tipoDescuentoSrvc;

    public ADMINProductoCtrl(SrvcProducto productosSrvc, SrvcPrecio precioSrvc, SrvcImagen imagenSrvc, SrvcSubcategoria subcategoriasSrvc, SrvcCategoria categoriasSrvc, SrvcSupermercado supermercadoSrvc, SrvcDescuento descuentoSrvc, SrvcTipodescuento tipoDescuentoSrvc) {
        this.productosSrvc = productosSrvc;
        this.precioSrvc = precioSrvc;
        this.imagenSrvc = imagenSrvc;
        this.subcategoriasSrvc = subcategoriasSrvc;
        this.categoriasSrvc = categoriasSrvc;
        this.supermercadoSrvc = supermercadoSrvc;
        this.descuentoSrvc = descuentoSrvc;
        this.tipoDescuentoSrvc = tipoDescuentoSrvc;
    }


    // Mostrar lista de productos
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productosSrvc.buscarEntidades();
        model.addAttribute("productos", productos);
        return "/admin/CRUD-Productos";
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/agregar")
    public String mostrarFormularioCreacion(Model model) {
        Producto producto = new Producto();
        List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();
        List<Categoria> categorias = categoriasSrvc.buscarEntidades();
        List<Supermercado> supermercados = supermercadoSrvc.buscarEntidades();
        model.addAttribute("producto", producto);
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("categorias", categorias);
        model.addAttribute("supermercados", supermercados);
        return "/admin/crear-producto";
    }

    //  formulario de creación de producto
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto,
                                  BindingResult result,
                                  @RequestParam("precio") String precioValor,
                                  @RequestParam("subcategoria.id") Long subcategoriaId,
                                  @RequestParam("supermercado.id") Long supermercadoId
    ) throws Exception {
        if (result.hasErrors()) {
            return "/admin/crear-producto";
        }

        // Set the 'activo' field to true if it is null
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }

        // Set the subcategory for the product
        Optional<Subcategoria> subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId);
        if (subcategoria.isPresent()) {
            producto.setSubcategoria(subcategoria.get());
        }

        // Save the product first to ensure it has an ID
        Producto productoGuardado = productosSrvc.guardar(producto);

        // Procesar imagenes
        if (producto.getImagenesArchivo() != null){
            for (MultipartFile file : producto.getImagenesArchivo()) {
                if (!file.isEmpty()) {
                    Imagen imagen = imagenSrvc.guardarImagenProducto(file, producto);
                    imagen.setProductos(producto);
                    imagenSrvc.guardar(imagen);
                }
            }
        }
        // Save the price
        Supermercado supermercado = (Supermercado) supermercadoSrvc.encuentraPorId(supermercadoId).orElse(null);
        Precio precio = new Precio();
        precio.setProducto(productoGuardado);
        precio.setValor(Double.parseDouble(precioValor.replace(",", ".")));
        precio.setSupermercado(supermercado);
        precio.setActivo(true);
        precioSrvc.guardar(precio);

        return "redirect:/admin/productos";
    }

    // Mostrar formulario para editar un producto
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productosSrvc.encuentraPorId(id);
        List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();
        List<Categoria> categorias = categoriasSrvc.buscarEntidades();
        List<Supermercado> supermercados = supermercadoSrvc.buscarEntidades();
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
        }
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("categorias", categorias);
        model.addAttribute("supermercados", supermercados);
        model.addAttribute("imagenes", imagenSrvc.buscarEntidades());
        return "/admin/crear-producto";
    }

    // post de edición de producto
//    @PreAuthorize("hasRole('ADMIN')")
    // Método para editar un producto
    @PostMapping("/editar/{id}")
    public String guardarEdicionProducto(@PathVariable Long id,
                                         @Valid @ModelAttribute("producto") Producto producto,
                                         BindingResult result,
                                         @RequestParam("precio") String precioValor,
                                         @RequestParam("subcategoria.id") Long subcategoriaId,
                                         @RequestParam("supermercado.id") Long supermercadoId,
                                         @RequestParam("imagenesArchivo") MultipartFile imagenesArchivo
    ) throws Exception {
        if (result.hasErrors()) {
            return "/admin/crear-producto";
        }

        // Set the 'activo' field to true if it is null
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }

        // Set the subcategory for the product
        Optional<Subcategoria> subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId);
        if (subcategoria.isPresent()) {
            producto.setSubcategoria(subcategoria.get());
        }

        // Save the product first to ensure it has an ID
        Producto productoGuardado = productosSrvc.guardar(producto);

        // Procesa la nueva imagen subida
        if (imagenesArchivo != null && !imagenesArchivo.isEmpty()) {
            // Eliminar la imagen existente si la hay
            Set<Imagen> imagenesExistentes = imagenSrvc.buscarPorProducto(producto);
            for (Imagen imagen : imagenesExistentes) {
                imagenSrvc.eliminarPorId(imagen.getId());
            }

            // Guardar la nueva imagen
            Imagen nuevaImagen = imagenSrvc.guardarImagenProducto(imagenesArchivo, producto);
            nuevaImagen.setProductos(producto);
            imagenSrvc.guardar(nuevaImagen);
        }
        // Update the price
        Supermercado supermercado = (Supermercado) supermercadoSrvc.encuentraPorId(supermercadoId).orElse(null);
        Optional<Precio> precioExistenteOpt = precioSrvc.encuentraPorId(productoGuardado.getId());
        if (precioExistenteOpt.isPresent()) {
            Precio precioExistente = precioExistenteOpt.get();
            precioExistente.setValor(Double.parseDouble(precioValor.replace(",", ".")));
            precioExistente.setSupermercado(supermercado);
            precioSrvc.guardar(precioExistente);
        } else {
            Precio nuevoPrecio = new Precio();
            nuevoPrecio.setProducto(productoGuardado);
            nuevoPrecio.setValor(Double.parseDouble(precioValor.replace(",", ".")));
            nuevoPrecio.setSupermercado(supermercado);
            nuevoPrecio.setActivo(true);
            precioSrvc.guardar(nuevoPrecio);
        }

        return "redirect:/admin/productos";
    }

    // Eliminar un producto
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        Set<Precio> precios = precioSrvc.buscarTodosSet(); // Asegúrate de que esto devuelve un Set<Precio>
        Optional<Precio> precioExistenteOpt = precios.stream()
                .filter(precio -> precio.getProducto() != null && precio.getProducto().getId() == id)
                .findFirst();
        // Si hay un precio existente, eliminarlo
        precioExistenteOpt.ifPresent(precio -> precioSrvc.eliminarPorId(precio.getId()));
        productosSrvc.eliminarPorId(id);
        return "redirect:/admin/productos"; // Redirige a la lista de productos
    }

    // DESCUENTOS
    // Mostrar formulario para agregar descuento a un producto
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/descuento/{id}")
    public String mostrarFormularioDescuento(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productosSrvc.encuentraPorId(id);
        List<TipoDescuento> tiposDescuento = tipoDescuentoSrvc.buscarEntidades();
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            model.addAttribute("tiposDescuento", tiposDescuento);
        }
        return "/admin/agregar-descuento";
    }

    // Post para agregar descuento a un producto
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/descuento/{id}")
    public String agregarDescuento(@PathVariable Long id,
                                   @RequestParam("tipoDescuentoId") Long tipoDescuentoId,
                                   @RequestParam("porcentaje") Double porcentaje,
                                   @RequestParam("fechaInicio") String fechaInicio,
                                   @RequestParam("fechaFin") String fechaFin) throws Exception {


        Optional<Producto> productoOpt = productosSrvc.encuentraPorId(id);
        Optional<TipoDescuento> tipoDescuentoOpt = tipoDescuentoSrvc.encuentraPorId(tipoDescuentoId);

        if (productoOpt.isPresent() && tipoDescuentoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            TipoDescuento tipoDescuento = tipoDescuentoOpt.get();

            Descuento descuento = new Descuento();
            descuento.setProducto(producto);
            descuento.setTipoDescuento(tipoDescuento);
            descuento.setPorcentaje(porcentaje);
            descuento.setFechaInicio(LocalDate.parse(fechaInicio));
            descuento.setFechaFin(LocalDate.parse(fechaFin));
            descuento.setActivo(true);

            descuentoSrvc.guardar(descuento);

            // Aplicar el descuento al precio actual
            Set<Precio> precios = producto.getPrecios();
            for (Precio precio : precios) {
                Double nuevoValor = precio.getValor() * (1 - porcentaje / 100);
                precio.setValor(nuevoValor);
                precioSrvc.guardar(precio);
            }
        }
        return "redirect:/admin/productos";
    }
}

