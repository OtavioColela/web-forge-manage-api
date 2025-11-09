package webforge.row_manage_api;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import webforge.row_manage_api.dto.material.MaterialRequest;
import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.exception.ObjectNotFoundException;
import webforge.row_manage_api.model.MaterialEntity;
import webforge.row_manage_api.repository.MaterialRepository;
import webforge.row_manage_api.service.EstoqueService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EstoqueTest {
    @Mock
    MaterialRepository materialRepository;
    @InjectMocks
    EstoqueService estoqueService;

    MaterialEntity materialEntity;
    MaterialEntity materialEntity1;
    MaterialRequest materialRequest;

    @Before
    public void setup(){
        materialEntity = new MaterialEntity(1L,"Caneta", 2);
        materialEntity1 = new MaterialEntity(2L, "Lapis", 200);
        materialRequest = new MaterialRequest("Canetinha", 3);
    }

    @Test
    public void mustGetAllItens(){
        var materiais = List.of(materialEntity1, materialEntity);
        given(materialRepository.findAll()).willReturn(materiais);
        var result = estoqueService.listarMateriais();

        assertEquals(2L, result.get(0).getId().longValue());
        assertEquals(1L, result.get(1).getId().longValue());
        assertEquals("Caneta", result.get(1).getNome());
        assertEquals("Lapis", result.get(0).getNome());
        assertEquals(2, result.get(1).getQuantidade());
        assertEquals(200, result.get(0).getQuantidade());

    }

    @Test
    public void mustCreateMaterial(){
        var material = new MaterialEntity(3L, materialRequest.getNome(), materialRequest.getQuantidade());
        given(materialRepository.save(any(MaterialEntity.class))).willReturn(material);
        var result = estoqueService.criarMaterial(materialRequest);

        assertEquals("Canetinha", result.getNome());
        assertEquals(3, result.getQuantidade());
        verify(materialRepository, times(1)).save(any(MaterialEntity.class));
    }

    @Test
    public void mustFailGettingItens(){
        given(materialRepository.findAll()).willReturn(List.of());
        assertThrows(ObjectNotFoundException.class, () ->{
                    estoqueService.listarMateriais();
                }
        );
    }
    @Test
    public void mustAddMaterial(){
        given(materialRepository.findById(1L)).willReturn(Optional.ofNullable(materialEntity));
        given(materialRepository.save(any(MaterialEntity.class))).willReturn(materialEntity);
        estoqueService.adicionarMaterial(1L, 200);

        assertEquals(202, materialEntity.getQuantidade());
        assertEquals(1L, materialEntity.getId().longValue());
        verify(materialRepository, times(1)).save(materialEntity);
    }
    @Test
    public void mustReduceMaterial(){
        given(materialRepository.findById(1L)).willReturn(Optional.ofNullable(materialEntity));
        given(materialRepository.save(any(MaterialEntity.class))).willReturn(materialEntity);
        estoqueService.reduzirMaterial(1L, 1);

        assertEquals(1, materialEntity.getQuantidade());
        assertEquals(1L, materialEntity.getId().longValue());
        verify(materialRepository, times(1)).save(materialEntity);
    }
    @Test
    public void mustNotReduceMaterial(){
        given(materialRepository.findById(1L)).willReturn(Optional.ofNullable(materialEntity));

        assertThrows(BadRequestException.class, () ->{
            estoqueService.reduzirMaterial(1L, 200);
        });
    }


}
