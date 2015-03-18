package ConversExcelToLDIF;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by den on 13.08.2014.
 */
public class ParseDocumentExcel {
    /**
     * метод получает нужный лист из файла
     * @param absolutePath абсолютный путь к файлу
     * @param numberSheet  номер листа в EXCEL
     * @return  возвращает документ
     * обрабатывает исключение - когда указан другой формат файла
     */

       protected static Sheet getDocExel(String absolutePath, int numberSheet) {
           Sheet sheet = null;
           try {
               Workbook documentExcel = WorkbookFactory.create(new File(absolutePath));
               sheet = documentExcel.getSheetAt(numberSheet);

           } catch (IOException e) {
               e.printStackTrace();
               MessageWindows.msbox("Указанный файл не EXCEL.");
               LogerError.log.info("Указанный файл не EXCEL.");
           } catch (InvalidFormatException e) {
               e.printStackTrace();
               MessageWindows.msbox("ОШИБКА");
               LogerError.log.info(e);
           }
           return sheet;
       }

    /**
     * получает строку из EXEL с заданным номером в виде объекта Row
     * @param sheet документ
     * @param numberRow  номер нужной строки
     * @return  возвращает строку из файла
     */
       protected static Row getRow(Sheet sheet, int numberRow){
           Row row = sheet.getRow(numberRow);
           return row;
       }

    /**
     * получает ячеку из строки
     * @param row  объект Row (строка из файла)
     * @param numberCell  номер ячейки
     * @return возвращает объект типа Cell (ячейка)
     */
       protected static Cell GetCell(Row row, int numberCell){
           //int numberOfCell = row.getLastCellNum();
           Cell cell = row.getCell(numberCell);
           return cell;
       }

    /**
     * возвращает номер последней строки
     * @param sheet документ
     * @return номер последней строки
     */
       protected static int largestNumberOfRow(Sheet sheet){
           int maxNumberRow = sheet.getPhysicalNumberOfRows();
           System.out.println(maxNumberRow + "  строки");
        return maxNumberRow;
       }


    /**
     * возвращает номер последней колонки
      * @param row  объект типа Row (вся строка)
     * @return номер последней колонки
     */
        protected static int largestNumberOfCell2(Row row){
           int maxNumberCell = row.getPhysicalNumberOfCells();
            System.out.println(maxNumberCell +  "  столбцы");
           return maxNumberCell;
       }
    protected static int largestNumberOfCell(Sheet sheet){
        int maxNumberCell = 0;
        for (int f =0; f<largestNumberOfRow(sheet);f++){
            int n = sheet.getRow(f).getLastCellNum();
            if (n>maxNumberCell){maxNumberCell=n;}
        }


        System.out.println(maxNumberCell +  "  столбцы");
        return maxNumberCell;
    }


    /**
     * поиск заданных значений в ячейках первой строки
     * @param sheet  объект Sheet
     * @param xValue  значение для поиска
     * @return  если найдено значение, вернёт номер колонки, если нет вернёт -1
     * если одно значение встречается больше одного раза вернёт -2
     */
    protected static int searchCellByName(Sheet sheet, String xValue) {
        int secondValue = 0;
        int iteratorByCell;
        int iteratorReturn = -1;
        for (iteratorByCell = 0; iteratorByCell <  sheet.getRow(0).getLastCellNum(); iteratorByCell++) {
            if (ParseDocumentExcel.parseCell(0,iteratorByCell,sheet).trim().toLowerCase().contains(xValue)) {
                iteratorReturn=iteratorByCell;++secondValue;
            }
        }
        if (secondValue > 1){iteratorReturn = -2;}
        return iteratorReturn;
    }

    /**
     *  получение типа ячейки, преобразование в String если текст или номер, остальные типы к пусто
     * @param iteratorRow   итерация по строкам
     * @param numberCell   номер ячейки
     * @param sheet  лист экселя
     * @return  возвращаемое значение ячейки
     */

    protected static String parseCell(int iteratorRow, int numberCell, Sheet sheet){
        String valueString = null;
        if (numberCell > -1 && sheet.getRow(iteratorRow).getCell(numberCell)!=null){
            switch (sheet.getRow(iteratorRow).getCell(numberCell).getCellType()){
                case 0: valueString = String.valueOf((int)sheet.getRow(iteratorRow).getCell(numberCell).getNumericCellValue()).trim();break;
                case 1: valueString = sheet.getRow(iteratorRow).getCell(numberCell).getStringCellValue().trim();break;
                case 2: valueString = "";break;
                case 3: valueString = "";break;
                case 4: valueString = "";break;
                case 5: valueString = "";break;
            }} else {valueString="";}
        return valueString;
    }

    /**
     *  создаёт аррей лист, состоящий из аррей листов, в которые записаны ячейки последовательно по строкам
     * @return  возвращает аррей лист аррей листов
     */
    protected static ArrayList<ArrayList<String>> getExcelAllRowAndCell(){
        final ArrayList<ArrayList<String>> allCellByRow = new ArrayList<ArrayList<String>>();
        final Sheet sheet = ParseDocumentExcel.getDocExel(AllWindows.getFileAbsolutePath(),AllWindows.getNumberSheetValue()-1);
        final int quantityRow = ParseDocumentExcel.largestNumberOfRow(sheet);       //колличество строк в Excel
        final int quantityCell = ParseDocumentExcel.largestNumberOfCell(sheet);   //наибольшее количество ячеек, из всех строк
        for (int iteratorRow = 0; iteratorRow < quantityRow;iteratorRow++){    //итерация по строкам
           final ArrayList<String> rowRecord = new ArrayList<String>();
            for (int iteratorCell = 0 ;iteratorCell < quantityCell;iteratorCell++){  //итерация по ячейкам
                rowRecord.add(ParseDocumentExcel.parseCell(iteratorRow,iteratorCell,sheet));  //каждая строка в ArrayList<String>
            }
            allCellByRow.add(rowRecord); //каждый ArrayList<String> со строкой, в ArrayList<String> ArrayList - ов

        }
        return allCellByRow;
    }

}
