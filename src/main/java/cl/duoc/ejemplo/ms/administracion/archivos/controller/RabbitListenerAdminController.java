package cl.duoc.ejemplo.ms.administracion.archivos.controller;

import cl.duoc.ejemplo.ms.administracion.archivos.service.RabbitListenerControlService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbit-listener")
public class RabbitListenerAdminController {

    private final RabbitListenerControlService service;

    public RabbitListenerAdminController(RabbitListenerControlService service) {
        this.service = service;
    }

    @PostMapping("/pausar/{id}")
    public String pausar(@PathVariable String id) {
        service.pausarListener(id);
        return "Listener pausado: " + id;
    }

    @PostMapping("/reanudar/{id}")
    public String reanudar(@PathVariable String id) {
        service.reanudarListener(id);
        return "Listener reanudado: " + id;
    }

    @GetMapping("/status/{id}")
    public String status(@PathVariable String id) {
        return "Listener " + id + " está " + (service.isListenerRunning(id) ? "activo" : "pausado");
    }
}
