SocketLink allows developers to create their own applications that connect to the Minecraft server chat. By sending a simple json message with the following format:

```json
{
  "username": "testuser",
  "message": "test message",
  "external": true
}

```
their messages will be displayed to users on the server. The plugin is very simple to use, and is easy to setup. Just install the plugin, optionally change the port in the config and you're good to go!

The plugin also provides a `/metrics` command and `SocketLink.metrics` permission to display Websocket info

![metrics](https://r2.e-z.host/66429241-79bf-4da7-b4b6-33cb201c59b4/76l31qzn.png)

By default, the plugin will listen on port `8887`, which is coonfigurable, in a future update the listen address will also be configurable.

If you would like to see a feature added or report a bug, feel free to check out the [Github](https://github.com/TheBozzz34/SocketLink/tree/master) or [Discord](https://dsc.gg/t0ast)
