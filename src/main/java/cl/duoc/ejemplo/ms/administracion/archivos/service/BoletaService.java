package cl.duoc.ejemplo.ms.administracion.archivos.service;

import cl.duoc.ejemplo.ms.administracion.archivos.dto.BoletaDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoletaService {

    public String crearBoleta(BoletaDTO boleta) {
        return "Boleta creada: " + boleta.getNombre();
    }

    public BoletaDTO actualizarBoleta(Long id, BoletaDTO boleta) {
        boleta.setId(id);
        return boleta;
    }

    public void eliminarBoleta(Long id) {
        System.out.println("Boleta eliminada: " + id);
    }

    public List<BoletaDTO> listarBoletas() {
        return new ArrayList<>();
    }
}
