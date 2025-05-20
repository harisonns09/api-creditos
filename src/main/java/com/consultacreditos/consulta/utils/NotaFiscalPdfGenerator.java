package com.consultacreditos.consulta.utils;

import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.io.ByteArrayOutputStream;

import com.consultacreditos.consulta.model.Credito;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class NotaFiscalPdfGenerator {

    public static byte[] gerarNotaFiscalPdf(Credito credito) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        doc.add(new Paragraph("NOTA FISCAL DE SERVIÇO", titleFont));
        doc.add(new Paragraph("Número NFSe: " + credito.getNumeroNfse(), textFont));
        doc.add(new Paragraph("Número Crédito: " + credito.getNumeroCredito(), textFont));
        doc.add(new Paragraph("Tipo de Crédito: " + credito.getTipoCredito(), textFont));
        doc.add(new Paragraph("Valor Faturado: " + NumberFormat.getCurrencyInstance().format(credito.getValorFaturado()), textFont));
        doc.add(new Paragraph("Data de Constituição: " + credito.getDataConstituicao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), textFont));
        doc.add(new Paragraph("Simples Nacional: " + (credito.isSimplesNacional() ? "Sim" : "Não"), textFont));

        doc.close();

        return baos.toByteArray();
    }

}
