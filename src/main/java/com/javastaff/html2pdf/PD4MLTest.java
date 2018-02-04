package com.javastaff.html2pdf;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

/**
 * Test HTML 2 PDf using PD4ML
 */
public class PD4MLTest {
     protected static Dimension format = PD4Constants.A4;
     
    public static void main(String a[]) throws FileNotFoundException, IOException {
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/simple.html","simple-pd4ml.pdf");
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/standard.html","standard-pd4ml.pdf");
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/css.html","imageCSS-pd4ml.pdf");
    }
    
    private static void pdfTest(String url,String fileOut) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileOut);
        PD4ML pd4ml = new PD4ML();
        pd4ml.setPageSize(format);
        pd4ml.render(url, fos);
        fos.close();
        System.out.println( fileOut+" created." );
    }
}
