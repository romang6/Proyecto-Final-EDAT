package GrafoRutas;

/**
 *
 * @author romangattas
 */
public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdy;
    private double etiqueta;
    
    public NodoAdy(NodoVert vert, NodoAdy sig, double kms){
        vertice=vert;
        sigAdy=sig;
        etiqueta=kms;
}
    public NodoAdy(){
        vertice=null;
        sigAdy=null;
        etiqueta=0;
    }

    public NodoVert getVertice() {
        return vertice;
    }

    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public NodoAdy getSigAdy() {
        return sigAdy;
    }

    public void setSigAdy(NodoAdy sigAdy) {
        this.sigAdy = sigAdy;
    }

    public double getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(double etiqueta) {
        this.etiqueta = etiqueta;
    }
}
