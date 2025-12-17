package webforge.row_manage_api.dto.ItemPedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequest {
    private Long materialId;
    private int quantidade;
}
