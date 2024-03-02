package xyz.necrozma.socketlink.websocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.format.TextColor;

import java.util.logging.Logger;

public class MessageUtil {

    private static final Logger logger = Logger.getLogger("SocketLink");

    public static Message createMessage(String username, String message, Boolean external) {
        Message m = new Message();
        m.setUsername(username);
        m.setMessage(message);
        m.setExternal(external);
        return m;
    }

    public static String serializeMessageToJSON(Message m) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(m);
        } catch (Exception e) {
            logger.severe("Failed to serialize message: " + m);
            return null;
        }
    }

    public static Message deserializeMessageFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Message.class);
        } catch (Exception e) {
            logger.severe("Failed to deserialize message: " + json);
            return null;
        }
    }

    public static String stringToJSON(String username, String message, Boolean external) {
        Message m = createMessage(username, message, external);
        return serializeMessageToJSON(m);
    }

    public static void broadcastMessageToServer(Message message, JavaPlugin plugin) {
        Component messageToSend = Component.text(message.getMessage());
        Component wrappedUsername = Component.text("<" + message.getUsername() + ">");
        Component prefix = Component.text("[").append(Component.text("SocketLink", TextColor.color(0x00FF00))).append(Component.text("] ")).append(wrappedUsername).append(Component.text(": "));
        plugin.getServer().broadcast(prefix.append(messageToSend));
    }

}
