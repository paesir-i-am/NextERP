package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : InvoiceItemServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:02  paesir      최초 생성
 */

import com.nexterp.accounting.dto.InvoiceItemDTO;
import com.nexterp.accounting.entity.InvoiceItem;
import com.nexterp.accounting.repository.InvoiceItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceItemServiceImpl implements InvoiceItemService {
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    public Page<InvoiceItemDTO> getInvoiceItems(Pageable pageable) {
        return invoiceItemRepository.findAll(pageable).map(this::entityToDTO);
    }

    @Override
    public InvoiceItemDTO getInvoiceItemById(Long id) {
        return invoiceItemRepository.findById(id)
            .map(this::entityToDTO)
            .orElseThrow(() -> new IllegalArgumentException("Invoice item not found: " + id));
    }

    @Override
    public InvoiceItemDTO createInvoiceItem(InvoiceItemDTO invoiceItemDTO) {
        InvoiceItem invoiceItem = dtoToEntity(invoiceItemDTO);
        InvoiceItem savedItem = invoiceItemRepository.save(invoiceItem);
        return entityToDTO(savedItem);
    }

    @Override
    public InvoiceItemDTO updateInvoiceItem(Long id, InvoiceItemDTO invoiceItemDTO) {
        InvoiceItem existingItem = invoiceItemRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invoice item not found: " + id));

        existingItem.setItemName(invoiceItemDTO.getItemName());
        existingItem.setQuantity(invoiceItemDTO.getQuantity());
        existingItem.setUnitPrice(invoiceItemDTO.getUnitPrice());
        existingItem.setTotalPrice(invoiceItemDTO.getTotalPrice());

        InvoiceItem updatedItem = invoiceItemRepository.save(existingItem);
        return entityToDTO(updatedItem);
    }

    @Override
    public void deleteInvoiceItem(Long id) {
        if (!invoiceItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice item not found: " + id);
        }
        invoiceItemRepository.deleteById(id);
    }

    private InvoiceItem dtoToEntity(InvoiceItemDTO dto) {
        InvoiceItem entity = new InvoiceItem();
        entity.setItemName(dto.getItemName());
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        return entity;
    }

    private InvoiceItemDTO entityToDTO(InvoiceItem entity) {
        InvoiceItemDTO dto = new InvoiceItemDTO();
        dto.setId(entity.getId());
        dto.setItemName(entity.getItemName());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        return dto;
    }

}
