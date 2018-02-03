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
public class FlyingSaucer {
    public static void main(String a[]) throws IOException, FileNotFoundException, DocumentException {
        simpleTest();
        standardTest();
        imageCSSTest();
    }

    private static void simpleTest() throws FileNotFoundException, DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();

        // if you have html source in hand, use it to generate document object
        renderer.setDocument("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/simple.html");
        renderer.layout();

        String fileNameWithPath = "simple.pdf";
        FileOutputStream fos = new FileOutputStream( fileNameWithPath );
        renderer.createPDF( fos );
        fos.close();

        System.out.println( "Simple PDF created." );
    }

    private static void standardTest() throws MalformedURLException, IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();

        Document d = Jsoup.parse(new URL("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/standard.html"),5000);
        d.outputSettings().syntax(Document.OutputSettings.Syntax.xml);   
		
        renderer.setDocumentFromString(d.outerHtml());
        renderer.layout();

        String fileNameWithPath = "standard.pdf";
        FileOutputStream fos = new FileOutputStream( fileNameWithPath );
        renderer.createPDF( fos );
        fos.close();

        System.out.println("Standard PDF created." );
    }

    private static void imageCSSTest() throws MalformedURLException, IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();

        Document d = Jsoup.parse(new URL("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/css.html"),5000);
        d.outputSettings().syntax(Document.OutputSettings.Syntax.xml);   

        System.out.println(d.outerHtml());

        renderer.setDocumentFromString(d.outerHtml(),"https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/");
        renderer.layout();

        String fileNameWithPath = "imageCSS.pdf";
        FileOutputStream fos = new FileOutputStream( fileNameWithPath );
        renderer.createPDF( fos );
        fos.close();

        System.out.println( "File created." );
    }
}
