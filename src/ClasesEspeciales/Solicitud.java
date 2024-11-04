package ClasesEspeciales;

/**
 *
 * @author romangattas
 */
public class Solicitud {
    private String fechaSolicitud;
    private String id;
    private int nroID;
    private double m3;
    private int bultos;
    private String domRetiro;
    private String domEntrega;
    private boolean pago;
    
    public Solicitud(String fechaSol, String tipo, int nro,
                    double m, int bul, String domR, String domE, boolean p) {
        
        fechaSolicitud = fechaSol;
        id = tipo;
        nroID = nro;
        m3 = m;
        bultos = bul;
        domRetiro = domR;
        domEntrega = domE;
        pago = p;
    }


    
    // getters
    
    public String getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public String getID() {
        return id;
    }
    
    public int getNroID() {
        return nroID;
    }
    
    public double getM3() {
        return m3;
    }
    
    public int getBultos() {
        return bultos;
    }
    
    public String getDomRetiro() {
        return domRetiro;
    }
    
    public String getDomEntrega() {
        return domEntrega;
    }
    
    public boolean isPago() {
        return pago;
    }
    
    // setters
    
    
    public void setFechaSolicitud(String fechaSoli) {
        fechaSolicitud = fechaSoli;
    }
    
    public void setTipoID(String tipo) {
        id = tipo;
    }
    
    public void setNroID(int nro) {
        nroID = nro;
    }
    
    public void setM3(double m) {
        m3 = m;
    }
    
    public void setBultos(int bul) {
        bultos = bul;
    }
    
    public void setDomRetiro(String dom) {
        domRetiro = dom;
    }
    
    public void setDomEntrega(String dom) {
        domEntrega = dom;
    }
    
    public void setPago(boolean pag) {
        pago = pag;
    }
    
    //equalsCliente
    
    public boolean esCliente(String unID, int unNroID){
        return (this.getID().equalsIgnoreCase(unID) && this.getNroID() == unNroID);
    }
    
    //visualizadores
    
    public String toString(){
        return "Fecha de Solicitud: " +fechaSolicitud+ " ; ID Cliente: " +id+ " ; NRO ID: " +nroID+ "\n" + 
                "Domicilio de Retiro : " +domRetiro+ " ; Domicilio Entrega: " +domEntrega; 
    }
    
    
}
