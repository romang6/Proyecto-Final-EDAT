package HashSolicitudes;

import ClasesEspeciales.ClaveSolicitud;
import ClasesEspeciales.Solicitud;

/**
 *
 * @author romangattas
 */
public class HashMapeoAMuchos {
    private static final int TAMANIO = 100;
    private NodoHashMapeoM[] tabla;
    private int cant;
    
    public HashMapeoAMuchos(){
        
        tabla = new NodoHashMapeoM[TAMANIO];
        cant = 0;
        
    }
    
    public boolean insertar(ClaveSolicitud cl, Solicitud soli) {
        // primero verifica si el cl ya esta cargado
        // si no lo encuentra, lo pone adelante del resto
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            if(aux.getClave().equals(cl)){
                encontrado = true;
            }else{    
                aux = aux.getEnlace();
            }
        }

        if(!encontrado){
            this.tabla[pos] = new NodoHashMapeoM(cl, this.tabla[pos]);
            this.tabla[pos].getPedidos().insertar(soli, 1);
            this.cant++;
        }else{
            aux.getPedidos().insertar(soli, aux.getPedidos().longitud()+1);
        }

        return !encontrado;
        
    }
    
    public boolean eliminar(ClaveSolicitud cl, int nroSoli) {
        
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false, exito = false;
        while (!encontrado && aux != null) {
            if(aux.getClave().equals(cl)){
                encontrado = true;
            }else{    
                aux = aux.getEnlace();
            }
        }
  
        if(encontrado){
            exito = aux.getPedidos().eliminar(nroSoli);
//            if(aux.getPedidos().esVacia()){
//                eliminarClave(cl);
//            }
        }
     
        return exito;
    
    }
    
    //elimina la clave si no hay ninguna solicitud
    
    public boolean eliminarClave(ClaveSolicitud cl){
        
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean resultado = false;
        if(aux!=null){
            if(this.tabla[pos].getClave().equals(cl)){
                this.tabla[pos] = aux.getEnlace();
                resultado = true;
            }else{
                while(aux.getEnlace()!=null && !aux.getEnlace().getClave().equals(cl)){
                    aux = aux.getEnlace();
                }
                if(aux.getEnlace()!=null){
                    aux.setEnlace(aux.getEnlace().getEnlace());
                    resultado = true;
                }
                
            }
        }
        return resultado;
    }    
    
    //obtener dato
    
    public Solicitud obtenerDato(ClaveSolicitud cl, int nroSoli){
        
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            
            if(aux.getClave().equals(cl)){
                encontrado = true;
            }else{    
                aux = aux.getEnlace();
            }
        }
        
        Solicitud buscada = null;
        
        if(encontrado){
            buscada = aux.getPedidos().recuperar(nroSoli);
        }

        return buscada;
    }
    
    
    public String solisYCargaCamion(ClaveSolicitud cl){
        String salida = "";
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean control = false;
        
        //busca la ciudad de la que va a sacar la info
        while(aux!=null && !control){
            
            if(aux.getClave().equals(cl)){
                control = true;
            }else{
                aux = aux.getEnlace();
            }   
            
        }
        
        if(control){
            
            salida += aux.getPedidos().toString();
            salida += "\nEl espacio necesario es: " +aux.getPedidos().calculaEspacio();
            
        }
        
        return salida;
    }
    
    public double calculaEspacio(ClaveSolicitud cl){
        int salida = 0;
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean control = false;
        
        //busca la ciudad de la que va a sacar la info
        while(aux!=null && !control){
            
            if(aux.getClave().equals(cl)){
                control = true;
            }else{
                aux = aux.getEnlace();
            }   
            
        }
        
        if(control){
            
            salida += aux.getPedidos().calculaEspacio();
        }
        return salida;
    }
    
    //metodo para verificar que existen solicitudes en dicha clave
    public boolean solisPendientes(ClaveSolicitud cl){
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false, exito = false;
        while (!encontrado && aux != null) {
            if(aux.getClave().equals(cl)){
                encontrado = true;
            }else{    
                aux = aux.getEnlace();
            }
        }
        
        if(encontrado){
            exito = !aux.getPedidos().esVacia();
        }
        
        return exito;
        
    }
    
    //eliminar solicitudes asociadas a un cliente
    public void actualizaSistemaClientes(String id, int nroID){
        int i = 0;
        NodoHashMapeoM aux;
        while(i<this.tabla.length){
            
            aux = this.tabla[i];
            while(aux!=null){
                
                int pos = aux.getPedidos().localizarSolicitudAsociada(id, nroID);
                if(pos != -1){
                    
                    aux.getPedidos().eliminar(pos);
                    //si la lista es vacia, elimina la clave
                    if(aux.getPedidos().esVacia()){
                        this.eliminarClave(aux.getClave());
                    } 
                }
                aux = aux.getEnlace();
            }
            i++;
        }
    }
    
    //eliminar las solicitudes asociadas a una ciudad
    public void actualizarSistemaCiudades(int cp){
        int i = 0;
        NodoHashMapeoM aux;
        NodoHashMapeoM anterior;
        while(i<this.tabla.length){
            anterior = null;
            aux = this.tabla[i];
            while(aux!=null){
                
                if(aux.getClave().esCiudad(cp)){
                    if(anterior == null){
                        
                        //si el elemento es la primera posicion(cabecera)
                        this.tabla[i] = this.tabla[i].getEnlace();
                        
                    }else{
                        
                        anterior.setEnlace(aux.getEnlace());
                        
                    }
                    
                    aux = aux.getEnlace();
                    
                }else{
                    anterior = aux;
                    aux = aux.getEnlace();
                }
            }
            i++;
        }
    }
    
    public String toString(){
        //devuelve todas las solicitudes cargadas
        String msg = "";
            int i = 0;
            NodoHashMapeoM aux;
            while(i<this.tabla.length){
                aux = this.tabla[i];
                while(aux!=null){
                    msg += "-" +aux.getClave().toString()+ "\n";
                    msg += aux.getPedidos().toString() + "\n";
                    aux = aux.getEnlace();
                }
                i++;
            }
          return msg;  
    }
    
    public String toStringConClave(ClaveSolicitud cl){
        
        //devuelve las solicitudes con una clave especifica
        String msg = "";
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            if(aux.getClave().equals(cl)){
                encontrado = true;
            }else{    
                aux = aux.getEnlace();
            }
  
        }
        
        if(encontrado){
                
                msg = aux.getPedidos().toString();

        }else{
                
            msg = "ERROR.";
                
        }
        return msg;
    }
    
    
}
