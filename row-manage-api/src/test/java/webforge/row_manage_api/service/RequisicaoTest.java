package webforge.row_manage_api.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import webforge.row_manage_api.dto.requisicao.RequisicaoRequest;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.dto.user.UserResponse;
import webforge.row_manage_api.exception.ObjectNotFoundException;
import webforge.row_manage_api.model.ItemPedido;
import webforge.row_manage_api.model.MaterialEntity;
import webforge.row_manage_api.model.RequisicaoEntity;
import webforge.row_manage_api.model.UserEntity;
import webforge.row_manage_api.repository.MaterialRepository;
import webforge.row_manage_api.repository.RequisicaoRepository;
import webforge.row_manage_api.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static webforge.row_manage_api.enums.StatusPedido.APROVADO;
import static webforge.row_manage_api.enums.StatusPedido.PENDENTE;

@RunWith(MockitoJUnitRunner.class)
public class RequisicaoTest {

    @Mock
    RequisicaoRepository requisicaoRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    MaterialRepository materialRepository;
    @InjectMocks
    RequisicaoService requisicaoService;

    MaterialEntity materialEntity;
    UserEntity userEntity;
    UserRequest userRequest;
    RequisicaoRequest requisicaoRequest;
    RequisicaoEntity requisicaoEntity;
    UserResponse userResponse;

    @Before
    public void setup() {
        materialEntity = new MaterialEntity(1L, "Caneta", 10);
        userEntity = new UserEntity(1L, "Otavio", "otavioborges123@yahoo.com", "COMAP", "2", "Walley123_", "ADMIN");
        userRequest = new UserRequest(1L, "Otavio","otavioborges123@yahoo.com",  "COMAP", "2", "Walley123_");
        userResponse = new UserResponse(1L ,"Otavio", "COMAP", "otavioborges123@yahoo.com");

        var itemPedidoRequest = new ItemPedido(1L, requisicaoEntity, materialEntity, 2);

        requisicaoRequest = new RequisicaoRequest(
                List.of(itemPedidoRequest),
                LocalDateTime.now(),
                PENDENTE,
                userEntity
        );

        requisicaoEntity = new RequisicaoEntity(
                1L,
                null,
                LocalDateTime.now(),
                PENDENTE,
                userEntity
        );
    }

    @Test
    public void mustCreateRequisition() {
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(userEntity));
        given(materialRepository.findById(any(Long.class))).willReturn(Optional.of(materialEntity));
        given(requisicaoRepository.save(any(RequisicaoEntity.class))).willReturn(requisicaoEntity);

        var result = requisicaoService.criarRequisicao(requisicaoRequest);

        assertEquals(PENDENTE, result.getStatusPedido());
        assertEquals("Otavio", result.getSolicitante().getName());

        verify(userRepository, times(1)).findById(any(Long.class));
        verify(materialRepository, times(1)).findById(any(Long.class));
        verify(requisicaoRepository, times(1)).save(any(RequisicaoEntity.class));
    }

    @Test
    public void mustGetAllRequisitions(){
        given(requisicaoRepository.findAll()).willReturn(List.of(requisicaoEntity, requisicaoEntity));
        var result = requisicaoService.listarRequisicoes();

        assertEquals(PENDENTE, result.get(0).getStatusPedido());
        assertEquals(userResponse, result.get(0).getSolicitante());
        assertEquals(PENDENTE, result.get(1).getStatusPedido());
        assertEquals(userResponse, result.get(1).getSolicitante());
        verify(requisicaoRepository, times(1)).findAll();
    }
    @Test
    public void mustNotGetAllRequisitions(){
        given(requisicaoRepository.findAll()).willReturn(List.of());
        assertThrows(ObjectNotFoundException.class, () ->{
            requisicaoService.listarRequisicoes();
        });
    }
    @Test
    public void mustAtualizeRequisitionStatus(){
        given(requisicaoRepository.findById(requisicaoEntity.getId())).willReturn(Optional.ofNullable(requisicaoEntity));
        var statusPedido = APROVADO;
        requisicaoService.definirRequisicao(requisicaoEntity.getId(), statusPedido);
        assertEquals(APROVADO, requisicaoEntity.getStatusPedido());
        verify(requisicaoRepository, times(1)).save(requisicaoEntity);
    }

    @Test
    public void mustFindAllRequisitionsByUser(){
        given(requisicaoRepository.findBySolicitanteIdOrderByDataRequisicaoDesc(userEntity.getId())).willReturn(List.of(requisicaoEntity));
        var result = requisicaoService.getAllRequisitionsByUser(userEntity.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(requisicaoRepository, times(1)).findBySolicitanteIdOrderByDataRequisicaoDesc(userEntity.getId());

    }
    @Test
    public void mustNotFindAllRequisitionsByUser(){
        given(requisicaoRepository.findBySolicitanteIdOrderByDataRequisicaoDesc(userEntity.getId())).willReturn(List.of());
        assertThrows(ObjectNotFoundException.class, () ->{
            requisicaoService.getAllRequisitionsByUser(userEntity.getId());
        });

    }
}
