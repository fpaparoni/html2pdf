package com.javastaff.html2pdf;

import java.io.File;
import java.io.FileOutputStream;

import org.xhtmlrenderer.pdf.ITextRenderer;

public class HelloPDF {
	public static void main(String a[]) throws Exception {
		ITextRenderer renderer = new ITextRenderer();

        // if you have html source in hand, use it to generate document object
        renderer.setDocument(new File("/home/federico/test.html") );
        renderer.layout();

        String fileNameWithPath = "/home/federico/test.pdf";
        FileOutputStream fos = new FileOutputStream( fileNameWithPath );
        renderer.createPDF( fos );
        fos.close();

        System.out.println( "File created." );
	}
}
