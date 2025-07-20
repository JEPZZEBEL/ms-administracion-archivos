package cl.duoc.ejemplo.ms.administracion.archivos.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitListenerControlService {

    private final Map<String, Boolean> estados = new HashMap<>();

    public void pausarListener(String id) {
        estados.put(id, false);
    }

    public void reanudarListener(String id) {
        estados.put(id, true);
    }

    public boolean isListenerRunning(String id) {
        return estados.getOrDefault(id, false);
    }
}
