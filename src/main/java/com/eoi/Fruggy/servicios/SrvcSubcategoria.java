package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoProducto;
import com.eoi.Fruggy.repositorios.RepoSubcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SrvcSubcategoria extends AbstractSrvc<Subcategoria, Long, RepoSubcategoria> {
    @Autowired
    private RepoSubcategoria repoSubcategoria;

    @Autowired
    protected SrvcSubcategoria(RepoSubcategoria repoSubcategoria) {
        super(repoSubcategoria);
    }

    public List<Subcategoria> buscarPorCategoriaId(Long categoriaId) {
        return repoSubcategoria.findByCategoriaId(categoriaId);
    }
    public Map<Long, List<Subcategoria>> buscarEntidadesAgrupadasPorCategoria() {
        List<Subcategoria> subcategorias = repoSubcategoria.findAll();
        return subcategorias.stream().collect(Collectors.groupingBy(subcategoria -> subcategoria.getCategoria().getId()));
    }

}
