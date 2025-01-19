package com.nexterp.payroll.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.payroll.service
 * FileName       : SalaryServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 19.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 19.오후 11:09  paesir      최초 생성
 */

import com.nexterp.accounting.entity.*;
import com.nexterp.accounting.repository.*;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.repository.EmployeeRepository;
import com.nexterp.payroll.dto.SalaryDTO;
import com.nexterp.payroll.entity.PaymentStatus;
import com.nexterp.payroll.entity.Salary;
import com.nexterp.payroll.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
  private final AccountRepository accountRepository;
  private final SalaryRepository salaryRepository;
  private final EmployeeRepository employeeRepository;
  private final InvoiceRepository invoiceRepository;
  private final InvoiceItemRepository invoiceItemRepository;
  private final JournalEntryRepository journalEntryRepository;
  private final TransactionRepository transactionRepository;

  @Override
  public SalaryDTO createSalary(SalaryDTO salaryDTO) {
    // 1. Employee 확인
    Employee employee = getEmployeeById(salaryDTO.getEmployeeId());

    // 2. Salary 저장
    Salary savedSalary = saveSalary(salaryDTO, employee);

    // 3. Transaction 생성 및 저장
    Transaction transaction = saveTransaction(savedSalary);

    // 4. Invoice 생성 및 저장
    Invoice savedInvoice = saveInvoice(savedSalary, employee, transaction);

    // 5. InvoiceItem 생성 및 저장
    saveInvoiceItems(savedInvoice, savedSalary);

    // 6. JournalEntry 생성 및 저장
    createJournalEntries(savedSalary, transaction);

    // 7. 상태 업데이트
    updateSalaryStatus(savedSalary);

    return entityToDTO(savedSalary);
  }

  private Employee getEmployeeById(Integer employeeId) {
    return employeeRepository.findById(employeeId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + employeeId));
  }

  private Salary saveSalary(SalaryDTO salaryDTO, Employee employee) {
    Salary salary = new Salary();
    salary.setEmployee(employee);
    salary.setBaseSalary(salaryDTO.getBaseSalary());
    salary.setBonus(salaryDTO.getBonus());
    salary.setDeductions(salaryDTO.getDeductions());
    salary.setTotalSalary(salaryDTO.getBaseSalary()
        .add(salaryDTO.getBonus())
        .subtract(salaryDTO.getDeductions()));
    salary.setStatus(PaymentStatus.PENDING);
    return salaryRepository.save(salary);
  }

  private Transaction saveTransaction(Salary salary) {
    Transaction transaction = new Transaction();
    transaction.setDate(LocalDateTime.now());
    transaction.setAmount(salary.getTotalSalary());
    transaction.setType(TransactionType.SALARY);
    transaction.setDescription("급여 지급");
    return transactionRepository.save(transaction);
  }

  private Invoice saveInvoice(Salary salary, Employee employee, Transaction transaction) {
    Invoice invoice = new Invoice();
    invoice.setTransaction(transaction);
    invoice.setInvoiceNumber(generateInvoiceNumber());
    invoice.setDate(LocalDateTime.now());
    invoice.setBuyer("Company");
    invoice.setSeller(employee.getName());
    invoice.setTotalAmount(salary.getTotalSalary());
    invoice.setDescription("급여 지급 명세서");
    return invoiceRepository.save(invoice);
  }

  private void saveInvoiceItems(Invoice invoice, Salary salary) {
    List<InvoiceItem> items = List.of(
        createInvoiceItem(invoice, "기본급", salary.getBaseSalary(), 1),
        createInvoiceItem(invoice, "보너스", salary.getBonus(), 1),
        createInvoiceItem(invoice, "공제", salary.getDeductions().negate(), 1)
    );
    invoiceItemRepository.saveAll(items);
  }

  private InvoiceItem createInvoiceItem(Invoice invoice, String itemName, BigDecimal unitPrice, int quantity) {
    InvoiceItem item = new InvoiceItem();
    item.setInvoice(invoice);
    item.setItemName(itemName);
    item.setQuantity(quantity);
    item.setUnitPrice(unitPrice);
    item.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));
    return item;
  }

  private Account getAccountByCode(String accountCode) {
    return accountRepository.findByCode(accountCode)
        .orElseThrow(() -> new IllegalArgumentException("Invalid account code: " + accountCode));
  }

  private void createJournalEntries(Salary salary, Transaction transaction) {
    // 차변 계정 (급여 비용)
    Account debitAccount = getAccountByCode("501"); // 급여 비용 계정
    Account creditAccount = getAccountByCode("101"); // 현금 계정

    // 차변 JournalEntry 생성
    JournalEntry debitEntry = createJournalEntry(
        debitAccount,
        salary.getTotalSalary(),  // Debit 값
        BigDecimal.ZERO,         // Credit 값
        salary.getTotalSalary(), // Amount 값
        "급여 지급 - 차변",
        transaction
    );
    journalEntryRepository.save(debitEntry);

    // 계정 잔액 업데이트
    debitAccount.addBalance(salary.getTotalSalary());
    accountRepository.save(debitAccount);

    // 대변 JournalEntry 생성
    JournalEntry creditEntry = createJournalEntry(
        creditAccount,
        BigDecimal.ZERO,             // Debit 값
        salary.getTotalSalary(),     // Credit 값
        salary.getTotalSalary().negate(), // Amount 값
        "급여 지급 - 대변",
        transaction
    );
    journalEntryRepository.save(creditEntry);

    // 계정 잔액 업데이트
    creditAccount.subtractBalance(salary.getTotalSalary());
    accountRepository.save(creditAccount);
  }

  private JournalEntry createJournalEntry(Account account, BigDecimal debit, BigDecimal credit, BigDecimal amount, String description, Transaction transaction) {
    JournalEntry entry = new JournalEntry();
    entry.setAccount(account);
    entry.setDate(LocalDate.now());
    entry.setDebit(debit);
    entry.setCredit(credit);
    entry.setAmount(amount); // Amount 설정
    entry.setDescription(description);
    entry.setTransaction(transaction);
    return entry;
  }

  private void updateSalaryStatus(Salary salary) {
    salary.setStatus(PaymentStatus.PAID);
    salary.setPaymentDate(LocalDate.now());
    salaryRepository.save(salary);
  }

  private String generateInvoiceNumber() {
    return "SAL-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
  }


  // DTO → Entity 변환
  private Salary dtoToEntity(SalaryDTO dto) {
    Salary salary = new Salary();
    salary.setBaseSalary(dto.getBaseSalary());
    salary.setBonus(dto.getBonus());
    salary.setDeductions(dto.getDeductions());
    salary.setTotalSalary(dto.getBaseSalary()
        .add(dto.getBonus())
        .subtract(dto.getDeductions()));
    salary.setStatus(PaymentStatus.valueOf(dto.getStatus()));
    return salary;
  }

  // Entity → DTO 변환
  private SalaryDTO entityToDTO(Salary salary) {
    SalaryDTO dto = new SalaryDTO();
    dto.setId(salary.getId());
    dto.setEmployeeId(salary.getEmployee().getId());
    dto.setBaseSalary(salary.getBaseSalary());
    dto.setBonus(salary.getBonus());
    dto.setDeductions(salary.getDeductions());
    dto.setTotalSalary(salary.getTotalSalary());
    dto.setStatus(salary.getStatus().name());
    dto.setPaymentDate(salary.getPaymentDate() != null ? salary.getPaymentDate().toString() : null);
    return dto;
  }
}
