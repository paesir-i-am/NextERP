package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : VATServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 5:30  paesir      최초 생성
 */

import com.nexterp.accounting.entity.Transaction;
import com.nexterp.accounting.entity.VAT;
import com.nexterp.accounting.repository.TransactionRepository;
import com.nexterp.accounting.repository.VATRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class VATServiceImpl implements VATService {
    private final VATRepository vatRepository;
    private final TransactionRepository transactionRepository;

    public VATServiceImpl(VATRepository vatRepository, TransactionRepository transactionRepository) {
        this.vatRepository = vatRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public VAT createVAT(Long transactionId, BigDecimal vatRate, BigDecimal baseAmount) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));

        BigDecimal vatAmount = baseAmount.multiply(vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);

        VAT vat = new VAT();
        vat.setTransaction(transaction);
        vat.setVatRate(vatRate);
        vat.setVatAmount(vatAmount);

        return vatRepository.save(vat);
    }

    @Override
    public VAT getVATByTransactionId(Long transactionId) {
        return vatRepository.findByTransactionId(transactionId);
    }

    @Override
    public void deleteVAT(Long vatId) {
        vatRepository.deleteById(vatId);
    }
}
