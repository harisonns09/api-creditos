package com.consultacreditos.consulta.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultacreditos.consulta.services.CreditoMessageProducer;
import com.consultacreditos.consulta.services.CreditoService;
import com.consultacreditos.consulta.shared.CreditoDTO;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    @Autowired
    private CreditoService creditoService; 
    
    @Autowired
    private CreditoMessageProducer creditoProducer;


    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> listarPorNumeroNfse(@PathVariable String numeroNfse) {
        //List<Credito> creditos = creditoService.listarPorNumeroNfse(numeroNfse);
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

}
