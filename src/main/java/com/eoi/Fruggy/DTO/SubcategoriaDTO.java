package com.eoi.Fruggy.DTO;

public class SubcategoriaDTO {
    private Long id;
    private String tipo;

    public SubcategoriaDTO() {
    }

    public SubcategoriaDTO(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
