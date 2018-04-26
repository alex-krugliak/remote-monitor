package com.web.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.service.WebSocketService;
import com.web.wrapper.response.DataCurrentWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created on 24.06.16.
 */
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = Logger.getLogger(WebSocketHandler.class);

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        logger.debug("After connection established, id = " + session.getId());
        sessions.add(session);
        logger.debug("Open session, id = " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        logger.debug("After connection closed, id = " + session.getId());
        //sessions.removeSession(session);
        logger.debug("Close session, id = " + session.getId());
    }

    public void broadcastCurrentData(DataCurrentWrapper currentData) throws IOException {

        String jsonInString = new ObjectMapper().writeValueAsString(currentData);


        Iterator<WebSocketSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession s = iterator.next();
            if (s.isOpen()) {
                // send the current data to all the connected clients
                logger.debug("Sent message session id = " + s.getId());
                try {
                    s.sendMessage(new TextMessage(jsonInString.getBytes(StandardCharsets.UTF_8)));
                } catch (Exception ex) {
                    logger.info("Error sent message session id = " + s.getId() + ",  exception: " + ex.getMessage());
                }

            }
        }

    }

}
