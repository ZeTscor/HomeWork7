package ru.zets;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Condition;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;



public class FileTest {

    @Test
        //Загрузка файла
    void uploadSpanAndLinkDisplayeadAfterUploadTest() {
        open("http://file.karelia.ru/");
        $("input[id='file_0']").uploadFromClasspath("cat.jpg");
        $("button[name='file_submit']").click();
        $("input[id='downloadUrl']").shouldBe(Condition.visible);
    }

    @Test
        //Скачивание файла
    void downloadTextFileTest() throws IOException {
        open("https://www.mediafire.com/file/dorkl2bczkmdfyk/cpuz_readme.txt/file");
        File download = $("a[class= 'input popsok']").download();
        String fileParser = IOUtils.toString(new FileReader(download));
        Assertions.assertTrue((fileParser.contains("CPU-Z Readme file")));
    }

    @Test
        //Скачивание pdf
    void downloadPdfFileTest() throws IOException {
        open("https://liveinmsk.ru/down/hitrovka");
        File pdf = $("a[class='sub load']").download();
        PDF parserPdf = new PDF(pdf);
        Assertions.assertEquals(14, parserPdf.numberOfPages);


    }

    @Test
    void testXlsFileDownLoad() throws IOException {
        String xls_string = "   Московская обл., Раменский район,  д.  Островцы,";
        open("https://ckmt.ru/price-download.html");
        File xls1 = $(byLinkText("Скачать")).download();
        XLS xlsParser = new XLS(xls1);
        boolean check = xlsParser.excel
                .getSheetAt(0)
                .getRow(1)
                .getCell(3)
                .getStringCellValue()
                .contains(xls_string);
        Assertions.assertTrue(check);
    }

}
