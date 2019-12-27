package me.varchar42.teamspeakIntegration;

import me.varchar42.teamspeakIntegration.streamDeck.Client;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    //-port 28196 -pluginUUID 71CE796D0E574B3AF04F9844A88EE23B -registerEvent registerPlugin -info "{\"application\":{\"language\":\"de\",\"platform\":\"windows\",\"version\":\"4.4.2.12189\"},\"devicePixelRatio\":1,\"devices\":[{\"id\":\"64535485DDED1448165477F414A11B55\",\"name\":\"Stream Deck\",\"size\":{\"columns\":5,\"rows\":3},\"type\":0}],\"plugin\":{\"version\":\"0.1\"}}"

    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        if(args.length != 8) return;
        System.out.println(String.join(" ", args));

        System.out.println("Init");
        int port = Integer.parseInt(args[1]);
        String uuid = args[3];

        URI uri = new URI( "ws://localhost:"+port);

        Client client = new Client(uri, uuid);

        client.connect();
    }

}
