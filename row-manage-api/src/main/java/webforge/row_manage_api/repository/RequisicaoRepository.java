package webforge.row_manage_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webforge.row_manage_api.enums.StatusPedido;
import webforge.row_manage_api.model.RequisicaoEntity;

import java.util.List;
@Repository
public interface RequisicaoRepository extends JpaRepository<RequisicaoEntity, Long> {
    List<RequisicaoEntity> findBySolicitanteIdOrderByDataRequisicaoDesc(Long id);
    List<RequisicaoEntity> findByStatusPedidoOrderByDataRequisicaoDesc(StatusPedido statusPedido);
    @Query("SELECT r FROM RequisicaoEntity r " +
            "WHERE r.statusPedido IN :statusPedido " +
            "ORDER BY r.dataRequisicao DESC")
    List<RequisicaoEntity> buscarPorStatus(@Param("statusPedido") List<StatusPedido> statusPedido);
}
