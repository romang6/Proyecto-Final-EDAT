package AVLCiudades;

import ClasesEspeciales.Ciudad;
import lineales.dinamicas.Lista;

/**
 *
 * @author romangattas
 */
public class ArbolAVL {
    private NodoAVL raiz;
    
    public ArbolAVL(){
        this.raiz = null;
    }
    
    //PERTENECE
    public boolean pertenece(Comparable elem){
        boolean exito;
        if(esVacio()){
            exito = false;
        }else{
            exito = perteneceAux(this.raiz, elem);
        }
        return exito;
       
    }  
    
    private boolean perteneceAux(NodoAVL n, Comparable elem){
        boolean exito = false;
        if(n!= null){
            if(elem.compareTo(n.getElem()) == 0){
                //si es igual entonces lo encontro
                exito = true;
            }else if(elem.compareTo(n.getElem()) < 0){
                // si es menor entonces busca en el HI
                exito = perteneceAux(n.getIzquierdo(), elem);
            }else{
                // si es mayor entonces busca en el HD
                exito = perteneceAux(n.getDerecho(), elem);
            }
        }
        return exito;
    }
    //-------------
    
    //INSERTAR
    public boolean insertar(Comparable elem, Ciudad ciud){
        boolean exito;
        if(this.raiz == null){
            this.raiz = new NodoAVL(elem, ciud,null,null);
            exito = true;
        }else{
            exito = insertarAux(this.raiz, elem, ciud);
            
        }
        return exito;
    }
    
    private boolean insertarAux(NodoAVL n, Comparable elem, Ciudad ciud){
        boolean exito = true;
        
        if(elem.compareTo(n.getElem())<0){
            if(n.getIzquierdo() == null){
                n.setIzquierdo(new NodoAVL(elem, ciud,null,null));
            }else{
                exito = insertarAux(n.getIzquierdo(),elem, ciud);
            }
        }else if(elem.compareTo(n.getElem())>0){
            if(n.getDerecho() == null){
                n.setDerecho(new NodoAVL(elem, ciud,null,null));
            }else{
                exito = insertarAux(n.getDerecho(), elem, ciud);   
            }
        }else{
            //el elem esta repetido
            exito = false;
        }
        if(exito){
            //actualizacion de altura

            n.recalcularAltura();

            //balanceo     
                
            if (n.getElem().compareTo(this.raiz.getElem()) == 0) { //caso raiz
                NodoAVL nuevaR = balancearAux(n);
                this.raiz = nuevaR;
            }

            n.setIzquierdo(balancearAux(n.getIzquierdo()));
            n.setDerecho(balancearAux(n.getDerecho()));
            
        }
        
        return exito;
        
    }
    
    //------------------------------------
    
    //PRIVADOS PARA INSERTAR Y ELIMINAR
    private NodoAVL balancearAux(NodoAVL n) {
        NodoAVL aux = n;
        if (n != null) {
            if (balance(n) == 2) {
                if (balance(n.getIzquierdo()) >= 0) {
                    aux = rotarDerecha(n);
                } else {
                    aux = rotarIzqDer(n);
                }

            }
            if (balance(n) == -2) {
                if (balance(n.getDerecho()) <= 0) {
                    aux = rotarIzquierda(n);
                } else {
                    aux = rotarDerIzq(n);
                }
            }
        }
        return aux;
    }
    
    private int balance(NodoAVL nodo) {
        return alturaActual(nodo.getIzquierdo()) - alturaActual(nodo.getDerecho());
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
    
    //rotaciones
    private NodoAVL rotarIzquierda(NodoAVL r){
        NodoAVL h, temp;

        h = r.getDerecho();
        temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        h.recalcularAltura();
        r.recalcularAltura();
        
        return h;
    }
    
    private NodoAVL rotarDerecha(NodoAVL r){
        NodoAVL h, temp;

        h = r.getIzquierdo();
        temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        h.recalcularAltura();
        r.recalcularAltura();  
        
        return h;
    }
    
    private NodoAVL rotarIzqDer(NodoAVL r){
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo()));
        return rotarDerecha(r);
    }
    
    private NodoAVL rotarDerIzq(NodoAVL r){
        r.setDerecho(rotarDerecha(r.getDerecho()));
        return rotarIzquierda(r);
    }
    
    //-----------------------------------
    
    //ELIMINAR
    
    public boolean eliminar(Comparable elem){
        boolean exito = false;
        if(this.raiz != null){
            exito = eliminarAux(this.raiz, null, elem);
        }  
        return exito;      
    }   
    
    private boolean eliminarAux(NodoAVL n, NodoAVL padre, Comparable elem){
        boolean exito = false;
        if(n != null){
            if((elem.compareTo(n.getElem())) == 0){
                
                if(n.getIzquierdo() == null && n.getDerecho() == null){
                    //si n no tiene hijos
                    noTieneHijos(padre, elem);
                    exito = true;
                    
                    
                }else if((n.getIzquierdo() != null ^ n.getDerecho() != null)){
                    //si n tiene UN hijo
                    tieneUnHijo(n, padre, elem);
                    exito = true;
                    
                }else{
                    // si n tiene ambos hijos
                    if(n.getIzquierdo().getDerecho()!=null){
                        //caso candidato
                        tieneAmbosCandidato(n.getIzquierdo(), null,n);
                    }else{
                        //caso HI
                        tieneAmbosHI(n);
                    } 
                    exito = true;
                 }
                
            }else{
                if(elem.compareTo(n.getElem())<0){
                    exito = eliminarAux(n.getIzquierdo(), n, elem);
                }
                if(elem.compareTo(n.getElem())>0){
                    exito = eliminarAux(n.getDerecho(), n, elem);
                }
            }
            
            if(exito){
                
                //balanceo     
                if (n.getElem().compareTo(this.raiz.getElem()) == 0) { //caso raiz
                    this.raiz = balancearAux(n);
                }

                n.setIzquierdo(balancearAux(n.getIzquierdo()));
                n.setDerecho(balancearAux(n.getDerecho()));
                
                //actualizacion de altura
                n.recalcularAltura();          
                
            }   
        }
        
        
        return exito;
    
    }
    
    //CASOS DE ELIMINAR
    
    private void noTieneHijos(NodoAVL padre, Comparable elem){
        
        //si no tiene hijos
        if(padre == null){
            //caso especial si el padre es nulo (raiz)
            this.raiz = null;
        }else{
            if(padre.getIzquierdo()!= null){
                if(padre.getIzquierdo().getElem().compareTo(elem) == 0){
                    //si n es el HI de padre
                    padre.setIzquierdo(null);
                }else{
                    //si n es HD de padre
                    padre.setDerecho(null);
                }
            }else{
                //si HI es nulo entonces es el HD
                padre.setDerecho(null);
            }
        }
        
    }
    
    private void tieneUnHijo(NodoAVL n, NodoAVL padre, Comparable elem){
        
        if(n.getIzquierdo() != null){
            //si n tiene HI
            if(padre == null){
                // caso especial si el padre es nulo
                this.raiz = n.getIzquierdo();
            }else{
                if(padre.getIzquierdo().getElem().compareTo(elem) == 0){
                     //si n es el HI de padre
                    padre.setIzquierdo(n.getIzquierdo());
                }else{
                     //si n es el HD de padre
                    padre.setDerecho(n.getIzquierdo());
                }
            }
        }
        if(n.getDerecho() != null){
            //si n tiene HD
            if(padre == null){
                // caso especial si el padre es nulo
                this.raiz.setDerecho(n.getDerecho());
            }else{
                if(padre.getIzquierdo().getElem().compareTo(elem) == 0){
                    //si n es el HI de padre
                    padre.setIzquierdo(n.getDerecho());
                }else{
                    //si n es el HD de padre
                    padre.setDerecho(n.getDerecho());
                }
            }
        }
        
    }
    
    private void tieneAmbosCandidato(NodoAVL n, NodoAVL anterior, NodoAVL raiz){
        
        if(n.getDerecho()!=null){
            
            tieneAmbosCandidato(n.getDerecho(), n, raiz);
            
        }else{
            
            //encontro el candidato, setea los elementos del nodo a modificar(raiz)
            raiz.setElem(n.getElem());
            raiz.setCiudad(n.getCiudad());
            anterior.setDerecho(n.getIzquierdo());
        }
        
        //balanceo
        n.setIzquierdo(balancearAux(n.getIzquierdo()));
        n.setDerecho(balancearAux(n.getDerecho()));

        //actualizacion de altura
        n.recalcularAltura(); 
   
    }
    
    private void tieneAmbosHI(NodoAVL n){
        n.setElem(n.getIzquierdo().getElem());
        n.setCiudad(n.getIzquierdo().getCiudad());        
        n.setIzquierdo(n.getIzquierdo().getIzquierdo());
    }

    //--------------------
    
    public int obtenerAltura(Comparable elem){
        int alt = -1;
        if(this.raiz!=null){
            alt = obAlturaAux(this.raiz, elem);
        }
        return alt;
    }
    
    private int obAlturaAux(NodoAVL n, Comparable elem){
        int a = -1;
        if(n!=null){
            if(elem.compareTo(n.getElem()) == 0){
                a = n.getAltura();
            }else{
                if(elem.compareTo(n.getElem())<0){
                    a = obAlturaAux(n.getIzquierdo(), elem);
                }else{
                    a = obAlturaAux(n.getDerecho(), elem);            
                }
            }
        }
        return a;
    }
    
    public void balancear(){
        if(this.raiz!=null){
            this.raiz = balancearAux(this.raiz);
        }
    }
    
    public Lista listarMenorAMayor(){
        Lista laLista = new Lista();
        listarMenorMayorAux(this.raiz, laLista);
        return laLista;
    }
    
    private void listarMenorMayorAux(NodoAVL n, Lista ls){ //recorrido inorden de arbolBin
        if(n!=null){
            listarMenorMayorAux(n.getIzquierdo(), ls);
            
            ls.insertar(n.getElem(), ls.longitud()+1);
            
            listarMenorMayorAux(n.getDerecho(), ls);
        }
    }
    
    public Lista listarMayorAMenor(){
        Lista laLista = new Lista();
        listarMayorAMenorAux(this.raiz, laLista);
        return laLista;
    }
    
    private void listarMayorAMenorAux(NodoAVL n, Lista ls){ //recorrido inorden de arbolBin al reves
        if(n!=null){
            
            listarMayorAMenorAux(n.getDerecho(), ls);
            
            ls.insertar(n.getElem(), ls.longitud()+1);
            
            listarMayorAMenorAux(n.getIzquierdo(), ls);
            
        }
    }
    
    public Lista listarRango(Comparable minimo, Comparable maximo){
        Lista laLista = new Lista();
        if(!this.esVacio()){
           listarRangoAux(this.raiz, minimo, maximo, laLista);
        }
        return laLista;
    }
    
    private void listarRangoAux(NodoAVL n, Comparable min, Comparable max, Lista ls){
        if(n!=null){
            if(min.compareTo(n.getElem())<=0){ // min<=n
                listarRangoAux(n.getIzquierdo(), min, max, ls);
            }
            if(min.compareTo(n.getElem())<=0 && max.compareTo(n.getElem())>=0){ //min<=n<=max
                ls.insertar("CP-->"+n.getElem()+"\nDatos: " +n.getCiudad().toString(), ls.longitud()+1);
            }
            if(max.compareTo(n.getElem())>=0){ //max>=n
                listarRangoAux(n.getDerecho(), min, max, ls);
            }
        }
    }
    
    public Comparable minimoElem(){
        Comparable resultado = null;
        if(this.raiz != null){
            resultado = minimoAux(this.raiz);
        }
        return resultado;
    }
    
    private Comparable minimoAux(NodoAVL n){
        Comparable res = null;
        if(n!=null){
            if(n.getIzquierdo()!=null){
                res = minimoAux(n.getIzquierdo());
            }else{
                res = n.getElem();
            }    
        }
        
        return res;
    }
    
    public Comparable maximoElem(){
        Comparable resultado = null;
        if(this.raiz != null){
            resultado = maximoAux(this.raiz);
        }
        return resultado;
        
    }
    
    private Comparable maximoAux(NodoAVL n){
        Comparable res = null;
        if(n!=null){
            if(n.getDerecho()!=null){
                res = maximoAux(n.getDerecho());
            }else{
                res = n.getElem();
            }    
        }
        
        return res;
        
    }
    
    public boolean esVacio(){
        return raiz == null;
    }
    
    
    //OBTENER DATO
    public Ciudad obtenerDato(Comparable elem){
        Ciudad res = null;
        if(this.raiz!=null){
            res = obtenerDatoAux(this.raiz, elem);
        }
        return res;
    }
    
    private Ciudad obtenerDatoAux(NodoAVL n, Comparable elem){
        Ciudad encontrada = null;
        if(n!=null){
            if(elem.compareTo(n.getElem()) == 0){
                 encontrada = n.getCiudad();
            }else{
                if(elem.compareTo(n.getElem()) < 0){
                    encontrada = obtenerDatoAux(n.getIzquierdo(),elem);
                
                }else{
                    encontrada = obtenerDatoAux(n.getDerecho(),elem);
                }
            }       
        }   
        return encontrada;
    }
    
    public String toString() {
        String mensaje = " ";
        if (raiz == null) {
            mensaje = "Arbol Vacio";
        } else {
            mensaje = toStringAux(this.raiz);
        }
        return mensaje;
    }

    private String toStringAux(NodoAVL nodo) {
        String mensaje = "";
        if (nodo != null) {
            mensaje =  "~Nodo(ALT = "+nodo.getAltura()+"): C.P-->"+ nodo.getElem()+ " ; " +nodo.getCiudad().toString();
            if (nodo.getIzquierdo() != null) {
                mensaje += ("\n  H.I(ALT = "+nodo.getIzquierdo().getAltura()+"): C.P-->"+nodo.getIzquierdo().getElem()+ " ; " +nodo.getIzquierdo().getCiudad().toString())+"   || ";
            } else {
                mensaje += "\n H.I: - " ;
            }
            if (nodo.getDerecho() != null) {
                mensaje += ("  H.D(ALT = "+nodo.getDerecho().getAltura()+"): C.P-->"+nodo.getDerecho().getElem()+ " ; " +nodo.getDerecho().getCiudad().toString()+"\n\n");
            } else {
                mensaje += " H.D: - \n\n";
            }
        }
        if(nodo.getIzquierdo()!=null){
            mensaje+= toStringAux(nodo.getIzquierdo());
        }
        if(nodo.getDerecho()!=null){
            mensaje+= toStringAux(nodo.getDerecho());
        }

        return mensaje;
    }
    
}
