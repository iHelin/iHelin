package me.ianhe.controller;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.WQQClient;
import com.thankjava.wqq.consts.MsgType;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.GroupInfo;
import com.thankjava.wqq.entity.wqq.GroupsList;
import me.ianhe.config.QQNotifyListener;
import me.ianhe.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/9/21 10:01
 */
@Controller
public class QQController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QQNotifyListener listener;

    private SmartQQClient smartQQClient;

    @PostConstruct
    private void init() {
        smartQQClient = new WQQClient(false, 3, listener);
        listener.setSmartQQClient(smartQQClient);
    }

    @GetMapping("qq")
    public void qqLogin(HttpServletResponse response) {
        logger.debug("smart-qq");
        response.setContentType("image/png");
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        smartQQClient.login(true, listenerAction -> {
            try {
                OutputStream os = response.getOutputStream();
                ImageIO.write((BufferedImage) listenerAction.getData(), "png", os);
                os.flush();
                os.close();
                response.flushBuffer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, listenerAction -> logger.debug("登录成功"));
    }

    @ResponseBody
    @GetMapping("send")
    public String send(String msg) {
        logger.debug("消息：" + msg);
        GroupsList groupsList = smartQQClient.getGroupsList(true);
        logger.debug(JSON.toJson(groupsList.getGroups()));
        Map<Long, GroupInfo> groupInfoMap = groupsList.getGroups();
        for (Map.Entry<Long, GroupInfo> groupInfoEntry : groupInfoMap.entrySet()) {
            if (groupInfoEntry.getValue().getName().equals("一点点学习小组")) {
                long gid = groupInfoEntry.getKey();
                SendMsg sendMsg = new SendMsg(gid, MsgType.group_message, msg);
                smartQQClient.sendMsg(sendMsg);
                logger.debug("发送一条群消息");
            }
        }
        return success();
    }

}