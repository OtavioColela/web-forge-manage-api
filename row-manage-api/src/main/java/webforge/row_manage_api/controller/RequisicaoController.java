package webforge.row_manage_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webforge.row_manage_api.dto.requisicao.RequisicaoRequest;
import webforge.row_manage_api.dto.requisicao.RequisicaoResponse;
import webforge.row_manage_api.enums.StatusPedido;
import webforge.row_manage_api.service.RequisicaoService;

import java.util.List;


@RestController
@RequestMapping("/requisicao")
public class RequisicaoController {
    @Autowired
    private RequisicaoService requisicaoService;
    @PostMapping
    public RequisicaoResponse criarRequisicao(@RequestBody RequisicaoRequest requisicaoRequest){
        return requisicaoService.criarRequisicao(requisicaoRequest);
    }
    @GetMapping("/abertas")
    public ResponseEntity<List<RequisicaoResponse>> mostrarRequisicoes(){
        return ResponseEntity.ok(requisicaoService.listarRequisicoes());
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<Void> definirStatusRequisicao(@PathVariable Long id, StatusPedido statusPedido){
        requisicaoService.definirRequisicao(id, statusPedido);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/abertas/{Id}")
    public ResponseEntity<List<RequisicaoResponse>> mostrarHistoricoUsuario(@PathVariable Long id){
        return ResponseEntity.ok(requisicaoService.getAllRequisitionsByUser(id));
    }
    @GetMapping("/pendentes")
    public ResponseEntity<List<RequisicaoResponse>> mostrarTodasPendentes(StatusPedido statusPedido){
        return ResponseEntity.ok(requisicaoService.getAllRequisitionsByStatus(statusPedido));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RequisicaoResponse> mostrarRequisicaoPorId(@PathVariable Long id){
        return ResponseEntity.ok(requisicaoService.mostrarPorId(id));
    }
}
