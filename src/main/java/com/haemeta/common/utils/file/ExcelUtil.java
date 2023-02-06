package com.haemeta.common.utils.file;

import com.haemeta.common.enums.ResultStatusCodes;
import com.haemeta.common.exception.HaemetaException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static boolean isXls(String fileName) throws HaemetaException {
        // (?i)忽略大小写
        if (fileName.matches("^.+\\.(?i)(xls)$")) {
            return true;
        } else if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return false;
        } else {
            throw new HaemetaException(ResultStatusCodes.DATA_ERROR);
        }
    }

    public static boolean isExcel(String fileName) throws HaemetaException {
        // (?i)忽略大小写
        if (fileName.matches("^.+\\.(?i)(xls)$") || fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return true;
        } else {
            throw new HaemetaException(ResultStatusCodes.DATA_ERROR);
        }
    }

    public static HSSFWorkbook createExcel(String...sheetsName){
        HSSFWorkbook excel = new HSSFWorkbook();
        for(int i = 0 ; i < sheetsName.length ; i ++){
            excel.createSheet(sheetsName[i]);
        }
        return excel;
    }

    public static HSSFWorkbook createExcel(String sheetName,Integer...widths){
        HSSFWorkbook excel = new HSSFWorkbook();
        HSSFSheet sheet = excel.createSheet(sheetName);
        for(int i = 0 ; i < widths.length ; i ++){
            sheet.setColumnWidth(i,widths[i]);
        }
        return excel;
    }

    public static HSSFWorkbook createExcel(Sheet...sheets){
        HSSFWorkbook excel = new HSSFWorkbook();
        for(Sheet sheetDemo:sheets){
            HSSFSheet sheet = excel.createSheet(sheetDemo.name);
            for(int i = 0 ; i < sheetDemo.widths.size() ; i ++){
                sheet.setColumnWidth(i,sheetDemo.widths.get(i));
            }
        }
        return excel;
    }

    public static class Sheet{
        private String name;
        private List<Integer> widths;

        public Sheet(String name,Integer...widths){
            this.name = name;
            this.widths = new ArrayList<>();
            for(Integer width : widths){
                this.widths.add(width);
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getWidths() {
            return widths;
        }

        public void setWidths(List<Integer> widths) {
            this.widths = widths;
        }
    }

    public static void createRow(HSSFSheet sheet,Integer rowNumber, Cell...cells){
        HSSFRow row = sheet.createRow(rowNumber);

        for(int i = 0; i < cells.length ; i ++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(cells[i].style);
            cell.setCellValue(cells[i].value);
        }
    }

    public static void createRow(HSSFSheet sheet,Integer rowNumber, Cells cells){
        HSSFRow row = sheet.createRow(rowNumber);

        for(int i = 0; i < cells.size() ; i ++){
            if(cells.values(i) != null){
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(cells.style);
                cell.setCellValue(cells.values(i));
            }
        }
    }

    /**
     * 创建单元格样式
     * @param workbook
     * @param xAlign 左右居中
     * @param yAlign 上下居中
     * @param fontSize 字体大小
     * @param fontbold 字体加粗
     * @return
     */
    public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, HorizontalAlignment xAlign, VerticalAlignment yAlign, short fontSize, Boolean fontbold){
        HSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(xAlign);
        style.setVerticalAlignment(yAlign == null? VerticalAlignment.CENTER:yAlign);

        HSSFFont font = workbook.createFont();

        font.setBold(fontbold);
        font.setFontHeightInPoints(fontSize);
        style.setFont(font);

        return style;
    }

    /**
     * 创建单元格样式
     * @param workbook
     * @param xAlign 左右居中
     * @param yAlign 上下居中
     * @param fontSize 字体大小
     * @param fontbold 字体加粗
     * @
     * @return
     */
    public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,HorizontalAlignment xAlign,VerticalAlignment yAlign,short fontSize,Boolean fontbold,Short color){
        HSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(xAlign);
        style.setVerticalAlignment(yAlign == null? VerticalAlignment.CENTER:yAlign);

        HSSFFont font = workbook.createFont();

        font.setBold(fontbold);
        font.setFontHeightInPoints(fontSize);
        font.setColor(color);

        style.setFont(font);

        return style;
    }

    public static HSSFCellStyle createHeadCellStyle(HSSFWorkbook workbook,short fontSize){
        HSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont font = workbook.createFont();

        font.setBold(true);
        font.setFontHeightInPoints(fontSize);
        style.setFont(font);

        return style;
    }



    /**
     * 合并单元格
     * @param sheet
     * @param xStart x轴开始位置(坐标从0开始)
     * @param xEnd x轴结束位置(坐标从0开始)
     * @param yStart y轴开始位置(坐标从0开始)
     * @param yEnd y轴结束位置(坐标从0开始)
     */
    public static void merge(HSSFSheet sheet , Integer xStart,Integer xEnd,Integer yStart,Integer yEnd){
        sheet.addMergedRegion(new CellRangeAddress(yStart,yEnd,xStart,xEnd));
    }

    public static class Cell{
        private String value;
        private HSSFCellStyle style;
        private Integer colspan = 1;
        private Integer rowspan = 1;

        public Cell(String value){
            this.value = value;
        }

        public Cell(String value,HSSFCellStyle style){
            this.value = value;
            this.style = style;
        }

        public String value() {
            return value;
        }

        public void value(String value) {
            this.value = value;
        }

        public HSSFCellStyle style() {
            return style;
        }

        public void style(HSSFCellStyle style) {
            this.style = style;
        }
    }

    public static class Cells{
        private List<String> values;
        private HSSFCellStyle style;

        public Cells(HSSFCellStyle style,String...values){
            this.style = style;
            this.values = new ArrayList();
            for(String value : values){
                this.values.add(value);
            }
        }

        public Integer size(){
            return values == null? 0:values.size();
        }

        public List<String> values() {
            return values == null? values=new ArrayList<>():values;
        }

        public void values(List<String> values) {
            this.values = values;
        }

        public String values(Integer index) {
            return values==null? null:values.get(index);
        }

        public void addValues(String value){
            if(values == null)
                values = new ArrayList<>();
            values.add(value);
        }

        public HSSFCellStyle style() {
            return style;
        }

        public void style(HSSFCellStyle style) {
            this.style = style;
        }
    }


    /**
     * 快速获取excel
     *
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return
     * @throws Exception
     */
//    public static List<JSONObject> getExcelList(String fileName, InputStream inputStream) throws Exception {
//        List<JSONObject> list = new ArrayList<>();
//        try {
//            boolean ret = isXls(fileName);
//            Workbook workbook = null;
//            // 根据后缀创建不同的对象
//            if (ret) {
//                workbook = new HSSFWorkbook(inputStream);
//            } else {
//                workbook = new XSSFWorkbook(inputStream);
//            }
//            Sheet sheet = workbook.getSheetAt(0);
//            // 得到标题行
//            Row titleRow = sheet.getRow(0);
//
//            int lastRowNum = sheet.getLastRowNum();
//            int lastCellNum = titleRow.getLastCellNum();
//
//            for (int i = 1; i <= lastRowNum; i++) {
//                JSONObject target = new JSONObject();
//                Row row = sheet.getRow(i);
//                for (int j = 0; j < lastCellNum; j++) {
//                    // 得到列名
//                    String key = titleRow.getCell(j).getStringCellValue();
//                    Cell cell = row.getCell(j);
//                    if (cell == null)
//                        continue;
//                    cell.setCellType(CellType.STRING);
//
//                    target.put(key, cell.getStringCellValue());
//                }
//                list.add(target);
//            }
//            workbook.close();
//        } catch (Exception exception) {
//            throw exception;
//        }
//
//        return list;
//    }

    /**
     * 仅获取头部
     * @param fileName
     * @param inputStream
     * @return
     * @throws Exception
     */
//    public static List<String> getTitleFromExcel(String fileName, InputStream inputStream) throws Exception {
//        List<String> list = new ArrayList<>();
//        try {
//            boolean ret = isXls(fileName);
//            Workbook workbook = null;
//            // 根据后缀创建不同的对象
//            if (ret) {
//                workbook = new HSSFWorkbook(inputStream);
//            } else {
//                workbook = new XSSFWorkbook(inputStream);
//            }
//            Sheet sheet = workbook.getSheetAt(0);
//            // 得到标题行
//            Row titleRow = sheet.getRow(0);
//
//            int lastCellNum = titleRow.getLastCellNum();
//
//            for (int j = 0; j < lastCellNum; j++) {
//                list.add(titleRow.getCell(j).getStringCellValue());
//            }
//
//            workbook.close();
//        } catch (Exception exception) {
//            throw exception;
//        }
//
//        return list;
//    }




}
