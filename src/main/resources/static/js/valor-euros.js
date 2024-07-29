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

document.addEventListener("DOMContentLoaded", function() {
    // Prevenir la modificación del valor mientras se escribe
    const inputPrecio = document.getElementById("precio");
    inputPrecio.addEventListener("input", function () {
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
    inputPrecio.addEventListener("blur", function() {
        formatPrice(this); // Aplicar el formato cuando el usuario deja el campo
        // Validar que el precio no sea mayor a 1000
        const priceValue = parseFloat(this.value.replace(" €", "").replace(",", "."));
        if (priceValue > 1000) {
            alert("El precio no puede ser mayor a 1000 €.");
            this.value = ""; // Limpiar el campo si es mayor
        }
    });
});