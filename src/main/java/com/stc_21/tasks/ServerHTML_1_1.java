package com.stc_21.tasks;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * Реализовать на сокетах простейший HTTP 1.1 сервер, который на любой GET запрос выдает список файлов и директорий
 * в текущей директории. На остальные запросы отдавать 404.
 * Проверку осуществлял в браузере. По запросу localhost:8080(GET) выводится рабочая директория и список файлов в ней.
 * По запросу из формы(POST) выводится ошибка 404.
*/

public class ServerHTML_1_1 {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            while (true) {
                Socket client = server.accept();
                System.out.println("Connection accepted.");
                System.out.println("...");

                PrintWriter os = new PrintWriter(client.getOutputStream());
                BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));

                while (true) {

                    os.println("HTTP/1.1 200 OK");
                    os.println("Content-Type: text/html; charset=utf-8");
                    os.println();
                    os.write("Server reading from channel" + "\n");
                    os.flush();

                    os.println("<form method=\"post\" action=\"/form_request\">\n" +
                            "                    <input type=\"text\" name=\"name\">\n" +
                            "                    <button type=\"submit\">send</button>\n" +
                            "                    </form>");
                    String entry = is.readLine();
                    os.println("<p>READ from client message: " + entry + "</p>");
                    os.flush();
                    if ((entry != null) && (entry.length() != 0) && (entry.contains("GET"))) {
                        os.write("<p>Working Directory = " + System.getProperty("user.dir") + "</p>");
                        os.flush();
                        File folder = new File(System.getProperty("user.dir"));
                        for (File file : Objects.requireNonNull(folder.listFiles())) {
                            os.write("<p>Directory file = " + file.getName() + "</p>");
                            os.flush();
                        }
                        break;
                    } else {
                        os.println("<p>HTTP/1.1 404 Not Found" + "</p>");
                        os.flush();
                        break;
                    }
                }
                is.close();
                os.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

