package ListaSolicitudes;

import ClasesEspeciales.Solicitud;

public class Nodo {
    private Solicitud solicitud;
    private Nodo enlace;
    
    //constructor
    
    public Nodo(Solicitud soli, Nodo en){
        solicitud = soli;
        enlace = en;
    }
    
    //setters
    
    public void setSolicitud(Solicitud soli){
        solicitud = soli;
    }
    
    public void setEnlace(Nodo en){
        enlace = en;
    }
    
    //getters
    
    public Solicitud getSolicitud(){
        return solicitud;
    }
    
    public Nodo getEnlace(){
        return enlace;
    }
   
}
