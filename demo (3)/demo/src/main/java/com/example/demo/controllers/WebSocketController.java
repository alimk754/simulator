package com.example.demo.controllers;

import com.example.demo.classes.Machine;
import com.example.demo.dto.MachineDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketController extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("New WebSocket connection established: {}", session.getId());
        sessions.put(session.getId(), session);
    }

    public void updateMachineState(Machine machine) {
        try {
            MachineDto stateDTO = new MachineDto(
                    machine.getMachineId(),
                    machine.getColor(),
                    machine.isWorking()
            );
            String message = objectMapper.writeValueAsString(stateDTO);

            sessions.values().forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(message));
                    }
                } catch (Exception e) {
                    logger.error("Error sending message to session {}", session.getId(), e);
                }
            });
        } catch (Exception e) {
            logger.error("Error updating machine state", e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            logger.info("Received message from session {}: {}", session.getId(), message.getPayload());
            // Your message handling logic here
        } catch (Exception e) {
            logger.error("Error handling message", e);
            session.sendMessage(new TextMessage("Error processing message"));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Transport error for session " + session.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("WebSocket connection closed for session {}: {}", session.getId(), status);
        sessions.remove(session.getId());
    }
}