package com.example.bank.datasource.mock

import com.example.bank.datasource.BankDataSource
import com.example.bank.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource:BankDataSource {
    override fun retrieveBanks(): Collection<Bank> {

        val bank1 = Bank("123", 12.0, 12)
        val bank2 = Bank("234", 17.0, 100)
        val bank3 = Bank("345", 0.0, 122)
        return listOf(bank1,bank2,bank3)
    }
}