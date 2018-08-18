package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ritmukherjee on 11/24/2015.
 */
public class ViewFormInfo  implements Serializable{

    private static final long serialVersionUID = 4349892793996090775L;


    /*coveredFilers:keep List of all filers who are covered with responsibleFiler*/
    private List<CoveredFilerInfo> coveredFilers;


    /* Current Form1095BInfo*/
    private Form1095BInfo currentForm;

    /*historic List<Form1095BInfo>*/
    private List<Form1095BInfo> historicForms;

    public List<CoveredFilerInfo> getCoveredFilers() {
        return coveredFilers;
    }

    public void setCoveredFilers(List<CoveredFilerInfo> coveredFilers) {
        this.coveredFilers = coveredFilers;
    }

    public Form1095BInfo getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(Form1095BInfo currentForm) {
        this.currentForm = currentForm;
    }

    public List<Form1095BInfo> getHistoricForms() {
        return historicForms;
    }

    public void setHistoricForms(List<Form1095BInfo> historicForms) {
        this.historicForms = historicForms;
    }
}
