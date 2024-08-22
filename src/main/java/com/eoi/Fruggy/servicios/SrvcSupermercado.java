package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoSupermercado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SrvcSupermercado extends AbstractSrvc {
    protected SrvcSupermercado(RepoSupermercado repoSupermercado) {
        super(repoSupermercado);
    }

    public Page<Supermercado> obtenerSupermercadosPaginados(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Order.desc(sortField) : Sort.Order.asc(sortField));
        Pageable pageable = PageRequest.of(page, size, sort);
        return getRepo().findAll(pageable);
    }
}