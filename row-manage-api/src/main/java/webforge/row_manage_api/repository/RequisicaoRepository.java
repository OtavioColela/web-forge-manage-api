package webforge.row_manage_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webforge.row_manage_api.model.RequisicaoEntity;

import java.util.List;

public interface RequisicaoRepository extends JpaRepository<RequisicaoEntity, Long> {
    List<RequisicaoEntity> findBySolicitanteIdOrderByDataRequisicaoDesc(Long id);
}
