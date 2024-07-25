package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.DTO.SubcategoriaDTO;
import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping
    public String adminListarProductos(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model) {
        Page<Producto> pagina = productosSrvc.obtenerProductosPaginados(page, size);
        model.addAttribute("paginaProductos", pagina);
        return "admin/CRUD-Productos";
    }

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

    @PostMapping("/guardar")
    public String guardarProductoConDetalles(@ModelAttribute Producto producto, Descuento descuento,
                                             @RequestParam (value="tipoDescuentos", required = false) List<Long> tipoDescuentosSeleccionados,
                                             @RequestParam(value = "file", required = false) MultipartFile file,
                                             @RequestParam("precio") Double precio,
                                             @RequestParam("categoria.id") Long categoriaId,
                                             @RequestParam(value = "subcategoria.id", required = false) Long subcategoriaId,
                                             Model model) throws Exception {
        try {
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

            Precio nuevoPrecio = producto.getProductoPrecios();
            if (nuevoPrecio == null) {
                nuevoPrecio = new Precio();
                nuevoPrecio.setActivo(true);
            }
            nuevoPrecio.setValor(precio);
            preciosSrvc.guardar(nuevoPrecio);
            producto.setProductoPrecios(nuevoPrecio);

            Categoria categoria = categoriasSrvc.encuentraPorId(categoriaId).orElse(null);
            Subcategoria subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId).orElse(null);
            producto.setCategoria(categoria);
            producto.setSubcategoria(subcategoria);

            Set<TipoDescuento> tipoDescuentos =new HashSet<>();
            if (tipoDescuentosSeleccionados != null) {
                for (Long tipoDescuentoId : tipoDescuentosSeleccionados) {
                    Optional<TipoDescuento> tipoDescuentoOpt = tipodescuentosSrvc.encuentraPorId(tipoDescuentoId);
                    if (tipoDescuentoOpt.isPresent()) {
                        tipoDescuentos.add(tipoDescuentoOpt.get());
                    }else {
                        System.out.println("No existe el tipo de descuento" + tipoDescuentoId);
                    }
                }
            }
            producto.setTipoDescuentos(tipoDescuentos);
            producto.setDescuento(descuento);
            productosSrvc.guardar(producto);
            descuentosSrvc.guardar(descuento);
            for (TipoDescuento tipoDescuento : tipoDescuentos) {
                tipodescuentosSrvc.actualizarTipoDescuento(tipoDescuento);
            }
            return "redirect:/admin/productos";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el producto");
            return "error";
        }
    }

    @PostMapping("/descuentos/asignar-tipoDescuento")
    public String asignarTipoDescuento(@RequestParam("producto_id") Long productoId, @RequestParam("tipoDescuentoId") Long tipoDescuentoId) throws Exception {
        Optional<Producto> productoOpt = productosSrvc.encuentraPorId(productoId);
        Optional<TipoDescuento> tipoDescuentoOpt = tipodescuentosSrvc.encuentraPorId(tipoDescuentoId);
        if (productoOpt.isPresent() && tipoDescuentoOpt.isPresent()) {
            Producto p = productoOpt.get();
            TipoDescuento t = tipoDescuentoOpt.get();
            tipodescuentosSrvc.asignarTipoDescuentoAProducto(p, t);
        }
        return "redirect:/admin/productos";
    }
    @PostMapping("/descuentos/quitar-tipoDescuento")
    public String quitarTipoDescuento(@RequestParam("producto_id") Long productoId, @RequestParam("tipoDescuentoId") Long tipoDescuentoId) throws Exception {
        Optional<Producto> productoOpt = productosSrvc.encuentraPorId(productoId);
        Optional<TipoDescuento> tipoDescuentoOpt = tipodescuentosSrvc.encuentraPorId(tipoDescuentoId);
        if (productoOpt.isPresent() && tipoDescuentoOpt.isPresent()) {
            Producto p = productoOpt.get();
            TipoDescuento t = tipoDescuentoOpt.get();
            tipodescuentosSrvc.quitarTipoDescuentoAProducto(p, t);
        }
        return "redirect:/admin/productos";
    }

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