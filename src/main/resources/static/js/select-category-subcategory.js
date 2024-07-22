document.addEventListener('DOMContentLoaded', function () {
    let categoriaSelect = document.getElementById('categoria');
    let subcategoriaSelect = document.getElementById('subcategoria');

    let subcategoriasPorCategoria =  {};
    console.log('Subcategorías por Categoría:', JSON.stringify(subcategoriasPorCategoria, null, 2));


    categoriaSelect.addEventListener('change', function () {
        let categoriaId = this.value;
        let subcategorias = subcategoriasPorCategoria[categoriaId] || [
        ];

        // Limpia las subcategorías actuales
        subcategoriaSelect.innerHTML = '<option value="">Selecciona una subcategoría</option>';

        // Añade nuevas subcategorías
        subcategorias.forEach(subcategoria => {
            let option = document.createElement('option');
            option.value = subcategoria.id;
            option.textContent = subcategoria.tipo;
            subcategoriaSelect.appendChild(option);
        });
    });
});
