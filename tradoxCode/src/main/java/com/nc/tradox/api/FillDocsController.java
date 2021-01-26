package com.nc.tradox.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.model.Document;
import com.nc.tradox.model.FullRoute;
import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.Documents;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public RedirectView fillDocs(@RequestBody FullRoute fullRoute, BindingResult bindingResult, HttpSession session) throws InvalidFormatException, IOException {
        if (!bindingResult.hasErrors()){
            int userId = (int) session.getAttribute("userId");
            User user = tradoxService.getUserById(userId);
            Documents documents = tradoxService.getDocumentsByCountriesIds(fullRoute.getDepartureCountry().getDepartureCountry().getShortName(),
                                                                           fullRoute.getDestinationCountry().getDestinationCountry().getShortName());
            List<Document> docs = documents.getList();
            Map<String,XWPFDocument> mapDocs = new HashMap<>();
            for(Document doc: docs){
              XWPFDocument docx = fillFile(doc.getFileLink(),user,fullRoute);
              if (docx!=null){
                  mapDocs.put(doc.getName(),docx);
              }
            }
            session.setAttribute("documents",mapDocs);
        }
        return new RedirectView("/api/v1/docs/show");
    }

    @GetMapping("/show")
    public Boolean showPdf(HttpSession session) throws IOException {
        Map<String,XWPFDocument> docs = (Map<String, XWPFDocument>) session.getAttribute("documents");
        if (docs!=null){
            PdfOptions options = PdfOptions.create();
            for (Map.Entry<String,XWPFDocument> entry: docs.entrySet()){
                PdfConverter.getInstance().convert(entry.getValue(),new FileOutputStream(entry.getKey()),options);
                PDDocument document = PDDocument.load(new File(entry.getKey()));
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                for (int page = 0; page < document.getNumberOfPages(); ++page) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(
                            page, 300, ImageType.RGB);
                    ImageIOUtil.writeImage(
                            bim, String.format("output/pdf-%d.%s", page + 1,".jpg"), 300);
                }
                document.close();
            }
            return true;
        }
        return false;
    }

    @GetMapping("/pdf")
    public Boolean getPdf(HttpSession session) throws IOException {
        Map<String,XWPFDocument> docs = (Map<String, XWPFDocument>) session.getAttribute("documents");
        if (docs==null){
            return false;
        }
        PdfOptions options = PdfOptions.create();
        for (Map.Entry<String,XWPFDocument> entry: docs.entrySet()){
            PdfConverter.getInstance().convert(entry.getValue(),new FileOutputStream(entry.getKey()),options);
        }
        return true;
    }

    private XWPFDocument fillFile(String path, User user, FullRoute fullRoute){
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
                                    text = text.replace("country",fullRoute.getDestinationCountry().getDestinationCountry().getFullName());
                                    text = text.replace("name", user.getFirstName()+' '+user.getLastName());
                                    text = text.replace("(birth)",String.valueOf(user.getBirthDate()));
                                    text = text.replace("(passport code)",user.getPassport().getPassportId());
                                    r.setText(text,0);
                                }
                            }
                        }
                    }
                }
            }
            //docx.write(new FileOutputStream("output.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return docx;
    }
}
