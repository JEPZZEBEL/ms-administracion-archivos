package cl.duoc.ejemplo.ms.administracion.archivos.controller;

import cl.duoc.ejemplo.ms.administracion.archivos.dto.ProductoDTO;
import cl.duoc.ejemplo.ms.administracion.archivos.dto.UsuarioDTO;
import cl.duoc.ejemplo.ms.administracion.archivos.service.ConsumirMensajeService;
import cl.duoc.ejemplo.ms.administracion.archivos.service.ProducirMensajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MensajeController {

    private final ProducirMensajeService producirMensajeService;
    private final ConsumirMensajeService consumirMensajeService;

    public MensajeController(ProducirMensajeService producirMensajeService, ConsumirMensajeService consumirMensajeService) {
        this.producirMensajeService = producirMensajeService;
        this.consumirMensajeService = consumirMensajeService;
    }

    @PostMapping("/mensajes")
    public ResponseEntity<String> enviar(@RequestBody String mensaje) {
        producirMensajeService.enviarMensaje(mensaje);
        return ResponseEntity.ok("Mensaje enviado: " + mensaje);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<String> enviarObjetoUsuario(@RequestBody UsuarioDTO usuario) {
        producirMensajeService.enviarObjeto(usuario);
        return ResponseEntity.ok("Mensaje enviado: " + usuario.toString());
    }

    @PostMapping("/productos")
    public ResponseEntity<String> enviarObjetoProducto(@RequestBody ProductoDTO producto) {
        producirMensajeService.enviarObjeto(producto);
        return ResponseEntity.ok("Mensaje enviado: " + producto.toString());
    }

    @GetMapping("/mensaje/ultimo")
    public ResponseEntity<String> obtenerUltimoMensaje() {
        String message = consumirMensajeService.obtenerUltimoMensaje();
        if (message == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
