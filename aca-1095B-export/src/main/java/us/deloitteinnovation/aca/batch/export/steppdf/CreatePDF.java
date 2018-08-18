package us.deloitteinnovation.aca.batch.export.steppdf;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import us.deloitteinnovation.aca.model.Filer;

public class CreatePDF {
    
    public static void create(Filer filer, String targetFileName, String year)
            throws IOException {

        if ("R".equals(filer.getFilerStatus())) {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
           
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 25, 701, "Form");
            setPDFText(contentStream, PDType1Font.HELVETICA, 21, 41, 701, "1095-B");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 25, 689, "Department of the Treasury");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 25, 679, "Internal Revenue Service");
            if(1 == filer.getCorrectionIndicator()){
                setPDFText(contentStream, PDType1Font.HELVETICA, 9, 150, 705, "CORRECTED");
            }
            setPDFText(contentStream, PDType1Font.HELVETICA, 21, 225, 701, "Health Coverage");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 220, 689, "Do not attach to your tax return. Keep for your records.");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 169, 679, "Information about Form 1095B and its separate instructions is at www.irs.gov/form1095b");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 462, 705, "VOID");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 462, 686, "CORRECTED");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 556, 720, "5601".concat(year.substring(2, 4)));
            setPDFText(contentStream, PDType1Font.HELVETICA, 21, 542, 699, year);
            setPDFText(contentStream, PDType1Font.HELVETICA, 6, 525, 687, "OMB No. 1545-2252");
            if(2 == filer.getCorrectionIndicator()){
                setPDFText(contentStream, PDType1Font.HELVETICA, 10, 451, 687, "X"); 
            }
            setPDFText(contentStream, PDType1Font.HELVETICA, 10, 254, 478, filer.getPolicyOrigin());
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 25, 38, "For Privacy Act and Paperwork Reduction Act Notice, see separate instructions.");
            
            contentStream.drawLine(25, 670, 590, 670);
            contentStream.setStrokingColor(Color.gray);
            if(1 == filer.getCorrectionIndicator()){
                contentStream.addRect(140, 701, 80, 15);
            }
            contentStream.addRect(250, 475, 15, 15);
            contentStream.addRect(25, 545, 280, 100);
            contentStream.addRect(305, 545, 280, 100);
            contentStream.addRect(25, 518, 280, 27);
            contentStream.addRect(305, 518, 280, 27);
            contentStream.addRect(448, 702, 13, 15);
            contentStream.addRect(448, 683, 13, 15);
            contentStream.addRect(25, 450, 280, 68);
            contentStream.addRect(25, 384, 280, 66);
            contentStream.addRect(305, 384, 140, 22);
            contentStream.addRect(445, 384, 140, 22);
            contentStream.addRect(305, 406, 280, 95);
            contentStream.addRect(25, 334, 107, 36);
            contentStream.addRect(132, 334, 68, 36);
            contentStream.addRect(200, 334, 62, 36);
            contentStream.addRect(262, 334, 44, 36);
            contentStream.addRect(306, 334, 279, 36);
            contentStream.addRect(306, 334, 23, 18);
            contentStream.addRect(329, 334, 23, 18);
            contentStream.addRect(352, 334, 23, 18);
            contentStream.addRect(375, 334, 23, 18);
            contentStream.addRect(398, 334, 23, 18);
            contentStream.addRect(421, 334, 23, 18);
            contentStream.addRect(444, 334, 23, 18);
            contentStream.addRect(467, 334, 23, 18);
            contentStream.addRect(490, 334, 23, 18);
            contentStream.addRect(513, 334, 23, 18);
            contentStream.addRect(536, 334, 23, 18);
            contentStream.addRect(559, 334, 26, 18);
            
            for(int i = 310; i >= 40; i -= 24){
            contentStream.addRect(25, i, 237, 24);
            contentStream.addRect(262, i, 44, 24);
            contentStream.addRect(306, i, 23, 24);
            contentStream.addRect(329, i, 23, 24);
            contentStream.addRect(352, i, 23, 24);
            contentStream.addRect(375, i, 23, 24);
            contentStream.addRect(398, i, 23, 24);
            contentStream.addRect(421, i, 23, 24);
            contentStream.addRect(444, i, 23, 24);
            contentStream.addRect(467, i, 23, 24);
            contentStream.addRect(490, i, 23, 24);
            contentStream.addRect(513, i, 23, 24);
            contentStream.addRect(536, i, 23, 24);
            contentStream.addRect(559, i, 26, 24);
            }
            
            contentStream.closeAndStroke();

            setPDFText(contentStream, PDType1Font.HELVETICA_BOLD, 9, 26, 650, "PART I");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 70, 650, "Responsible Individual");
            setPDFText(contentStream, PDType1Font.HELVETICA_BOLD, 9, 309, 650, "PART II");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 353, 650, "Employer Sponsored Coverage (see instructions)");
            StringBuilder nameBuilder = new StringBuilder(filer.getRecipientFirstName()).append(" ");
            nameBuilder.append(filer.getRecipientLastName());
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 622, nameBuilder.toString());
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 609, filer.getRecipientAddLine1());
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 600, filer.getRecipientAddLine2());
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 578, "");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 557, filer.getRecipientCity() +","+filer.getRecipientState()+" "+filer.getRecipientZip());    
            
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 317, 622, "Intentionally Blank");
            
            String tinOrSsn;
            String dateOfBirth;
            if(filer.getRecipientSSN() != null){
                tinOrSsn = filer.getRecipientSSN().substring(0, 5).replace(filer.getRecipientSSN().substring(0, 5), "xxx-xx-").concat(filer.getRecipientSSN().substring(5, filer.getRecipientSSN().length()));
                dateOfBirth = "Intentionally Blank";
            }else if(filer.getRecipientTIN() != null){
                tinOrSsn = filer.getRecipientTIN().substring(0, 5).replace(filer.getRecipientTIN().substring(0, 5), "xxx-xx-").concat(filer.getRecipientTIN().substring(5, filer.getRecipientTIN().length()));
                dateOfBirth = "Intentionally Blank";
            }else{
                dateOfBirth = filer.getRecipientDOB();
                tinOrSsn = "Intentionally Blank";
            }
            contentStream.drawLine(164, 518, 164, 544);
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 527, "DOB: "+dateOfBirth);
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 177, 527, "SSN/TIN: "+tinOrSsn);
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 318, 527, "EIN: Intentionally Blank");
            setPDFText(contentStream, PDType1Font.HELVETICA_BOLD, 9, 309, 507, "PART III");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 353, 507, "Issuer or Other Coverage Provider (see instructions)");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 489, "Enter letter identifying Origin of the Health Coverage");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 477, "(see instructions for codes): .....................................");
            
            
            String providerName = filer.getProviderName();
            String namePartOne = providerName;
            String namePartTwo = "";
            if(providerName != null && providerName.length()>30){
                int index = providerName.indexOf(" ", 30);
                namePartOne = providerName.substring(0, index);
                namePartTwo = providerName.substring(index+1, providerName.length());
            }
            
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 317, 485, namePartOne);
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 317, 472, namePartTwo);
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 317, 460, filer.getProviderAddLine1());
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 317, 446, filer.getProviderAddLine2());
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 317, 428, filer.getProviderCity()+","+filer.getProviderState()+" "+filer.getProviderZip());
            
            
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 37, 415, "Reserved");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 317, 390, "EIN: "+filer.getProviderEIN());
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 457, 390, "Telephone #: "+filer.getProviderContactNo());
            setPDFText(contentStream, PDType1Font.HELVETICA_BOLD, 9, 26, 374, "PART IV");
            setPDFText(contentStream, PDType1Font.HELVETICA, 9, 70, 374, "Covered Individuals (Enter the information for each covered individual(s).)");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 28, 349, "(a) Name of covered individual(s)");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 133, 349, "(b) SSN or other TIN");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 202, 358, "(c) DOB (If SSN or");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 207, 349, "other TIN is not");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 217, 339, "available)");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 268, 358, "(d) Covered");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 277, 349, "all 12");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 273, 339, "months)");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 410, 358, "(e) Months of coverage");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 312, 339, "Jan");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 335, 339, "Feb");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 358, 339, "Mar");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 381, 339, "Apr");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 402, 339, "May");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 427, 339, "Jun");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 452, 339, "Jul");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 471, 339, "Aug");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 496, 339, "Sep");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 518, 339, "Oct");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 541, 339, "Nov");
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 566, 339, "Dec");
            
            int yAxis = 320;
            
            
            yAxis = coveredPersonReport(contentStream, yAxis, filer);
            
            
            List<Filer> coveredPersons = filer.getCoveredPersonList();
            if(CollectionUtils.isNotEmpty(coveredPersons)){
                for(Filer person : coveredPersons){
                    yAxis = coveredPersonReport(contentStream, yAxis, person);
                    
                }
            }
            setPDFTextColor(contentStream, PDType1Font.HELVETICA, 15, 235, 740, "SUBSTITUTE FORM");
            contentStream.close();
            
            PDPage page2 = new PDPage();
            document.addPage(page2);
            PDPageContentStream contentStream2 = new PDPageContentStream(document, page2);
            
            setPDFText(contentStream2, PDType1Font.HELVETICA, 8, 26, 770, "12/19/2016");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 8, 309, 770, "LaborWise");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 7, 25, 741, "Form 1095B(2016)");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 7, 567, 741, "Page");
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD, 9, 585, 741, "2");
            
            contentStream2.drawLine(25, 730, 590, 730);
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD, 9, 26, 710, "Instructions for Recipient");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 698, "This Form 1095-B provides information needed to report on your");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 686, "income tax return that you, your spouse (if you file a joint return),");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 674, "and individuals you claim as dependents had qualifying health");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 662, "coverage (referred to as minimum essential coverage) for some");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 650, "or all months during the year. Individuals who don't have minimum");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 638, "essential coverage and don't qualify for an exemption from this");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 626, "requirement may be liable for the individual shared responsibility");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 614, "payment.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 592, "Minimum essential coverage includes government-sponsored");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 580, "programs, eligible employer-sponsored plans, individual market");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 568, "plans, and other coverage the Department of Health and Human");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 556, "Services designates as minimum essential coverage. For more");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 544, "information on the requirement to have minimum essential");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 532, "coverage and what is minimum essential coverage, see");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 520, "www.irs.gov/affordable-care-act/individuals-and");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 508, "families/individual-shared-responsibility-provision.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD_OBLIQUE, 9, 26, 486, "TIP:");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 46, 486, "Providers of minimum essential coverage are required to furnish");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 474, "only one Form 1095-B for all individuals whose coverage is reported");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 462, "on that form. As the recipient of this Form 1095-B, you should");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 450, "provide a copy to other individuals covered under the policy if they");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 438, "request it for their records.");
                    
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD, 9, 26, 416, "Part I. Responsible Individual,");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 156, 416, "reports information about you and");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 404, "the coverage, including your social security number (SSN) or other");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 392, "taxpayer identification number (TIN), if applicable. For your");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 380, "protection, this form may show only the last four digits. However,");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 368, "the coverage provider is required to report your complete SSN or");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 356, "other TIN, if applicable to the IRS. Your date of birth will be entered");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 26, 344, "only if the SSN is blank.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD_OBLIQUE, 9, 26, 322, "IMPORTANT:");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 85, 322, "If you don't provide your SSN or other TIN and the");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 310, "SSNs or other TINs of all covered individuals to the sponsor of the");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 298, "coverage, the IRS may not be able to match the Form 1095-B with the");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 286, "individuals to determine that they have complied with the individual");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 26, 274, "shared responsibility provision.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD, 9, 320, 710, "Origin of the Health Coverage code.");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 475, 710, "This is the code for the type");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 698, "of coverage in which you or other covered individuals were enrolled.");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 686, "Only one letter will be entered on this line.");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 330, 674, "A. Small Business Health Options Program (SHOP)");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 330, 662, "B. Employer-sponsored coverage");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 330, 650, "C. Government-sponsored program");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 330, 638, "D. Individual market insurance");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 330, 626, "E. Multiemployer plan.");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 330, 614, "F. Other designated minimum essential coverage.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD_OBLIQUE, 9, 320, 592, "TIP:");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 340, 592, "If you or another family member received health insurance");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 320, 580, "coverage through a Health Insurance Marketplace (also known as an");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 320, 568, "Exchange), that coverage will generally be reported on a Form 1095-");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 320, 556, "A rather than a Form 1095-B. If you or another family member");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 320, 544, "received employer-sponsored coverage, that coverage may be");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 320, 532, "reported on a Form 1095-C (Part III) rather than a Form 1095-B. For");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 320, 520, "more information, see www.irs.gov/affordable-care-act/questions");
            setPDFText(contentStream2, PDType1Font.HELVETICA_OBLIQUE, 9, 320, 508, "and-answers-about-health-care-information-forms-for-individuals.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD, 9, 320, 486, "Part II. EmployerSponsored Coverage.");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 486, 486, "If you had employer");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 474, "sponsored health coverage, this part may provide information about");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 462, "the employer sponsoring the coverage. This part may show only the");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 450, "last four digits of the employer's EIN. This part may also be left");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 438, "blank, even if you had employer-sponsored health coverage. If this");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 426, "part is blank, you do not need to fill in the information or return it to");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 414, "your employer or other coverage provider.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD, 9, 320, 392, "Part III. Issuer or Other Coverage Provider.");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 504, 392, "This part reports");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 380, "information about the coverage provider (insurance company,");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 368, "employer providing self-insured coverage, government agency");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 356, "sponsoring coverage under a government program such as");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 344, "Medicaid or Medicare, or other coverage sponsor). Underneath, is a");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 332, "telephone number for the coverage provider that you can call if you");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 320, "have questions about the information reported on the form.");
            
            setPDFText(contentStream2, PDType1Font.HELVETICA_BOLD, 9, 320, 298, "Part IV. Covered Individuals.");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 442, 298, "This part reports the name, SSN or");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 286, "other TIN, and coverage information for each covered individual. A");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 274, "date of birth will be entered in column (c) only if SSN or other TIN");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 262, "isn't entered in column (b). Column (d) will be checked if the");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 250, "individual was covered for at least one day in every month of the");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 238, "year. For individuals who were covered for some but not all months,");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 226, "information will be entered in column (e) indicating the months for");
            setPDFText(contentStream2, PDType1Font.HELVETICA, 9, 320, 214, "which these individuals were covered.");
            
            contentStream2.close();
            document.save(new File(targetFileName));
            document.close();
        }
    }

    private static int coveredPersonReport(PDPageContentStream contentStream, int yAxis, Filer person) throws IOException {
        int monthCounter;
        String ssnOrTin;
        String dob;
        monthCounter = 0;
        setPDFText(contentStream, PDType1Font.HELVETICA, 7, 31, yAxis, person.getRecipientFirstName()+" "+person.getRecipientLastName());
        if(person.getRecipientSSN() != null){
            ssnOrTin = person.getRecipientSSN().substring(0, 5).replace(person.getRecipientSSN().substring(0, 5), "xxx-xx-").concat(person.getRecipientSSN().substring(5, person.getRecipientSSN().length()));
            dob = "     ---";
        }else if(person.getRecipientTIN() != null){
            ssnOrTin = person.getRecipientTIN().substring(0, 5).replace(person.getRecipientTIN().substring(0, 5), "xxx-xx-").concat(person.getRecipientTIN().substring(5, person.getRecipientTIN().length()));
            dob = "     ---";
        }else{
            dob = person.getRecipientDOB();
            ssnOrTin = "     ---";
        }
        setPDFText(contentStream, PDType1Font.HELVETICA, 7, 148 , yAxis, ssnOrTin);
        setPDFText(contentStream, PDType1Font.HELVETICA, 7, 214, yAxis, dob);
        if("1".equals(person.getJan())){
            monthCounter++;
        }
        if("1".equals(person.getFeb())){
            monthCounter++;
        }
        if("1".equals(person.getMar())){
            monthCounter++;
        }
        if("1".equals(person.getApr())){
            monthCounter++;
        }
        if("1".equals(person.getMay())){
            monthCounter++;
        }
        if("1".equals(person.getJun())){
            monthCounter++;
        }
        if("1".equals(person.getJul())){
            monthCounter++;
        }
        if("1".equals(person.getAug())){
            monthCounter++;
        }
        if("1".equals(person.getSep())){
            monthCounter++;
        }
        if("1".equals(person.getOct())){
            monthCounter++;
        }
        if("1".equals(person.getNov())){
            monthCounter++;
        }
        if("1".equals(person.getDec())){
            monthCounter++;
        }
        if(monthCounter == 12){
            setPDFText(contentStream, PDType1Font.HELVETICA, 7, 285, yAxis, "x");

        }else{
            if("1".equals(person.getJan())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 315, yAxis, "x");
            }
            if("1".equals(person.getFeb())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 338, yAxis, "x");
            }
            if("1".equals(person.getMar())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 361, yAxis, "x");
            }
            if("1".equals(person.getApr())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 384, yAxis, "x");
            }
            if("1".equals(person.getMay())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 407, yAxis, "x");
            }
            if("1".equals(person.getJun())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 430, yAxis, "x");
            }
            if("1".equals(person.getJul())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 453, yAxis, "x");
            }
            if("1".equals(person.getAug())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 476, yAxis, "x");
            }
            if("1".equals(person.getSep())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 499, yAxis, "x");
            }
            if("1".equals(person.getOct())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 522, yAxis, "x");
            }
            if("1".equals(person.getNov())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 545, yAxis, "x");
            }
            if("1".equals(person.getDec())){
                setPDFText(contentStream, PDType1Font.HELVETICA, 7, 568, yAxis, "x");
            }
        }
        yAxis = yAxis -24;
        return yAxis;
    }

    private static void setPDFText(PDPageContentStream contentStream, PDType1Font fontType, int fontSize, int xAxis, int yAxis, String text) throws IOException {
        contentStream.beginText();
        contentStream.setFont(fontType, fontSize);
        contentStream.newLineAtOffset(xAxis, yAxis);
        contentStream.showText(text);
        contentStream.endText();
    }
    
    private static void setPDFTextColor(PDPageContentStream contentStream, PDFont font, int fontSize, int xAxis, int yAxis, String text)
            throws IOException {
        contentStream.beginText(); 
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
        contentStream.newLineAtOffset(xAxis, yAxis);
        contentStream.showText(text);      
        contentStream.endText();
    }
}
