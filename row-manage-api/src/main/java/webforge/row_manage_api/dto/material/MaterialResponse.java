package webforge.row_manage_api.dto.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webforge.row_manage_api.enums.Categoria;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialResponse {
    private Long id;
    private String nome;
    private Categoria categoria;
    private int quantidade;
}
