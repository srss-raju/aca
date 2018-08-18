package us.deloitteinnovation.aca.batch.export.steppdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import us.deloitteinnovation.aca.model.Filer;

public class FillPdf {
	
	public static void fillPdfwithData(PDField field, Filer filer) throws IOException{
		if ("topmostSubform[0].Page1[0].f1_01[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getRecipientFirstName()+" "+filer.getRecipientMiddleName()+" "+filer.getRecipientLastName());
		}
		
		if ("topmostSubform[0].Page1[0].f1_02[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getRecipientSSN());
		}
		
		if ("topmostSubform[0].Page1[0].f1_03[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getRecipientDOB());
		}
		
		if ("topmostSubform[0].Page1[0].f1_04[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getRecipientAddLine1()+" "+filer.getRecipientAddLine2());
		}
		
		if ("topmostSubform[0].Page1[0].f1_05[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getRecipientCity());
		}
		
		if ("topmostSubform[0].Page1[0].f1_06[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getRecipientState());
		}
		
		if ("topmostSubform[0].Page1[0].f1_07[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getRecipientZip());
		}
		if ("topmostSubform[0].Page1[0].f1_08[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue("X");
		}
		if ("topmostSubform[0].Page1[0].f1_09[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue("RESERVED");
		}
		
		if ("topmostSubform[0].Page1[0].f1_10[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getEmployerName());
		}
		
		if ("topmostSubform[0].Page1[0].f1_11[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getEmployerEIN());
		}
		
		if ("topmostSubform[0].Page1[0].f1_12[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getEmployerAddressLine1()+" "+filer.getEmployerAddressLine2());
		}
		
		if ("topmostSubform[0].Page1[0].f1_13[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getEmployerCity());
		}
		
		if ("topmostSubform[0].Page1[0].f1_14[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getEmployerState());
		}
		
		if ("topmostSubform[0].Page1[0].f1_15[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getEmployerZIP());
		}
		
		if ("topmostSubform[0].Page1[0].f1_16[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getProviderName());
		}
		
		if ("topmostSubform[0].Page1[0].f1_17[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getProviderEIN());
		}
		
		if ("topmostSubform[0].Page1[0].f1_18[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(String.valueOf(filer.getProviderContactNo()));
		}
		
		if ("topmostSubform[0].Page1[0].f1_19[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getProviderAddLine1()+" "+filer.getProviderAddLine2());
		}
		
		if ("topmostSubform[0].Page1[0].f1_20[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getProviderCity());
		}
		
		if ("topmostSubform[0].Page1[0].f1_21[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getProviderState());
		}
		
		if ("topmostSubform[0].Page1[0].f1_22[0]".equalsIgnoreCase(field.getFullyQualifiedName())) {
			field.setValue(filer.getProviderZip());
		}
		
	}

}
