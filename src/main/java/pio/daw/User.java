package pio.daw;

public class User implements Localizable { // guarda las entradas y el estado
    private String id;
    private EventType lasEvent = null;
    private Boolean inside = false;

    public User(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    //IMPLEMENTO LOCALIZABLE
     public Boolean isInside(){
        return null;//completar
     }

}
