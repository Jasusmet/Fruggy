document.addEventListener('DOMContentLoaded', function () {
    let categoriaSelect = document.getElementById('categoria');
    let subcategoriaSelect = document.getElementById('subcategoria');
    //let subcategoriasPorCategoria = {};
    //console.log('Subcategorías por Categoría:', JSON.stringify(subcategoriasPorCategoria, null, 2));
    categoriaSelect.addEventListener('change', function () {

        let categoriaId = this.value;


        // Define the API URL
        const apiUrl = '/api/subcategorias?categoriaId=' + categoriaId;
        // Make a GET request
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                let subcategorias = data;

                // Limpia las subcategorías actuales
                subcategoriaSelect.innerHTML = '<option value="">Selecciona una subcategoría</option>';

                // Añade nuevas subcategorías
                subcategorias.forEach(subcategoria => {
                    let option = document.createElement('option');
                    option.value = subcategoria.id;
                    option.textContent = subcategoria.tipo;
                    subcategoriaSelect.appendChild(option);
                });

            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});




