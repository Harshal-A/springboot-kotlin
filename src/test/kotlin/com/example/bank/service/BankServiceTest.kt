package com.example.bank.service

import com.example.bank.datasource.BankDataSource
import com.example.bank.datasource.mock.MockBankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest{

    private val datasource:BankDataSource= mockk(relaxed = true)
    private val bankService=BankService(datasource)

    @Test
    fun shouldReturnBanks(){
        //given
        every { datasource.retrieveBanks() } returns emptyList()

        //when
        val banks=bankService.getBanks()

        //then
        verify(exactly = 1) { datasource.retrieveBanks() }
    }
}