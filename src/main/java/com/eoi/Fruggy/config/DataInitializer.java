package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.repositorios.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            repoRol.save(new Rol(messageSource.getMessage("role.admin", null, locale)));
            repoRol.save(new Rol(messageSource.getMessage("role.user", null, locale)));
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
                repoCategoria.save(new Categoria(messageSource.getMessage("category.snacks", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.household", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.babies_kids", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.drinks", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.coffee_tea", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.chocolates_sweets", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.canned_goods", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.dietary", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.drugstore", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.fresh_meats", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.dairy_eggs", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.pets", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.leisure_culture", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.bakery", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.pasta_rice_legumes", null, locale)));
                repoCategoria.save(new Categoria(messageSource.getMessage("category.perfumery_pharmacy", null, locale)));
            }
            List<Categoria> categorias = repoCategoria.findAll();

            // Cargar subcategorías
            if (repoSubcategoria.count() == 0) {
                for (Categoria categoria : categorias) {
                    switch (categoria.getTipo()) {
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
                        case "Bébes & Niños":
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
                        case "Diéteticos":
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

            // Crear descuentos
            Descuento descuento1 = new Descuento(10.0, LocalDate.now(), LocalDate.now().plusDays(30), true, null, descuentoPorVolumen);
            Descuento descuento2 = new Descuento(20.0, LocalDate.now(), LocalDate.now().plusDays(15), true, null, descuentoPorTemporada);
            Descuento descuento3 = new Descuento(5.0, LocalDate.now(), LocalDate.now().plusMonths(6), true, null, descuentoFidelidad);
            Descuento descuento4 = new Descuento(15.0, LocalDate.now(), LocalDate.now().plusMonths(1), true, null, descuentoLanzamiento);
            Descuento descuento5 = new Descuento(25.0, LocalDate.now(), LocalDate.now().plusMonths(2), true, null, descuentoLiquidacion);
            Descuento descuento6 = new Descuento(30.0, LocalDate.now(), LocalDate.now().plusMonths(4), true, null, descuentoEspecial);

            repoDescuento.save(descuento1);
            repoDescuento.save(descuento2);
            repoDescuento.save(descuento3);
            repoDescuento.save(descuento4);
            repoDescuento.save(descuento5);
            repoDescuento.save(descuento6);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    }