package libs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

    private String inputFile;

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public ArrayList<String> read() throws IOException  {
        ArrayList<String> result = new ArrayList<String>();
      
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            
            // Get the first sheet
            Sheet sheet = w.getSheet(0);

            // Loop over first 10 column and lines
            for (int i = 1; i < sheet.getRows(); i++) {
                String[] tmp = new String[sheet.getColumns()];
                for (int j = 0; j < sheet.getColumns(); j++) {
                    Cell cell = sheet.getCell(j, i);
//                    CellType type = cell.getType();
//                    if (type == CellType.LABEL) {
//                        System.out.println("I got a label " + cell.getContents());
//                    }
//
//                    if (type == CellType.NUMBER) {
//                        System.out.println("I got a number " + cell.getContents());
//                    }
                    tmp[j] = cell.getContents();
                }
                
                //String cat_index = tmp[1].contains("Individual")?"INDIVIDUAL":(tmp[1].contains("Pair")?"PAIR":"GROUP");
                System.out.println(tmp[0]);
                result.add(tmp[0]);
            }
        } catch (BiffException e) {
            //e.printStackTrace();
        }
        
        return result;
    }

//    public static void main(String[] args) throws IOException {
//        ReadExcel test = new ReadExcel();
//        test.setInputFile("c:/temp/lars.xls");
//        test.read();
//    }

} 