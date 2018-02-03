package com.javastaff.html2pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Test HTML 2 PDf using iText
 */
public class IText {
    public static void main(String a[]) throws IOException, FileNotFoundException, DocumentException, Exception {
        simpleTest();
        //standardTest();
        //cssTest();
    }

    private static void simpleTest() throws DocumentException, FileNotFoundException, IOException, Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("simple.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(getText("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/simple.html").getBytes()));
        document.close();
    }
    
    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine);

        in.close();

        return response.toString();
    }

}
