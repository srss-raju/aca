package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritmukherjee on 10/27/2015.
 */
public class Pdfs {
    private List<FormPdfInfo> pdfInfoList=new ArrayList<FormPdfInfo>();

    public List<FormPdfInfo> getPdfInfoList() {
        return pdfInfoList;
    }

    public void setPdfInfoList(List<FormPdfInfo> pdfInfoList) {
        this.pdfInfoList = pdfInfoList;
    }

    public  void addPdf(FormPdfInfo formPdfInfo){
        if(pdfInfoList!=null){
            this.getPdfInfoList().add(formPdfInfo);
        }
    }
}
