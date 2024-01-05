package com.example.bank.service

import com.example.bank.datasource.BankDataSource
import com.example.bank.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val datasource: BankDataSource) {
    fun getBanks():Collection<Bank> = datasource.retrieveBanks()
    fun getBank(accountNumber: String) = datasource.retrieveBank(accountNumber)
}