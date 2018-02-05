package com.javastaff.html2pdf;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class ApacheFOPTest {

    public static void main(String a[]) throws URISyntaxException, FileNotFoundException {
 
        try {

            // Setup input and output files
            String xmlfile = "https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/test.xml";
            String xsltfile = "https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/test2fo.xsl";
            File pdffile = new File("test-apachefop.pdf");

            // configure fopFactory as desired
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            OutputStream out = new java.io.FileOutputStream(pdffile);
            out = new java.io.BufferedOutputStream(out);

            try {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(new URL(xsltfile).openStream()));
                Source src = new StreamSource(new URL(xmlfile).openStream());
                Result res = new SAXResult(fop.getDefaultHandler());
                transformer.transform(src, res);
            } finally {
                out.close();
            }

            System.out.println("ApacheFOP test created");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
}
