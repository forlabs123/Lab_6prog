package org.example.ServerMoudle;

import org.example.*;
import org.example.ClientMoudle.Client;
import org.example.ServerMoudle.commands.ManagerResult;
import org.example.Util.HelperUtil;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private int port1;
    private int port2;
    private Factory factory;

    private Scanner scanner;
    private UdpServer serverSocket;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(int port1, int port2, Factory factory) {
        this.port1 = port1;
        this.port2 = port2;
        this.factory = factory;
        scanner = new Scanner(System.in);
    }
    public void start(){
        try {
            //serverSocket = new ServerSocket(port);
            serverSocket = new UdpServer(port1,port2);

            ReadThread thread = new ReadThread(factory);
            thread.start();

 //           while(true){
                connectClient();

 //               }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean exitFromConsole() {
        if (!scanner.hasNext()) {
            return false;
        }
        String command = scanner.nextLine().toLowerCase(Locale.ROOT);
        if ("save".equals(command)) {
            factory.save();
            return false;
        }
        if ("exit".equals(command)){
            return true;
        }
        System.out.println("unknown command");
        return false;
    }
    public void connectClient() {
        while (true){
  //      while (!exitFromConsole()){
            ManagerResult.clear();

            byte[] bytes = serverSocket.listenAndGetData();
            if (bytes == null){
                continue;
            }
            Request request = (Request) HelperUtil.toObj(bytes);
            LOGGER.log(Level.INFO,"поступил запрос ({0},{1})",new Object[]{request.getCommand(),request.getArgs()});
            Response response = new Response();
            try{
                CommandResult command = factory.create(request.getCommand(), request.getArgs(), request.getObj());
                command.getCommand().execute(command.getArgs());
                if (command.getCommand().getFlag() == false){
                    response.setStatus(Status.EXIT);
                } else {
                    response.setStatus(Status.OK);
                }
                LOGGER.log(Level.INFO,"ответ отправлен клиенту ");
                response.setMessage(ManagerResult.getResult());
            } catch (Exception e){
                response.setException(e);
                response.setStatus(Status.ERROR);
                LOGGER.log(Level.SEVERE,"произошла ошибка - {0}", e.getMessage());
            }
            byte[] bytesArray = HelperUtil.toByteArray(response);
            serverSocket.sendData(bytesArray);
            LOGGER.log(Level.INFO,"ответ - {0}",response.getMessage());

        }
    }
}
