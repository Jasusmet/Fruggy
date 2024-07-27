function formatPrice(input) {
    let value = input.value;

    // Validar que el valor es un número válido
    if (!/^(\d{0,4})(,\d{0,2})?$/.test(value.replace(" €", ""))) {
        input.value = ''; // Si no es válido, vaciar el campo
        return;
    }

    // Formatear el precio
    if (value) {
        // Limpiar el valor y formatear
        value = value.replace(",", ".").trim(); // Reemplazar coma por punto
        input.value = parseFloat(value).toFixed(2).replace(".", ",") + " €"; // Asegurarse que el valor tiene 2 decimales y agregar símbolo de euro
    }
}

// Prevenir la modificación del valor mientras se escribe
document.getElementById("precio").addEventListener("input", function (event) {
    let value = this.value;

    // Remover caracteres no válidos
    this.value = value.replace(/[^0-9,]/g, '');

    // Limitar a 4 dígitos antes de la coma
    const parts = this.value.split(',');
    if (parts[0].length > 4) {
        this.value = parts[0].substring(0, 4) + (parts[1] ? ',' + parts[1] : '');
    }

    // Limitar a 2 dígitos después de la coma
    if (parts[1] && parts[1].length > 2) {
        this.value = parts[0] + ',' + parts[1].substring(0, 2);
    }
});

// Formatear el precio al salir del campo
document.getElementById("precio").addEventListener("blur", function() {
    formatPrice(this); // Aplicar el formato cuando el usuario deja el campo
});