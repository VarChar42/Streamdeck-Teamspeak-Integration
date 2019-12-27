package me.varchar42.teamspeakIntegration.streamDeck;


import me.varchar42.teamspeakIntegration.ts3Query.TeamspeakQuery;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;

public class Client extends WebSocketClient {

    String uuid;
    JSONParser parser;
    TeamspeakQuery query;
    Thread queryThread;

    public Client(URI serverUri, String uuid) {
        super(serverUri);
        this.uuid = uuid;

        query = new TeamspeakQuery();
        queryThread = new Thread(query::connect);
        queryThread.start();

        parser = new JSONParser();

    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        JSONObject handshake = new JSONObject();
        handshake.put("event", "registerPlugin");
        handshake.put("uuid",  uuid);
        send(handshake.toString());
    }

    @Override
    public void onMessage(String s) {
        try {
            JSONObject msg = (JSONObject) parser.parse(s);
            if(msg.containsKey("action")) {
                String action = msg.get("action").toString();
                String event = msg.get("event").toString();

                if (action.equals("me.varchar42.streamdeck.teamspeakIntegration.toggleMute") && event.equals("keyDown")) {
                    System.out.println("Pressed: "+action);
                    System.out.println(query.mute() ? "OK":"ERROR");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.exit(0);

    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
