package ru.netology.honeybadger;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/*
В данной задаче выбрал NonBlocking, так как пользователь может продолжительное время набирать большой
текст и передавать его каналу, а тот складывать данные в буфер, в это время, чтобы не простаивать сервер может заниматься
другими задачами, после обратиться к каналу, чтобы тот проверил если там какие-то данные для обрботки в буфере, если есть
сервер получает, обратывает и передает каналу, который отправляет их в буфер для клиента.
 */

public class Server {
    private final static int PORT = 56432;
    private final static String HOST = "netology.homework";

    public static void main(String[] args) {
        try {
            final ServerSocketChannel SOCKET_CHANNEL = ServerSocketChannel.open();
            SOCKET_CHANNEL.bind(new InetSocketAddress(HOST, PORT));
            while (true) {
                try (SocketChannel socketChannel = SOCKET_CHANNEL.accept()) {
                    final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                    while (socketChannel.isConnected()) {
                        int byteCount = socketChannel.read(inputBuffer);
                        if (byteCount == -1) break;
                        final String msg = new String(inputBuffer.array(), 0, byteCount, StandardCharsets.UTF_8);
                        if (msg.equals("end")) break;
                        inputBuffer.clear();
                        socketChannel.write(ByteBuffer.wrap(("\nРезультат вычисления: " + getFibonacciValue(new BigInteger(msg))).getBytes(StandardCharsets.UTF_8)));
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
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
