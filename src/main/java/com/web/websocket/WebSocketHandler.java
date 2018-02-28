package com.web.websocket;

import com.web.service.WebSocketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created on 24.06.16.
 */
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = Logger.getLogger(WebSocketHandler.class);

    @Autowired
    private WebSocketService webSocketService;



    @Secured("ROLE_USER")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        logger.debug("After connection established, id = " + session.getId());
        webSocketService.addSession(session);
        logger.debug("Open session, id = " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        logger.debug("After connection closed, id = " + session.getId());
        webSocketService.removeSession(session);
        logger.debug("Close session, id = " + session.getId());
    }



}
