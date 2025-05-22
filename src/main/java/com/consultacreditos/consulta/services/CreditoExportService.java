package com.consultacreditos.consulta.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.consultacreditos.consulta.model.Credito;
import com.consultacreditos.consulta.repository.CreditoRepository;
import com.consultacreditos.consulta.shared.CreditoDTO;
import com.consultacreditos.consulta.utils.ExcelGenerator;
import com.consultacreditos.consulta.utils.PdfGenerator;

import jakarta.servlet.ServletOutputStream;

@Service
@RequiredArgsConstructor
public class CreditoExportService {

    private final CreditoRepository repository;
    private final CreditoService creditoService;

    public void exportarExcel(OutputStream out, String tipoCredito, Boolean simplesNacional) throws IOException {
        // List<Credito> creditos = aplicarFiltros(tipoCredito, simplesNacional);
        // ExcelGenerator.gerarPlanilha(creditos, out);
    }

    public void exportarPdf(OutputStream out, String tipoCredito, Boolean simplesNacional) {
        // List<Credito> creditos = aplicarFiltros(tipoCredito, simplesNacional);
        // PdfGenerator.gerarRelatorio(creditos, out);
    }

    // private List<Credito> aplicarFiltros(String tipoCredito, Boolean
    // simplesNacional) {
    // if (tipoCredito != null && simplesNacional != null) {
    // return repository.findByTipoCreditoAndSimplesNacional(tipoCredito,
    // simplesNacional);
    // } else if (tipoCredito != null) {
    // return repository.findByTipoCredito(tipoCredito);
    // } else if (simplesNacional != null) {
    // return repository.findBySimplesNacional(simplesNacional);
    // } else {
    // return repository.findAll();
    // }
    // }

    public void exportarExcel(OutputStream out,
            boolean dataConstituicao, boolean valorIssqn, boolean tipoCredito, boolean simplesNacional,
            boolean aliquota, boolean valorFaturado, boolean valorDeducao, boolean baseCalculo) throws IOException {

        List<CreditoDTO> creditos = creditoService.obterTodos();
        ExcelGenerator.gerarPlanilha(creditos, out,
                dataConstituicao, valorIssqn, tipoCredito, simplesNacional,
                aliquota, valorFaturado, valorDeducao, baseCalculo);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'exportarExcel'");
    }
}
