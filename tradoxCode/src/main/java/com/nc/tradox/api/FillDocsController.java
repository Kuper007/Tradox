package com.nc.tradox.api;

import com.nc.tradox.model.Document;
import com.nc.tradox.model.FullRoute;
import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.Documents;
import com.nc.tradox.service.TradoxService;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.document.XDocReport;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import fr.opensagres.xdocreport.converter.Options;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            List<XWPFDocument> resDocs = new ArrayList<>();
            for(Document doc: docs){
              XWPFDocument docx = fillFile(doc.getFileLink(),user,fullRoute);
              resDocs.add(docx);
            }
            session.setAttribute("documents",resDocs);
        }
        return new RedirectView("/api/v1/docs/pdf");
    }

    @GetMapping("/pdf")
    public Boolean getPdf(HttpSession session){
        List<XWPFDocument> docs = (List<XWPFDocument>) session.getAttribute("documents");
        for (XWPFDocument doc: docs){
            if (doc==null){
                return false;
            } else {
                //TODO:finish later

            }
        }
        return true;
    }

    public XWPFDocument fillFile(String path, User user, FullRoute fullRoute){
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
