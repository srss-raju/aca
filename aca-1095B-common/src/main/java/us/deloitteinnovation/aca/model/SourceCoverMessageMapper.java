package us.deloitteinnovation.aca.model;


import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SourceCoverMessageMapper implements RowMapper<SourceCoverMessage> {

    @Override
    public SourceCoverMessage mapRow(ResultSet resultSet, int i) throws SQLException {
        SourceCoverMessage sourceCoverMessage = new SourceCoverMessage();
        if(resultSet.getObject(CommonDataConstants.SOURCE_CD)!=null)
            sourceCoverMessage.setSourceCd(resultSet.getObject(CommonDataConstants.SOURCE_CD).toString());
        if(resultSet.getObject(CommonDataConstants.COVER_MESSAGE_EN)!=null)
            sourceCoverMessage.setCoverMessageEn(resultSet.getObject(CommonDataConstants.COVER_MESSAGE_EN).toString());
        if(resultSet.getObject(CommonDataConstants.COVER_MESSAGE_SP)!=null)
            sourceCoverMessage.setCoverMessageSp(resultSet.getObject(CommonDataConstants.COVER_MESSAGE_SP).toString());
        if(resultSet.getObject(CommonDataConstants.COVER_MESSAGE_VT)!=null)
            sourceCoverMessage.setCoverMessageVt(resultSet.getObject(CommonDataConstants.COVER_MESSAGE_VT).toString());
        if(resultSet.getObject(CommonDataConstants.LANGUAGE_CD)!=null)
            sourceCoverMessage.setLanguageCd(resultSet.getObject(CommonDataConstants.LANGUAGE_CD).toString());
        return sourceCoverMessage;
    }
}
