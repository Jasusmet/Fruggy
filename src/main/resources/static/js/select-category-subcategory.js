document.addEventListener('DOMContentLoaded', function () {
    let categoriaSelect = document.getElementById('categoria');
    let subcategoriaSelect = document.getElementById('subcategoria');

    categoriaSelect.addEventListener('change', function () {
        let categoriaId = this.value;

        // Define la URL de la API
        const apiUrl = '/api/subcategorias?categoriaId=' + categoriaId;

        // Realiza una solicitud GET
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Verifica la estructura de los datos
                console.log(data);

                // Limpia las subcategorías actuales
                subcategoriaSelect.innerHTML = '<option value="">Selecciona una subcategoría</option>';

                // Añade nuevas subcategorías
                data.forEach(subcategoria => {
                    let option = document.createElement('option');
                    option.value = subcategoria.id;
                    option.textContent = subcategoria.tipo_es; // Usa el campo en español o el que prefieras
                    subcategoriaSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});
