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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller()
@RequestMapping("/admin/productos")
public class ADMINProductoCtrl {

    private final SrvcProducto productosSrvc;
    private final SrvcPrecio preciosSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcCategoria categoriasSrvc;
    private final SrvcSubcategoria subcategoriasSrvc;

    public ADMINProductoCtrl(SrvcProducto productosSrvc, SrvcPrecio preciosSrvc, SrvcImagen imagenSrvc, SrvcCategoria categoriasSrvc, SrvcSubcategoria subcategoriasSrvc) {
        this.productosSrvc = productosSrvc;
        this.preciosSrvc = preciosSrvc;
        this.imagenSrvc = imagenSrvc;
        this.categoriasSrvc = categoriasSrvc;
        this.subcategoriasSrvc = subcategoriasSrvc;
    }


    @GetMapping
    public String adminListarProductos(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model) {
        Page<Producto> pagina = (Page<Producto>) productosSrvc.obtenerProductosPaginados(page, size);
        model.addAttribute("paginaProductos", pagina);
        return "admin/CRUD-Productos"; // Lo he llamado asi, porque desde esta vista se pueden hacer todas las funciones CRUD como admin de productos.
    }


    @GetMapping("/agregar")
    public String agregarProducto(Model model) throws JsonProcessingException {
        List<Categoria> categorias = categoriasSrvc.buscarEntidades();
        List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();

        // Crear un mapa de ID de categoría a lista de DTOs de subcategorías
        Map<Long, List<SubcategoriaDTO>> subcategoriasMap = subcategorias.stream()
                .collect(Collectors.groupingBy(
                        subcategoria -> subcategoria.getCategoria().getId(),
                        Collectors.mapping(
                                subcategoria -> new SubcategoriaDTO(subcategoria.getId(), subcategoria.getTipo()),
                                Collectors.toList()
                        )
                ));

        // Convertir el mapa a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String subcategoriasJson = objectMapper.writeValueAsString(subcategoriasMap);
        System.out.println("Subcategorías JSON: " + subcategoriasJson);


        model.addAttribute("categorias", categorias);
        model.addAttribute("subcategoriasMap", subcategoriasJson);
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("producto", new Producto());
        return "admin/crear-producto";
    }


    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                  @RequestParam("precio") Double precio,
                                  @RequestParam("categoria.id") Long categoriaId,
                                  @RequestParam(value = "subcategoria.id", required = false) Long subcategoriaId,
                                  Model model) throws Exception {
        // Actualización de un producto existente
        if (producto.getId() != null) {
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
                // Actualizar precio
                Precio nuevoActualizado = productoActualizado.getProductoPrecios();
                if (nuevoActualizado == null) {
                    nuevoActualizado = new Precio();
                    nuevoActualizado.setActivo(true);
                }
                Precio nuevoPrecio = productoActualizado.getProductoPrecios();
                nuevoPrecio.setValor(precio);
                preciosSrvc.guardar(nuevoPrecio);
                productoActualizado.setProductoPrecios(nuevoPrecio);

                // Actualizar la categoría y la subcategoría
                Categoria categoria = categoriasSrvc.encuentraPorId(categoriaId).orElse(null);
                Subcategoria subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId).orElse(null);
                productoActualizado.setCategoria(categoria);
                productoActualizado.setSubcategoria(subcategoria);
                //Guardar producto actualizado
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

            // Asocia la categoría y subcategoría
            Categoria categoria = categoriasSrvc.encuentraPorId(categoriaId).orElse(null);
            Subcategoria subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId).orElse(null);
            producto.setCategoria(categoria);
            producto.setSubcategoria(subcategoria);
            // Guarda el nuevo producto
            productosSrvc.guardar(producto);
        }
        return "redirect:/admin/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable("id") long id, Model model) throws JsonProcessingException {
        Optional<Producto> producto = productosSrvc.encuentraPorId(id);
        if (producto.isPresent()) {
            Producto productoEditado = producto.get();

            // Cargar todas las categorías y subcategorías
            List<Categoria> categorias = categoriasSrvc.buscarEntidades();
            List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();

            // Crear un mapa de ID de categoría a lista de DTOs de subcategorías
            Map<Long, List<SubcategoriaDTO>> subcategoriasMap = subcategorias.stream()
                    .collect(Collectors.groupingBy(
                            subcategoria -> subcategoria.getCategoria().getId(),
                            Collectors.mapping(
                                    subcategoria -> new SubcategoriaDTO(subcategoria.getId(), subcategoria.getTipo()),
                                    Collectors.toList()
                            )
                    ));
            // Convertir el mapa a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String subcategoriasJson = objectMapper.writeValueAsString(subcategoriasMap);

            model.addAttribute("producto", productoEditado);
            model.addAttribute("categorias", categorias);
            model.addAttribute("subcategoriasMap", subcategoriasJson);
            model.addAttribute("subcategorias", subcategorias);
            return "admin/crear-producto";
        } else {
            model.addAttribute("error", "Producto no encontrado");
            return "error";
        }
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
