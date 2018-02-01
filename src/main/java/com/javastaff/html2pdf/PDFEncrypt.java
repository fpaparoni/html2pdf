package com.javastaff.html2pdf;

import java.io.File;
import java.io.FileOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

public class PDFEncrypt {
	public static void main(String a[]) throws Exception{
		ITextRenderer renderer = new ITextRenderer();
		
		Document d = Jsoup.parse(new File("/home/federico/template.html"),"UTF-8");
		d.outputSettings().syntax(Document.OutputSettings.Syntax.xml);   
		
		System.out.println(d.outerHtml());
		
        renderer.setDocumentFromString(d.outerHtml());
        renderer.layout();
        PDFEncryption enc=new PDFEncryption("passUser".getBytes(), "passOwner".getBytes());
        renderer.setPDFEncryption(enc);
        
        String fileNameWithPath = "/home/federico/template.pdf";
        FileOutputStream fos = new FileOutputStream( fileNameWithPath );
        renderer.createPDF( fos );
        fos.close();

        System.out.println( "File created." );
	}
}