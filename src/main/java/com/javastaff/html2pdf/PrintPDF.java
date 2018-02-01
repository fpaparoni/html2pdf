package com.javastaff.html2pdf;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.simple.XHTMLPrintable;

public class PrintPDF {
	public static void main(String a[]) throws Exception {
		
		Document d = Jsoup.parse(new File("/home/federico/template.html"),"UTF-8");
		d.outputSettings().syntax(Document.OutputSettings.Syntax.xml);   
		
		System.out.println(d.outerHtml());
		ByteArrayInputStream bais=new ByteArrayInputStream(d.outerHtml().getBytes());
		
		XHTMLPanel panel=new XHTMLPanel();
		panel.setDocument(bais,"");
		
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(new XHTMLPrintable(panel));
		if(printJob.printDialog()) {
		  printJob.print();
		}
	}
}
