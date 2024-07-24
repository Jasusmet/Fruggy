package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoCategoria;
import com.eoi.Fruggy.repositorios.RepoGenero;
import com.eoi.Fruggy.repositorios.RepoRol;
import com.eoi.Fruggy.repositorios.RepoSubcategoria;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class DataInitializer {
    @Autowired
    private RepoRol repoRol;
    @Autowired
    private RepoGenero repoGenero;
    @Autowired
    RepoCategoria repoCategoria;
    @Autowired
    RepoSubcategoria repoSubcategoria;

    @PostConstruct
    public void init() {
        //  roles
        if (repoRol.count() == 0) {
            repoRol.save(new Rol("admin"));
            repoRol.save(new Rol("user"));
        }

        //  géneros
        if (repoGenero.count() == 0) {
            repoGenero.save(new Genero("Masculino"));
            repoGenero.save(new Genero("Femenino"));
            repoGenero.save(new Genero("Otros"));
        }
        try {
            // Cargar categorías
            if (repoCategoria.count() == 0) {
                repoCategoria.save(new Categoria("Aperitivos"));
                repoCategoria.save(new Categoria("Bazar & Casa"));
                repoCategoria.save(new Categoria("Bébes & Niños"));
                repoCategoria.save(new Categoria("Bebidas"));
                repoCategoria.save(new Categoria("Café e Infusiones"));
                repoCategoria.save(new Categoria("Chocolates & Dulces"));
                repoCategoria.save(new Categoria("Conservas, Aceite & Condimentos"));
                repoCategoria.save(new Categoria("Diéteticos"));
                repoCategoria.save(new Categoria("Droguería"));
                repoCategoria.save(new Categoria("Frescos & Charcutería"));
                repoCategoria.save(new Categoria("Lácteos & Huevos"));
                repoCategoria.save(new Categoria("Mascotas"));
                repoCategoria.save(new Categoria("Ocio y Cultura"));
                repoCategoria.save(new Categoria("Panadería, Bollería y Pastelería"));
                repoCategoria.save(new Categoria("Pasta, Arroz & Legumbres"));
                repoCategoria.save(new Categoria("Perfumería & Parafarmacia"));
            }
            List<Categoria> categorias = repoCategoria.findAll();

            // Cargar subcategorías
            if (repoSubcategoria.count() == 0) {
                for (Categoria categoria : categorias) {
                    switch (categoria.getTipo()) {
                        case "Aperitivos":
                            repoSubcategoria.save(new Subcategoria("Patatas Fritas", categoria));
                            repoSubcategoria.save(new Subcategoria("Snacks", categoria));
                            repoSubcategoria.save(new Subcategoria("Frutos Secos", categoria));
                            repoSubcategoria.save(new Subcategoria("Chocolates", categoria));
                            repoSubcategoria.save(new Subcategoria("Galletas Saladas", categoria));
                            break;
                        case "Bazar & Casa":
                            repoSubcategoria.save(new Subcategoria("Cestas de Mimbre", categoria));
                            repoSubcategoria.save(new Subcategoria("Utensilios de Cocina", categoria));
                            repoSubcategoria.save(new Subcategoria("Almacenaje", categoria));
                            repoSubcategoria.save(new Subcategoria("Decoración", categoria));
                            repoSubcategoria.save(new Subcategoria("Limpieza", categoria));
                            break;
                        case "Bébes & Niños":
                            repoSubcategoria.save(new Subcategoria("Pañales", categoria));
                            repoSubcategoria.save(new Subcategoria("Toallitas", categoria));
                            repoSubcategoria.save(new Subcategoria("Ropa de Bebé", categoria));
                            repoSubcategoria.save(new Subcategoria("Juguetes", categoria));
                            repoSubcategoria.save(new Subcategoria("Alimentación Infantil", categoria));
                            break;
                        case "Bebidas":
                            repoSubcategoria.save(new Subcategoria("Refresco", categoria));
                            repoSubcategoria.save(new Subcategoria("Zumo", categoria));
                            repoSubcategoria.save(new Subcategoria("Bebida Energética", categoria));
                            repoSubcategoria.save(new Subcategoria("Bebida Isotónica", categoria));
                            repoSubcategoria.save(new Subcategoria("Agua Mineral", categoria));
                            repoSubcategoria.save(new Subcategoria("Bebida Vegetal", categoria));
                            break;
                        case "Café e Infusiones":
                            repoSubcategoria.save(new Subcategoria("Café en Grano", categoria));
                            repoSubcategoria.save(new Subcategoria("Café Molido", categoria));
                            repoSubcategoria.save(new Subcategoria("Infusiones Relajantes", categoria));
                            repoSubcategoria.save(new Subcategoria("Tés Frutales", categoria));
                            repoSubcategoria.save(new Subcategoria("Café Instantáneo", categoria));
                            break;
                        case "Chocolates & Dulces":
                            repoSubcategoria.save(new Subcategoria("Chocolate con Leche", categoria));
                            repoSubcategoria.save(new Subcategoria("Chocolate Negro", categoria));
                            repoSubcategoria.save(new Subcategoria("Bombones", categoria));
                            repoSubcategoria.save(new Subcategoria("Chocolates sin Azúcar", categoria));
                            repoSubcategoria.save(new Subcategoria("Caramelos", categoria));
                            break;
                        case "Conservas, Aceite & Condimentos":
                            repoSubcategoria.save(new Subcategoria("Aceite de Oliva", categoria));
                            repoSubcategoria.save(new Subcategoria("Salsas", categoria));
                            repoSubcategoria.save(new Subcategoria("Conservas de Pescado", categoria));
                            repoSubcategoria.save(new Subcategoria("Especias", categoria));
                            repoSubcategoria.save(new Subcategoria("Vinagre", categoria));
                            break;
                        case "Diéteticos":
                            repoSubcategoria.save(new Subcategoria("Cereales", categoria));
                            repoSubcategoria.save(new Subcategoria("Productos sin Gluten", categoria));
                            repoSubcategoria.save(new Subcategoria("Suplementos Nutricionales", categoria));
                            repoSubcategoria.save(new Subcategoria("Snacks Saludables", categoria));
                            repoSubcategoria.save(new Subcategoria("Barras Energéticas", categoria));
                            break;
                        case "Droguería":
                            repoSubcategoria.save(new Subcategoria("Limpiadores Multiusos", categoria));
                            repoSubcategoria.save(new Subcategoria("Detergentes", categoria));
                            repoSubcategoria.save(new Subcategoria("Productos para la Ropa", categoria));
                            repoSubcategoria.save(new Subcategoria("Productos de Higiene", categoria));
                            repoSubcategoria.save(new Subcategoria("Ambientadores", categoria));
                            break;
                        case "Frescos & Charcutería":
                            repoSubcategoria.save(new Subcategoria("Embutidos", categoria));
                            repoSubcategoria.save(new Subcategoria("Carnes", categoria));
                            repoSubcategoria.save(new Subcategoria("Pescados", categoria));
                            repoSubcategoria.save(new Subcategoria("Frutas y Verduras", categoria));
                            repoSubcategoria.save(new Subcategoria("Quesos Frescos", categoria));
                            break;
                        case "Lácteos & Huevos":
                            repoSubcategoria.save(new Subcategoria("Leche", categoria));
                            repoSubcategoria.save(new Subcategoria("Yogur", categoria));
                            repoSubcategoria.save(new Subcategoria("Queso", categoria));
                            repoSubcategoria.save(new Subcategoria("Mantequilla", categoria));
                            repoSubcategoria.save(new Subcategoria("Nata", categoria));
                            break;
                        case "Mascotas":
                            repoSubcategoria.save(new Subcategoria("Piensos", categoria));
                            repoSubcategoria.save(new Subcategoria("Juguetes para Mascotas", categoria));
                            repoSubcategoria.save(new Subcategoria("Accesorios para Mascotas", categoria));
                            repoSubcategoria.save(new Subcategoria("Cuidado y Higiene", categoria));
                            repoSubcategoria.save(new Subcategoria("Snacks para Mascotas", categoria));
                            break;
                        case "Ocio y Cultura":
                            repoSubcategoria.save(new Subcategoria("Libros", categoria));
                            repoSubcategoria.save(new Subcategoria("Revistas", categoria));
                            repoSubcategoria.save(new Subcategoria("Películas", categoria));
                            repoSubcategoria.save(new Subcategoria("Música", categoria));
                            repoSubcategoria.save(new Subcategoria("Juegos de Mesa", categoria));
                            break;
                        case "Panadería, Bollería y Pastelería":
                            repoSubcategoria.save(new Subcategoria("Pan", categoria));
                            repoSubcategoria.save(new Subcategoria("Croissants", categoria));
                            repoSubcategoria.save(new Subcategoria("Pasteles", categoria));
                            repoSubcategoria.save(new Subcategoria("Bollos", categoria));
                            repoSubcategoria.save(new Subcategoria("Donuts", categoria));
                            break;
                        case "Pasta, Arroz & Legumbres":
                            repoSubcategoria.save(new Subcategoria("Pasta", categoria));
                            repoSubcategoria.save(new Subcategoria("Arroz", categoria));
                            repoSubcategoria.save(new Subcategoria("Legumbres", categoria));
                            repoSubcategoria.save(new Subcategoria("Salsas para Pasta", categoria));
                            repoSubcategoria.save(new Subcategoria("Cereales", categoria));
                            break;
                        case "Perfumería & Parafarmacia":
                            repoSubcategoria.save(new Subcategoria("Perfumes", categoria));
                            repoSubcategoria.save(new Subcategoria("Cremas Faciales", categoria));
                            repoSubcategoria.save(new Subcategoria("Jabones", categoria));
                            repoSubcategoria.save(new Subcategoria("Geles de Ducha", categoria));
                            repoSubcategoria.save(new Subcategoria("Productos de Maquillaje", categoria));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}