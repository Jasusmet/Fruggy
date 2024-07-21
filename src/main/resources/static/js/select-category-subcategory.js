function updateSubcategorias() {
    const categoriaId = parseInt(categoriaSelect.value, 10);
    console.log(`categoriaId: ${categoriaId}`); // Verifica el valor del ID

    // Limpiar el selector de subcategorías
    subcategoriaSelect.innerHTML = '<option value="">Selecciona una subcategoría</option>';

    if (categoriaId) {
        fetch(`/api/subcategorias?categoriaId=${categoriaId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Subcategorías:', data);
                data.forEach(subcategoria => {
                    const option = document.createElement('option');
                    option.value = subcategoria.id;
                    option.textContent = subcategoria.tipo;
                    subcategoriaSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching subcategorias:', error));
    }
}
