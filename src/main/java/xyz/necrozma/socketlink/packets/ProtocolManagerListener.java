package xyz.necrozma.socketlink.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.socketlink.websocket.MessageUtil;
import xyz.necrozma.socketlink.websocket.ServerImpl;
import xyz.necrozma.socketlink.websocket.SocketMetrics;


public class ProtocolManagerListener {
    static Logger logger = LoggerFactory.getLogger(ProtocolManagerListener.class);

    private static SocketMetrics metrics = ServerImpl.getMetrics();

    public static void initialize(Plugin plugin, ServerImpl s) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        if(manager == null) {
            logger.error("ProtocolLib not found, disabling plugin");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                String message = packet.getStrings().read(0);

                if(message.startsWith("/")) {
                    return;
                }

                String json = MessageUtil.stringToJSON(player.getName(), message, false);

                s.broadcast(json);

                metrics.incrementMessagesSent();
            }
        });
    }
}