package me.varchar42.teamspeakIntegration.ts3Query;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.HashMap;

public class TeamspeakQuery {

    private Socket socket;
    private String apiKey = "3EXZ-4LBO-Z1NX-EYR4-NW1S-4XAB";
    private Thread readerThread;

    private BufferedReader in;
    private PrintWriter out;


    public void connect() {
        try {
            socket = new Socket("localhost", 25639);
            socket.setKeepAlive(true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            readerThread = new Thread(this::read);
            readerThread.start();

            out.println("auth apikey="+apiKey);

            out.println("whoami");





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Integer> data = new HashMap<String, Integer>();

    private void read() {
        try {
            while (socket.isConnected()) {
                String msg = in.readLine().trim();
                if(msg.startsWith("error")) {
                    System.out.println(msg);
                    continue;
                };

                String[] pair = msg.split(" ");
                for (String s : pair) {
                    String[] keyV = s.split("=");
                    if(keyV.length != 2) continue;
                    data.put(keyV[0], Integer.parseInt(keyV[1]));
                    System.out.println(keyV[0]+ ": "+keyV[1]);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean muted = false;

    public void mute() {
        if(muted) muted = false;
        else muted = true;

        mute(muted);

    }

    public void mute(boolean muted) {
        out.println("clientupdate client_input_muted="+(muted?1:0));

    }






}
