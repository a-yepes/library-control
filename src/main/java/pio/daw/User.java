package pio.daw;

public class User implements Localizable { // guarda las entradas y el estado
    private String id;
    private EventType lastEvent = null;
    private Boolean inside = false;
    private int entries=0;

    public User(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public int getEntries(){
        return entries;
    }
    //IMPLEMENTO LOCALIZABLE
     public Boolean isInside(){
        return inside; 
     }

    public void processEvent (EventType e){ //actualiza el estado del usuario y controla las entradas y salidas duplicadas/invalidas

        this.lastEvent = e;  //para coger el ultimo movimiento del usuario (e o s)
        
        if (e == EventType.ENTRY){

            if(!inside){ // si no esta dentro lo suma, porque si ya esta dentro y vuelve a haber una entry, es duplicada y se descarta
                inside = true;
                entries++;

            }
              
        
        }
        else if (e == EventType.EXIT){ 
                if(inside){ //si esta dentro ya, cuenta como salida
                    inside=false;

                }
            
        }
       

    }

    

}
