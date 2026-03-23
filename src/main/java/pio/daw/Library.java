package pio.daw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library implements Controlable {

    private Map<String, User> users;

    public static Library fromFile(Path path) {

        Library library = new Library();

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {

                if (line == null || line.trim().isEmpty()) continue;
                if (!line.contains(";")) continue;

                String[] parts = line.split(";");
                if (parts.length < 2) continue;

                String id = parts[0].trim();
                String action = parts[1].trim();

                EventType event =
                        action.equalsIgnoreCase("ENTRADA") ?
                                EventType.ENTRY : EventType.EXIT;

                library.registerChange(id, event);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo");
            System.exit(1);
        }

        return library;
    }

    private Library() {
        this.users = new HashMap<>();
    }

    public void registerChange(String id, EventType e) {
        users.putIfAbsent(id, new User(id));
        users.get(id).registerNewEvent(e);
    }

    public List<User> getCurrentInside() {
        List<User> inside = new ArrayList<>();

        for (User u : users.values()) {
            if (u.isInside()) inside.add(u);
        }

        inside.sort(Comparator.comparing(User::getId));
        return inside;
    }

    public List<User> getMaxEntryUsers() {

        int max = users.values().stream()
                .mapToInt(User::getNEntries)
                .max()
                .orElse(0);

        List<User> result = new ArrayList<>();

        for (User u : users.values()) {
            if (u.getNEntries() == max) result.add(u);
        }

        result.sort(Comparator.comparing(User::getId));
        return result;
    }

    public List<User> getUserList() {
        List<User> list = new ArrayList<>(users.values()).stream()
                                                         .filter((u1) -> u1.getNEntries() > 0)
                                                         .sorted((u1,u2) -> u1.getId().compareTo(u2.getId()))
                                                         .toList();
        return list;
    }

    public void printResume() {

        System.out.println("Usuarios actualmente dentro de la biblioteca:");
        for (User u : getCurrentInside()) {
            System.out.println(u.getId());
        }

        System.out.println();

        System.out.println("Número de entradas por usuario:");
        for (User u : getUserList()) {
            System.out.println(u.getId() + " -> " + u.getNEntries());
        }

        System.out.println();

        System.out.println("Usuario(s) con más entradas:");
        List<User> maxUsers = getMaxEntryUsers();
        for (int i = 0; i < maxUsers.size(); i++) {
            System.out.print(maxUsers.get(i).getId());
            if (i < maxUsers.size() - 1) System.out.print(" ");
        }
    }
}
