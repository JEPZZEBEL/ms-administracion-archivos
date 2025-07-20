package cl.duoc.ejemplo.ms.administracion.archivos.repository;

import cl.duoc.ejemplo.ms.administracion.archivos.entity.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeEntity, Long> {
}
