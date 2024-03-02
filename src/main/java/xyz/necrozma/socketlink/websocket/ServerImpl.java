package xyz.necrozma.socketlink.websocket;

/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.logging.Logger;

import lombok.Getter;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import xyz.necrozma.socketlink.SocketLink;


public class ServerImpl extends WebSocketServer {

    private static final Logger logger = Logger.getLogger("SocketLink");

    @Getter
    private static final SocketMetrics metrics = new SocketMetrics();

    public ServerImpl(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public ServerImpl(InetSocketAddress address) {
        super(address);
    }

    public ServerImpl(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        metrics.incrementConnections();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        metrics.decrementConnections();
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        metrics.incrementMessagesReceived();
        try {
            Message m = MessageUtil.deserializeMessageFromJSON(message);
            assert m != null;
            if (m.getExternal()) {
                MessageUtil.broadcastMessageToServer(m, SocketLink.getPlugin());
            } else {
                broadcast(message);
            }

        } catch (Exception e) {
            logger.severe("Failed to deserialize message: " + message);
        }

    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        metrics.incrementMessagesReceived();
    }
    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.severe("An error occurred on connection " + conn + ": " + ex);
        metrics.incrementErrors();
    }

    @Override
    public void onStart() {
        logger.info("Server started on port " + getPort());
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}
