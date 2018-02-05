package com.javastaff.html2pdf;

import java.io.ByteArrayInputStream;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import org.apache.fop.apps.FOPException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ApacheFOPTest {

    public static void main(String a[]) throws Exception {
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/simple.html","simple-fop.pdf");
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/standard.html","standard-fop.pdf");
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/css.html","css-fop.pdf");
    }

    private static void pdfTest(String url, String fileOut) 
            throws FileNotFoundException, FOPException, 
                   IOException, TransformerConfigurationException, 
                   TransformerException {
        String xsltfile = "https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/html2fo.xsl";
        File pdffile = new File(fileOut);

        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new java.io.FileOutputStream(pdffile);
        out = new java.io.BufferedOutputStream(out);

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();

            Document d = Jsoup.parse(new URL(url),5000);
            d.outputSettings().syntax(Document.OutputSettings.Syntax.xml);   
            Transformer transformer = factory.newTransformer(new StreamSource(new URL(xsltfile).openStream()));
            Source src = new StreamSource(new ByteArrayInputStream(d.outerHtml().getBytes()));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } finally {
            out.close();
        }

        System.out.println(fileOut+" created");
        
    }
}
