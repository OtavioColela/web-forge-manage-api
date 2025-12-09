package webforge.row_manage_api.dto.requisicao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webforge.row_manage_api.dto.ItemPedido.ItemPedidoRequest;
import webforge.row_manage_api.enums.StatusPedido;
import webforge.row_manage_api.model.ItemPedido;
import webforge.row_manage_api.model.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequisicaoRequest {
    private List<ItemPedidoRequest> itemPedido;
    private Long solicitanteId;
}
