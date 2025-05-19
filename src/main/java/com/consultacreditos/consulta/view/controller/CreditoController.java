package com.consultacreditos.consulta.view.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultacreditos.consulta.model.Credito;
import com.consultacreditos.consulta.services.CreditoMessageProducer;
import com.consultacreditos.consulta.services.CreditoService;
import com.consultacreditos.consulta.shared.CreditoDTO;
import com.consultacreditos.consulta.view.model.CreditoRequest;
import com.consultacreditos.consulta.view.model.ValidacaoCredito;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;

    @Autowired
    private CreditoMessageProducer creditoProducer;

    ValidacaoCredito validacaoCredito = new ValidacaoCredito();

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> listarPorNumeroNfse(@PathVariable String numeroNfse) {
        // List<Credito> creditos = creditoService.listarPorNumeroNfse(numeroNfse);
        List<CreditoDTO> creditos = creditoService.listarPorNumeroNfse(numeroNfse)
                .stream()
                .map(CreditoDTO::new)
                .toList();

        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> buscarPorNumeroCredito(@PathVariable String numeroCredito) {
        return creditoService.buscarPorNumeroCredito(numeroCredito)
                .map(c -> {
                    CreditoDTO dto = new CreditoDTO(c);
                    creditoProducer.enviarCreditoParaFila(dto); // Envia o DTO para a fila
                    return ResponseEntity.ok(dto); // Retorna o DTO como resposta
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreditoDTO> salvarCredito(@RequestBody CreditoRequest creditoRequest) {

        ModelMapper mapper = new ModelMapper();

        // Converte CreditoRequest para Credito
        Credito credito = mapper.map(creditoRequest, Credito.class);

        credito = validacaoCredito.gerarNumerosDocumentos(credito);

        // Salva a entidade e recebe o objeto salvo
        Credito creditoSalvo = creditoService.salvarCredito(validacaoCredito.validarCampos(credito));

        // Converte Credito para CreditoDTO para retorno
        CreditoDTO creditoSalvoDTO = mapper.map(creditoSalvo, CreditoDTO.class);

        // Envia o DTO para a fila
        creditoProducer.enviarCreditoParaFila(creditoSalvoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(creditoSalvoDTO);
    }

    @GetMapping
    public ResponseEntity<List<CreditoDTO>> listarTodos() {
        List<CreditoDTO> creditos = creditoService.obterTodos();

        if (creditos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(creditos); // 200
    }

    @DeleteMapping("/credito/{numeroCredito}")
    public ResponseEntity<String> deletarPorNumeroCredito(@PathVariable String numeroCredito) {
        boolean deletado = creditoService.deletarPorNumeroCredito(numeroCredito);

        if (deletado) {
            return ResponseEntity.ok("Crédito com número " + numeroCredito + " deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Crédito com número " + numeroCredito + " não encontrado.");
        }
    }

}
