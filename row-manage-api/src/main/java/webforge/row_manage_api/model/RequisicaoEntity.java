package webforge.row_manage_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webforge.row_manage_api.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequisicaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "requisicao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemPedido> itemPedido;
    private LocalDateTime dataRequisicao;
    private StatusPedido statusPedido;
    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private UserEntity solicitante;

    public RequisicaoEntity(StatusPedido statusPedido, LocalDateTime dataRequisicao, List<ItemPedido> itemPedido ) {
        this.statusPedido = statusPedido;
        this.dataRequisicao = dataRequisicao;
        this.itemPedido = itemPedido;
    }


}
