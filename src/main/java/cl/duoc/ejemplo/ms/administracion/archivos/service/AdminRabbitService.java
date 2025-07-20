package cl.duoc.ejemplo.ms.administracion.archivos.service;

import cl.duoc.ejemplo.ms.administracion.archivos.dto.BindingDTO;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Service;

@Service
public class AdminRabbitService {

    private final AmqpAdmin amqpAdmin;

    public AdminRabbitService(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    public void crearCola(String nombreCola) {
        amqpAdmin.declareQueue(new Queue(nombreCola));
    }

    public void crearExchange(String nombreExchange) {
        amqpAdmin.declareExchange(new DirectExchange(nombreExchange));
    }

    public void crearBinding(BindingDTO dto) {
        Binding binding = BindingBuilder
            .bind(new Queue(dto.getNombreCola()))
            .to(new DirectExchange(dto.getNombreExchange()))
            .with(dto.getRoutingKey());

        amqpAdmin.declareBinding(binding);
    }

    public void eliminarCola(String nombreCola) {
        amqpAdmin.deleteQueue(nombreCola);
    }

    public void eliminarExchange(String nombreExchange) {
        amqpAdmin.deleteExchange(nombreExchange);
    }
}
