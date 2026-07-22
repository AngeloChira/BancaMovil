package com.gastulochiracoronadoluciano.bancamovil.controller;



import com.gastulochiracoronadoluciano.bancamovil.dto.AlertaDTO;
import com.gastulochiracoronadoluciano.bancamovil.service.AlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alertas")
public class AlertaController {

    private final AlertService service;

    public AlertaController(AlertService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlertaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<AlertaDTO> crear(@RequestBody AlertaDTO dto) {
        AlertaDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<Map<String,String>> enviar(@PathVariable Integer id, @RequestBody Map<String,Object> payload) {
        String res = service.enviar(id, payload);
        return ResponseEntity.ok(Map.of("result", res));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

