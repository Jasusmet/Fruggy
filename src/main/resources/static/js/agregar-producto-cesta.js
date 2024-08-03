    document.addEventListener('DOMContentLoaded', function () {
    var agregarCestaModal = document.getElementById('agregarCestaModal');
    agregarCestaModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var productoId = button.getAttribute('data-producto-id');
    var modalBodyInput = agregarCestaModal.querySelector('#productoId');
    modalBodyInput.value = productoId;
});
});
