// Script para manejar el botón de agregar a cesta
document.addEventListener('DOMContentLoaded', function () {
    // Cuando se abra el modal, obtén el ID del producto
    var agregarCestaModal = document.getElementById('agregarCestaModal');
    agregarCestaModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget; // Botón que abrió el modal
        var productoId = button.getAttribute('data-producto-id'); // Extraer el ID del producto

        // Actualizar el campo oculto con el ID del producto
        var modalBodyInput = agregarCestaModal.querySelector('#productoId');
        modalBodyInput.value = productoId;
    });
});