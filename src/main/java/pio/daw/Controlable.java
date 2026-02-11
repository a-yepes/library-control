package pio.daw;

import java.util.List;

public interface Controlable { // NO tiene logica, va en cada clase que la implemente
//controla: usuarios, entradas y salidas, quien esta dentro,cuantas veces ha entrado, resumen


    /**
     * Register a entry/exit change of a user.
     * @param id ID of the user to update.
     * @param s event type detected.
     */
    public void registerChange(String id, EventType e);
     //localizar al usuario,actualizar su estado, contar entradas

    /**
     * Return the list of all of the current Users
     * @return current users.
     */
    public List<User> getCurrentInside();

    /**
     * Get the user with the biggest amount of entries.
     * @return user that enters more tiemes.
     */
    public  List<User> getMaxEntryUsers();
    // devuelve una lista porque puede haber mas de uno


    /**
     * Get the list with all the users that has enter the place ordered by User ID.
     * @return
     */
    public List<User> getUserList();
    // devuelve todos los usuarios ordenados por ID sin duplicados

    /**
     * Print a resume of the current status:
     * 1. Current users
     * 2. Entries per user
     * 3. User with more entries
     */
    public void printResume();
    
}
