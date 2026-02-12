package pio.daw;

public class User implements Localizable { // guarda las entradas y el estado
    private String id;
    private Boolean inside = false;
    private Integer nEntries=0;

    public User(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public int getEntries(){
        return nEntries;
    }

    //IMPLEMENTO LOCALIZABLE
    
     public Boolean isInside(){
        return inside; 
     }

    public void processEvent (EventType e){ //actualiza el estado del usuario y controla las entradas y salidas duplicadas/invalidas

        
        if (e == EventType.ENTRY && !this.inside){ // si no esta dentro lo suma, porque si ya esta dentro y vuelve a haber una entry, es duplicada y se descarta
            inside=true;
            this.nEntries++;   
        
        }
        else if (e == EventType.EXIT && this.inside){ 
                    inside=false;

                }
            
        }

    

}
/*1. mirar que el txt existe (en library)
2. leer el txt y pasarlo a list<string> (en library)
3.lee linea a linea y crear map users: si no existe lo crea nuevo y si no coge el existente; llama a register event (en library)
4.crear library a partir de list<user> (en library)
*/
