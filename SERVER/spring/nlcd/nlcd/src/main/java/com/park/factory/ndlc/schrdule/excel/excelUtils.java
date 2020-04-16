package com.park.factory.ndlc.schrdule.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.park.factory.ndlc.db.entitys.citys;
import com.park.factory.ndlc.db.entitys.courses;
import com.park.factory.ndlc.db.mappers.coursesMapper;

public class excelUtils {
	private static final Logger log = LoggerFactory.getLogger(excelUtils.class);

	public ArrayList<courses> courses = new ArrayList<>();
	public HashMap<String, citys> mCitys = new HashMap<>();
	public HashMap<String, citys> mCitySubs = new HashMap<>();

	public void CreateWorkBook(String ext, File mFile) {
		try {
			HSSFWorkbook workbook = getWorkBook(ext, mFile);

			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (int a = 1; a < rows; a++) {
				String Cell = "";
				Row row = sheet.getRow(a);
				int cells = row.getPhysicalNumberOfCells();
				/*
				 * for (int b = 0; b < cells; b++) { _Courses.setCoursename( getValue(row, a,
				 * 0));
				 * 
				 * Cell += getValue(row, a, b) + "_"; } log.info(Cell);
				 */ 
				courses _Courses = new courses();
				_Courses.setCoursename(getValue(row, a, 0));
				_Courses.setCourseteachername(getValue(row, a, 1));
				_Courses.setTrainingstartdate(getValue(row, a, 2));
				_Courses.setTrainingEnddate(getValue(row, a, 3));
				_Courses.setTrainingstarttime(getValue(row, a, 4));
				_Courses.setTrainingendtime(getValue(row, a, 5));
				_Courses.setContent(getValue(row, a, 6));
				_Courses.setEdutarget(getValue(row, a, 7));
				_Courses.setEdumthod(getValue(row, a, 8));
				_Courses.setOperatingday(getValue(row, a, 9));
				_Courses.setTrainingplace(getValue(row, a, 10));
				_Courses.setMaxstudents(getValue(row, a, 11));
				_Courses.setFee(getValue(row, a, 12));
				
				
				String addr = getValue(row, a, 13);
				addr = addr.replace("\"", "");

				String[] addrs = addr.trim().split(" ");
				if(addrs.length == 1) {
					if (!mCitys.containsKey(addrs[0].trim())) {
						mCitys.put(addrs[0].trim(), new citys(addrs[0], mCitys.size()));
						// 없다면 넣는다 .
					}
				}else {
					// 서울시
					if (!mCitys.containsKey(addrs[0].trim())) {
						mCitys.put(addrs[0].trim(), new citys(addrs[0], mCitys.size()));
						// 없다면 넣는다 .
					}
					
					// 서초구
					citys si = mCitys.get(addrs[0]);
					try {
						if(!mCitySubs.containsKey(addrs[1].trim())) {
							mCitySubs.put(addrs[1].trim(), new citys(addrs[1], mCitySubs.size(), si.cityid ));
						}					
					} catch (Exception e) {
						log.info(addr);
						// TODO: handle exception
						break;
					}
				}
				
				

				_Courses.setRoaaddress(addr);
				_Courses.setOpername(getValue(row, a, 14));
				_Courses.setOpernumber(getValue(row, a, 15));
				_Courses.setReceptionstart(getValue(row, a, 16));
				_Courses.setReceptionend(getValue(row, a, 17));
				_Courses.setReceptionmethod(getValue(row, a, 18));
				_Courses.setReceptionselection(getValue(row, a, 19));
				_Courses.setHomepage(getValue(row, a, 20));
				_Courses.setDevelopmenttraining(getValue(row, a, 21));
				_Courses.setBankingevaluation(getValue(row, a, 22));
				_Courses.setAccountevaluation(getValue(row, a, 23));
				_Courses.setDatabasedate(getValue(row, a, 24));
				_Courses.setProviderpode(getValue(row, a, 25));
				_Courses.setProvidername(getValue(row, a, 26));
				courses.add(_Courses);
				log.info(String.format("%s ,%s ,%s ,%s ,%s , %s , %s", _Courses.getCoursename(),
						_Courses.getEdutarget(), _Courses.getEdumthod(), _Courses.getFee(), _Courses.getOpername(),
						_Courses.isDevelopmenttraining(), _Courses.isBankingevaluation(),
						_Courses.getRoaaddress()));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private HSSFWorkbook getWorkBook(String ext, File mFile) throws IOException {
		if (ext.equals("xls")) {

			FileInputStream fileInputStream = new FileInputStream(mFile);
			return new HSSFWorkbook(fileInputStream);

		} else {
			throw new NoClassDefFoundError();
		}
	}

	private String getValue(Row row, int rowindex, int Cellindex) {

		String value = "";
		if (row != null) {
			Cell cell = row.getCell(Cellindex);

			switch (cell.getCellType()) {
			case FORMULA:
				value = cell.getCellFormula();
				break;
			case NUMERIC: {
				value = cell.getNumericCellValue() + "";
			}
				break;
			case STRING: {
				value = new String(cell.getStringCellValue().getBytes(Charset.forName("UTF-8")));
				;
			}
				break;
			case BLANK: {
				value = cell.getBooleanCellValue() + "";

			}
				break;
			case ERROR: {

				value = "ERROR";
			}
				break;
			default:
				break;
			}

		}
		return value;
	}



}
