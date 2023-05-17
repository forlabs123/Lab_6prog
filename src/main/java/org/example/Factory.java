package org.example;


import org.example.ClientMoudle.ExecuteScript;
import org.example.Exeption.UnknownCommandException;
import org.example.Exeption.WrongAmountOfElementsException;
import org.example.File.Collection;
import org.example.ServerMoudle.commands.*;
import org.example.model.Worker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Factory {
    private final HashMap<String, Command> commandMap;
    private final HashSet<String> commandsWithArgs;


        public Factory(Collection collection){
        commandMap = new HashMap<>();
        commandMap.put("info", new Info(collection));
        commandMap.put("add", new Add(collection));
        commandMap.put("show", new Show(collection));
        commandMap.put("update", new UpdateId(collection));
        commandMap.put("remove_by_id", new RemoveById(collection));
        commandMap.put("clear", new Clear(collection));
        commandMap.put("save", new Save(collection));
        commandMap.put("execute_script", new ExecuteScript(collection, this));
        commandMap.put("exit", new Exit());
        commandMap.put("remove_first", new RemoveFirst(collection));
        commandMap.put("remove_head", new RemoveHead(collection));
        commandMap.put("remove_greater", new RemoveGreater(collection));
        commandMap.put("remove_all_by_salary", new RemoveAllBySalary(collection));
        commandMap.put("print_descending", new PrintDescending(collection));
        commandMap.put("print_field_ascending_organization", new PrintFieldAscendingOrganization(collection));
        commandMap.put("help", new Help());
        commandsWithArgs = new HashSet<>();
        commandsWithArgs.add("update");
        commandsWithArgs.add("remove_by_id");
        commandsWithArgs.add("remove_greater");
        commandsWithArgs.add("execute_script");
        commandsWithArgs.add("remove_all_by_salary");
        commandsWithArgs.add("save_as");
        }
        public void save(){
            commandMap.get("save").execute(null);
        }

    public CommandResult create(String command1, String args, Serializable obj){
        String[] args1;
        if (args.isEmpty()){
            args1 = new String[0];
        } else{
            args1 = args.split(" ");
        }

        if (commandsWithArgs.contains(command1)){
            if (args1.length != 1) throw new WrongAmountOfElementsException();
        } else if (args1.length > 1) throw new WrongAmountOfElementsException();
        Command command = commandMap.get(command1);
        if (command == null) {
            throw new UnknownCommandException();
        }
        if (command instanceof Add){
            ((Add) command).setClientWorker((Worker) obj);
        }
        //String[] a1 = Arrays.copyOfRange(a, 1, a.length);
        return new CommandResult(command, args1);
    }

        public CommandResult create(String command1,String args){
            return create(command1, args, null);
        }
    public static boolean checkCommand(String s) {
        if (s.equals("info") || s.equals("add") || s.equals("show") || s.equals("update") || s.equals("remove_by_id") || s.equals("clear") || s.equals("save") || s.equals("execute_script") || s.equals("exit") || s.equals("remove_first") || s.equals("remove_head") || s.equals("remove_greater") || s.equals("remove_all_by_salary") || s.equals("print_descending") || s.equals("print_field_ascending_organization")) {
            return true;
        }
        return false;
    }
}



