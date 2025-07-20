package cl.duoc.ejemplo.ms.administracion.archivos.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducirMensajeService {

    private final RabbitTemplate rabbitTemplate;

    public ProducirMensajeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensaje(String mensaje) {
        rabbitTemplate.convertAndSend("myExchange", "", mensaje);
    }

    public void enviarObjeto(Object objeto) {
        rabbitTemplate.convertAndSend("myExchange", "", objeto);
    }
}
