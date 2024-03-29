package com.example.bank.controller

import com.example.bank.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

private const val s = "/api/banks"

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun shouldReturnAllBanks() {
            //when then
            mockMvc.get("/api/banks")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[1].accountNumber") { value("345") }
                }
        }
    }


    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun shouldReturnBankForGivenAccountNumber() {
            //given
            val accountNumber = "345"

            //when then
            mockMvc.get("/api/banks/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value(0.0) }
                    jsonPath("$.transactionFee") { value(122) }
                }
        }

        @Test
        fun shouldReturnNotFoundForAccountNumberNotFound() {
            //given
            val accountNumber = "1234"

            //when then
            mockMvc.get("/api/banks/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBank {
        @Test
        fun shouldAddNewBank() {
            // given
            val newBank = Bank("123a", 10.0, 11)

            //when
            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON // for request body
                content = objectMapper.writeValueAsString(newBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) } // for response body
                    jsonPath("$.accountNumber") { value(newBank.accountNumber) }
                    jsonPath("$.trust") { value(newBank.trust) }
                    jsonPath("$.transactionFee") { value(newBank.transactionFee) }
                }
        }

        @Test
        fun shouldThrowExceptionWhenAccountNumberAlreadyExists() {
            // given
            val newBank = Bank("123", 10.0, 11)

            //when
            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON // for request body
                content = objectMapper.writeValueAsString(newBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("PUT /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PutExistingBank {

        @Test
        fun shouldPutExitingBank() {
            //given
            val updatedBank = Bank("345", 5.0, 11)

            //when
            val performPutRequest = mockMvc.put("/api/banks") {
                contentType = MediaType.APPLICATION_JSON // for request body
                content = objectMapper.writeValueAsString(updatedBank)
            }
            //then
            performPutRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("/api/banks/${updatedBank.accountNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }
        }

        @Test
        fun shouldThrowExceptionIfBankDoesNotExist() {
            //given
            val updatedBank = Bank("1234", 5.0, 11)

            //when
            val performPutRequest = mockMvc.put("/api/banks") {
                contentType = MediaType.APPLICATION_JSON // for request body
                content = objectMapper.writeValueAsString(updatedBank)
            }
            //then
            performPutRequest
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {

        @Test
        fun shouldDeleteExitingBank() {
            //given
            val accountNumber = 123

            //when
            val performDeleteRequest = mockMvc.delete("/api/banks/${accountNumber}") {
                contentType = MediaType.APPLICATION_JSON // for request body
            }
            //then
            performDeleteRequest
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("/api/banks/${accountNumber}")
                .andExpect {
                    status { isNotFound() }
                }
        }

        @Test
        fun shouldReturnNotFoundIfAccountNumberDoesNotExist() {
            //given
            val accountNumber = 1234

            //when
            val performDeleteRequest = mockMvc.delete("/api/banks/${accountNumber}") {
                contentType = MediaType.APPLICATION_JSON // for request body
            }
            //then
            performDeleteRequest
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }


}