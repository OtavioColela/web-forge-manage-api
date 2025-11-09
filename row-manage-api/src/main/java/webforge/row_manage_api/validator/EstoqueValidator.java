package webforge.row_manage_api.validator;

import org.springframework.stereotype.Component;
import webforge.row_manage_api.model.MaterialEntity;

@Component
public class EstoqueValidator {


    public static void validarEstoque(MaterialEntity material, int quantidade) {
        if (material.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para o material: " + material.getNome());
        }
    }
}
