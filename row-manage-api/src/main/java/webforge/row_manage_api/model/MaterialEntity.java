package webforge.row_manage_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webforge.row_manage_api.enums.Categoria;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private int quantidade;
}
