package GrafoRutas;

import lineales.dinamicas.Lista;
import java.util.HashMap;

/**
 *
 * @author romangattas
 */
public class GrafoEtiquetado {
    private NodoVert inicio;

    public GrafoEtiquetado() {
        inicio = null;
    }
    
    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }
    
    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVert();
        }
        return aux;
    }
    
    public boolean existeVertice(Object buscado){
        boolean exito = false;
        if(this.ubicarVertice(buscado)!=null){
            exito = true;
        }
        return exito;
    }
    
    public boolean insertarArco(Object or, Object des, double etiq){
        
        boolean res = false;
        NodoVert origen = ubicarVertice(or);
        NodoVert destino = ubicarVertice(des);
        
        if(origen != null && destino != null){
            res = insertarArcoAux(origen, destino, etiq);
        }
        
        return res;
        
    }
 
    private boolean insertarArcoAux(NodoVert origen, NodoVert destino, double etiq){
        
        NodoAdy aux1 = origen.getPrimerAdy();
        
        //seteo el adyacente desde origen a destino
        
        if(aux1!=null){
            
            while(aux1.getSigAdy()!=null){
                aux1 = aux1.getSigAdy();
            }
            aux1.setSigAdy(new NodoAdy(destino, null, etiq));
            
        }else{
            
            origen.setPrimerAdy(new NodoAdy(destino, null, etiq));
        }
        
        //seteo el adyacente desde destino a origen
        
        NodoAdy aux2 = destino.getPrimerAdy();
        
        if(aux2!=null){
            
            while(aux2.getSigAdy()!=null){
                aux2 = aux2.getSigAdy();
            }
            aux2.setSigAdy(new NodoAdy(origen, null, etiq));
            
        }else{
            destino.setPrimerAdy(new NodoAdy(origen, null, etiq));
        }
        
        return true;
    }
    
    public boolean eliminarVertice(Object vertAEliminar){
        boolean resultado = false;
        if(this.inicio!=null){
            NodoVert aux = this.inicio;
            if(aux.getElem().equals(vertAEliminar)){ //si el vertice esta en el inicio
                
                //elimina los arcos que lo tienen como destino
                eliminarArcosDestino(aux, vertAEliminar);
                //elimina el vertice, como consecuencia tamien elimina los arcos que lo tienen como origen
                this.inicio = aux.getSigVert();
                
                resultado = true;
                
            }else{ //si el verice no esta en el inicio, lo sigue buscando en los demas
                
                boolean encontrado = false;
                
                while(aux.getSigVert()!=null && !encontrado){
                    if(aux.getSigVert().getElem().equals(vertAEliminar)){
                        encontrado = true;
                    }else{
                        aux = aux.getSigVert();
                    }
                }
                
                if(encontrado){
                    
                    //elimina los arcos que lo tienen como destino
                    eliminarArcosDestino(aux.getSigVert(), vertAEliminar);
                    //elimina el vertice, como consecuencia tambien elimina los arcos que lo tienen como origen
                    aux.setSigVert(aux.getSigVert().getSigVert());
                    resultado = true;
                    
                }
            } 
        }
        return resultado;
    }
    
    private void eliminarArcosDestino(NodoVert n, Object vertAEliminar){

        NodoAdy adyAux = n.getPrimerAdy();

        while(adyAux!=null){
            
            //desde el nodo que voy a eliminar, voy al nodo destino
            NodoVert auxVert = adyAux.getVertice();
            //desde el destino, borro el arco que va al vertAEliminar
            NodoAdy adyAux2 = auxVert.getPrimerAdy();
            
            if(adyAux2!=null){
                
                //si el primer arco va al vertAEliminar
                if(adyAux2.getVertice().getElem().equals(vertAEliminar)){
                    
                    auxVert.setPrimerAdy(adyAux2.getSigAdy());
                    
                }else{   //sino es el primero, analiza los demas arcos
                         //si alguno va al destino, lo borra
                    
                    boolean encontrado = false;
                    
                    while(adyAux2.getSigAdy()!=null && !encontrado){
                        
                        if(adyAux2.getSigAdy().getVertice().getElem().equals(vertAEliminar)){
                            encontrado = true;
                        }else{
                            adyAux2 = adyAux2.getSigAdy();
                        }
                        
                    }
                    
                    if(encontrado){
                        
                        adyAux2.setSigAdy(adyAux2.getSigAdy().getSigAdy());
                        
                    }
                    
                }
            }
            //voy al siguiente nodo destino
            adyAux = adyAux.getSigAdy();
        }  

    }  
    
    public boolean eliminarArco(Object or, Object des){
        boolean res = false;
        if(this.inicio!=null){
            NodoVert origen = ubicarVertice(or);
            NodoVert destino = ubicarVertice(des);
            //si existen ambos vertices, va a eliminar el arco
            if(origen != null && destino != null){
                res = eliminarArcoAux(origen, destino, or, des);
            }
            
        }
        return res;
    }
    
    private boolean eliminarArcoAux(NodoVert origen, NodoVert destino, Object or, Object des){
        boolean exito = false;
        NodoAdy aux1 = origen.getPrimerAdy();
        
        //busca el arco desde origen a destino
        if(aux1!=null){
            
            //elimina el arco si lo encuentra
            if(aux1.getVertice().getElem().equals(des)){
                
                origen.setPrimerAdy(aux1.getSigAdy().getSigAdy());
                exito=true;
                
            }else{
                
                boolean control = false;
                while(aux1.getSigAdy()!=null && !control){
                    
                    if(aux1.getSigAdy().getVertice().getElem().equals(des)){
                        control = true;
                    }else{
                        aux1 = aux1.getSigAdy();
                    }  
                }
                if(control){
                    
                    aux1.setSigAdy(aux1.getSigAdy().getSigAdy());
                    exito=true;
                }
            }
        }
        
        //si pudo eliminar el arco desde origen a destino, elimina el de destino a origen
        if(exito){
            
            aux1 = destino.getPrimerAdy();
            if(aux1!=null){
                
                if(aux1.getVertice().getElem().equals(or)){
                    
                    destino.setPrimerAdy(aux1.getSigAdy().getSigAdy());
                    
                }else{
                    
                    boolean control = false;
                    while(aux1.getSigAdy()!=null && !control){
                        if(aux1.getSigAdy().getVertice().getElem().equals(or)){
                            control = true;
                        }else{
                            aux1 = aux1.getSigAdy();
                        }  
                    }
                    if(control){
                        aux1.setSigAdy(aux1.getSigAdy().getSigAdy());
                    }
                    
                }
                
            }
        }

      return exito;          
                
    }
    
    public boolean existeArco(Object or, Object des){
        boolean exito = false;
        //verifica si ambos vertices existen
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        NodoVert aux = this.inicio;
        while ((nodoOrigen == null || nodoDestino == null) && aux != null) {
            if (aux.getElem().equals(or)) {
                nodoOrigen = aux;
            }
            if (aux.getElem().equals(des)) {
                nodoDestino = aux;
            }
            aux = aux.getSigVert();
        }
        
        if (nodoOrigen != null && nodoDestino != null) {
            exito = existeArcoAux(nodoOrigen, des);
        }
        return exito;
    }
    
    private static boolean existeArcoAux(NodoVert origen, Object des){
        boolean exito = false;
        
        NodoAdy aux1 = origen.getPrimerAdy();
        //busca el arco desde origen a destino
        
        while(aux1!=null && !exito) {
            if(aux1.getVertice().getElem().equals(des)){
                exito = true;
            }else{
                aux1 = aux1.getSigAdy();
            }
        }      
        return exito;
    }
    
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // verifica si ambos vertices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVert();
        }
        if (auxO != null && auxD != null) {
        // si ambos vertices existen busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: HAY CAMINO!
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y destino
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdy();
                }
            }
        }
        return exito;
    }
    
    public Lista caminoMasCorto(Object origen, Object destino) {
        
        Lista masCorto = new Lista();
        Lista visitados = new Lista();    
        NodoVert nodoOrigen = ubicarVertice(origen);
        if (nodoOrigen != null) {
            visitados.insertar(origen, 1);
            masCorto = caminoMasCortoAux(nodoOrigen, destino, visitados, masCorto);

        }
        return masCorto;
        
    }

    private Lista caminoMasCortoAux(NodoVert n, Object destino, Lista visitados, Lista masCorto) {

        if (n != null) {
            
            //Si el nodo actual es el destino, se guarda una lista con los visitados que son un camino de llegada
            if (n.getElem().equals(destino)) {
                
                masCorto = visitados.clone();
                
            } else {
                    
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {

                    //si el adyacente aun no fue visitado entonces entra
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        
                        if (!masCorto.esVacia()) { //si ya encontro un camino(masCorto no es vacia), entonces 
                                                   //verifica que los nodos visitados no sean mas que los del camino encontrado
                                                    
                            if (visitados.longitud() < masCorto.longitud()) {
                                visitados.insertar(ady.getVertice().getElem(), visitados.longitud()+1);
                                masCorto = caminoMasCortoAux(ady.getVertice(), destino, visitados, masCorto);
                                //elimina al ultimo visitado para comparar con otros caminos a la vuelta
                                visitados.eliminar(visitados.longitud());
                            }

                        } else { //Si la lista es vacia, es la primera iteracion, busca el primer camino a destino
                            
                            //inserta el nodo adyacente visitado y llama recursivamente con el mismo
                            visitados.insertar(ady.getVertice().getElem(), visitados.longitud()+1);
                            masCorto = caminoMasCortoAux(ady.getVertice(), destino, visitados, masCorto);
                            //elimina al ultimo visitado para comparar con otros caminos a la vuelta
                            visitados.eliminar(visitados.longitud());
                            
                        }
                    }

                    ady = ady.getSigAdy();
                }
                
            }
        }
        return masCorto;
    }
    
    public HashMap caminoMasRapido(Object origen, Object destino) {
        
        HashMap aux = new HashMap();
        double km = 0;
        Lista visitados = new Lista();
        Lista rutaMasRapida = new Lista(); 
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        
        if (nodoOrigen != null && nodoDestino != null) {

            aux.put("distancia", Double.MAX_VALUE); 
            rutaMasRapida = CaminoMasRapidoAux(nodoOrigen, destino, visitados, rutaMasRapida, aux, km);
            aux.put("caminoMasRapido", rutaMasRapida);

        }
        return aux;
    }

    private Lista CaminoMasRapidoAux(NodoVert n, Object destino, Lista visitados, Lista rutaMasRapida, HashMap aux, double km) {
        
        if (n != null) {
            
            visitados.insertar(n.getElem(), visitados.longitud()+1);
            
            if (n.getElem().equals(destino)) {
                //si llego a destino y los km son menores a los de        
                //la ultima ruta guardada, actualiza la ruta
                if (km < (double) aux.get("distancia")) {
                    
                    rutaMasRapida = visitados.clone();
                    aux.put("distancia", km);
                }
                
            } else {
                
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    //suma los km de la ruta
                    km += ady.getEtiqueta();
                    //si no visito el vertice y los km son menores a los recorridos
                    if (visitados.localizar(ady.getVertice().getElem()) < 0 && km < (double) aux.get("distancia")) {
                        rutaMasRapida = CaminoMasRapidoAux(ady.getVertice(), destino, visitados, rutaMasRapida, aux, km);
                    }
                    //resta los km a la vuelta de la recursíon, 
                    //con esto suma los km de los caminos que aun no recorre a los que ya recorrio
                    km -= ady.getEtiqueta();
                    ady = ady.getSigAdy();
                    
                }
            }
            //elimina el ultimo visitado para comparar con otros caminos a la vuelta
            visitados.eliminar(visitados.longitud());
        }

        return rutaMasRapida;
    }
    
    public Lista listarEnProfundidad() {
        
        Lista visitados = new Lista();
        // define un vertice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
            // si el vertice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVert();
        }
        return visitados;
        
    }
   
    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        
        if (n != null) {
            // marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
            // visita en profundidad los adyacentes de n aun no visitados
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdy();
            }
        }
        
    }
    
    public String toString() {
        String msj = "El grafo se encuentra vacio";
        if (inicio != null) {
            msj = toStringAux(inicio);
        }
        return msj;
    }

    private String toStringAux(NodoVert n) {
        String msj = "";
        if (n != null) {
            msj = "Origen de la ruta: C.P " + n.getElem() + '\n';
            NodoAdy conex = n.getPrimerAdy();
            String msjAdyacentes = "";
            while (conex != null) {
                msjAdyacentes += "-" + "(" + "Destino: C.P " + conex.getVertice().getElem() + ";" + " -Distancia: " + conex.getEtiqueta() + ")";
                conex = conex.getSigAdy();
            }
            msj = msj + msjAdyacentes + '\n';
            msj += '\n' + toStringAux(n.getSigVert());
        }
        return msj;
    }

}
