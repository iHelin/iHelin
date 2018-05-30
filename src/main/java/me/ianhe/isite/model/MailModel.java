package me.ianhe.isite.model;

import java.io.Serializable;

/**
 * MailModel
 *
 * @author iHelin
 * @since 2017/10/17 15:51
 */
public class MailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String toAddress;
    private String toPersonalName;
    private String subject;
    private String content;

    public MailModel(String toAddress, String toPersonalName, String subject, String content) {
        this.toAddress = toAddress;
        this.toPersonalName = toPersonalName;
        this.subject = subject;
        this.content = content;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToPersonalName() {
        return toPersonalName;
    }

    public void setToPersonalName(String toPersonalName) {
        this.toPersonalName = toPersonalName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
