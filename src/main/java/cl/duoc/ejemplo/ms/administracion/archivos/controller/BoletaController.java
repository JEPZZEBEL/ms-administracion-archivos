package cl.duoc.ejemplo.ms.administracion.archivos.controller;

import cl.duoc.ejemplo.ms.administracion.archivos.dto.BoletaDTO;
import cl.duoc.ejemplo.ms.administracion.archivos.service.BoletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boletas")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody BoletaDTO boleta) {
        return ResponseEntity.ok(boletaService.crearBoleta(boleta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoletaDTO> actualizar(@PathVariable Long id, @RequestBody BoletaDTO boleta) {
        BoletaDTO actualizada = boletaService.actualizarBoleta(id, boleta);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boletaService.eliminarBoleta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BoletaDTO>> listar() {
        return ResponseEntity.ok(boletaService.listarBoletas());
    }
}
