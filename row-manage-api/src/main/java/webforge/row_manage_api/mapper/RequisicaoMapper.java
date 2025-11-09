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


    public static RequisicaoEntity toEntity(RequisicaoRequest requisicaoRequest){
        return RequisicaoEntity.builder()
                .itemPedido(requisicaoRequest.getItemPedido())
                .dataRequisicao(requisicaoRequest.getDataRequisicao())
                .statusPedido(requisicaoRequest.getStatusPedido())
                .solicitante(requisicaoRequest.getSolicitante())
                .build();
    }

    public static RequisicaoRequest toRequest(RequisicaoEntity requisicaoEntity){
        return RequisicaoRequest.builder()
                .dataRequisicao(requisicaoEntity.getDataRequisicao())
                .itemPedido(requisicaoEntity.getItemPedido())
                .statusPedido(requisicaoEntity.getStatusPedido())
                .solicitante(requisicaoEntity.getSolicitante())
                .build();
    }

}
