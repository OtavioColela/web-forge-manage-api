package webforge.row_manage_api.dto.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialResponse {
    private Long id;
    private String nome;
    private int quantidade;
}
