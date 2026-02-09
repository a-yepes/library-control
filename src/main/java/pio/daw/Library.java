package pio.daw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
        List<String> lines = Files.readAllLines(path); //pide un try catch porque devuelve un exception

        for (String line : lines) {
            String[] parts = line.split(" ");
            String id = parts[0];
            EventType event = EventType.valueOf(parts[1]);

            library.registerChange(id, event);
        }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo");
            System.exit(1);
        }

        return library;
    }

    private Library(){
        this.users = new HashMap<>();
    }


    //IMPLEMENTAR CONTROLABLE

    public void registerChange(String id, EventType e){ //buscar user por ID, si no existe lo creo, uso eventype para poner las e/s

        User u = users.get(id); //users es el mapa creado arriba

        if( u == null){
            u = new User(id);
            users.put(id,u);
        }
        u.processEvent(e);
        
    }
    


    public List<User> getCurrentInside(){//recorrer todos los usuarios y coger solo los que esten dentro 

        List<User> inside = new java.util.ArrayList<>();

        for (User u : users.values()) {
            if (u.isInside()) {
            inside.add(u);
            }
        }
        
        return inside;
    }

    
    

    public  List<User> getMaxEntryUsers(){//recorrer todos los usuarios, calcular el maximo y devolver el usuario o usuarios con mas entradas (lista)
        int max=0;
        for( int i; i<users.size(); i++){
            if (u.getEntries() > max){
                max= u.getEntries;
            }
        }
        List<User> result = new ArrayList<>(); 
        
        for (User u : users.values()) {
             if (u.getEntries() == max) { 
                result.add(u); 
            }
        }

    }
    

    public List<User> getUserList(){ // devuelve todos los usuarios ordenados por ID sin duplicados COMPLETAR
        return null;
    }

   
   
    public void printResume() {
    System.out.println("Usuarios actualmente dentro de la biblioteca: ");
    for (User u : getCurrentInside()) {
        System.out.println(u.getId()); //metodo que esta en user
    }

    System.out.println("Número de entradas por usuario:");
    for (User u : getUserList()) {
        System.out.println(u.getId() + " -> " + u.getEntries()); //hacer metodo getEntries
    }

    System.out.println("Usuario(s) con más entradas:");
    for (User u : getMaxEntryUsers()) {
        System.out.println(u.getId());
    }
    }
}

   
