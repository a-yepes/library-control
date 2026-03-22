package pio.daw;

public class User implements Localizable { // guarda las entradas y el estado
    private String id;
    private Boolean inside = false;
    private Integer nEntries = 0;

    public User(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public int getNEntries(){
        return nEntries;
    }

    public int getEntries(){
        return nEntries;
    }

    public static Integer compare(User u1, User u2){ //para comparar usuarios en getUsersList
        return u1.getId().compareTo(u2.getId());
    }

    //IMPLEMENTO LOCALIZABLE
    public Boolean isInside(){
        return inside;
    }

    public void registerNewEvent(EventType e){
    if (e == EventType.ENTRY) {
        if (!inside) {
            inside = true;
            nEntries++;
        }
    } else if (e == EventType.EXIT) {
        if (inside) {
            inside = false;
        }
    }
}

public void processEvent(EventType e){
    registerNewEvent(e);
}

    
}