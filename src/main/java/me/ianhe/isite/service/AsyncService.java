package me.ianhe.isite.service;

/**
 * @author iHelin
 * @since 2018/5/30 20:10
 */
public interface AsyncService {

    void asyncSendEmail(String sendTo, String title, String content);
}
