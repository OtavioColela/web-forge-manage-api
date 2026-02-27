package webforge.row_manage_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webforge.row_manage_api.dto.material.MaterialRequest;
import webforge.row_manage_api.dto.material.MaterialResponse;
import webforge.row_manage_api.enums.Categoria;
import webforge.row_manage_api.model.MaterialEntity;
import webforge.row_manage_api.service.EstoqueService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<MaterialResponse>> mostrarMateriais(){
        return ResponseEntity.ok(estoqueService.listarMateriais());
    }
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<MaterialEntity>> mostrarMateriaisPorCategoria(@PathVariable Categoria categoria){
        return ResponseEntity.ok(estoqueService.buscarPorCategoria(categoria));
    }

    @PostMapping("/criar")
    public ResponseEntity<MaterialResponse> criarMaterial(MaterialRequest materialRequest){
        return ResponseEntity.status(CREATED).body(estoqueService.criarMaterial(materialRequest));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MaterialResponse> editarMaterial(@PathVariable Long id , @RequestBody MaterialRequest materialRequest){
        return ResponseEntity.ok().body(estoqueService.atualizarMaterial(id, materialRequest));
    }


}
