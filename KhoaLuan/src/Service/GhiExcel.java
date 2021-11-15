package Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import enity.HoaDon;

public class GhiExcel {
	
	private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
	}
	public static void main(String[] args) throws IOException {

		HoaDonDAO hoadonDao = new HoaDonDAO();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Hoa Don sheet");

        List<HoaDon> list = hoadonDao.GetAllHoaDon();

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

       
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("ID");
        cell.setCellStyle(style);
       
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tổng tiền");
        cell.setCellStyle(style);
        
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Tên nhân viên");
        cell.setCellStyle(style);
      
        

        // Data
        for (HoaDon hd : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(hd.getId());
       
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(hd.getNgayTao());
   
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(hd.getTongTien());
         
            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(hd.getPhieukhambenh().getNhanvien().getTen());
           
        }
        File file = new File("E:/KhoaLuan/HoaDon.xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());

    }

}

