package cl.duoc.ejemplo.ms.administracion.archivos.service;

import org.springframework.stereotype.Service;

@Service
public class ConsumirMensajeService {

    private String ultimoMensaje;

    public void recibirMensaje(String mensaje) {
        this.ultimoMensaje = mensaje;
    }

    public String obtenerUltimoMensaje() {
        return ultimoMensaje;
    }
}
