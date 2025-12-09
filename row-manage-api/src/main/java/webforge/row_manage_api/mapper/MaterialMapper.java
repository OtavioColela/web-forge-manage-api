package webforge.row_manage_api.mapper;


import webforge.row_manage_api.dto.material.MaterialRequest;
import webforge.row_manage_api.dto.material.MaterialResponse;
import webforge.row_manage_api.model.MaterialEntity;

import java.util.List;

public class MaterialMapper {
    public static List<MaterialResponse> toResponses(List<MaterialEntity> materiais){
        return materiais.stream()
                .map(m -> new MaterialResponse(m.getId(), m.getNome(),m.getCategoria(), m.getQuantidade()))
                .toList();
    }
    public static MaterialEntity toEntity(MaterialRequest materialRequest){
        return MaterialEntity.builder()
                .nome(materialRequest.getNome())
                .categoria(materialRequest.getCategoria())
                .quantidade(materialRequest.getQuantidade()).build();
    }
    public static MaterialResponse toResponse(MaterialEntity materialEntity){
        return MaterialResponse.builder()
                .nome(materialEntity.getNome())
                .quantidade(materialEntity.getQuantidade())
                .categoria(materialEntity.getCategoria())
                .id(materialEntity.getId()).build();

    }
}
