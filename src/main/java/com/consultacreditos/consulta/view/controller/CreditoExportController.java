package com.consultacreditos.consulta.view.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consultacreditos.consulta.services.CreditoExportService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creditos/exportar")
@RequiredArgsConstructor
public class CreditoExportController {

    private final CreditoExportService exportService;

    @GetMapping("/pdf")
    public void exportarPdf(HttpServletResponse response,
                               @RequestParam(required = false) String tipoCredito,
                               @RequestParam(required = false) Boolean simplesNacional) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=creditos.pdf");
        exportService.exportarPdf(response.getOutputStream(), tipoCredito, simplesNacional);
    }

    @GetMapping("/excel")
    public void exportarExcel(HttpServletResponse response,
                             
                             @RequestParam(required = false) boolean dataConstituicao,
                             @RequestParam(required = false) boolean valorIssqn,
                             @RequestParam(required = false) boolean tipoCredito,
                             @RequestParam(required = false) boolean simplesNacional,
                             @RequestParam(required = false) boolean aliquota,
                             @RequestParam(required = false) boolean valorFaturado,
                             @RequestParam(required = false) boolean valorDeducao,
                             @RequestParam(required = false) boolean baseCalculo) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=creditos.xlsx");
        exportService.exportarExcel(response.getOutputStream(), dataConstituicao,
                valorIssqn, tipoCredito, simplesNacional, aliquota, valorFaturado, valorDeducao, baseCalculo);
    }

    
}
