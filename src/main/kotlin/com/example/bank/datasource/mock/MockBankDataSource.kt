package com.example.bank.datasource.mock

import com.example.bank.datasource.BankDataSource
import com.example.bank.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {

    private val banks = mutableListOf(
        Bank("123", 12.0, 12),
        Bank("234", 17.0, 100),
        Bank("345", 0.0, 122)
    )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.firstOrNull { it.accountNumber==accountNumber }
            ?: throw NoSuchElementException("Could not find bank for accountNumber : $accountNumber")
    }

    override fun createBank(bank: Bank): Bank {
        if(banks.any { it.accountNumber==bank.accountNumber }){
            throw IllegalArgumentException("Bank already exists with given accountNumber")
        }
        banks.add(bank)
        return bank
    }
}