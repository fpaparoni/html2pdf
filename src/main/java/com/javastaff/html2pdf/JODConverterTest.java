package com.javastaff.html2pdf;

import java.io.File;
import java.io.IOException;
import org.jodconverter.DocumentConverter;
import org.jodconverter.OnlineConverter;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.jodconverter.office.OnlineOfficeManager;

public class JODConverterTest {

    public static void main(String a[]) throws IOException, OfficeException {
        File inputFile = new File("document.doc");
        File outputFile = File.createTempFile("test", ".pdf");
        OfficeManager officeManager;
         OnlineOfficeManager manager = OnlineOfficeManager.make("localhost");
         manager.start();
         OnlineConverter.make(manager).convert(inputFile).to(outputFile).execute();
    }
}
