package com.springCommunity.service;

import com.springCommunity.vo.UserInfoVO;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

//ExcelUserService.java
@Service
public class ExcelUserService {

	 private static final Map<String, Integer> DEPARTMENT_MAP = new HashMap<>();
	 private static final Map<String, Integer> POSITION_MAP = new HashMap<>();
	 private static final Map<String, Integer> BANK_MAP = new HashMap<>();
	
	 static {
	     DEPARTMENT_MAP.put("경영지원팀", 1);
		 DEPARTMENT_MAP.put("인사팀", 2);
		 DEPARTMENT_MAP.put("마케팅팀", 3);
		 DEPARTMENT_MAP.put("기획팀", 4);
		 DEPARTMENT_MAP.put("개발팀", 5);
		 DEPARTMENT_MAP.put("디자인팀", 6);
		 DEPARTMENT_MAP.put("고객지원팀", 7);
		 DEPARTMENT_MAP.put("회계팀", 8);
		
		 POSITION_MAP.put("사원", 1);
		 POSITION_MAP.put("주임", 2);
		 POSITION_MAP.put("대리", 3);
		 POSITION_MAP.put("과장", 4);
		 POSITION_MAP.put("팀장", 5);
		 POSITION_MAP.put("부장", 6);
		 POSITION_MAP.put("이사", 7);
		 POSITION_MAP.put("대표이사", 8);
		
		 BANK_MAP.put("국민은행", 1);
		 BANK_MAP.put("농협은행", 2);
		 BANK_MAP.put("신한은행", 3);
		 BANK_MAP.put("카카오뱅크", 4);
		 BANK_MAP.put("우리은행", 5);
		 BANK_MAP.put("하나은행", 6);
		 BANK_MAP.put("전북은행", 7);
		 BANK_MAP.put("토스뱅크", 8);
		 BANK_MAP.put("기업은행", 9);
		 BANK_MAP.put("수협은행", 10);
		 BANK_MAP.put("부산은행", 11);
		 BANK_MAP.put("새마을금고", 12);
		 BANK_MAP.put("광주은행", 13);
	 }
	
	 public List<UserInfoVO> readUsersFromExcel(InputStream inputStream) throws Exception {
	     List<UserInfoVO> users = new ArrayList<>();
	     System.out.println("test1");
	     try (Workbook workbook = new XSSFWorkbook(inputStream)) {
	         Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트 사용
	         System.out.println("test2");
		     for(Row row : sheet) {
		         if (row.getRowNum() == 0) continue; // 헤더 행 건너뜀
		         
		         UserInfoVO user = new UserInfoVO();
		         
		         
		         user.setUser_id("jj" + getCellValue(row, 0));
		         System.out.println(user.getUser_id());
		         String random_PASSWORD = generateRandom(6);
		         user.setUser_password(random_PASSWORD); // 비밀번호는 암호화 필요
		         user.setUser_name(getCellValue(row, 1));
		         user.setUser_email(getCellValue(row, 2));
		         user.setUser_phone(getCellValue(row, 3));
		
		         String department = getCellValue(row, 4);
		         user.setDepartment_id(DEPARTMENT_MAP.get(department));
		
		         String position = getCellValue(row, 5);
		         user.setJob_position_id(POSITION_MAP.get(position));
		
		         user.setUser_join_date(getCellValue(row, 6));
		
		         String bank = getCellValue(row, 7);
		         System.out.println(bank);
		         user.setBank_id(BANK_MAP.get(bank));
		
		         user.setUser_account(getCellValue(row, 8));
		         user.setUser_address(getCellValue(row, 9));
		
	             users.add(user);
	         }
	     }catch(Exception e) {
	         e.printStackTrace();
	     }
	     return users;
	 }
	
	 private String getCellValue(Row row, int cellIndex) {
	     String value = "";
	     if (row.getCell(cellIndex) != null) {
	         value = row.getCell(cellIndex).toString().trim();
	     }
	     return value;
	 }
	 
	 //랜덤 비밀번호 6글자 생성
	 public String generateRandom(int length) {
        String datas = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int rand = secureRandom.nextInt(datas.length());
            code.append(datas.charAt(rand));
        }

        return code.toString();
    }
}