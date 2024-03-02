package xyz.necrozma.socketlink.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String username;
    private String message;
    private Boolean external;
}
