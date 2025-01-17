package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : InvoiceServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 3:33  paesir      최초 생성
 */

import com.nexterp.accounting.dto.InvoiceDTO;
import com.nexterp.accounting.entity.Invoice;
import com.nexterp.accounting.entity.Transaction;
import com.nexterp.accounting.repository.InvoiceRepository;
import com.nexterp.accounting.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
  private final InvoiceRepository invoiceRepository;
  private final TransactionRepository transactionRepository;

  public InvoiceServiceImpl(InvoiceRepository invoiceRepository, TransactionRepository transactionRepository) {
    this.invoiceRepository = invoiceRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Page<InvoiceDTO> getAllInvoices(Pageable pageable) {
    return invoiceRepository.findAll(pageable).map(this::entityToDTO);
  }

  @Override
  public InvoiceDTO getInvoiceById(Long id) {
    return invoiceRepository.findById(id)
        .map(this::entityToDTO)
        .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));
  }

  @Override
  public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
    Transaction transaction = transactionRepository.findById(invoiceDTO.getId())
        .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + invoiceDTO.getId()));

    Invoice invoice = dtoToEntity(invoiceDTO);
    invoice.setTransaction(transaction);

    Invoice savedInvoice = invoiceRepository.save(invoice);
    return entityToDTO(savedInvoice);
  }

  @Override
  public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
    Invoice invoice = invoiceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));

    Invoice updatedInvoice = dtoToEntity(invoiceDTO);
    updatedInvoice.setId(invoice.getId());
    Invoice savedInvoice = invoiceRepository.save(updatedInvoice);
    return entityToDTO(savedInvoice);
  }

  @Override
  public void deleteInvoice(Long id) {
    if (!invoiceRepository.existsById(id)) {
      throw new IllegalArgumentException("Invoice not found: " + id);
    }
    invoiceRepository.deleteById(id);
  }

  private InvoiceDTO entityToDTO(Invoice invoice) {
    InvoiceDTO dto = new InvoiceDTO();
    dto.setId(invoice.getId());
    dto.setInvoiceNumber(invoice.getInvoiceNumber());
    dto.setDate(invoice.getDate());
    dto.setBuyer(invoice.getBuyer());
    dto.setSeller(invoice.getSeller());
    dto.setTotalAmount(invoice.getTotalAmount());
    dto.setVatAmount(invoice.getVatAmount());
    dto.setDescription(invoice.getDescription());
    return dto;
  }

  private Invoice dtoToEntity(InvoiceDTO invoiceDTO) {
    Invoice invoice = new Invoice();
    invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
    invoice.setDate(invoiceDTO.getDate());
    invoice.setBuyer(invoiceDTO.getBuyer());
    invoice.setSeller(invoiceDTO.getSeller());
    invoice.setTotalAmount(invoiceDTO.getTotalAmount());
    invoice.setVatAmount(invoiceDTO.getVatAmount());
    invoice.setDescription(invoiceDTO.getDescription());
    return invoice;
  }

}
