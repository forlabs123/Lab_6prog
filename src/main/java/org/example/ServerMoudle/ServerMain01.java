package org.example.ServerMoudle;

import org.example.CommandResult;
import org.example.Factory;
import org.example.File.Collection;
import org.example.File.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.lang.System.in;

public class ServerMain01 {

    public static void main(String[] args) {
        String fileName = args[2];
        Collection collection = null;
        try {
            FileManager fileManager = new FileManager(fileName);
            collection = new Collection(fileManager);
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(2);
        }


        Factory factory = new Factory(collection);
        Server server = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]), factory);
        server.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isRunning = true;
        while (isRunning){
            try{
                if (reader.ready()) {
                    String input = reader.readLine();

                    if ("exit".equalsIgnoreCase(input)) {
                        isRunning = false;
                    }
                    if ("save".equalsIgnoreCase(input)){
                        CommandResult command = factory.create("save", null);
                        command.getCommand().execute(command.getArgs());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try{
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
