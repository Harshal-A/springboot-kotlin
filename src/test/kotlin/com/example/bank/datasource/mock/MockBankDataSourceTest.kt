package com.example.bank.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {

    private val mockDataSource = MockBankDataSource()

    @Test
    fun shouldProvideCollectionOfBanks() {
        //when
        val banks = mockDataSource.retrieveBanks()

        //then
        assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data with satisfy`() {
        //when
        val banks = mockDataSource.retrieveBanks()

        //then
        assertThat(banks).allSatisfy { assertThat(it.accountNumber).isNotBlank }
    }

    @Test
    fun `should provide some mock data with all match and any match`() {
        //when
        val banks = mockDataSource.retrieveBanks()

        //then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).anyMatch { it.transactionFee != 0 }
    }

    @Test
    fun `should have unique account numbers`() {
        //when
        val banks = mockDataSource.retrieveBanks()
        val accountNumbers = banks.stream().map { it.accountNumber }.toList().toSet()

        //then
        assertThat(accountNumbers.size).isEqualTo(banks.size)
    }
}