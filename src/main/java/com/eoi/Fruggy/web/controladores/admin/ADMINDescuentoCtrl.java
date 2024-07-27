package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.TipoDescuento;
import com.eoi.Fruggy.servicios.SrvcDescuento;
import com.eoi.Fruggy.servicios.SrvcPrecio;
import com.eoi.Fruggy.servicios.SrvcProducto;
import com.eoi.Fruggy.servicios.SrvcTipodescuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/productos/descuentos")
public class ADMINDescuentoCtrl {

    private final SrvcDescuento descuentoSrvc;
    private final SrvcProducto productoSrvc;
    private final SrvcTipodescuento tipoDescuentoSrvc;
    private final SrvcPrecio precioSrvc;

    public ADMINDescuentoCtrl(SrvcDescuento descuentoSrvc, SrvcProducto productoSrvc, SrvcTipodescuento tipoDescuentoSrvc, SrvcPrecio precioSrvc) {
        this.descuentoSrvc = descuentoSrvc;
        this.productoSrvc = productoSrvc;
        this.tipoDescuentoSrvc = tipoDescuentoSrvc;
        this.precioSrvc = precioSrvc;
    }


    @GetMapping("/editar/{id}")
    public String verDescuento(@PathVariable("id") Long id, Model model) {
        Optional<Descuento> descuento = descuentoSrvc.encuentraPorId(id);
        if (descuento.isPresent()) {
            model.addAttribute("descuento", descuento.get());
            model.addAttribute("tiposDescuento", tipoDescuentoSrvc.buscarEntidades());
            model.addAttribute("precios", precioSrvc.buscarEntidades());

            // Obtenemos el precio asociado
            Precio precio = descuento.get().getDescuentosPrecios();
            model.addAttribute("precio", precio);

            return "admin/descuentos-productos";
        } else {
            model.addAttribute("error", "Descuento no encontrado");
            return "error";
        }
    }

    @PostMapping("/guardar/{id}")
    public String guardarDescuento(
            @PathVariable("id") Long id,
            @ModelAttribute Descuento descuentoActualizado,
            @RequestParam("tipoDeDescuentoId") Long tipoDescuentoId,
            Model model) throws Exception {
        Optional<Descuento> descuentoOptional = descuentoSrvc.encuentraPorId(descuentoActualizado.getId());
        if (descuentoOptional.isPresent()) {
            Descuento existente = descuentoOptional.get();
            // Actualizar los valores
            existente.setFechaInicio(descuentoActualizado.getFechaInicio());
            existente.setFechaFin(descuentoActualizado.getFechaFin());
            existente.setActivo(descuentoActualizado.getActivo());

            // Asignar el tipo de descuento
            Optional<TipoDescuento> tipoDescuentoOptional = tipoDescuentoSrvc.getRepo().findById(tipoDescuentoId);
            if (tipoDescuentoOptional.isPresent()) {
                TipoDescuento tipo = tipoDescuentoOptional.get();
                existente.setDescuentoTipoDescuento(tipo);
            } else {
                model.addAttribute("error", "Tipo de descuento no encontrado");
                return "error";
            }

            // Obtener el precio del producto asociado
            Producto producto = existente.getProducto(); // Asegúrate de que el descuento tenga un producto asociado
            if (producto != null) {
                Precio precio = producto.getProductoPrecios(); // Obtener el precio asociado al producto
                if (precio != null) {
                    existente.setDescuentosPrecios(precio);

                    // No modificar el precio original
                    Double precioOriginal = precio.getValor();
                    Double porcentajeDescuento = tipoDescuentoOptional.get().getPorcentaje();
                    if (precioOriginal != null && porcentajeDescuento != null) {
                        Double descuentoAplicado = precioOriginal * (porcentajeDescuento / 100);
                        Double precioConDescuento = precioOriginal - descuentoAplicado;

                        // Solo guardar el descuento, sin modificar el precio original
                        model.addAttribute("precioOriginal", precioOriginal);
                        model.addAttribute("precioConDescuento", precioConDescuento);
                    }
                } else {
                    model.addAttribute("error", "Precio no encontrado para el producto asociado");
                    return "error";
                }
            } else {
                model.addAttribute("error", "Producto no asociado al descuento");
                return "error";
            }

            // Guardar Descuento
            try {
                descuentoSrvc.guardar(existente);
                model.addAttribute("descuento", existente);
                model.addAttribute("mensaje", "Descuento actualizado correctamente");
                return "redirect:/admin/productos/descuentos/descuento-actualizado/" + existente.getId();
            } catch (Exception e) {
                model.addAttribute("error", "Error al guardar descuento");
                return "error";
            }
        } else {
            model.addAttribute("error", "Descuento no encontrado");
            return "error";
        }
    }

    @GetMapping("/descuento-actualizado/{id}")
    public String verDescuentoActualizado(@PathVariable("id") Long id, Model model) {
        Optional<Descuento> descuentoActualizado = descuentoSrvc.encuentraPorId(id);
        if (descuentoActualizado.isPresent()) {
            Descuento descuento = descuentoActualizado.get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaInicioFormateada = (descuento.getFechaInicio() != null) ? convertToDate(descuento.getFechaInicio()).toInstant().atZone(ZoneId.systemDefault()).format(formatter) : "No disponible";
            String fechaFinFormateada = (descuento.getFechaFin() != null) ? convertToDate(descuento.getFechaFin()).toInstant().atZone(ZoneId.systemDefault()).format(formatter) : "No disponible";

            // Si el descuento tiene un producto asociado, obtén el producto
            Producto producto = descuento.getProducto();
            Double precioOriginal = (producto != null && producto.getProductoPrecios() != null)
                    ? producto.getProductoPrecios().getValor()
                    : null;
            // Calcula el precio con descuento
            Double precioConDescuento = (descuento.getDescuentoTipoDescuento() != null && precioOriginal != null)
                    ? precioOriginal * (1 - (descuento.getDescuentoTipoDescuento().getPorcentaje() / 100))
                    : precioOriginal;

            model.addAttribute("fechaInicioFormateada", fechaInicioFormateada);
            model.addAttribute("fechaFinFormateada", fechaFinFormateada);
            model.addAttribute("descuento", descuento);
            model.addAttribute("precioOriginal", precioOriginal);
            model.addAttribute("precioConDescuento", precioConDescuento);
            return "admin/descuentos-productos-actualizados";
        } else {
            model.addAttribute("error", "Descuento no encontrado");
            return "error";
        }
    }


    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

// mirar https://www.thymeleaf.org/doc/articles/springsecurity.html
//Principal principal
// https://stackoverflow.com/questions/47927962/accessing-userdetails-object-from-controller-in-spring-using-spring-security