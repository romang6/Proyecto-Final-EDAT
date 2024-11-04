package ClasesEspeciales;

/**
 *
 * @author romangattas
 */
public class ClaveCliente {
    private String tipo;
    private int nroID;

    public ClaveCliente(String t, int nro) {
        tipo = t;
        nroID = nro;
    }

    //getters 
    
    public String getTipo() {
        return tipo;
    }
    
    public int getNroId() {
        return nroID;
    }
    
    //setters

    public void setTipo(String t) {
        tipo = t;
    }

    public void setNumeroIdentificacion(int nro) {
        nroID = nro;
    }
    
    
    //toString
    
    public String toString(){
        return "TIPO ID: " +this.tipo+ " ; NUMERO ID: " +this.nroID;
    }
    
    //hashcode
    
    public int hashCode(){
        int code = 0;
        code = this.nroID%10;
        return code;
    }
    
    //equals
    
    public boolean equals(ClaveCliente otraClave){
        return ((this.getTipo().equalsIgnoreCase(otraClave.getTipo())) && (this.getNroId() == otraClave.getNroId()));
    }
}
