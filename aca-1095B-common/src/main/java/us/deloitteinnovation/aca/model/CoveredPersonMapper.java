package us.deloitteinnovation.aca.model;

import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CoveredPersonMapper  implements RowMapper<CoveredPerson> {

    @Override
    public CoveredPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
    	CoveredPerson cp = new CoveredPerson();
    	String name = rs.getString(CommonEntityConstants.RECIPIENT_FIRST_NAME) +" "+ rs.getString(CommonEntityConstants.RECIPIENT_LAST_NAME);
		String ssn = rs.getString(CommonDataConstants.RECIPIENT_SSN);
		String jan = (rs.getString(CommonEntityConstants.JAN) != null)?rs.getString(CommonEntityConstants.JAN):"0";
		String feb = (rs.getString(CommonEntityConstants.FEB) != null)?rs.getString(CommonEntityConstants.FEB):"0";
		String mar = (rs.getString(CommonEntityConstants.MAR) != null)?rs.getString(CommonEntityConstants.MAR):"0";
		String apr = (rs.getString(CommonEntityConstants.APR) != null)?rs.getString(CommonEntityConstants.APR):"0";
		String may = (rs.getString(CommonEntityConstants.MAY) != null)?rs.getString(CommonEntityConstants.MAY):"0";
		String jun = (rs.getString(CommonEntityConstants.JUN) != null)?rs.getString(CommonEntityConstants.JUN):"0";
		String jul = (rs.getString(CommonEntityConstants.JUL) != null)?rs.getString(CommonEntityConstants.JUL):"0";
		String aug = (rs.getString(CommonEntityConstants.AUG) != null)?rs.getString(CommonEntityConstants.AUG):"0";
		String sep = (rs.getString(CommonEntityConstants.SEP) != null)?rs.getString(CommonEntityConstants.SEP):"0";
		String oct = (rs.getString(CommonEntityConstants.OCT) != null)?rs.getString(CommonEntityConstants.OCT):"0";
		String nov = (rs.getString(CommonEntityConstants.NOV) != null)?rs.getString(CommonEntityConstants.NOV):"0";
		String dec = (rs.getString(CommonEntityConstants.DEC) != null)?rs.getString(CommonEntityConstants.DEC):"0";
		cp.setFirstName(rs.getString(CommonEntityConstants.RECIPIENT_FIRST_NAME));
		cp.setLastName(rs.getString(CommonEntityConstants.RECIPIENT_LAST_NAME));
		cp.setMiddleName(rs.getString(CommonEntityConstants.RECIPIENT_MIDDLE_NAME));
		cp.setSuffix(rs.getString(CommonEntityConstants.RECIPIENT_NAME_SUFFIX));
		cp.setName(name);
		cp.setDob(rs.getDate(CommonDataConstants.RECIPIENT_DOB).toString());
		cp.setSsn(ssn);
		cp.setTin(rs.getString(CommonDataConstants.RECIPIENT_TIN));
    	cp.setJan((jan != null) ? (jan.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCHECKED);
        cp.setFeb((feb != null) ? (feb.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setMar((mar != null) ? (mar.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setApr((apr != null) ? (apr.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
		cp.setMay((may != null) ? (may.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setJun((jun != null) ? (jun.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setJul((jul != null) ? (jul.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setAug((aug != null) ? (aug.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setSep((sep != null) ? (sep.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setOct((oct != null) ? (oct.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        cp.setNov((nov != null) ? (nov.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
		cp.setDec((dec != null) ? (dec.equals(CommonEntityConstants.COVERED) ? CommonEntityConstants.COVERED : CommonEntityConstants.UNCOVERED) : CommonEntityConstants.UNCOVERED);
        if(jan.equals(CommonEntityConstants.COVERED) &&
        		feb.equals(CommonEntityConstants.COVERED)&&
        		mar.equals(CommonEntityConstants.COVERED)&&
        		apr.equals(CommonEntityConstants.COVERED)&&
        		may.equals(CommonEntityConstants.COVERED)&&
        		jun.equals(CommonEntityConstants.COVERED)&&
        		jul.equals(CommonEntityConstants.COVERED)&&
        		aug.equals(CommonEntityConstants.COVERED)&&
        		sep.equals(CommonEntityConstants.COVERED)&&
        		oct.equals(CommonEntityConstants.COVERED)&&
        		nov.equals(CommonEntityConstants.COVERED)&&
        		dec.equals(CommonEntityConstants.COVERED)){
        	cp.setAll(CommonEntityConstants.CHECKED);
        }else
		{
			cp.setAll(CommonEntityConstants.UNCHECKED);
		}

        return cp;
    }
}
