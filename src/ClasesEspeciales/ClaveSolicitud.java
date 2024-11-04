package ClasesEspeciales;

/**
 *
 * @author romangattas
 */
public class ClaveSolicitud {
    private int origen;
    private int destino;
    
    public ClaveSolicitud(int or, int des){
        origen = or;
        destino = des;
    
    }
    
    public int getOrigen(){
        return origen;
    }
    
    public int getDestino(){
        return destino;
    }
    
    //toString
    
    public String toString(){
        return "Solicitudes con origen: " +origen+ " y destino: " +destino;
    }
    
    
    //hashcode
    
    public int hashCode(){
        int code;
        code = (origen+destino) % 10;
        return code; 
    }
    
    //equals
    
    public boolean equals(ClaveSolicitud otraClave){
        return (this.getOrigen() == otraClave.getOrigen() && this.getDestino() == otraClave.getDestino());
    }
    
    //esCiudad
    
    public boolean esCiudad(int cp){
        return (this.getOrigen() == cp || this.getDestino() == cp);
    }
    
}
