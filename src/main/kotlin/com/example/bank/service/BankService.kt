package com.example.bank.service

import com.example.bank.datasource.BankDataSource
import com.example.bank.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val datasource: BankDataSource) {
    fun getBanks():Collection<Bank> = datasource.retrieveBanks()
    fun getBank(accountNumber: String) = datasource.retrieveBank(accountNumber)
    fun addBank(bank: Bank): Bank = datasource.createBank(bank)
    fun updateBank(updatedBank: Bank): Bank =datasource.updateBank(updatedBank)
    fun deleteBank(accountNumber: String) =datasource.deleteBank(accountNumber)
}