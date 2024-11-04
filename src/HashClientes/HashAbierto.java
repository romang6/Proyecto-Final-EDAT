package HashClientes;


import ClasesEspeciales.Cliente;
import ClasesEspeciales.ClaveCliente;

/**
 *
 * @author romangattas
 */

public class HashAbierto {
    
    private static final int TAMANIO = 100;
    private NodoHash[] tabla;
    private int cant;
    
    public HashAbierto(){
        tabla = new NodoHash[TAMANIO];
        cant = 0;
    }
    
    public boolean insertar(ClaveCliente cl, Cliente cli) {
        // primero verifica si el cl ya esta cargado
        // si no lo encuentra, lo pone adelante del resto
        int pos = cl.hashCode();
        NodoHash aux = this.tabla[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(cl);
            aux = aux.getEnlace();
        }

        if(!encontrado){
            this.tabla[pos] = new NodoHash(cl, cli, this.tabla[pos]);
            this.cant++;
        }

        return !encontrado;
    
    }
    
    public boolean eliminar(ClaveCliente cl){
        int pos = cl.hashCode();
        NodoHash aux = this.tabla[pos];
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
    
    public boolean pertenece(ClaveCliente cl){
        int pos = cl.hashCode();
        NodoHash aux = this.tabla[pos];
        boolean resultado = false;
        while(aux!=null && !resultado){
            if(aux.getClave().equals(cl)){
                resultado = true;
            }else{
                aux = aux.getEnlace();
            }    
        }
        return resultado;
    }
    
    public Cliente obtenerDato(ClaveCliente cl){
        
        int pos = cl.hashCode();
        NodoHash aux = this.tabla[pos];
        Cliente buscado = null;
        boolean control = false;
        while(aux!=null && !control){
            if(aux.getClave().equals(cl)){
                buscado = aux.getCliente();
                control = true;
            }
            aux = aux.getEnlace();
        }
        return buscado;
    }
    
    @Override
    public String toString(){
        String msg = "";
        int i = 0;
        NodoHash aux;
        while(i<this.cant){
            aux = this.tabla[i];
            while(aux!=null){
                msg += aux.getClave().toString() + "\n";
                msg += aux.getCliente().toString() + "\n";
                aux = aux.getEnlace();
                msg += "---" + "\n";
            }
            i++;
        }
      return msg;  
    }
    
}
