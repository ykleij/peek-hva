package com.example.backend.service;

import com.example.backend.models.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class NotificationDistributor extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    Map<String, Set<WebSocketSession>> sessionsMap = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession fromSession, TextMessage message) {
        String command = message.getPayload();
        String topic = command.split(" ")[1];

        if (command.startsWith("notify ")) {
            this.notify(topic, null, fromSession);
        } else if (command.startsWith("subscribe ")) {
            this.subscribe(topic, fromSession);
        } else if (command.startsWith("unsubscribe ")) {
            this.unsubscribe(topic, fromSession);
        } else {
//            SLF4J.error("Unsupported message '{}' from {}", command, fromSession);
            System.out.printf("Unsupported message '%s' from %s", command, fromSession);
        }
    }

    private void subscribe(String topic, WebSocketSession session) {
        Set<WebSocketSession> sessions = this.sessionsMap.get(topic);
        if (sessions == null) {
            this.sessionsMap.put(topic, new HashSet<>(Collections.singletonList(session)));
        } else {
            sessions.add(session);
        }
    }

    private void unsubscribe(String topic, WebSocketSession session) {
        Set<WebSocketSession> sessions = this.sessionsMap.get(topic);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    public void notify(String topic, Notification notification, WebSocketSession fromSession) {
        Set<WebSocketSession> sessions = this.sessionsMap.get(topic);
        if (sessions == null || sessions.isEmpty()) {
            // If there are no sessions, remove the topic
            this.sessionsMap.remove(topic);
            return;
        }

        TextMessage message;
        if (notification != null) {
            try {
                String dataJson = objectMapper.writeValueAsString(notification);
                message = new TextMessage("notify " + topic + " " + dataJson);
                System.out.println(message);
            } catch (Exception e) {
                System.out.printf("Error serializing data for topic %s: %s", topic, e.getMessage());
                return;
            }
        } else {
            message = new TextMessage("notify " + topic);
        }

        // Iterate over sessions and send messages
        Iterator<WebSocketSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession session = iterator.next();
            try {
                if (session.isOpen()) {
                    // If session is open, send the message
                    if (fromSession == null || !session.getId().equals(fromSession.getId())) {
                        session.sendMessage(message);
                    }
                } else {
                    // If session is closed, remove it from the set
                    iterator.remove();
                }
            } catch (Exception e) {
                System.out.printf("Communication %s:'%s' on %s to %s",
                        e.getClass().getName(), e.getMessage(), topic, session);
            }
        }

        // If there are no more sessions, remove the topic
        if (sessions.isEmpty()) {
            this.sessionsMap.remove(topic);
        }
    }

    public void notify(String topic, Notification notification) {
        this.notify(topic, notification, null);
    }
}
