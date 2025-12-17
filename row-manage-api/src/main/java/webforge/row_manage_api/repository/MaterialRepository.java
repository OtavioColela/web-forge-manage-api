package webforge.row_manage_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webforge.row_manage_api.dto.material.MaterialResponse;
import webforge.row_manage_api.enums.Categoria;
import webforge.row_manage_api.model.MaterialEntity;

import java.util.List;
@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    List<MaterialEntity> findByCategoria(Categoria categoria);
}
