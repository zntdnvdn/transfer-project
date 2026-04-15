package com.eduproject.transferrequest.controller;


import com.eduproject.transferrequest.config.TestcontainersConfiguration;
import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import com.eduproject.transferrequest.mapper.TestMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Sql(
        scripts = "/sql/init.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
class TransferRequestControllerIT {
    @Autowired
    private TestMapper testMapper;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void cleanUp() {
        testMapper.delete();
    }

    @Test
    void getTransfer_shouldReturnTransferByRequestId() throws Exception {
        TransferRequestDto transferRequestDto = getTransferRequestDto();

        testMapper.insert(transferRequestDto);

        mockMvc.perform(get("/api/v1/transfers/{requestId}", "req-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value("req-123"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    private static @NotNull TransferRequestDto getTransferRequestDto() {
        TransferRequestDto transferRequestDto = new TransferRequestDto();
        transferRequestDto.setRequestId("req-123");
        transferRequestDto.setStatus("NEW");
        transferRequestDto.setFromAccount("ACC-001");
        transferRequestDto.setToAccount("ACC-002");
        transferRequestDto.setAmount(BigDecimal.valueOf(1500.00));
        transferRequestDto.setCurrency("RUB");
        transferRequestDto.setCreatedAt(LocalDateTime.now());
        transferRequestDto.setUpdatedAt(LocalDateTime.now());
        return transferRequestDto;
    }

    @Test
    void getTransfer_shouldReturn404WhenRequestNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/transfers/{requestId}", "missing-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Transfer request not found"))
                .andExpect(jsonPath("$.detail").value("Transfer request not found. requestId=missing-id"));
    }
}