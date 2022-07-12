package ru.netology.honeybadger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int PORT = 56432;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                try (Socket socket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader((new InputStreamReader(socket.getInputStream())))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        out.println(getFibonacciValue(new BigInteger(line)));
                        if (line.equals("end")) break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BigInteger getFibonacciValue(BigInteger n) {
        if (n.compareTo(new BigInteger("2")) == -1) {
            return new BigInteger("0");
        } else if (n.compareTo(new BigInteger("2")) == 0) {
            return new BigInteger("1");
        } else {
            return getFibonacciValue(n.subtract(new BigInteger("1"))).add(getFibonacciValue(n.subtract(new BigInteger("2"))));
        }
    }
}
