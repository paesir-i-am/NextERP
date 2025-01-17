package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : ReportFileGenerator
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:27  paesir      최초 생성
 */

import com.nexterp.accounting.entity.ReportType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ReportFileGenerator {

  // 기본 파일 디렉토리
  @Value("${file.base-dir}")
  private String baseDir;

  // 보고서 디렉토리
  @Value("${file.report-dir}")
  private String reportDir;

  public String generateReportFile(ReportType reportType, List<String[]> content) throws IOException {
    // 디렉토리 확인 및 생성
    File reportDirFile = new File(reportDir);
    if (!reportDirFile.exists() && !reportDirFile.mkdirs()) {
      throw new IOException("Report 디렉토리 생성에 실패했습니다.");
    }

    // 고유 파일 이름 생성
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    String fileName = reportType.getFileNamePrefix() + "_" + timestamp + "_" + UUID.randomUUID() + ".csv";

    // 파일 저장 경로
    Path filePath = Paths.get(reportDir, fileName);

    // CSV 파일 생성
    try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
      for (String[] row : content) {
        // CSV 형식으로 작성 (쉼표로 구분)
        writer.write(String.join(",", row));
        writer.newLine();
      }
    }

    System.out.println("CSV Report 파일이 생성되었습니다: " + filePath.toAbsolutePath());

    // 저장된 파일 경로 반환
    return filePath.toAbsolutePath().toString();
  }
}