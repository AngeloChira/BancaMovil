package com.gastulochiracoronadoluciano.bancamovil.controller;

import com.gastulochiracoronadoluciano.bancamovil.domain.singleton.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

    @GetMapping
    public ResponseEntity<List<String>> obtenerLogs() {
        List<String> historial = Logger.getInstance().getHistorial();
        return ResponseEntity.ok(historial);
    }
}
