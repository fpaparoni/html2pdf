package com.javastaff.html2pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;

/**
 * Test HTML 2 PDf using iText
 */
public class ITextTest {
    public static void main(String a[]) throws IOException, FileNotFoundException, DocumentException, Exception {
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/simple.html","simple-itext.pdf");
        pdfTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/standard.html","standard-itext.pdf");
        imageCSSPDFTest("https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/css.html",
                        "https://raw.githubusercontent.com/fpaparoni/html2pdf/master/html/",
                        "image-css-itext.pdf");
    }

    private static void pdfTest(String url,String fileOut) throws DocumentException, FileNotFoundException, IOException, Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileOut));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(StringUtil.getText(url).getBytes()));
        document.close();
    }

    private static void imageCSSPDFTest(String url,final String baseImageUrl,String fileOut) throws FileNotFoundException, DocumentException, IOException, Exception{
        org.jsoup.nodes.Document d = Jsoup.parse(new URL(url),5000);
        d.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);   
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileOut));
        document.open();
        // CSS
        CSSResolver cssResolver =
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
 
        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new CustomImageProvider(baseImageUrl));
        
        
        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
 
        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(d.outerHtml().getBytes()));
        document.close();
    }

}

 class CustomImageProvider extends AbstractImageProvider {
     
     private String baseUrl;
     
     public CustomImageProvider(String baseUrl) {
         this.baseUrl=baseUrl;
     }
 
        @Override
        public Image retrieve(String src) {
            System.out.println("PASSO DI QUI "+src);
            int pos = src.indexOf("base64,");
            try {
                if (src.startsWith("data") && pos > 0) {
                    byte[] img = Base64.decode(src.substring(pos + 7));
                    return Image.getInstance(img);
                }
                else {
                    if (!src.startsWith("http")) 
                        src=baseUrl.concat(src);
                    return Image.getInstance(new URL(src));
                }
            } catch (BadElementException ex) {
                return null;
            } catch (IOException ex) {
                return null;
            }
        }
 
        @Override
        public String getImageRootPath() {
            System.out.println("PASSO DI QUA ");
            return baseUrl;
        }
    }
