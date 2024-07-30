package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.DTO.SubcategoriaDTO;
import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/productos")
public class ADMINProductoCtrl {

    private final SrvcProducto productosSrvc;
    private final SrvcPrecio preciosSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcCategoria categoriasSrvc;
    private final SrvcSubcategoria subcategoriasSrvc;
    private final SrvcDescuento descuentosSrvc;
    private final SrvcTipodescuento tipodescuentosSrvc;

    public ADMINProductoCtrl(SrvcProducto productosSrvc, SrvcPrecio preciosSrvc, SrvcImagen imagenSrvc, SrvcCategoria categoriasSrvc, SrvcSubcategoria subcategoriasSrvc, SrvcDescuento descuentosSrvc, SrvcTipodescuento tipodescuentosSrvc) {
        this.productosSrvc = productosSrvc;
        this.preciosSrvc = preciosSrvc;
        this.imagenSrvc = imagenSrvc;
        this.categoriasSrvc = categoriasSrvc;
        this.subcategoriasSrvc = subcategoriasSrvc;
        this.descuentosSrvc = descuentosSrvc;
        this.tipodescuentosSrvc = tipodescuentosSrvc;
    }

//    @GetMapping
//    public String adminListarProductos(@RequestParam(defaultValue = "0") int page,
//                                       @RequestParam(defaultValue = "10") int size,
//                                       Model model) {
//        Page<Producto> pagina = productosSrvc.obtenerProductosPaginados(page, size);
//
//        pagina.forEach(producto -> {
//            if (producto.getDescuento() != null && producto.getDescuento().getDescuentoTipoDescuento() != null) {
//                Double precioOriginal = producto.getProductoPrecios().getValor();
//                Double porcentajeDescuento = producto.getDescuento().getDescuentoTipoDescuento().getPorcentaje();
//                Double descuentoAplicado = precioOriginal * (porcentajeDescuento / 100);
//                Double precioConDescuento = precioOriginal - descuentoAplicado;
//                producto.getProductoPrecios().setPrecioConDescuento(precioConDescuento);
//            } else {
//                producto.getProductoPrecios().setPrecioConDescuento(producto.getProductoPrecios().getValor());
//            }
//        });
//
//        model.addAttribute("paginaProductos", pagina);
//        return "admin/CRUD-Productos";
//    }

    @GetMapping("/agregar")
    public String agregarProducto(Model model) throws JsonProcessingException {
        List<Categoria> categorias = categoriasSrvc.buscarEntidades();
        List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();
        Map<Long, List<SubcategoriaDTO>> subcategoriasMap = subcategorias.stream()
                .collect(Collectors.groupingBy(
                        subcategoria -> subcategoria.getCategoria().getId(),
                        Collectors.mapping(
                                subcategoria -> new SubcategoriaDTO(subcategoria.getId(), subcategoria.getTipo()),
                                Collectors.toList()
                        )
                ));

        ObjectMapper objectMapper = new ObjectMapper();
        String subcategoriasJson = objectMapper.writeValueAsString(subcategoriasMap);
        System.out.println("Subcategorías JSON: " + subcategoriasJson);

        model.addAttribute("categorias", categorias);
        model.addAttribute("subcategoriasMap", subcategoriasJson);
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("producto", new Producto());
        model.addAttribute("descuentos", new Descuento());
        List<TipoDescuento> tipoDescuentos = tipodescuentosSrvc.buscarEntidades();
        model.addAttribute("tiposDescuento", tipoDescuentos);
        return "admin/crear-producto";
    }
//
//    @PostMapping("/guardar")
//    public String guardarProductoConDetalles(@Valid @ModelAttribute Producto producto,
//                                             Descuento descuento,
//                                             @RequestParam(value = "tipoDescuentos", required = false) List<Long> tipoDescuentosSeleccionados,
//                                             @RequestParam(value = "file", required = false) MultipartFile file,
//                                             @RequestParam("precio") String precio, // Recibido como String
//                                             @RequestParam("categoria.id") Long categoriaId,
//                                             @RequestParam(value = "subcategoria.id", required = false) Long subcategoriaId,
//                                             BindingResult result,
//                                             Model model) throws Exception {
//        // Validar el producto
//        if (result.hasErrors()) {
//            model.addAttribute("categorias", categoriasSrvc.buscarEntidades());
//            model.addAttribute("subcategorias", subcategoriasSrvc.buscarEntidades());
//            return "admin/crear-producto";
//        }
//
//        try {
//            // No funciona el simobolo del dolar, por lo tanto: eliminar el símbolo de euro y convertir a formato Double
//            String precioSinEuro = precio.replace(" €", "").replace(",", ".").trim();
//            double precioDouble = Double.parseDouble(precioSinEuro);
//
//            // Validar el rango del precio
//            if (precioDouble < 0.10 || precioDouble > 1000.00) {
//                model.addAttribute("error", "El precio debe estar entre 0.10 y 1000.00");
//                model.addAttribute("categorias", categoriasSrvc.buscarEntidades());
//                model.addAttribute("subcategorias", subcategoriasSrvc.buscarEntidades());
//                return "admin/crear-producto"; // Regresar al formulario si la validación falla
//            }
//
//            // Manejo de la imagen
//            if (file != null && !file.isEmpty()) {
//                String directoryPath = "D:\\img";
//                File directory = new File(directoryPath);
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//                String fileName = file.getOriginalFilename();
//                File targetFile = new File(directoryPath + File.separator + fileName);
//                file.transferTo(targetFile);
//
//                Imagen imagen = new Imagen();
//                imagen.setNombreArchivo(fileName);
//                imagen.setRuta(directoryPath);
//                imagen.setPathImagen("/images/" + fileName);
//                imagenSrvc.guardar(imagen);
//                producto.setImagen(imagen);
//            }
//
//            // Guardar precio
//            Precio nuevoPrecio = new Precio();
//            nuevoPrecio.setActivo(true);
//            nuevoPrecio.setValor(precioDouble); // Asignar el precio validado
//            preciosSrvc.guardar(nuevoPrecio);
//            producto.setProductoPrecios(nuevoPrecio);
//
//            // Asignar categoría y subcategoría
//            Categoria categoria = categoriasSrvc.encuentraPorId(categoriaId).orElse(null);
//            Subcategoria subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId).orElse(null);
//            producto.setCategoria(categoria);
//            producto.setSubcategoria(subcategoria);
//
//            // Asignar tipos de descuentos
//            Set<TipoDescuento> tipoDescuentos = new HashSet<>();
//            if (tipoDescuentosSeleccionados != null) {
//                for (Long tipoDescuentoId : tipoDescuentosSeleccionados) {
//                    Optional<TipoDescuento> tipoDescuentoOpt = tipodescuentosSrvc.encuentraPorId(tipoDescuentoId);
//                    tipoDescuentoOpt.ifPresent(tipoDescuentos::add);
//                }
//            }
//            producto.setTipoDescuentos(tipoDescuentos);
//            producto.setDescuento(descuento);
//            productosSrvc.guardar(producto);
//            descuentosSrvc.guardar(descuento);
//            for (TipoDescuento tipoDescuento : tipoDescuentos) {
//                tipodescuentosSrvc.actualizarTipoDescuento(tipoDescuento);
//            }
//            return "redirect:/admin/productos";
//        } catch (NumberFormatException e) {
//            model.addAttribute("error", "El precio debe estar en un formato válido.");
//            model.addAttribute("categorias", categoriasSrvc.buscarEntidades());
//            model.addAttribute("subcategorias", subcategoriasSrvc.buscarEntidades());
//            return "admin/crear-producto"; // Regresar al formulario si hay un error de formato
//        } catch (Exception e) {
//            model.addAttribute("error", "Error al guardar el producto");
//            return "error"; // hay que crear un error generico
//        }
//    }
//
//    @PostMapping("/descuentos/asignar-tipoDescuento")
//    public String asignarTipoDescuento(@RequestParam("producto_id") Long productoId, @RequestParam("tipoDescuentoId") Long tipoDescuentoId) throws Exception {
//        Optional<Producto> productoOpt = productosSrvc.encuentraPorId(productoId);
//        Optional<TipoDescuento> tipoDescuentoOpt = tipodescuentosSrvc.encuentraPorId(tipoDescuentoId);
//        if (productoOpt.isPresent() && tipoDescuentoOpt.isPresent()) {
//            Producto p = productoOpt.get();
//            TipoDescuento t = tipoDescuentoOpt.get();
//            tipodescuentosSrvc.asignarTipoDescuentoAProducto(p, t);
//        }
//        return "redirect:/admin/productos";
//    }
//
//    @PostMapping("/descuentos/quitar-tipoDescuento")
//    public String quitarTipoDescuento(@RequestParam("producto_id") Long productoId, @RequestParam("tipoDescuentoId") Long tipoDescuentoId) throws Exception {
//        Optional<Producto> productoOpt = productosSrvc.encuentraPorId(productoId);
//        Optional<TipoDescuento> tipoDescuentoOpt = tipodescuentosSrvc.encuentraPorId(tipoDescuentoId);
//        if (productoOpt.isPresent() && tipoDescuentoOpt.isPresent()) {
//            Producto p = productoOpt.get();
//            TipoDescuento t = tipoDescuentoOpt.get();
//            tipodescuentosSrvc.quitarTipoDescuentoAProducto(p, t);
//        }
//        return "redirect:/admin/productos";
//    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable("id") long id, Model model) throws Exception {
        Optional<Producto> producto = productosSrvc.encuentraPorId(id);
        if (producto.isPresent()) {
            Producto productoEditado = producto.get();

            List<Categoria> categorias = categoriasSrvc.buscarEntidades();
            List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();

            Map<Long, List<SubcategoriaDTO>> subcategoriasMap = subcategorias.stream()
                    .collect(Collectors.groupingBy(
                            subcategoria -> subcategoria.getCategoria().getId(),
                            Collectors.mapping(
                                    subcategoria -> new SubcategoriaDTO(subcategoria.getId(), subcategoria.getTipo()),
                                    Collectors.toList()
                            )
                    ));


            model.addAttribute("categorias", categorias);
            model.addAttribute("subcategoriasMap", subcategoriasMap);
            model.addAttribute("subcategorias", subcategorias);
            model.addAttribute("producto", productoEditado);
            List<TipoDescuento> tiposDescuento = tipodescuentosSrvc.buscarEntidades();
            model.addAttribute("tiposDescuento", tiposDescuento);
            return "admin/crear-producto";
        } else {
            model.addAttribute("error", "Producto no encontrado");
            return "error";
        }
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicion(@PathVariable("id") long id, @ModelAttribute Producto producto) throws Exception {
        producto.setId(id);
        productosSrvc.guardar(producto);
        return "redirect:/admin/productos";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            productosSrvc.eliminarPorId(id);
            return "redirect:/admin/productos";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}