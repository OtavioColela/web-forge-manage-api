package webforge.row_manage_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webforge.row_manage_api.dto.ItemPedido.ItemPedidoRequest;
import webforge.row_manage_api.dto.requisicao.RequisicaoRequest;
import webforge.row_manage_api.dto.requisicao.RequisicaoResponse;
import webforge.row_manage_api.enums.StatusPedido;
import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.exception.ObjectNotFoundException;
import webforge.row_manage_api.mapper.RequisicaoMapper;
import webforge.row_manage_api.model.ItemPedido;
import webforge.row_manage_api.model.MaterialEntity;
import webforge.row_manage_api.model.RequisicaoEntity;
import webforge.row_manage_api.repository.MaterialRepository;
import webforge.row_manage_api.repository.RequisicaoRepository;
import webforge.row_manage_api.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static webforge.row_manage_api.validator.EstoqueValidator.validarEstoque;

@Service
public class RequisicaoService {
    @Autowired
    private RequisicaoRepository requisicaoRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EstoqueService estoqueService;
//    @Autowired
//    private NotificationClient notificationClient;

    public RequisicaoResponse criarRequisicao(RequisicaoRequest requisicaoRequest) {

        var solicitante = userRepository.findById(requisicaoRequest.getSolicitanteId())
                .orElseThrow(() -> new RuntimeException("Solicitante não encontrado"));

        var requisicao = RequisicaoEntity.builder()
                        .statusPedido(StatusPedido.PENDENTE)
                                .dataRequisicao(LocalDateTime.now())
                                        .solicitante(solicitante)
                                                .build();

        List<ItemPedido> itens = requisicaoRequest.getItemPedido().stream().map(itemRequest -> {
            var material = materialRepository.findById(itemRequest.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material não encontrado"));

            validarEstoque(material, itemRequest.getQuantidade());

            var itemPedido = new ItemPedido();
            itemPedido.setMaterialEntity(material);
            itemPedido.setQuantidade(itemRequest.getQuantidade());
            itemPedido.setRequisicao(requisicao);
            return itemPedido;
        }).toList();

        requisicao.setItemPedido(itens);
        requisicaoRepository.save(requisicao);
//        var message = "Uma nova requisição foi criada pela escola" + solicitante.getSchool();
//        notificationClient.sendNotification(message, solicitante.getEmail(), "estoque@smesb.com.br");
        return RequisicaoMapper.toResponse(requisicao);
    }

    public List<RequisicaoResponse> listarRequisicoes(){
        var requisicoes = requisicaoRepository.findAll();
        if(requisicoes.isEmpty()){
            throw new ObjectNotFoundException("Não há requisições abertas");
        }
        return requisicoes.stream().map(RequisicaoMapper::toResponse).toList();
    }

    public void definirRequisicao(Long id, StatusPedido statusPedido){
        var requisicao = requisicaoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Requisição não encontrada"));
        requisicao.setStatusPedido(statusPedido);
//        var message = "Sua requisição foi definida como" + statusPedido;
//        notificationClient.sendNotification(message, "estoque@smesb.com.br", requisicao.getSolicitante().getEmail());
        if(statusPedido.equals(StatusPedido.APROVADO)){
            for (ItemPedido item : requisicao.getItemPedido()) {
                estoqueService.reduzirMaterial(item.getMaterialEntity().getId(), item.getQuantidade());
            }
        }
        requisicaoRepository.save(requisicao);
    }

    public List<RequisicaoResponse> getAllRequisitionsByUser(Long id){
        var requisicoes = requisicaoRepository.findBySolicitanteIdOrderByDataRequisicaoDesc(id);
        if(requisicoes.isEmpty()){
            throw new ObjectNotFoundException("Este usuário nunca fez uma requisição");
        }
        return RequisicaoMapper.toResponse(requisicoes);
    }

    public List<RequisicaoResponse> getAllRequisitionsByStatus(StatusPedido statusPedido){
        return RequisicaoMapper.toResponse(requisicaoRepository.findByStatusPedidoOrderByDataRequisicaoDesc(statusPedido));
    }
    public RequisicaoResponse mostrarPorId(Long id){
        var req = requisicaoRepository.findById(id);
        if(req.isEmpty()){
            throw new BadRequestException("Requisição não encontrada");
        }
        return RequisicaoMapper.toResponse(req.get());
    }

}
