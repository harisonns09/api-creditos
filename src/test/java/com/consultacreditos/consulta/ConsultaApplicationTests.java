package com.consultacreditos.consulta;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.consultacreditos.consulta.model.Credito;
import com.consultacreditos.consulta.repository.CreditoRepository;



@SpringBootTest
@AutoConfigureMockMvc
class ConsultaApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreditoRepository creditoRepository;

    @BeforeEach
    void setup() {
        creditoRepository.deleteAll();

        Credito credito = new Credito();
        credito.setNumeroCredito("123456789");
        credito.setNumeroNfse("NFSE-001");
        credito.setDataConstituicao(LocalDate.of(2024, 1, 1));
        credito.setValorIssqn(new BigDecimal("1500.00"));
        credito.setTipoCredito("ISS");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("0.05"));
        credito.setValorFaturado(new BigDecimal("30000.00"));
        credito.setValorDeducao(BigDecimal.ZERO);
        credito.setBaseCalculo(new BigDecimal("30000.00"));

        creditoRepository.save(credito);
    }

    @Test
    @DisplayName("✅ Deve retornar créditos por número de NFS-e existente")
    void testBuscarPorNumeroNfseExistente() throws Exception {
        mockMvc.perform(get("/api/creditos/NFSE-001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCredito").value("123456789"))
                .andExpect(jsonPath("$[0].numeroNfse").value("NFSE-001"))
                .andExpect(jsonPath("$[0].tipoCredito").value("ISS"));
    }

    @Test
    @DisplayName("❌ Deve retornar array vazio ao buscar por NFS-e inexistente")
    void testBuscarPorNumeroNfseInexistente() throws Exception {
        mockMvc.perform(get("/api/creditos/NFSE-999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("✅ Deve retornar crédito por número do crédito existente")
    void testBuscarPorNumeroCreditoExistente() throws Exception {
        mockMvc.perform(get("/api/creditos/credito/123456789")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCredito").value("123456789"))
                .andExpect(jsonPath("$.numeroNfse").value("NFSE-001"));
    }

    @Test
    @DisplayName("❌ Deve retornar 404 ao buscar por número do crédito inexistente")
    void testBuscarPorNumeroCreditoInexistente() throws Exception {
        mockMvc.perform(get("/api/creditos/credito/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("❌ Deve retornar array vazio se não houver créditos no banco")
    void testObterTodosComBancoVazio() throws Exception {
        creditoRepository.deleteAll();

        mockMvc.perform(get("/api/creditos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

