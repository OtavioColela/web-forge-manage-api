package webforge.row_manage_api.dto.requisicao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.enums.StatusPedido;
import webforge.row_manage_api.model.ItemPedido;


import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequisicaoResponse {
    private List<ItemPedido> itemPedido;
    private LocalDateTime dataRequisicao;
    private StatusPedido statusPedido;
    private UserResponse solicitante;

    public RequisicaoResponse(StatusPedido statusPedido, LocalDateTime dataRequisicao, UserResponse solicitante) {
        this.statusPedido = statusPedido;
        this.dataRequisicao = dataRequisicao;
        this.solicitante = solicitante;
    }

}
