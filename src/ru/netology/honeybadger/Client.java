package ru.netology.honeybadger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final static int PORT = 56432;
    private final static String HOST = "netology.homework";

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            String msg;
            while (true) {
                System.out.println("(end-exit)Введите целое цисло:");
                msg = scanner.nextLine();
                out.println(msg);
                if (msg.equals("end")) break;
                System.out.println("Server: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
