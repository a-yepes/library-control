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
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] parts = line.split(";");
                String id = parts[0].trim();
                String action = parts[1].trim();

                EventType event;

                if (action.equals("ENTRADA")) {
                    event = EventType.ENTRY;
                } else {
                    event = EventType.EXIT;
                }

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

    public void registerChange(String id, EventType e){

        User u = this.users.get(id);

        if (u == null) {
            u = new User(id);
        }

        u.registerNewEvent(e);
        this.users.put(id, u);
    }

    public List<User> getCurrentInside(){

        List<User> inside = new ArrayList<>();

        for (User u : users.values()) {
            if (u.isInside()) {
                inside.add(u);
            }
        }

        inside.sort((a, b) -> a.getId().compareTo(b.getId()));
        return inside;
    }

    public List<User> getMaxEntryUsers(){
        int max = 0;

        for (User u : users.values()) {
            if (u.getNEntries() > max) {
                max = u.getNEntries();
            }
        }

        List<User> result = new ArrayList<>();

        for (User u : users.values()) {
            if (u.getNEntries() == max) {
                result.add(u);
            }
        }

        result.sort((a, b) -> a.getId().compareTo(b.getId()));
        return result;
    }

    public List<User> getUserList(){

        List<User> list = new ArrayList<>(this.users.values());
        Collections.sort(list, User::compare);
        return list;
    }

    public void printResume() {

        System.out.print("Usuarios actualmente dentro de la biblioteca:\n");
        for (User u : getCurrentInside()) {
            System.out.print(u.getId() + "\n");
        }

        System.out.print("\n");

        System.out.print("N\u00FAmero de entradas por usuario:\n");
        for (User u : getUserList()) {
            System.out.print(u.getId() + " -> " + u.getNEntries() + "\n");
        }

        System.out.print("\n");

        System.out.print("Usuario(s) con m\u00E1s entradas:\n");
        for (User u : getMaxEntryUsers()) {
            System.out.print(u.getId());
        }
    }
}