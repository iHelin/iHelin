package me.ianhe.isite.service;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 邮件服务
 *
 * @author iHelin
 * @since 2018/5/12 13:13
 */
public interface EmailService {

    /**
     * 发送简单邮件
     *
     * @param sendTo  收件人地址
     * @param title   邮件标题
     * @param content 邮件内容
     */
    void sendSimpleMail(String sendTo, String title, String content);

    /**
     * 发送带附件的邮件
     *
     * @param sendTo              收件人地址
     * @param title               邮件标题
     * @param content             邮件内容
     * @param attachments <文件名，附件> 附件列表
     */
    void sendAttachmentsMail(String sendTo, String title, String content, List<Pair<String, File>> attachments);

    /**
     * 发送模板邮件
     *
     * @param sendTo
     * @param title
     * @param templateName
     * @param content
     * @param attachments
     */
    void sendTemplateMail(String sendTo, String title, String templateName, Map<String, Object> content, List<Pair<String, File>> attachments);

    /**
     * 发送模板邮件
     *
     * @param sendTo
     * @param title
     * @param templateName
     * @param content
     */
    void sendTemplateMail(String sendTo, String title, String templateName, Map<String, Object> content);
}
