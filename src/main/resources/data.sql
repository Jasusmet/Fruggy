INSERT INTO supermercados (id, nombre_super, url, imagen_path, horario, calle, municipio, pais, codigo_postal)
VALUES
    (1, 'Supermercado 1', 'https://supermercado1.com', '/images/super1.png', '8:00-22:00', 'Calle 1', 'Municipio 1', 'Pais 1', 1000),
    (2, 'Supermercado 2', 'https://supermercado2.com', '/images/super2.png', '9:00-21:00', 'Calle 2', 'Municipio 2', 'Pais 2', 2000);




INSERT INTO tipo_descuento (id, tipo, porcentaje, fecha_inicio, fecha_fin, activo)
VALUES
    (1, 'Descuento por volumen', 10.0, '2023-01-01', '2023-12-31', true),
    (2, 'Descuento estacional', 20.0, '2023-06-01', '2023-08-31', true),
    (3, 'Descuento por lanzamiento', 15.0, '2023-03-01', '2023-05-31', true),
    (4, 'Descuento por liquidación', 50.0, '2023-09-01', '2023-12-31', false),
    (5, 'Descuento especial', 25.0, '2023-11-01', '2023-11-30', true);


INSERT INTO descuentos (descuentos_id, fecha_inicio, fecha_fin, activo,  tipo_descuento_id)
VALUES
    (1, '2023-01-01', '2023-12-31', true,  1),
    (2, '2023-02-01', '2023-11-30', true,  2),
    (3, '2023-03-01', '2023-10-31', false, 3);


INSERT INTO imagenes (id, nombre_archivo, ruta, path_imagen, usuario_id, detalle_id, supermercado_id)
VALUES
    (1, 'imagen1.png', '/images', '/images/imagen1.png', NULL, NULL, 1),
    (2, 'imagen2.png', '/images', '/images/imagen2.png', NULL, NULL, 2),
    (3, 'imagen3.png', '/images', '/images/imagen3.png', NULL, NULL, 1),
    (4, 'imagen4.png', '/images', '/images/imagen4.png', NULL, NULL, 2),
    (5, 'imagen5.png', '/images', '/images/imagen5.png', NULL, NULL, 1),
    (6, 'imagen6.png', '/images', '/images/imagen6.png', NULL, NULL, 2),
    (7, 'imagen7.png', '/images', '/images/imagen7.png', NULL, NULL, 1),
    (8, 'imagen8.png', '/images', '/images/imagen8.png', NULL, NULL, 2),
    (9, 'imagen9.png', '/images', '/images/imagen9.png', NULL, NULL, 1),
    (10, 'imagen10.png', '/images', '/images/imagen10.png', NULL, NULL, 2);


INSERT INTO productos (id, nombre_producto, marca, descripcion, activo, path_imagen, subcategoria_id, categoria_id, imagen_id, supermercado_id)
VALUES
    (1, 'Producto 1', 'Marca 1', 'Descripción del Producto 1', true, '/images/producto1.png', 1, 1, 1, 1),
    (2, 'Producto 2', 'Marca 2', 'Descripción del Producto 2', true, '/images/producto2.png', 2, 1, 2, 1),
    (3, 'Producto 3', 'Marca 3', 'Descripción del Producto 3', false, '/images/producto3.png', 1, 2, 3, 2),
    (4, 'Producto 4', 'Marca 4', 'Descripción del Producto 4', true, '/images/producto4.png', 2, 2, 4, 2),
    (5, 'Producto 5', 'Marca 5', 'Descripción del Producto 5', true, '/images/producto5.png', 1, 1, 5, 1),
    (6, 'Producto 6', 'Marca 6', 'Descripción del Producto 6', false, '/images/producto6.png', 2, 1, 6, 2),
    (7, 'Producto 7', 'Marca 7', 'Descripción del Producto 7', true, '/images/producto7.png', 1, 2, 7, 1),
    (8, 'Producto 8', 'Marca 8', 'Descripción del Producto 8', true, '/images/producto8.png', 2, 2, 8, 1),
    (9, 'Producto 9', 'Marca 9', 'Descripción del Producto 9', false, '/images/producto9.png', 1, 1, 9, 2),
    (10, 'Producto 10', 'Marca 10', 'Descripción del Producto 10', true, '/images/producto10.png', 2, 1, 10, 1);


INSERT INTO precios (id, fecha_inicio, fecha_fin, activo, valor, supermercado_id, producto_id)
VALUES
    (1, '2023-01-01 00:00:00', '2023-12-31 23:59:59', true, 10.99, 1, 1),
    (2, '2023-02-01 00:00:00', '2023-11-30 23:59:59', true, 20.99, 1, 2),
    (3, '2023-03-01 00:00:00', '2023-10-31 23:59:59', false, 30.99, 2, 3),
    (4, '2023-04-01 00:00:00', '2023-09-30 23:59:59', true, 15.99, 2, 4),
    (5, '2023-05-01 00:00:00', '2023-08-31 23:59:59', true, 25.99, 1, 5),
    (6, '2023-06-01 00:00:00', '2023-07-31 23:59:59', false, 35.99, 2, 6),
    (7, '2023-07-01 00:00:00', '2023-12-31 23:59:59', true, 12.99, 1, 7),
    (8, '2023-08-01 00:00:00', '2023-10-31 23:59:59', true, 22.99, 1, 8),
    (9, '2023-09-01 00:00:00', '2023-11-30 23:59:59', false, 32.99, 2, 9),
    (10, '2023-10-01 00:00:00', '2023-12-31 23:59:59', true, 18.99, 1, 10);


INSERT INTO producto_descuento (producto_id, descuento_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 1),
    (5, 2),
    (6, 3),
    (7, 1),
    (8, 2),
    (9, 3),
    (10, 1);
