package AVLCiudades;

import ClasesEspeciales.Ciudad;

/**
 *
 * @author romangattas
 */
public class NodoAVL {
    private Comparable elem;
    private Ciudad ciudad;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    
    public NodoAVL(Comparable e, Ciudad c, NodoAVL i, NodoAVL d){
        
        elem = e;
        ciudad = c;
        altura = 0;
        izquierdo = i;
        derecho = d;
    
    }
    
    //getters
    public Comparable getElem(){
        return elem;
    }
    
    public Ciudad getCiudad(){
        return ciudad;
    }

    public int getAltura(){
        return altura;
    }

    
    public NodoAVL getIzquierdo(){
        return izquierdo;
    }

    public NodoAVL getDerecho(){
        return derecho;
    }
     
     
    //setters
    public void setElem(Comparable e){
        elem = e;
    }
    
    public void setCiudad(Ciudad unaCiudad){
        ciudad = unaCiudad;
    }

    public void setIzquierdo(NodoAVL nodo){
        izquierdo = nodo;
    }

    public void setDerecho(NodoAVL nodo){
        derecho = nodo;
    }

    public void setAltura(int a){
        altura = a;
    }
    
    public void recalcularAltura(){
        if(this.getIzquierdo() == null && this.getDerecho() != null){
            this.setAltura(this.getDerecho().getAltura()+1);
        }else if(this.getIzquierdo() != null && this.getDerecho() == null){
            this.setAltura(this.getIzquierdo().getAltura()+1);
        }else{
            int nuevaAlt = Math.max(alturaActual(this.getIzquierdo()), alturaActual(this.getDerecho()));
            this.setAltura(nuevaAlt+1);
        }
    }
    
    private int alturaActual(NodoAVL n){
        int alt;
        if(n == null){
            alt = -1;
        }else{
            alt = 1 + Math.max(alturaActual(n.getIzquierdo()), alturaActual(n.getDerecho()));
        }
        return alt;
    }
    
    
     
}
