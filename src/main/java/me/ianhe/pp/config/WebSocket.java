package me.ianhe.pp.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        logger.debug("[websocket消息]有新的连接，总数：{}", webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        logger.debug("[websocket消息]断开连接，总数：{}", webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        logger.debug("[websocket消息]收到客户端发来的消息：{}", message);
        sendMessage("收到消息啦，" + new Date());
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            logger.debug("[websocket消息]广播消息,message:{}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("[websocket消息]消息发送异常", e);
            }
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().build();
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().build();
    }
}
