package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.repositorios.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class DataInitializer {

    private final RepoRol repoRol;
    private final RepoGenero repoGenero;
    private final RepoCategoria repoCategoria;
    private final RepoSubcategoria repoSubcategoria;
    private final RepoTipoDescuento repoTipoDescuento;
    private final RepoDescuento repoDescuento;
    private final MessageSource messageSource;


    @Autowired
    public DataInitializer(RepoRol repoRol, RepoGenero repoGenero, RepoCategoria repoCategoria,
                           RepoSubcategoria repoSubcategoria, RepoTipoDescuento repoTipoDescuento, RepoDescuento repoDescuento, MessageSource messageSource) {
        this.repoRol = repoRol;
        this.repoGenero = repoGenero;
        this.repoCategoria = repoCategoria;
        this.repoSubcategoria = repoSubcategoria;
        this.repoTipoDescuento = repoTipoDescuento;
        this.repoDescuento = repoDescuento;
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        Locale locale = new Locale("es");
        // Inicialización de roles
        if (repoRol.count() == 0) {
            repoRol.save(new Rol("ROLE_" + messageSource.getMessage("role.admin", null, locale),
                            messageSource.getMessage("role.admin.es.desc", null, locale),
                            messageSource.getMessage("role.admin.en.desc", null, locale)
                    )
            );
            repoRol.save(new Rol("ROLE_" + messageSource.getMessage("role.user", null, locale),
                            messageSource.getMessage("role.user.es.desc", null, locale),
                            messageSource.getMessage("role.user.en.desc", null, locale)
                    )
            );

        }

        // Inicialización de géneros
        if (repoGenero.count() == 0) {
            repoGenero.save(new Genero(messageSource.getMessage("gender.male", null, locale)));
            repoGenero.save(new Genero(messageSource.getMessage("gender.female", null, locale)));
            repoGenero.save(new Genero(messageSource.getMessage("gender.other", null, locale)));
        }

        try {
            // Cargar categorías
            if (repoCategoria.count() == 0) {
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.snacks", null, locale),
                        messageSource.getMessage("category.en.snacks", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.household", null, locale),
                        messageSource.getMessage("category.en.household", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.babies_kids", null, locale),
                        messageSource.getMessage("category.en.babies_kids", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.drinks", null, locale),
                        messageSource.getMessage("category.en.drinks", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.coffee_tea", null, locale),
                        messageSource.getMessage("category.en.coffee_tea", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.chocolates_sweets", null, locale),
                        messageSource.getMessage("category.en.chocolates_sweets", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.canned_goods", null, locale),
                        messageSource.getMessage("category.en.canned_goods", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.dietary", null, locale),
                        messageSource.getMessage("category.en.dietary", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.drugstore", null, locale),
                        messageSource.getMessage("category.en.drugstore", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.fresh_meats", null, locale),
                        messageSource.getMessage("category.en.fresh_meats", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.dairy_eggs", null, locale),
                        messageSource.getMessage("category.en.dairy_eggs", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.pets", null, locale),
                        messageSource.getMessage("category.en.pets", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.leisure_culture", null, locale),
                        messageSource.getMessage("category.en.leisure_culture", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.bakery", null, locale),
                        messageSource.getMessage("category.en.bakery", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.pasta_rice_legumes", null, locale),
                        messageSource.getMessage("category.en.pasta_rice_legumes", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.es.perfumery_pharmacy", null, locale),
                        messageSource.getMessage("category.en.perfumery_pharmacy", null, locale)));
            }
            List<Categoria> categorias = repoCategoria.findAll();

            // Cargar subcategorías
            if (repoSubcategoria.count() == 0) {
                for (Categoria categoria : categorias) {
                    switch (categoria.getTipo_es()) {
                        case "Aperitivos":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.chips", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.snacks", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.nuts", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.chocolates", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.salty_cookies", null, locale), categoria));
                            break;
                        case "Bazar & Casa":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.basket", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.kitchen_utensils", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.storage", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.decoration", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cleaning", null, locale), categoria));
                            break;
                        case "Bebés & Niños":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.diapers", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.wipes", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.baby_clothes", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.toys", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.infant_food", null, locale), categoria));
                            break;
                        case "Bebidas":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.soda", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.juice", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.energy_drink", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.sports_drink", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.mineral_water", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.vegetable_drink", null, locale), categoria));
                            break;
                        case "Café e Infusiones":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.coffee_beans", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.ground_coffee", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.relaxing_tea", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.fruit_tea", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.instant_coffee", null, locale), categoria));
                            break;
                        case "Chocolates & Dulces":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.milk_chocolate", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.dark_chocolate", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.bonbons", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.sugar_free_chocolates", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.candies", null, locale), categoria));
                            break;
                        case "Conservas, Aceite & Condimentos":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.olive_oil", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.sauces", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.canned_fish", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.spices", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.vinegar", null, locale), categoria));
                            break;
                        case "Dietéticos":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cereals", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.gluten_free_products", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.nutritional_supplements", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.healthy_snacks", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.energy_bars", null, locale), categoria));
                            break;
                        case "Droguería":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.multi_use_cleaners", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.detergent", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.bathroom_cleaners", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.sponges", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.broom", null, locale), categoria));
                            break;
                        case "Frescos & Charcutería":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.pork_sausage", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cooked_ham", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cooked_chicken", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.bacon", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cheese", null, locale), categoria));
                            break;
                        case "Lácteos & Huevos":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.lactose_free_milk", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.fresh_milk", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.eggs", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.yogurt", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.butter", null, locale), categoria));
                            break;
                        case "Mascotas":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.dog_food", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cat_food", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.bird_food", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.fish_food", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.dog_toys", null, locale), categoria));
                            break;
                        case "Ocio y Cultura":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.books", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.magazines", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.video_games", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.movies", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.toys", null, locale), categoria));
                            break;
                        case "Panadería, Bollería y Pastelería":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.bread", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.croissants", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cakes", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cupcakes", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.cookies", null, locale), categoria));
                            break;
                        case "Pasta, Arroz & Legumbres":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.rice", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.beans", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.pasta", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.lentils", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.chickpeas", null, locale), categoria));
                            break;
                        case "Perfumería & Parafarmacia":
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.perfumes", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.vitamins", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.medicines", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.hair_care", null, locale), categoria));
                            repoSubcategoria.save(new Subcategoria(messageSource.getMessage("subcategory.skincare", null, locale), categoria));
                            break;
                        default:
                            break;
                    }
                }
            }
            // Crear tipos de descuento
            if (repoTipoDescuento.count() == 0) {
                TipoDescuento descuentoPorVolumen = new TipoDescuento("Descuento por Volumen", true, LocalDate.now(), LocalDate.now().plusYears(1));
                TipoDescuento descuentoPorTemporada = new TipoDescuento("Descuento por Temporada", true, LocalDate.now(), LocalDate.now().plusMonths(3));
                TipoDescuento descuentoFidelidad = new TipoDescuento("Descuento por Fidelidad", true, LocalDate.now(), LocalDate.now().plusMonths(6));
                TipoDescuento descuentoLanzamiento = new TipoDescuento("Descuento por Lanzamiento", true, LocalDate.now(), LocalDate.now().plusMonths(1));
                TipoDescuento descuentoLiquidacion = new TipoDescuento("Descuento por Liquidación", true, LocalDate.now(), LocalDate.now().plusMonths(2));
                TipoDescuento descuentoEspecial = new TipoDescuento("Descuento Especial", true, LocalDate.now(), LocalDate.now().plusMonths(4));

                repoTipoDescuento.save(descuentoPorVolumen);
                repoTipoDescuento.save(descuentoPorTemporada);
                repoTipoDescuento.save(descuentoFidelidad);
                repoTipoDescuento.save(descuentoLanzamiento);
                repoTipoDescuento.save(descuentoLiquidacion);
                repoTipoDescuento.save(descuentoEspecial);
            }

            // Crear descuentos
            if (repoDescuento.count() == 0) {
                TipoDescuento descuentoPorVolumen = repoTipoDescuento.findByTipo("Descuento por Volumen");
                TipoDescuento descuentoPorTemporada = repoTipoDescuento.findByTipo("Descuento por Temporada");
                TipoDescuento descuentoFidelidad = repoTipoDescuento.findByTipo("Descuento por Fidelidad");
                TipoDescuento descuentoLanzamiento = repoTipoDescuento.findByTipo("Descuento por Lanzamiento");
                TipoDescuento descuentoLiquidacion = repoTipoDescuento.findByTipo("Descuento por Liquidación");
                TipoDescuento descuentoEspecial = repoTipoDescuento.findByTipo("Descuento Especial");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void crearsecuencial() {
//        // Creating and initializing the ArrayList
//        // Declaring object of integer type
//        List<String> productos = Arrays.asList("p1", "p2", "p3", "p2", "p5", "p6", "p2", "p1");
//        String[][] precios = {
//                {"12/05/2024", "12/05/2024", "1", "5.77"},
//                {"12/05/2024", "12/05/2024", "1", "5.77"},
//                {"12/05/2024", "12/05/2024", "1", "5.77"},
//                {"12/05/2024", "12/05/2024", "1", "5.77"},
//                {"12/05/2024", "12/05/2024", "1", "5.77"},
//                {"12/05/2024", "12/05/2024", "1", "5.77"},
//                {"12/05/2024", "12/05/2024", "1", "5.77"},
//                {"12/05/2024", "12/05/2024", "1", "5.77"}
//
//        };
//        List<String> nombreSuper = Arrays.asList("sup 1", "sup 2", "sup 3", "sup 1", "sup 1", "sup 2", "sup 3", "sup 1");
//
//        for (int i = 0; i < productos.size(); ++i) {
//            for (int j = 0; j < precios[i].length; ++j) {
    //Guardo la fecha iniciA J = 0
    //Guardo la fecha FINAL  J = 1
    //gUARDO ACTIVO J = 2
    //gUARDO PRECIO J = 3

    //gUARDO EL SUPER nombreSuper[0]


}
//uNA VEZ CREADO EL PRECIO CREO EL PRODUCTO AÑADIENDO EL PRECIO


//        }
//        ;
