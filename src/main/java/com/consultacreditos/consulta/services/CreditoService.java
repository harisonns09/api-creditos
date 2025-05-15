package com.consultacreditos.consulta.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultacreditos.consulta.model.Credito;
import com.consultacreditos.consulta.repository.CreditoRepository;
import com.consultacreditos.consulta.shared.CreditoDTO;



@Service
public class CreditoService {

    @Autowired
    private CreditoRepository creditoRepository;

    // Aqui você pode adicionar métodos para manipular os dados de crédito  
    // Exemplo: List<Credito> findByClienteId(Integer clienteId);
    // Outros métodos de consulta podem ser definidos aqui
    // Exemplo de método para obter todos os créditos   

    // public List<CreditoDTO> obterTodos() {

    //     List<Credito> creditos = creditoRepository.findAll();

    //     // Transformar um objeto tipo produto em um objeto tipo produtoDTO.
    //     return creditos.stream().map(credito -> new ModelMapper()
    //     .map(credito, CreditoDTO.class))
    //     .collect(Collectors.toList());

    // }

    public List<Credito> listarPorNumeroNfse(String numeroNfse) {
        return creditoRepository.findByNumeroNfse(numeroNfse);
    }

     public Optional<Credito> buscarPorNumeroCredito(String numeroCredito) {
        return creditoRepository.findByNumeroCredito(numeroCredito);
    }

}
