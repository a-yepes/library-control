package pio.daw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library implements Controlable {// guarda usuarios, procesa e/s y usa controlable


    private Map<String,User> users;

    /**
     * Read the library register file (.txt) and create a library object
     * with the current status of the users.
     * @param path Library registry file path.
     * @return Library object.
     */
    public static Library fromFile(Path path){ // crea objeto library, lee el archivo por lineas, las convierte en el formato ID-eventype, llama a registerchange y devuelve library actualizado
        
        Library library = new Library();
        
        try {
        List<String> lines = Files.readAllLines(path); //lines va a ser una lista con todos los datos del documento. Pide un try catch porque devuelve un exception

        for (String line : lines) {// separo el archivo en id y event (entrada/salida) y guardo cada parte por separado
            String[] parts = line.split(";");
            String id = parts[0];
            EventType event;

            if(parts[1].equals ("ENTRADA")){ //si en el archivo pone entrada o salida, que coincida con el entry o exit de la clase EventType
                event= EventType.ENTRY;
            }
            else{
                event=EventType.EXIT;
            }
            

            library.registerChange(id, event); //guardo registros
        }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo");
            System.exit(1);
        }

        return library;
    }

   
    private Library(){  //constructor
        this.users = new HashMap<>();
    }


    //IMPLEMENTO CONTROLABLE

    public void registerChange(String id, EventType e){ //busca user por ID, si no existe lo creo, uso eventype para poner las e/s

        User u = this.users.get(id); //users es el mapa creado arriba

        if( u == null){
            u = new User(id);
            
        }
        u.processEvent(e);//metodo creado en clase User
        this.users.put(id,u);//se pone aqui abajo para asegurarnos de que se registra bien
        
    }
    


    public List<User> getCurrentInside(){//recorre todos los usuarios y coge solo los que esten dentro 

        List<User> inside = new java.util.ArrayList<>();

        for (User u : users.values()) { //uso values porque users es un map 
            if (u.isInside()) {
            inside.add(u);
            }
        }
        
        return inside;
    }

    
    

    public  List<User> getMaxEntryUsers(){//recorre todos los usuarios, calcula el maximo y devuelve el usuario o usuarios con mas entradas (lista)
        int max=0;

        for( User u : users.values()){ 
            if (u.getEntries() > max){
                max= u.getEntries();
            }
        }
        List<User> result = new ArrayList<>(); 
        
        for (User u : users.values()) {
             if (u.getEntries() == max) { 
                result.add(u); 
            }
        }
        return result;

    }
    

    public List<User> getUserList(){ // devuelve todos los usuarios ordenados por ID sin duplicados 

        List<User> list = new ArrayList<>(this.users.values()); //coge solo los valores del mapa
        
        Collections.sort(list, User::compare); //ordenamos con la funcion creada en user para comparar usuarios


    return list;
    }
     

   
    public void printResume() {
    System.out.println("Usuarios actualmente dentro de la biblioteca: ");
    for (User u : getCurrentInside()) {
        System.out.println(u.getId()); //metodo que esta en user
    }

    System.out.println("Número de entradas por usuario:");
    for (User u : getUserList()) {
        System.out.println(u.getId() + " -> " + u.getEntries()); //metodo hecho en user
    }

    System.out.println("Usuario(s) con más entradas:");
    for (User u : getMaxEntryUsers()) {
        System.out.println(u.getId());
    }
    }
}

   
