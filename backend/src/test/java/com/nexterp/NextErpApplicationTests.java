package com.nexterp;

import com.nexterp.accounting.dto.JournalEntryDTO;
import com.nexterp.accounting.entity.*;
import com.nexterp.accounting.repository.*;
import com.nexterp.accounting.service.JournalEntryService;
import com.nexterp.accounting.service.ReportFileGenerator;
import com.nexterp.common.util.CustomJWTException;
import com.nexterp.common.util.JWTUtil;
import com.nexterp.employee.dto.AttendanceDTO;
import com.nexterp.employee.dto.EmployeeDTO;
import com.nexterp.employee.entity.Department;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.entity.Position;
import com.nexterp.employee.repository.EmployeeRepository;
import com.nexterp.employee.service.AttendanceService;
import com.nexterp.employee.service.EmployeeService;
import com.nexterp.member.entity.Member;
import com.nexterp.member.repository.MemberRepository;
import com.nexterp.member.service.MemberService;
import com.nexterp.payroll.dto.SalaryDTO;
import com.nexterp.payroll.entity.PaymentStatus;
import com.nexterp.payroll.service.SalaryService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class NextErpApplicationTests {

/*  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void initializeAccounts() {
    // 부모 계정 생성
    Account assetAccount = new Account("100", "자산", AccountType.ASSET, null, "자산 계정");
    accountRepository.save(assetAccount);

    // 자식 계정 생성
    accountRepository.saveAll(List.of(
        new Account("101", "현금", AccountType.ASSET, assetAccount, "현금 계정"),
        new Account("102", "보통예금", AccountType.ASSET, assetAccount, "보통예금 계정"),
        new Account("103", "당좌예금", AccountType.ASSET, assetAccount, "당좌예금 계정")
    ));

    // 데이터 검증
    List<Account> accounts = accountRepository.findAll();
    assertThat(accounts).isNotEmpty();
    assertThat(accounts.size()).isEqualTo(4); // 총 4개 계정 (부모 + 자식)
  }*/

  /*@Autowired
  private ReportFileGenerator reportFileGenerator;

  @Test
  public void createReport() {
    ReportType reportType = ReportType.BALANCE_SHEET; // 예제 ReportType
    List<String[]> content = List.of(
        new String[]{"Account", "Debit", "Credit"},
        new String[]{"Cash", "1000", "0"},
        new String[]{"Revenue", "0", "1000"}
    );

    try {
      String filePath = reportFileGenerator.generateReportFile(reportType, content);
      System.out.println("리포트 생성 완료: " + filePath);
    } catch (IOException e) {
      System.err.println("리포트 생성 중 오류 발생: " + e.getMessage());
    }
  }

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private ReportRepository reportRepository;
  @Autowired
  private JournalEntryRepository journalEntryRepository;
  @Autowired
  private JournalEntryService journalEntryService;
  @Test
  public void testCreateJournalEntry() {
    JournalEntryDTO dto = new JournalEntryDTO();
    dto.setDate(LocalDate.now());
    dto.setAccountCode("101");
    dto.setDebit(BigDecimal.valueOf(100));
    dto.setCredit(BigDecimal.ZERO);
    dto.setDescription("Test Entry");

    JournalEntryDTO result = journalEntryService.createJournalEntry(dto);

    assertNotNull(result.getId());
    assertEquals(BigDecimal.valueOf(100), result.getDebit());
    assertEquals(BigDecimal.ZERO, result.getCredit());
    assertEquals(BigDecimal.valueOf(100), result.getAmount()); // amount 검증
  }*/

  /*@Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberService memberService;

  @Autowired
  private AttendanceService attendanceService;

  @Test
  @Transactional
  @Rollback(false) // 롤백 방지
  void createMemberWithEmployee() {
    // 1. 기존 부서와 직위 객체 지정 (ID 값만 지정)
    Department department = new Department(1, "영업팀", "sales@company.com");
    Position position = new Position(1, "사원", "User");

    // 2. 직원 데이터 생성
    Employee employee = new Employee(
        1003, "김철수", LocalDate.of(1990, 1, 15), false,
        "010-1234-5678", "useongj490@gmail.com", "서울특별시 강남구",
        department, position, LocalDate.of(2020, 3, 1), null
    );

    // 3. 직원 객체를 데이터베이스에 저장
    employeeRepository.save(employee);
    employeeRepository.flush(); // 강제 플러시

    // 4. 저장된 Employee 조회
    Employee savedEmployee = employeeRepository.findById(1003)
        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

    // 5. Member 생성
    memberService.createMember(savedEmployee);

    // 6. 저장된 Member 조회
    Member savedMember = memberRepository.findById(savedEmployee.getId())
        .orElseThrow(() -> new IllegalArgumentException("Member not found"));

    // 7. DB 확인
    assertThat(savedMember).isNotNull();
    assertThat(savedMember.getId()).isEqualTo(savedEmployee.getId());
    assertThat(savedMember.getPassword()).isEqualTo("00000000"); // 기본 비밀번호 확인
    assertThat(savedMember.getEmployee().getName()).isEqualTo("김철수");
    assertThat(savedMember.getEmployee().getDepartment().getName()).isEqualTo("영업팀");
    assertThat(savedMember.getEmployee().getPosition().getTitle()).isEqualTo("사원");
  }*/

  /*@Test
  @Transactional
  @Rollback(false)
  void testSaveAttendance() {
    // Arrange
    Department department = new Department(1, "영업팀", "sales@company.com");
    Position position = new Position(1, "사원", "User");

    Employee employee = new Employee(
        1, "John Doe", LocalDate.of(1985, 5, 20), true,
        "010-1111-2222", "johndoe@example.com", "서울특별시 강남구",
        department, position, LocalDate.of(2010, 1, 1), null
    );
    employeeRepository.save(employee);
    employeeRepository.flush(); // 강제 플러시

    AttendanceDTO dto = new AttendanceDTO();
    dto.setEmployeeId(1);
    dto.setDate(LocalDate.now());
    dto.setCheckInTime(LocalTime.of(9, 0));
    dto.setCheckOutTime(LocalTime.of(19, 0));
    dto.setStatus("ON_TIME");

    // Act
    AttendanceDTO savedAttendance = attendanceService.saveAttendance(dto);

    // Assert
    assertNotNull(savedAttendance);
    assertEquals(dto.getEmployeeId(), savedAttendance.getEmployeeId());
    assertEquals(BigDecimal.valueOf(1.0), savedAttendance.getOvertimeHours());
  }*/

  /*@Autowired
  private JWTUtil jwtUtil;

  @BeforeEach
  void setup() {
    log.info("테스트 초기화 완료.");
  }

  @Test
  void testGenerateAndValidateToken() {
    log.info("테스트 시작: testGenerateAndValidateToken");

    // Given: 테스트용 클레임 데이터
    Map<String, Object> claims = new HashMap<>();
    claims.put("memberId", 12345);
    claims.put("role", "USER");
    log.info("클레임 데이터 준비 완료: {}", claims);

    // When: 토큰 생성
    String token = jwtUtil.generateToken(claims, 10); // 10분 유효 기간
    log.info("JWT 토큰 생성 완료: {}", token);

    // Then: 토큰이 null 또는 비어 있지 않아야 함
    assertNotNull(token);
    assertFalse(token.isEmpty());

    // When: 토큰 검증 및 클레임 추출
    Map<String, Object> extractedClaims = jwtUtil.validateToken(token);
    log.info("JWT 토큰 검증 완료, 추출된 클레임: {}", extractedClaims);

    // Then: 생성한 클레임과 검증된 클레임이 동일해야 함
    assertEquals(12345, extractedClaims.get("memberId"));
    assertEquals("USER", extractedClaims.get("role"));

    log.info("테스트 종료: testGenerateAndValidateToken");
  }

  @Test
  void testTokenExpiration() throws InterruptedException {
    log.info("테스트 시작: testTokenExpiration");

    // Given: 1초 유효 기간의 토큰
    Map<String, Object> claims = new HashMap<>();
    claims.put("memberId", 12345);
    String token = jwtUtil.generateToken(claims, 1 / 60); // 1초
    log.info("1초 유효 기간의 JWT 토큰 생성: {}", token);

    // Wait for the token to expire
    Thread.sleep(1100); // 1.1초 대기
    log.info("1.1초 대기 완료. 토큰 만료 예상.");

    // Then: 만료된 토큰 검증 시 CustomJWTException 발생
    Exception exception = assertThrows(CustomJWTException.class, () -> jwtUtil.validateToken(token));
    log.info("만료된 토큰 검증 결과: {}", exception.getMessage());
    assertEquals("Expired", exception.getMessage());

    log.info("테스트 종료: testTokenExpiration");
  }

  @Test
  void testInvalidToken() {
    log.info("테스트 시작: testInvalidToken");

    // Given: 잘못된 토큰
    String invalidToken = "invalid.jwt.token";
    log.info("잘못된 토큰 준비 완료: {}", invalidToken);

    // Then: 잘못된 토큰 검증 시 CustomJWTException 발생
    Exception exception = assertThrows(CustomJWTException.class, () -> jwtUtil.validateToken(invalidToken));
    log.info("잘못된 토큰 검증 결과: {}", exception.getMessage());
    assertEquals("MalFormed", exception.getMessage());

    log.info("테스트 종료: testInvalidToken");
  }

  @Test
  void testExtractMemberId() {
    log.info("테스트 시작: testExtractMemberId");

    // Given: 테스트용 클레임 데이터
    Map<String, Object> claims = new HashMap<>();
    claims.put("memberId", 12345);
    log.info("클레임 데이터 준비 완료: {}", claims);

    // When: 토큰 생성
    String token = jwtUtil.generateToken(claims, 10);
    log.info("JWT 토큰 생성 완료: {}", token);

    // Then: memberId를 추출할 수 있어야 함
    Integer memberId = jwtUtil.extractMemberId(token);
    log.info("토큰에서 추출된 memberId: {}", memberId);
    assertEquals(12345, memberId);

    log.info("테스트 종료: testExtractMemberId");
  }*/

  @Autowired
  private SalaryService salaryService;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private InvoiceItemRepository invoiceItemRepository;

  @Autowired
  private JournalEntryRepository journalEntryRepository;

  @Test
  @Transactional
  @Rollback(false)
  public void testCreateSalaryAndVerifyRelatedEntities() {
    System.out.println("========== 급여 생성 및 연관 엔터티 검증 테스트 시작 ==========");

    // 1. 테스트 데이터 준비
    System.out.println("[1] 테스트 데이터 준비 시작");
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setId(12345678);
    employeeDTO.setName("John Doe");
    employeeDTO.setBirthDate(LocalDate.of(1990, 1, 1));
    employeeDTO.setGender(true);
    employeeDTO.setPhone("010-1234-5678");
    employeeDTO.setEmail("johndoe@example.com");
    employeeDTO.setAddress("Seoul, Korea");
    employeeDTO.setHireDate(LocalDate.of(2023, 1, 1));
    employeeDTO.setDepartmentId(1); // 유효한 부서 ID
    employeeDTO.setPositionId(1); // 유효한 직위 ID

    Employee savedEmployee = employeeService.saveEmployee(employeeDTO);

    SalaryDTO salaryDTO = new SalaryDTO();
    salaryDTO.setEmployeeId(savedEmployee.getId());
    salaryDTO.setBaseSalary(new BigDecimal("5000000"));
    salaryDTO.setBonus(new BigDecimal("500000"));
    salaryDTO.setDeductions(new BigDecimal("200000"));
    System.out.println("[1] 테스트 데이터 준비 완료");

    // 2. 급여 생성 호출
    System.out.println("[2] createSalary 메서드 호출");
    SalaryDTO resultSalaryDTO = salaryService.createSalary(salaryDTO);

    // 3. Salary 검증
    System.out.println("[3] Salary 저장 검증");
    assertNotNull(resultSalaryDTO, "생성된 SalaryDTO가 null이면 안 됩니다.");
    assertEquals(PaymentStatus.PAID.name(), resultSalaryDTO.getStatus());
    assertEquals(0, new BigDecimal("5300000").compareTo(resultSalaryDTO.getTotalSalary()));
    System.out.println("-> Salary 저장 성공: " + resultSalaryDTO);

    // 4. Transaction 검증
    System.out.println("[4] Transaction 저장 검증");
    List<Transaction> transactions = transactionRepository.findByType(TransactionType.SALARY);
    assertFalse(transactions.isEmpty(), "SALARY 타입의 Transaction이 저장되지 않았습니다.");
    Transaction transaction = transactions.get(0);
    assertEquals(0, new BigDecimal("5300000.00").compareTo(transaction.getAmount()));
    assertEquals(TransactionType.SALARY, transaction.getType());
    assertNotNull(transaction.getCreatedAt(), "Transaction의 생성일자가 null입니다.");
    System.out.println("-> Transaction 저장 성공: " + transaction);

    // 5. Invoice 검증
    System.out.println("[5] Invoice 저장 검증");
    Optional<Invoice> optionalInvoice = invoiceRepository.findByTransactionId(transaction.getId());
    assertTrue(optionalInvoice.isPresent(), "Invoice가 저장되지 않았습니다.");
    Invoice invoice = optionalInvoice.get();
    assertEquals(0, new BigDecimal("5300000.00").compareTo(invoice.getTotalAmount()));
    assertEquals("Company", invoice.getBuyer());
    assertEquals("John Doe", invoice.getSeller());
    System.out.println("-> Invoice 저장 성공: " + invoice);

    // InvoiceItem 저장 검증
    System.out.println("[6] InvoiceItem 저장 검증");
    List<InvoiceItem> invoiceItems = invoiceItemRepository.findAllByInvoiceId(invoice.getId());
    assertEquals(3, invoiceItems.size(), "InvoiceItem 개수가 잘못되었습니다.");

    for (InvoiceItem item : invoiceItems) {
      System.out.println("-> 저장된 InvoiceItem: " + item);
      assertNotNull(item.getItemName(), "InvoiceItem의 itemName이 null입니다.");
      assertTrue(item.getQuantity() > 0, "InvoiceItem의 수량이 0보다 작습니다.");

      // Deductions는 음수일 수 있음
      if (item.getItemName().equals("공제")) {
        assertTrue(item.getTotalPrice().compareTo(BigDecimal.ZERO) < 0, "Deductions의 totalPrice는 음수여야 합니다.");
      } else {
        assertTrue(item.getTotalPrice().compareTo(BigDecimal.ZERO) > 0, "InvoiceItem의 totalPrice는 0보다 커야 합니다.");
      }
    }

    // 7. JournalEntry 검증
    System.out.println("[7] JournalEntry 저장 검증");
    List<JournalEntry> journalEntries = journalEntryRepository.findByTransactionId(transaction.getId());
    assertEquals(2, journalEntries.size(), "JournalEntry 개수가 잘못되었습니다.");

    JournalEntry debitEntry = journalEntries.stream()
        .filter(e -> e.getDebit().compareTo(BigDecimal.ZERO) > 0)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("차변 Entry가 없습니다."));
    assertEquals(0, new BigDecimal("5300000").compareTo(debitEntry.getDebit()));
    System.out.println("-> Debit JournalEntry 저장 성공: " + debitEntry);

    JournalEntry creditEntry = journalEntries.stream()
        .filter(e -> e.getCredit().compareTo(BigDecimal.ZERO) > 0)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("대변 Entry가 없습니다."));
    assertEquals(0, new BigDecimal("5300000").compareTo(creditEntry.getCredit()));
    System.out.println("-> Credit JournalEntry 저장 성공: " + creditEntry);

    // 8. 최종 로그
    System.out.println("========== 급여 생성 및 연관 엔터티 검증 테스트 종료 ==========");  }

}
