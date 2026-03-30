package webforge.row_manage_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webforge.row_manage_api.dto.material.MaterialRequest;
import webforge.row_manage_api.dto.material.MaterialResponse;
import webforge.row_manage_api.enums.Categoria;
import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.exception.ObjectNotFoundException;
import webforge.row_manage_api.mapper.MaterialMapper;
import webforge.row_manage_api.model.MaterialEntity;
import webforge.row_manage_api.repository.MaterialRepository;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private MaterialRepository materialRepository;


    public List<MaterialResponse> listarMateriais(){
        var materiais = materialRepository.findAll();
        if(materiais.isEmpty()){
            throw new ObjectNotFoundException("Não há materiais no estoque");
        }
        return MaterialMapper.toResponses(materiais);
    }
    public List<MaterialEntity> buscarPorCategoria(Categoria categoria){
        return materialRepository.findByCategoria(categoria);
    }


    public MaterialResponse criarMaterial(MaterialRequest materialRequest){
        var material = MaterialMapper.toEntity(materialRequest);
        materialRepository.save(material);
        return MaterialMapper.toResponse(material);
    }

    public MaterialResponse atualizarMaterial(Long id, MaterialRequest materialRequest){
        var material = materialRepository.findById(id);
        if(material.isEmpty()){
            throw new ObjectNotFoundException("Material inexistente");
        }
        material.get().setCategoria(materialRequest.getCategoria());
        material.get().setQuantidade(materialRequest.getQuantidade());
        material.get().setNome(materialRequest.getNome());

        return MaterialMapper.toResponse(materialRepository.save(material.get()));
    }

    public void reduzirMaterial(Long id, int quantidade) {
        var material = materialRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Material não encontrado"));
        if (material.getQuantidade() < quantidade)
            throw new BadRequestException("Quantidade insuficiente em estoque");
        material.setQuantidade(material.getQuantidade() - quantidade);
        materialRepository.save(material);
    }

    public void deletarMaterial(Long id){
         var material = materialRepository.findById(id);
         if(material.isEmpty()){
             throw new ObjectNotFoundException("Material não encontrado");
         }

         materialRepository.deleteById(id);
    }

    public List<MaterialResponse> mostrarMaterialSemEstoque(){
        var materiais = materialRepository.findMateriaisSemEstoque();
        if(materiais.isEmpty()){
            throw new BadRequestException("Não há materiais sem estoque! Tudo OK");
        }
        return MaterialMapper.toResponses(materiais);
    }




}
