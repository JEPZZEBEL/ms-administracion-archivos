package cl.duoc.ejemplo.ms.administracion.archivos.repository;

import cl.duoc.ejemplo.ms.administracion.archivos.entity.BoletaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<BoletaEntity, Long> {
}
