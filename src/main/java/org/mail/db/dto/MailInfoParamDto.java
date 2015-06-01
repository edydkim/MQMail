package org.mail.db.dto;

import oracle.sql.*;
import org.mail.enums.MailTypeEnum;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;


public class MailInfoParamDto implements ORAData, ORADataFactory {
    public static final String TYPE_NAME = "MAIL_INFO_PARAM";

    private static final ORADataFactory FACTORY = new MailInfoParamDto();
    public static final ORADataFactory getFactory() {
        return FACTORY;
    }

    // global user id
    private NUMBER globalUserId;
    // mail type
    private NUMBER mailInfoType;
    // timestamp
    private TIMESTAMP datetime;

    public MailInfoParamDto() {
    }

    public MailInfoParamDto(Datum datum) throws SQLException {
        Assert.notNull(datum, "datum must not be null.");
        Datum[] attrs = ((STRUCT) datum).getOracleAttributes();
        int count = 0;
        globalUserId = (NUMBER) attrs[count++];
        mailInfoType = (NUMBER) attrs[count++];
        datetime = (TIMESTAMP) attrs[count++];
    }

    /**
     * globalUserId's getter
     * @return globalUserId
     */
    public int getGlobalUserId() {
        try {
            return globalUserId == null ? 0 : globalUserId.intValue();
        }
        catch (SQLException e) {
            return 0;
        }
    }

    /**
     * globalUserId's setter
     * @param globalUserId
     */
    public void setGlobalUserId(int globalUserId) {
        this.globalUserId = new NUMBER(globalUserId);
    }

    /**
     * mailInfoType's getter
     * @return mailInfoType
     */
    public int getMailInfoType() {
        try {
            return mailInfoType == null ? 0 : mailInfoType.intValue();
        }
        catch (SQLException e) {
            return 0;
        }
    }

    /**
     * mailInfoType's setter
     * @param mailInfoType
     */
    public void setMailInfoType(int mailInfoType) {
        this.mailInfoType = new NUMBER(mailInfoType);
    }

    /**
     * datetime's getter
     * @return datetime
     */
    public Date getDatetime() {
        try {
            return datetime == null ? null : new Date(datetime.timestampValue().getTime());
        }
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * datetime's setter
     * @param datetime
     */
    public void setDatetime(Date datetime) {
        this.datetime = datetime == null ? null : new TIMESTAMP(new Timestamp(datetime.getTime()));
    }


    @Override
    public Datum toDatum(Connection connection) throws SQLException {
        return new STRUCT(
                StructDescriptor.createDescriptor(TYPE_NAME, connection),
                connection,
                new Object[] {
                        globalUserId, mailInfoType, datetime
                });
    }

    @Override
    public ORAData create(Datum datum, int i) throws SQLException {
        return datum == null ? null : new MailInfoParamDto(datum);
    }

    public byte toCode() {
        return (byte) MailTypeEnum.getEnum(getMailInfoType()).getCode();
    }

    @Override
    public String toString() {
        return "MailInfoParamDto{" +
                "globalUserId=" + getGlobalUserId() +
                ", mailInfoType=" + getMailInfoType() +
                ", datetime=" + getDatetime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MailInfoParamDto that = (MailInfoParamDto) o;

        if (globalUserId != null ? !globalUserId.equals(that.globalUserId) : that.globalUserId != null) return false;
        if (mailInfoType != null ? !mailInfoType.equals(that.mailInfoType) : that.mailInfoType != null) return false;
        return !(datetime != null ? !datetime.equals(that.datetime) : that.datetime != null);
    }

    @Override
    public int hashCode() {
        int result = globalUserId != null ? globalUserId.hashCode() : 0;
        result = 31 * result + (mailInfoType != null ? mailInfoType.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        return result;
    }
}
