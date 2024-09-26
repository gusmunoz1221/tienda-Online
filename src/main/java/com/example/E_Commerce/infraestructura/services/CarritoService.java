package com.example.E_Commerce.infraestructura.services;

import org.springframework.stereotype.Service;

@Service
public class CarritoService {
    /*Responsabilidades del Service de Carrito:
Crear un nuevo carrito asociado a un cliente.
Actualizar un carrito existente (por ejemplo, cambiar la dirección de envío).
Eliminar un carrito.
Agregar Productos al Carrito:
Recibir el ID del producto que se quiere agregar al carrito.
Validar que el producto exista (delegar esta validación a un servicio de producto).
Crear un nuevo ProductoCarritoEntity que asocie el producto al carrito.
Ajustar la cantidad del producto en el carrito si ya existe en la lista de productos.
Guardar los cambios en la base de datos.
**/


}