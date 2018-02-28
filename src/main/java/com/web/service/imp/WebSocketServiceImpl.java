package com.web.service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.service.WebSocketService;
import com.web.wrapper.response.DataCurrentWrapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 29.06.16.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {
    private static final Logger logger = Logger.getLogger(WebSocketServiceImpl.class);

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();

    public synchronized void addSession(WebSocketSession session) {
        logger.debug("Add session to session Map, id = " + session.getId());
        sessions.put(session.getId(), session);
    }

    public synchronized void removeSession(WebSocketSession session) {
        logger.debug("Remove session from session Map, id = " + session.getId());
        sessions.remove(session.getId());
    }

    public synchronized void broadcastCurrentData(DataCurrentWrapper currentData) throws IOException {

        String jsonInString = new ObjectMapper().writeValueAsString(currentData);


        for (String key : sessions.keySet()) {
            WebSocketSession s = sessions.get(key);
            if (s.isOpen()) {
                // send the current data to all the connected clients
                logger.debug("Sent message session id = " + s.getId());
                s.sendMessage(new TextMessage(jsonInString.getBytes(StandardCharsets.UTF_8)));

            }
        }

    }

}
