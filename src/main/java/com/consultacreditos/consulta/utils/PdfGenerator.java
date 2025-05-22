package com.consultacreditos.consulta.utils;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;


import com.lowagie.text.Document;

import com.consultacreditos.consulta.model.Credito;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfGenerator {

    public static void gerarRelatorio(List<Credito> creditos, OutputStream out) {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Font font = new Font(Font.HELVETICA, 12, Font.NORMAL);
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);

        Stream.of("Número Crédito", "Número NFS-e", "Data", "Valor ISSQN", "Tipo", "SN", "Aliquota", "Faturado", "Deducao", "Base Cálculo")
                .forEach(col -> table.addCell(new PdfPCell(new Phrase(col, font))));

        for (Credito c : creditos) {
            table.addCell(c.getNumeroCredito());
            table.addCell(c.getNumeroNfse());
            table.addCell(c.getDataConstituicao().toString());
            table.addCell(c.getValorIssqn().toString());
            table.addCell(c.getTipoCredito());
            table.addCell(c.isSimplesNacional() ? "Sim" : "Não");
            table.addCell(c.getAliquota().toString());
            table.addCell(c.getValorFaturado().toString());
            table.addCell(c.getValorDeducao().toString());
            table.addCell(c.getBaseCalculo().toString());
        }

        document.add(table);
        document.close();
    }
}
