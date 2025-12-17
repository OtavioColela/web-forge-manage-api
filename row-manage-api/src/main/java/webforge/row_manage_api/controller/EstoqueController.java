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

    @PostMapping("/adicionar")
    public ResponseEntity<Void> adicionarMaterial(@RequestParam Long id, @RequestParam int quantidade){
        estoqueService.adicionarMaterial(id, quantidade);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reduzir")
    public ResponseEntity<Void> reduzirMaterial(@RequestParam Long id,
                                                @RequestParam int quantidade) {
        estoqueService.reduzirMaterial(id, quantidade);
        return ResponseEntity.ok().build();
    }
}
