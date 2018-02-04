package com.javastaff.html2pdf;

import com.lowagie.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Test HTML 2 PDf using FlyingSaucer
 */
public class FlyingSaucerTest {
    public static void main(String a[]) throws IOException, FileNotFoundException, DocumentException, Exception {
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/simple.html","simple-flyingsaucer.pdf");
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/standard.html","standard-flyingsaucer.pdf");
        imageCSSPDFTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/css.html",
                        "https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/",
                        "image-css-flyingsaucer.pdf");
    }

    private static void pdfTest(String url,String fileOut) throws FileNotFoundException, DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();

        renderer.setDocument(url);
        renderer.layout();

        FileOutputStream fos = new FileOutputStream( fileOut );
        renderer.createPDF( fos );
        fos.close();

        System.out.println( fileOut+" created." );
    }

    private static void imageCSSPDFTest(String url,String baseUrl,String fileOut) throws MalformedURLException, IOException, DocumentException, Exception {
        ITextRenderer renderer = new ITextRenderer();
        Document d = Jsoup.parse(new URL(url),5000);
        d.outputSettings().syntax(Document.OutputSettings.Syntax.xml);   
        
        renderer.setDocumentFromString(d.outerHtml(),baseUrl);
        renderer.layout();

        FileOutputStream fos = new FileOutputStream( fileOut );
        renderer.createPDF( fos );
        fos.close();

        System.out.println( fileOut+" created." );
    }
}
