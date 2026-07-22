package com.gastulochiracoronadoluciano.bancamovil.controller;

import com.gastulochiracoronadoluciano.bancamovil.domain.composite.Agencia;
import com.gastulochiracoronadoluciano.bancamovil.domain.composite.Banco;
import com.gastulochiracoronadoluciano.bancamovil.domain.composite.Cajero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador que expone una estructura JSON representando el composite Banco -> Agencias -> Cajeros
 * Útil para que el frontend muestre el árbol y permita "enviar a banco" o a agencias específicas.
 */
@RestController
@RequestMapping("/api/v1/banco")
public class BancoController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerEstructuraBanco() {

        // Construcción en memoria del composite (puedes reemplazar por persistencia luego)
        Banco banco = new Banco("Banco Movil");

        Agencia chiclayo = new Agencia("Chiclayo");
        chiclayo.agregar(new Cajero("Cajero 1"));
        chiclayo.agregar(new Cajero("Cajero 2"));

        Agencia lambayeque = new Agencia("Lambayeque");
        lambayeque.agregar(new Cajero("Cajero 3"));
        lambayeque.agregar(new Cajero("Cajero 4"));

        banco.agregar(chiclayo);
        banco.agregar(lambayeque);

        // Representación simple para JSON (no serializamos directamente las clases del composite)
        Map<String, Object> respuesta = Map.of(
                "nombre", "Banco Movil",
                "agencias", new Object[] {
                        Map.of("nombre", "Chiclayo", "cajeros", new String[] {"Cajero 1", "Cajero 2"}),
                        Map.of("nombre", "Lambayeque", "cajeros", new String[] {"Cajero 3", "Cajero 4"})
                }
        );

        return ResponseEntity.ok(respuesta);
    }
}
