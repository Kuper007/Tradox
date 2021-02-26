package com.nc.tradox.api;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Document;
import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.CountryImpl;
import com.nc.tradox.model.impl.Documents;
import com.nc.tradox.model.impl.FullRouteImpl;
import com.nc.tradox.service.TradoxService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.codehaus.plexus.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/docs")
@RestController
public class FillDocsController {

    private final TradoxService tradoxService;

    @Autowired
    public FillDocsController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/fill")
    public String fillDocs(@RequestBody Map<String, String> json, BindingResult bindingResult, HttpSession session) throws InvalidFormatException, IOException {
        Map<String, XWPFDocument> mapDocs = new HashMap<>();
        if (!bindingResult.hasErrors()) {
            int userId = (int) session.getAttribute("userId");
            User user = tradoxService.getUserById(userId);
            Country departure = new CountryImpl(json.get("departure"), null);
            Country destination = new CountryImpl(json.get("destination"), null);
            Documents documents = tradoxService.getDocuments(new FullRouteImpl(departure, destination));
            List<Document> docs = documents.getList();
            String link = "C:\\Users\\Kuper\\Desktop\\Tradox\\tradoxCode\\src\\main\\resources\\documents\\insurance.docx";
            for (Document doc : docs) {
                XWPFDocument docx = fillFile(link, user, json);
                if (docx != null) {
                    if (wordToPdf(docx,doc.getName())) {
                        PdfToImage(doc.getName()+".pdf");
                    }
                    mapDocs.put(doc.getName(), docx);
                }
            }
            session.setAttribute("documents", mapDocs);
        }
        return showImage(mapDocs);
    }

    public String showImage(Map<String, XWPFDocument> docs) throws IOException {
        String img = "";
        String pdf = "";
        if (docs != null) {
            for (Map.Entry<String, XWPFDocument> entry : docs.entrySet()) {
                File f = new File(entry.getKey()+"-1.png");
                if(!f.exists() || f.isDirectory()) {
                    return "{\"res\": \"false\"}";
                } else {
                    File f2 = new File(entry.getKey()+".pdf");
                    String base = fileToBase64(f);
                    String pdfBase = fileToBase64(f2);
                    if (!base.equals("")) {
                        img = "data:image/png;base64," + base;
                    }
                    if (!pdfBase.equals("")){
                        pdf = "data:application/pdf;base64," + pdfBase;
                    } else {
                        return "{\"res\": \"false\"}";
                    }
                }
            }
        } else {
            return "{\"res\": \"false\"}";
        }
        return "{\"res\": \"true\",\"img\":\""+img+"\",\"pdf\":\""+pdf+"\"}";
    }

    @GetMapping("/pdf")
    public String getPdf(HttpSession session) throws IOException {
        Map<String, XWPFDocument> docs = (Map<String, XWPFDocument>) session.getAttribute("documents");
        String json = "{\"res\":\"false\"}";
        if (docs == null) {
            return json;
        }
        PdfOptions options = PdfOptions.create();
        for (Map.Entry<String, XWPFDocument> entry : docs.entrySet()) {
            PdfConverter.getInstance().convert(entry.getValue(), new FileOutputStream(entry.getKey()+".pdf"), options);
        }
        json = json.replace("false", "true");
        return json;
    }

    private XWPFDocument fillFile(String path, User user, Map<String,String> fullRoute) {
        XWPFDocument docx = null;
        try {
            docx = new XWPFDocument(OPCPackage.open(path));
            for (XWPFTable tbl : docx.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null) {
                                    text = text.replace("country", tradoxService.getCountryById(fullRoute.get("destination")).getFullName());
                                    text = text.replace("name", user.getFirstName() + ' ' + user.getLastName());
                                    text = text.replace("(birth)", String.valueOf(user.getBirthDate()));
                                    text = text.replace("(passport code)", user.getPassport().getPassportId());
                                    r.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }
            //docx.write(new FileOutputStream("output.docx"));
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return docx;
    }

    public Boolean wordToPdf(XWPFDocument docx, String name) {
        PdfOptions options = PdfOptions.create();
        try {
            OutputStream out = new FileOutputStream(name+".pdf");
            PdfConverter.getInstance().convert(docx, out, options);
            return true;
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return false;
    }

    public void PdfToImage(String path) throws IOException {
        PDDocument document = PDDocument.load(new File(path));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        for (int page = 0; page < document.getNumberOfPages(); ++page)
        {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            ImageIOUtil.writeImage(bim, "Insurance" + "-" + (page+1) + ".png", 300);
        }
        document.close();
    }

    public String fileToBase64(File file){
        String encodedfile = "";
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedfile;
    }

}
