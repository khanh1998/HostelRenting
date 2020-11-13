package org.avengers.capstone.hostelrenting.util;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

/**
 * @author duattt on 9/16/20
 * @created 16/09/2020 - 12:00
 * @project youthhostelapp
 */
public class Utilities {
    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);


    /**
     * Calculate Haversine Distance Algorithm between two places
     * <p>
     * R = earth’s radius (mean radius = 6,371km)
     * Δlat = lat2− lat1
     * Δlong = long2− long1
     * a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
     * c = 2.atan2(√a, √(1−a))
     * d = R.c
     */
    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double latDistance = toRad(lat2 - lat1);
        double lonDistance = toRad(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = Constant.EARTH_RADIUS * c;
        return Math.round(distance * 100.0) / 100.0;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public static float roundFloatNumber(Float number) {
        return (float) (Math.round(number * 100.0) / 100.0);
    }

    public static MultipartFile pathToMultipartFile(String pathFile, String fileName, String originalFileName, String contentType) {
        Path path = Paths.get(pathFile);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
        }
        return new MockMultipartFile(fileName,
                originalFileName, contentType, content);
    }

    public static String parseThymeleafTemplate(String template, Map<String, String> contractInfo) {
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        contractInfo.forEach(context::setVariable);

        String output = templateEngine.process(template, context);
        return output;
    }

    public static byte[] generatePdfFromHtml(String html, String fontPath) throws IOException, DocumentException {
//        String outputFolder = "src/main/resources/" + File.separator + Contract.class.getSimpleName() + Constant.Symbol.UNDERSCORE + contractId + Constant.Extension.PDF;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public static String getTimeStrFromMillisecond(Long millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        return String.format(Constant.Contract.DATE_TIME_STRING_PATTERN, mDay, mMonth, mYear);
    }

    public static String getFileNameWithoutExtensionFromPath(String path) {
        File f = new File(path);
        return f.getName().replaceFirst("[.][^.]+$", "");
    }

}
