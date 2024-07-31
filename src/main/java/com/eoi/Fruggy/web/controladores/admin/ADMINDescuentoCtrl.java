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

            return "admin/descuentos-productos";
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


            model.addAttribute("fechaInicioFormateada", fechaInicioFormateada);
            model.addAttribute("fechaFinFormateada", fechaFinFormateada);
            model.addAttribute("descuento", descuento);
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