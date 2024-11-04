package lineales.dinamicas;

import AVLCiudades.*;

public class Nodo {
    private Object elemento;
    private Nodo enlace;
    
    //constructor
    
    public Nodo(Object elem, Nodo en){
        elemento = elem;
        enlace = en;
    }
    
    //setters
    
    public void setElem(Object elem){
        elemento = elem;
    }
    
    public void setEnlace(Nodo en){
        enlace = en;
    }
    
    //getters
    
    public Object getElem(){
        return elemento;
    }
    
    public Nodo getEnlace(){
        return enlace;
    }
   
}
