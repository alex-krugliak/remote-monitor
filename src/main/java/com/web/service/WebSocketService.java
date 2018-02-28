package com.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.web.wrapper.response.DataCurrentWrapper;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created on 29.06.16.
 */
public interface WebSocketService {

    void addSession(WebSocketSession session);

    void removeSession(WebSocketSession session);

    void broadcastCurrentData(DataCurrentWrapper currentData) throws JsonProcessingException, IOException;
}
