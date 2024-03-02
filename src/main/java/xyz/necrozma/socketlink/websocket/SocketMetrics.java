package xyz.necrozma.socketlink.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketMetrics {
    private int messagesSent;
    private int messagesReceived;
    private int connections;
    private int disconnections;
    private int errors;

    public void incrementMessagesSent() {
        messagesSent++;
    }

    public void incrementMessagesReceived() {
        messagesReceived++;
    }

    public void incrementConnections() {
        connections++;
    }

    public void incrementDisconnections() {
        disconnections++;
    }

    public void decrementConnections() {
        connections--;
    }

    public void decrementDisconnections() {
        disconnections--;
    }

    public void incrementErrors() {
        errors++;
    }

    public void decrementErrors() {
        errors--;
    }
}
