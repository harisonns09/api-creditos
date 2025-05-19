package com.consultacreditos.consulta.view.model;

import java.util.Random;
import java.math.BigDecimal;

import com.consultacreditos.consulta.model.Credito;

public class ValidacaoCredito {

    public Credito gerarNumerosDocumentos(Credito credito){

        // Gera números aleatórios de 7 dígitos
        String numeroCreditoGerado = String.format("%07d", new Random().nextInt(10_000_000));
        String numeroNfseGerado = String.format("%07d", new Random().nextInt(10_000_000));

        // Seta os números gerados diretamente na entidade
        credito.setNumeroCredito(numeroCreditoGerado);
        credito.setNumeroNfse(numeroNfseGerado);

        return credito;
    }

    public Credito validarCampos(Credito credito) {
        // Aqui você pode adicionar a lógica de validação dos campos do crédito
        // Exemplo: verificar se os campos obrigatórios estão preenchidos, etc.
        if (credito.getValorIssqn() == null || credito.getValorIssqn().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do ISSQN deve ser maior que zero.");
        }
        if (credito.getDataConstituicao() == null) {
            throw new IllegalArgumentException("A data de constituição não pode ser nula.");
        }
        if (credito.getTipoCredito() == null || credito.getTipoCredito().isEmpty()) {
            throw new IllegalArgumentException("O tipo de crédito não pode ser nulo ou vazio.");
        }
        if (credito.getValorFaturado() == null || credito.getValorFaturado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor faturado deve ser maior que zero.");
        }
        if (credito.getValorDeducao() == null || credito.getValorDeducao().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor de dedução não pode ser nulo ou negativo.");
        }
        if (credito.getBaseCalculo() == null || credito.getBaseCalculo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("A base de cálculo não pode ser nula ou negativa.");
        }
        if (credito.getAliquota() == null || credito.getAliquota().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("A alíquota não pode ser nula ou negativa.");
        }


        // Adicione outras validações conforme necessário

        return credito;
    }
    
}
