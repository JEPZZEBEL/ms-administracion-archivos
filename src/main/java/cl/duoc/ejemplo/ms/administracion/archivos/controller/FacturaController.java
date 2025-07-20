package cl.duoc.ejemplo.ms.administracion.archivos.controller;

import cl.duoc.ejemplo.ms.administracion.archivos.dto.FacturaDto;
import cl.duoc.ejemplo.ms.administracion.archivos.model.Factura;
import cl.duoc.ejemplo.ms.administracion.archivos.service.FacturaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    private static final Logger logger = LoggerFactory.getLogger(FacturaController.class);

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDto facturaDto) {
        logger.info("Creando factura para clienteId: {}", facturaDto.getClienteId());
        Factura factura = new Factura();
        factura.setClienteId(facturaDto.getClienteId());
        factura.setFechaEmision(facturaDto.getFechaEmision());
        factura.setDescripcion(facturaDto.getDescripcion());
        factura.setMonto(facturaDto.getMonto());
        factura.setNombreArchivo(facturaDto.getNombreArchivo());

        Factura creada = facturaService.crearFactura(factura);
        logger.info("Factura creada con ID: {}", creada.getId());
        return ResponseEntity.ok(creada);
    }

    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasLasFacturas() {
        logger.info("Obteniendo todas las facturas");
        return ResponseEntity.ok(facturaService.obtenerTodasLasFacturas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Long id) {
        logger.info("Obteniendo factura con ID: {}", id);
        Optional<Factura> factura = facturaService.obtenerFactura(id);
        if (factura.isPresent()) {
            logger.info("Factura encontrada: {}", factura.get());
        } else {
            logger.warn("Factura con ID {} no encontrada", id);
        }
        return factura.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/historial/{clienteId}")
    public ResponseEntity<List<Factura>> obtenerHistorialPorCliente(@PathVariable String clienteId) {
        logger.info("Obteniendo historial de facturas para clienteId: {}", clienteId);
        return ResponseEntity.ok(facturaService.obtenerHistorialCliente(clienteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable Long id, @RequestBody FacturaDto facturaDto) {
        logger.info("Actualizando factura con ID: {}", id);
        Optional<Factura> existente = facturaService.obtenerFactura(id);
        if (existente.isEmpty()) {
            logger.warn("Factura con ID {} no encontrada para actualizar", id);
            return ResponseEntity.notFound().build();
        }

        Factura factura = existente.get();
        factura.setClienteId(facturaDto.getClienteId());
        factura.setFechaEmision(facturaDto.getFechaEmision());
        factura.setDescripcion(facturaDto.getDescripcion());
        factura.setMonto(facturaDto.getMonto());
        factura.setNombreArchivo(facturaDto.getNombreArchivo());

        Factura actualizada = facturaService.actualizarFactura(factura);
        logger.info("Factura actualizada con ID: {}", actualizada.getId());
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        logger.info("Eliminando factura con ID: {}", id);
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> subirFactura(@PathVariable Long id,
                                               @RequestParam("archivo") MultipartFile archivo) throws IOException {
        logger.info("Subiendo archivo para factura con ID: {}", id);
        facturaService.subirYGuardarFactura(id, archivo);
        logger.info("Archivo subido correctamente para factura ID: {}", id);
        return ResponseEntity.ok("Archivo subido correctamente");
    }
}
