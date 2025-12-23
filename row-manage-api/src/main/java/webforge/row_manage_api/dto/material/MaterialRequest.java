package webforge.row_manage_api.dto.material;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webforge.row_manage_api.enums.Categoria;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequest {
    private String nome;
    private Categoria categoria;
    private int quantidade;
}
