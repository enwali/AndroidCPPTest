package com.example.androidcpptest;
import android.os.AsyncTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class SSDPHelper {

    private static final String SSDP_ADDR = "239.255.255.250";
    private static final int SSDP_PORT = 8850;
    private static final int SOCKET_TIMEOUT = 3000;
    private final Callback callback;

    public interface Callback {
        void onMessageReceived(String message);
    }

    public SSDPHelper(Callback callback) {
        this.callback = callback;
    }

    public void discoverDevices() {
        AsyncTask.execute(() -> {
            DatagramSocket socket = null;
            try {
                InetAddress ssdpAddress = InetAddress.getByName(SSDP_ADDR);
                socket = new DatagramSocket(null);
                socket.setBroadcast(true);
                socket.setReuseAddress(true);
                socket.setSoTimeout(SOCKET_TIMEOUT);

                // Build and send the SSDP discover message
                String discoverMessage = buildDiscoverMessage();
                DatagramPacket sendPacket = new DatagramPacket(
                        discoverMessage.getBytes(StandardCharsets.UTF_8),
                        discoverMessage.length(),
                        ssdpAddress,
                        SSDP_PORT
                );
                socket.send(sendPacket);

                // Buffer for receiving responses
                byte[] receiveBuffer = new byte[1500];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                while (true) {
                    socket.receive(receivePacket);
                    String message = new String(
                            receivePacket.getData(),
                            0,
                            receivePacket.getLength(),
                            StandardCharsets.UTF_8
                    );
                    callback.onMessageReceived(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            }
        });
    }

    private String buildDiscoverMessage() {
        return "M-SEARCH * HTTP/1.1\r\n" +
                "HOST: 239.255.255.250:8850\r\n" +
                "MAN: \"ssdp:discover\"\r\n" +
                "MX: 3\r\n" +
                "ST: urn:device:LongerLaser\r\n" +
                "\r\n";
    }
}
