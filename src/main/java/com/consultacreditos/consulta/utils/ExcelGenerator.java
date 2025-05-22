package com.consultacreditos.consulta.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.consultacreditos.consulta.model.Credito;
import com.consultacreditos.consulta.shared.CreditoDTO;

public class ExcelGenerator {

    public static void gerarPlanilha(List<CreditoDTO> creditos, OutputStream out, boolean dataConstituicao,
            boolean valorIssqn, boolean tipoCredito, boolean simplesNacional,
            boolean aliquota, boolean valorFaturado, boolean valorDeducao, boolean baseCalculo) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Créditos");

            Row header = sheet.createRow(0);
            ArrayList<String> colunasList = new ArrayList<>();
            colunasList.add("Número Crédito");
            colunasList.add("Número NFS-e");
            if (dataConstituicao) {
                colunasList.add("Data");
            }
            if (valorIssqn)
                colunasList.add("Valor ISSQN");
            if (tipoCredito)
                colunasList.add("Tipo");
            if (simplesNacional)
                colunasList.add("SN");
            if (aliquota)
                colunasList.add("Aliquota");
            if (valorFaturado)
                colunasList.add("Faturado");
            if (valorDeducao)
                colunasList.add("Deducao");
            if (baseCalculo)
                colunasList.add("Base Cálculo");
            String[] colunas = colunasList.toArray(new String[0]);
            for (int i = 0; i < colunas.length; i++) {
                header.createCell(i).setCellValue(colunas[i]);
            }

            int rowIdx = 1;

            for (CreditoDTO credito : creditos) {
                Row row = sheet.createRow(rowIdx++);
                int columnIdx = 0;
                row.createCell(columnIdx).setCellValue(credito.getNumeroCredito());
                columnIdx++;
                row.createCell(columnIdx).setCellValue(credito.getNumeroNfse());
                columnIdx++;
                if (dataConstituicao == true) {
                    row.createCell(columnIdx).setCellValue(credito.getDataConstituicao().toString());
                    columnIdx++;
                }
                if (valorIssqn == true) {
                    row.createCell(columnIdx).setCellValue(credito.getValorIssqn().doubleValue());
                    columnIdx++;
                }
                if (tipoCredito == true) {
                    row.createCell(columnIdx).setCellValue(credito.getTipoCredito());
                    columnIdx++;
                }
                if (simplesNacional == true) {
                    row.createCell(columnIdx).setCellValue(credito.getSimplesNacional());
                    columnIdx++;
                }
                if (aliquota == true) {
                    row.createCell(columnIdx).setCellValue(credito.getAliquota().doubleValue());
                    columnIdx++;
                }
                if (valorFaturado == true) {
                    row.createCell(columnIdx).setCellValue(credito.getValorFaturado().doubleValue());
                    columnIdx++;
                }
                if (valorDeducao == true) {
                    row.createCell(columnIdx).setCellValue(credito.getValorDeducao().doubleValue());
                    columnIdx++;
                }
                if (baseCalculo == true) {
                    row.createCell(columnIdx).setCellValue(credito.getBaseCalculo().doubleValue());
                    columnIdx++;
                }

            }

            workbook.write(out);
        }
    }
}
