package fit.iuh;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{username}")
public class ChatServerEndpoint {

    // Store sessions and users
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static Map<Session, String> sessionUsernameMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        sessions.put(username, session);
        sessionUsernameMap.put(session, username); // Map session to username
        broadcast("User " + username + " has joined the chat.", null); // Broadcast join message to everyone
        System.out.println("Connected: " + username);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Get the username of the sender
        String username = sessionUsernameMap.get(session);
        if (username != null) {
            broadcast(username + ": " + message, session);  // Broadcast message to others excluding sender
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) throws IOException {
        sessions.remove(username);
        sessionUsernameMap.remove(session);  // Remove session from the map
        broadcast("User " + username + " has left the chat.", null); // Broadcast leave message to everyone
        System.out.println("Connection closed: " + username);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    // Broadcast method to send a message to all connected users, excluding the sender
    private void broadcast(String message, Session senderSession) {
        for (Session session : sessions.values()) {
            // Send the message to all users except the sender and ensure the session is open
            if (senderSession == null || !session.getId().equals(senderSession.getId())) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }




    

    
    
}
