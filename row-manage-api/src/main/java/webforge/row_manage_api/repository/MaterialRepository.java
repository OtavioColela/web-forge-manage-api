package webforge.row_manage_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webforge.row_manage_api.model.MaterialEntity;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
}
