package webforge.row_manage_api.mapper;

import webforge.row_manage_api.dto.requisicao.RequisicaoRequest;
import webforge.row_manage_api.dto.requisicao.RequisicaoResponse;
import webforge.row_manage_api.model.RequisicaoEntity;

import java.util.List;

public class RequisicaoMapper {

    public static RequisicaoResponse toResponse(RequisicaoEntity requisicaoEntity){
        return RequisicaoResponse.builder()
                .itemPedido(requisicaoEntity.getItemPedido())
                .dataRequisicao(requisicaoEntity.getDataRequisicao())
                .statusPedido(requisicaoEntity.getStatusPedido())
                .solicitante(UserMapper.toResponse(requisicaoEntity.getSolicitante()))
                .build();
    }

    public static List<RequisicaoResponse> toResponse(List<RequisicaoEntity> requisicoes) {
        return requisicoes.stream()
                .map(RequisicaoMapper::toResponse)
                .toList();
    }

}
