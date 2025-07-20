package cl.duoc.ejemplo.ms.administracion.archivos.controller;

import cl.duoc.ejemplo.ms.administracion.archivos.dto.BindingDTO;
import cl.duoc.ejemplo.ms.administracion.archivos.service.AdminRabbitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbit-admin")
public class RabbitMQAdminController {

    private final AdminRabbitService service;

    public RabbitMQAdminController(AdminRabbitService service) {
        this.service = service;
    }

    @PostMapping("/colas/{nombrecola}")
    public String crearCola(@PathVariable String nombrecola) {
        service.crearCola(nombrecola);
        return "Cola creada: " + nombrecola;
    }

    @PostMapping("/exchanges/{nombreexchange}")
    public String crearExchange(@PathVariable String nombreexchange) {
        service.crearExchange(nombreexchange);
        return "Exchange creado: " + nombreexchange;
    }

    @PostMapping("/bindings")
    public String crearBinding(@RequestBody BindingDTO request) {
        service.crearBinding(request);
        return "Binding creado en cola: " + request.getNombreCola() + " y exchange: " + request.getNombreExchange();
    }

    @DeleteMapping("/colas/{nombrecola}")
    public String eliminarCola(@PathVariable String nombrecola) {
        service.eliminarCola(nombrecola);
        return "Cola eliminada: " + nombrecola;
    }

    @DeleteMapping("/exchanges/{nombreexchange}")
    public String eliminarExchange(@PathVariable String nombreexchange) {
        service.eliminarExchange(nombreexchange);
        return "Exchange eliminado: " + nombreexchange;
    }
}
