package MudanzasCompartidas;
import ClasesEspeciales.Ciudad;
import ClasesEspeciales.Cliente;
import ClasesEspeciales.Solicitud;
import ClasesEspeciales.ClaveCliente;
import ClasesEspeciales.ClaveSolicitud;
import AVLCiudades.ArbolAVL;
import GrafoRutas.GrafoEtiquetado;
import HashClientes.HashAbierto;
import HashSolicitudes.HashMapeoAMuchos;
import lineales.dinamicas.Lista;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author romangattas
 */

public class MudanzasCompartidas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        ArbolAVL ciudades = new ArbolAVL();
        HashMapeoAMuchos solicitudes = new HashMapeoAMuchos();
        GrafoEtiquetado rutas = new GrafoEtiquetado();
        HashAbierto clientes = new HashAbierto();
        
        String datos = "/Users/romangattas/Downloads/BASE-DE-DATOS.txt";
        int eleccion = 0;
        
        System.out.println("Presione '1' para realizar la carga inicial");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            cargarDatos(datos, ciudades, solicitudes, rutas, clientes);
            String txt1 = "Se ha realizado la carga de datos correctamente. \n";
            System.out.println(txt1);
            txt1 += "ESTADO INICIAL DE LA BASE DE DATOS: \n";
            txt1 += mostrarSistemaAux(ciudades, solicitudes, rutas, clientes);
            registrarMovimiento(txt1);
            ejecutaMenu(ciudades, rutas, clientes, solicitudes);
        }
        
        String txt2 = "ESTADO FINAL DE LA BDD: \n";
        txt2 += mostrarSistemaAux(ciudades, solicitudes, rutas, clientes);
        registrarMovimiento(txt2);
        
        System.out.println("FIN.");
        
    }
    
    //CARGA DE DATOS
    
    public static void cargarDatos(String datos, ArbolAVL ciudades, HashMapeoAMuchos solicitudes,GrafoEtiquetado rutas, HashAbierto clientes){ 
        
        try(BufferedReader bufferLectura = new BufferedReader(new FileReader(datos))){
            String linea;
            while((linea = bufferLectura.readLine()) != null){
               String[] datosLinea = linea.split(";");
                if (datosLinea[0].equals("C")) {
                    cargarCiudad(datosLinea, ciudades);
                }
                if (datosLinea[0].equals("S")) {
                    cargarSolicitud(datosLinea, solicitudes);
                }
                if (datosLinea[0].equals("R")) {
                    cargarRuta(datosLinea, rutas);
                }
                if(datosLinea[0].equals("P")){
                    cargarcliente(datosLinea, clientes);
                } 
            
            }
        
        } catch (IOException ex) {
                System.err.println("Error leyendo o escribiendo en algun archivo.");
        }

    
    }
    
    private static void cargarCiudad(String[] datosCiudad, ArbolAVL ciudades){
        
        int codigoPostal = Integer.parseInt(datosCiudad[1]);
        String nombre = datosCiudad[2];
        String provincia = datosCiudad[3];
        
        Ciudad unaCiudad = new Ciudad(nombre, provincia);
        
        ciudades.insertar(codigoPostal, unaCiudad);
    }
    
    private static void cargarSolicitud(String[] datosSoli, HashMapeoAMuchos solicitudes){
        
        int origen = Integer.parseInt(datosSoli[1]);
        int destino = Integer.parseInt(datosSoli[2]);
        String fecha = datosSoli[3];
        String id = datosSoli[4];
        int nroID = Integer.parseInt(datosSoli[5]);
        double m3 = Double.parseDouble(datosSoli[6]);
        int cantBul = Integer.parseInt(datosSoli[7]);
        String direcRetiro = datosSoli[8];
        String direcEntrega = datosSoli[9];
        boolean pago = ("T").equals(datosSoli[10]);
        
        ClaveSolicitud unaClave = new ClaveSolicitud(origen, destino);
        Solicitud unaSolicitud = new Solicitud(fecha, id, nroID, m3, cantBul, direcRetiro, direcEntrega, pago);
        
        solicitudes.insertar(unaClave, unaSolicitud);
    }
    
    private static void cargarRuta(String[] datosRuta, GrafoEtiquetado rutas){
        
        int origen = Integer.parseInt(datosRuta[1]);
        int destino = Integer.parseInt(datosRuta[2]);
        double distancia = Double.parseDouble(datosRuta[3]);
        
        rutas.insertarVertice(origen);
        rutas.insertarVertice(destino);
        rutas.insertarArco(origen, destino, distancia);
        
    }
    
    private static void  cargarcliente(String[] datosCliente, HashAbierto clientes){
        
        int nroID;
        String id,nombre, apellido, email , telefono;
        
        id = datosCliente[1];
        nroID = Integer.parseInt(datosCliente[2]);
        apellido = datosCliente[3];
        nombre = datosCliente[4];
        telefono = datosCliente[5];
        email = datosCliente[6];
        
        ClaveCliente unaClave = new ClaveCliente(id, nroID);
        Cliente unCliente = new Cliente(nombre, apellido, telefono, email);

        clientes.insertar(unaClave, unCliente);
    
    }
    
    //------------------------------------
    
    // MENU PRINCIPAL
    
    public static void ejecutaMenu(ArbolAVL ciudades, GrafoEtiquetado rutas, HashAbierto clientes, HashMapeoAMuchos solicitudes){
        Scanner sc = new Scanner(System.in);
        int eleccion;
        
        registrarMovimiento("MODIFICACIONES EN EL TRANSCURSO DE LA EJECUCION: ");
        
        do{
            imprimeMenu();
            eleccion = sc.nextInt();
            
            switch(eleccion){
                
                case 0:
                    break;
                case 1:
                    abmCiudades(ciudades, rutas, solicitudes);                           
                    break;
                case 2:
                    abmRutas(rutas);
                    break;
                case 3:
                    abmClientes(clientes, solicitudes);
                    break;
                case 4:
                    abmSolicitudes(solicitudes, ciudades, rutas, clientes);
                    break;
                case 5:
                    consultarCliente(clientes);
                    break;
                case 6:
                    menuConsultarCiudades(ciudades);
                    break;
                case 7:
                    consultarViajes(rutas);
                    break;
                case 8:
                    verificarViaje(solicitudes, rutas);
                    break;
                case 9:
                    mostrarSistema(ciudades, solicitudes, rutas, clientes);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        }while(eleccion != 0);
    
    }
    
    public static void imprimeMenu(){
        
        System.out.println("---MENU---");
        
        System.out.println("0. SALIR.");
        
        System.out.println("1. ALTAS-BAJAS-MODIFICACIONES de CIUDADES.");
        
        System.out.println("2. ALTAS-BAJAS-MODIFICACIONES de RED DE RUTAS.");
        
        System.out.println("3. ALTAS-BAJAS-MODIFICACIONES de CLIENTES.");
        
        System.out.println("4. ALTAS-BAJAS-MODIFICACIONES de SOLICITUDES.");
        
        System.out.println("5. Consulta sobre clientes.");
        
        System.out.println("6. Consulta sobre ciudades.");
        
        System.out.println("7. Consulta sobre viajes.");
        
        System.out.println("8. Verificar viaje.");
        
        System.out.println("9. Mostrar Sistema.");
    
    }   
    
    //----------------------------------
    
    //1 - ABM CIUDADES
    
    public static void abmCiudades(ArbolAVL ciudades, GrafoEtiquetado rutas, HashMapeoAMuchos solicitudes){
        
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMCiudades();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarCiudad(ciudades, rutas);
                    break;              
                case 2:
                    eliminarCiudad(ciudades, rutas, solicitudes);
                    break;
                case 3:
                    modificarCiudad(ciudades);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        }while(eleccion != 0);
    
    }
    
    private static void menuABMCiudades(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar una ciudad.");  
        System.out.println("2. Eliminar una ciudad.");
        System.out.println("3. Modificar una ciudad.");
        
    
    }
    
    //ALTAS
    private static void agregarCiudad(ArbolAVL ciudades, GrafoEtiquetado rutas){
        
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int cp, eleccion;
        String nombre, provincia;
        System.out.println("ALTA DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el numero postal.");
            cp = sc.nextInt();
            System.out.println("Ingrese el nombre de la ciudad.");
            nombre = sc.next();
            System.out.println("Ingrese la provincia.");
            provincia = sc.next();

            Ciudad unaCiudad = new Ciudad(nombre, provincia);

            boolean exito = ciudades.insertar(cp, unaCiudad);
            
            if(exito){
                
                txt = ("Se agrego la ciudad: " +unaCiudad.toString()+ " correctamente.");
                System.out.println(txt);
                registrarMovimiento(txt);
                
                //actualiza el sistema, agrega la ciudad al grafo
                rutas.insertarVertice(cp);
                

            }else{

                System.out.println("ERROR. No ha sido posible agregar la ciudad.");

            }
        }
    }
    
    //BAJAS
    private static void eliminarCiudad(ArbolAVL ciudades, GrafoEtiquetado rutas, HashMapeoAMuchos solicitudes){
        
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int cp, eleccion;
        System.out.println("BAJA DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el numero postal de la ciudad que desea dar de BAJA.");

            cp = sc.nextInt();

            boolean exito = ciudades.eliminar(cp);
            
            if(exito){
                
                txt = "Ciudad con C.P: " +cp+", eliminada correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);
                
                //actualizo el sistema, se elimina tambien el vertice del grafo y sus rutas,
                //como tambien las solicitudes que involucren la ciudad
                rutas.eliminarVertice(cp);
                solicitudes.actualizarSistemaCiudades(cp);
                
                
            }else{
                
                System.out.println("ERROR. No ha sido posible eliminar la ciudad.");
                
            }
        }        
    }
    
    //MODIFICACIONES
    private static void modificarCiudad(ArbolAVL ciudades){
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int cp, eleccion;
        String nombre, provincia;
        System.out.println("MODIFICACION DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el numero postal de la ciudad que desea modificar");
            cp = sc.nextInt();
            Ciudad unaCiudad = ciudades.obtenerDato(cp);

            if(unaCiudad != null){

                System.out.println("La ciudad que va a modificar es: \n" +unaCiudad.toString()); 
                System.out.println("1. CONTINUAR.");
                System.out.println("2. CANCELAR.");
                eleccion = sc.nextInt();

                if(eleccion == 1){
                    System.out.println("Ingrese el nombre de la ciudad.");
                    nombre = sc.next();
                    System.out.println("---");
                    System.out.println("Ingrese el nombre de la provincia.");
                    provincia = sc.next();

                    unaCiudad.setNombre(nombre); 
                    unaCiudad.setProvincia(provincia);
                    
                    txt = "Los datos de la ciudad: " +unaCiudad.toString()+ " han sido modificados.";
                    System.out.println(txt);
                    registrarMovimiento(txt);

                }else{

                    System.out.println("OPERACION CANCELADA.");

                }


            }else{

                System.out.println("El codigo postal es INCORRECTO.");

            }
        }
    } 
    
    //-----------------------
    
    //2 - ABM RUTAS 
    
    public static void abmRutas(GrafoEtiquetado rutas){
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMRutas();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarRuta(rutas);
                    break;              
                case 2:
                    eliminarRuta(rutas);
                    break;
                case 3:
                    modificarRuta(rutas);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        }while(eleccion != 0);
        
        
    }
    
    public static void menuABMRutas(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar una ruta.");  
        System.out.println("2. Eliminar una ruta.");
        System.out.println("3. Modificar una ruta.");
        
    }
    
    //ALTAS 
    public static void agregarRuta(GrafoEtiquetado rutas){
        
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int origen, destino, eleccion;
        double distancia;
        System.out.println("ALTA DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el C.P de la ciudad origen");
            origen = sc.nextInt();

            System.out.println("Ingrese el C.P de la ciudad destino");
            destino = sc.nextInt();

            System.out.println("Ingrese la distancia entre las ciudades");
            distancia = sc.nextDouble();
            
            if(!rutas.existeArco(origen, destino)){
                
                if(rutas.insertarArco(origen, destino, distancia)){

                    txt = "Se agrego la ruta desde: "+origen+ " a: " +destino+ " correctamente.";
                    System.out.println(txt);
                    registrarMovimiento(txt);
                    
                }else{
                    
                    System.out.println("ERROR. No se pudo agregar la ruta.");
                    
                }
                
            }else{
                
                System.out.println("ERROR. Ya existe la ruta.");
                
            }
        }    
    }
    
    //BAJAS
    public static void eliminarRuta(GrafoEtiquetado rutas){
        
        Scanner sc = new Scanner(System.in);
        int origen, destino, eleccion;
        String txt = "";
        System.out.println("BAJA DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el C.P de la ciudad origen");
            origen = sc.nextInt();

            System.out.println("Ingrese el C.P de la ciudad destino");
            destino = sc.nextInt();

            boolean exito = rutas.eliminarArco(origen, destino);

            if(exito){
                
                txt = "Se borro el tramo de ruta entre las ciudades: " +origen+ " y: " +destino+ " correctamente."; 
                System.out.println(txt);
                registrarMovimiento(txt);
                
            }else{
                System.out.println("ERROR. No se pudo eliminar la ruta.");
            }
        }
        
        
    }
    
    //MODIFICACIONES
    public static void modificarRuta(GrafoEtiquetado rutas){
        
        Scanner sc = new Scanner(System.in);
        int origen, destino, eleccion;
        double distancia;
        String txt = "";
        
        System.out.println("MODIFICACION DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            
            System.out.println("De la ruta puede modificarse la distancia.");

            System.out.println("Ingrese el C.P de la ciudad origen");
            origen = sc.nextInt();

            System.out.println("Ingrese el C.P de la ciudad destino");
            destino = sc.nextInt();

            boolean exito = rutas.eliminarArco(origen, destino);

            if(exito){

                System.out.println("Ingrese la distancia MODIFICADA.");
                distancia = sc.nextDouble();
                rutas.insertarArco(origen, destino, distancia);
                
                txt = "Se modifico la distancia de la ruta " +origen+ " a " +destino+ " y ahora es: " +distancia+ "km.";
                System.out.println(txt);
                registrarMovimiento(txt);

            }else{

                System.out.println("ERROR. No se pudo modificar la ruta.");

            }
        }    
    }
    
    //-----------------------
   
    //3 - ABM CLIENTES
    
    public static void abmClientes(HashAbierto clientes, HashMapeoAMuchos solicitudes){
        
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMClientes();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarCliente(clientes);
                    break;              
                case 2:
                    eliminarCliente(clientes, solicitudes);
                    break;
                case 3:
                    modificarCliente(clientes);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        }while(eleccion != 0);
    
    }
    
    private static void menuABMClientes(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar un cliente.");  
        System.out.println("2. Eliminar un cliente.");
        System.out.println("3. Modificar un cliente.");
        
    
    }
    
    //ALTAS 
    private static void agregarCliente(HashAbierto clientes){
        
        Scanner sc = new Scanner(System.in);
        int nroID, eleccion;
        String id, nombre, apellido, email, telefono;
        String txt = "";
        
        System.out.println("ALTA DE UN CLIENTE");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el ID.");
            id = sc.next();
            System.out.println("Ingrese el nroID.");
            nroID = sc.nextInt();
            System.out.println("Ingrese el nombre.");
            nombre = sc.next();
            System.out.println("Ingrese el apellido.");
            apellido = sc.next();
            System.out.println("Ingrese el numero de telefono.");
            telefono = sc.next();
            System.out.println("Ingrese el email.");
            email = sc.next();

            ClaveCliente unaClave = new ClaveCliente(id, nroID);
            Cliente unCliente = new Cliente(nombre, apellido, telefono, email);

            boolean exito = clientes.insertar(unaClave, unCliente);

            if(exito){
                
                txt = "Se agrego el cliente: " +unCliente.toString()+ " correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);

            }else{

                System.out.println("ERROR. No ha sido posible agregar la ciudad.");

            }
        }    
    }
    
    //BAJAS  
    private static void eliminarCliente(HashAbierto clientes, HashMapeoAMuchos solicitudes){
        
        Scanner sc = new Scanner(System.in);
        String id;
        int nroID, eleccion;
        String txt = "";
        
        System.out.println("BAJA DE UN CLIENTE.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el tipo de ID del cliente que desea ELIMINAR.");
            id = sc.next();
            System.out.println("Ingrese el numero de ID del cliente que desea ELIMINAR.");
            nroID = sc.nextInt();
            ClaveCliente unaClave = new ClaveCliente(id, nroID);

            boolean exito = clientes.eliminar(unaClave);

            if(exito){
                
                txt = "Se elimino el cliente con " +unaClave.toString()+ " correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);
                
                //actualizo el sistema, se eliminan las solicitudes que tienen involucrado el cliente
                solicitudes.actualizaSistemaClientes(id, nroID);
                
            }else{

                System.out.println("ERROR. El cliente no se pudo eliminar.");

            }
        }
        
    }
    
    //MODIFICACIONES  
    private static void modificarCliente(HashAbierto clientes){
        Scanner sc = new Scanner(System.in);
        int nroID, eleccion;
        String id, nom, ap, email, tel;
        String txt = "";
        
        System.out.println("MODIFICACION DE UN CLIENTE.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
                
            System.out.println("Ingrese el tipo de ID del cliente que desea MODIFICAR.");
            id = sc.next();

            System.out.println("Ingrese el numero de ID del cliente que desea MODIFICAR.");
            nroID = sc.nextInt();

            ClaveCliente unaClave = new ClaveCliente(id, nroID);

            Cliente clienteAModificar = clientes.obtenerDato(unaClave);

            if(clienteAModificar != null){
                System.out.println("-Datos del cliente a modificar: \n" +clienteAModificar.toString());
                System.out.println("1. CONTINUAR.");
                System.out.println("2. CANCELAR.");
                eleccion = sc.nextInt();

                if(eleccion == 1){

                    System.out.println("Ingrese el nombre del cliente.");
                    nom = sc.next();

                    System.out.println("Ingrese el apellido del cliente.");
                    ap = sc.next();

                    System.out.println("Ingrese el numero de telefono.");
                    tel = sc.next();

                    System.out.println("Ingrese el email.");
                    email = sc.next();

                    clienteAModificar.setNombre(nom);
                    clienteAModificar.setApellido(ap);
                    clienteAModificar.setTelefono(tel);
                    clienteAModificar.setEmail(email);
                    
                    txt = "Los datos del cliente con: " +unaClave.toString()+ " fueron modificados.";
                    System.out.println(txt);
                    registrarMovimiento(txt);

                }else{

                    System.out.println("OPERACION CANCELADA.");

                }
            }else{

                System.out.println("ERROR. El cliente no se pudo modificar.");

            }
        }    
    } 
    
    //4 - ABM SOLICITUDES
    
    public static void abmSolicitudes(HashMapeoAMuchos solicitudes, ArbolAVL ciudades, GrafoEtiquetado rutas, HashAbierto clientes){
        
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMSolicitudes();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarSolicitud(solicitudes, ciudades, rutas, clientes);
                    break;              
                case 2:
                    eliminarSolicitud(solicitudes);
                    break;
                case 3:
                    modificarSolicitud(solicitudes);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        }while(eleccion != 0);
    
    }
    
    private static void menuABMSolicitudes(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar una solicitud.");  
        System.out.println("2. Eliminar una solicitud.");
        System.out.println("3. Modificar una solicitud.");
        
    
    }
    
    
    //ALTAS 
    public static void agregarSolicitud(HashMapeoAMuchos solicitudes, ArbolAVL ciudades, GrafoEtiquetado rutas, HashAbierto clientes){
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int origen, destino, nroID, bultos, eleccion;
        String fecha, id, domRetiro, domEntrega;
        double m3;
        boolean pago;
        
        System.out.println("ALTA DE UNA SOLICITUD.");
        
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el C.P de la ciudad origen.");
            origen = sc.nextInt();

            System.out.println("Ingrese el C.P de la ciudad destino.");
            destino=sc.nextInt();
            
            if(ciudades.pertenece(origen) && ciudades.pertenece(destino)){
                
                if(rutas.existeCamino(origen, destino)){

                    System.out.println("Ingrese el ID del cliente.");
                    id = sc.next();

                    System.out.println("Ingrese el NRO ID del cliente.");
                    nroID = sc.nextInt();
                    
                    ClaveCliente claveCli = new ClaveCliente(id, nroID);
                    if(clientes.pertenece(claveCli)){
                    
                        System.out.println("Ingrese la fecha.");
                        fecha = sc.next();

                        System.out.println("Ingrese la cantidad de bultos del envio.");
                        bultos = sc.nextInt();

                        System.out.println("Ingrese los m3.");
                        m3 = sc.nextDouble();

                        System.out.println("Ingrese el domicilio de Retiro.");
                        domRetiro = sc.next();
                        
                        System.out.println("Ingrese el domicilio de Entrega.");
                        domEntrega = sc.next();
                        
                        System.out.println("Desea dejar el envio pagado? 1.SI ; 2.NO");
                        eleccion = sc.nextInt();
                        pago = (eleccion == 1);
                     
                        ClaveSolicitud unaClave = new ClaveSolicitud(origen, destino);
                        Solicitud unaSolicitud = new Solicitud(fecha, id, nroID, m3, bultos, domRetiro, domEntrega, pago);

                        solicitudes.insertar(unaClave, unaSolicitud);
                        txt = "Solicitud con ciudad origen: " +origen+ " y destino: " +destino+ " AGREGADA correctamente.";
                        System.out.println(txt);
                        registrarMovimiento(txt);
                        
                    }else{
                        
                        System.out.println("No existe el cliente.");
                        System.out.println("Debe agregarlo desde ABM CLIENTES.");
                        
                    }
                    
                }else{
                    
                    System.out.println("No existe la ruta.");
                    System.out.println("Debe agregarla desde ABM RUTAS.");
                }
                
            }else{
                
                System.out.println("La ciudad de origen y/o destino no existe.");
                System.out.println("Debe agregarla desde ABM CIUDADES.");
                
            }
        }
    }

    //BAJAS
    public static void eliminarSolicitud(HashMapeoAMuchos solicitudes){
        Scanner sc = new Scanner(System.in);
        int origen, destino, nroSolicitud, eleccion;
        String txt = "";
        
        System.out.println("BAJA DE UNA SOLICITUD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el C.P de la ciudad origen.");
            origen = sc.nextInt();

            System.out.println("Ingrese el C.P de la ciudad destino.");
            destino=sc.nextInt();

            ClaveSolicitud unaClave = new ClaveSolicitud(origen, destino);

            String msg = solicitudes.toStringConClave(unaClave);

            if(msg.equals("ERROR.")){

                System.out.println("La clave ingresada no existe.");

            }else{

                System.out.println("Seleccione el NRO de solicitud que desea borrar.");
                System.out.println(msg);

                nroSolicitud = sc.nextInt();       

                boolean res = solicitudes.eliminar(unaClave, nroSolicitud);


                if(res){
                    txt = "Solicitud con ciudad origen: " +origen+ " y destino: " +destino+ " ELIMINADA correctamente.";
                    System.out.println(txt);
                    registrarMovimiento(txt);

                }else{

                    System.out.println("ERROR. La solicitud no existe.");

                }
            }

            //si no quedan solicitudes con esa clave, elimina la clave

            if(!solicitudes.solisPendientes(unaClave)){
                if(solicitudes.eliminarClave(unaClave)){
                    System.out.println("Se elimino la clave ya que no hay solicitudes pendientes.");
                    registrarMovimiento("Se elimino la clave ya que no hay solicitudes pendientes.");
                }
            }
        }
    }
    
    //MODIFICACIONES   
    public static void modificarSolicitud(HashMapeoAMuchos solicitudes){
        Scanner sc = new Scanner(System.in);
        int origen, destino, nroID, bultos, eleccion, nroSolicitud;
        String fecha, id, domRetiro, domEntrega;
        double m3;
        boolean pago;
        String txt = "";
        
        System.out.println("MODIFICACION DE UNA SOLICITUD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el C.P de la ciudad origen.");
            origen = sc.nextInt();

            System.out.println("Ingrese el C.P de la ciudad destino.");
            destino=sc.nextInt();

            ClaveSolicitud unaClave = new ClaveSolicitud(origen, destino);

            String msg = solicitudes.toStringConClave(unaClave);

            if(msg.equals("ERROR.")){

                System.out.println("La clave ingresada no existe.");

            }else{

                System.out.println("Seleccione el NRO de solicitud que desea modificar.");
                System.out.println(msg);
                nroSolicitud = sc.nextInt();

                Solicitud soliAModificar = solicitudes.obtenerDato(unaClave, nroSolicitud);

                if(soliAModificar != null){

                    System.out.println("-Datos de la solicitud a modificar: \n" +soliAModificar.toString());
                    System.out.println("1. CONTINUAR.");
                    System.out.println("2. CANCELAR.");
                    eleccion = sc.nextInt();

                    if(eleccion == 1){

                        System.out.println("Ingreso de datos para modificar la solicitud:");

                        System.out.println("Ingrese la fecha.");
                        fecha = sc.next();

                        System.out.println("Ingrese el ID del cliente.");
                        id = sc.next();

                        System.out.println("Ingrese el NRO ID del cliente.");
                        nroID = sc.nextInt();

                        System.out.println("Ingrese la cantidad de bultos del envio.");
                        bultos = sc.nextInt();

                        System.out.println("Ingrese los m3.");
                        m3 = sc.nextDouble();

                        System.out.println("Ingrese el domicilio de Retiro.");
                        domRetiro = sc.nextLine();
                        sc.next();

                        System.out.println("Ingrese el domicilio de Entrega.");
                        domEntrega = sc.nextLine();
                        sc.next();
                        
                        System.out.println("Desea dejar el envio pagado? 1.SI ; 2.NO");
                        eleccion = sc.nextInt();
                        pago = (eleccion == 1);

                        soliAModificar.setFechaSolicitud(fecha);
                        soliAModificar.setTipoID(id);
                        soliAModificar.setNroID(nroID);
                        soliAModificar.setBultos(bultos);
                        soliAModificar.setM3(m3);
                        soliAModificar.setDomRetiro(domRetiro);
                        soliAModificar.setDomEntrega(domEntrega);
                        soliAModificar.setPago(pago);
                        
                        txt = "Solicitud con ciudad origen: " +origen+ " y destino: " +destino+ " MODIFICADA correctamente.";
                        System.out.println(txt);
                        registrarMovimiento(txt);

                    }else{

                        System.out.println("OPERACION CANCELADA.");

                    }    

                }else{

                    System.out.println("ERROR. No se pudo modificar la solicitud.");

                }  
            }
        }
    }
     
    //-----------------------
    
    //5 - CONSULTA SOBRE CLIENTES
    
    public static void consultarCliente(HashAbierto clientes){
        Scanner sc = new Scanner(System.in);
        int nroID, eleccion;
        String id;
        System.out.println("CONSULTAR CLIENTE.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el tipo de ID del cliente que desea consultar.");
            id = sc.next();

            System.out.println("Ingrese el numero de ID del cliente que desea consultar.");
            nroID = sc.nextInt();

            ClaveCliente unaClave = new ClaveCliente(id, nroID);

            Cliente clienteAConsultar = clientes.obtenerDato(unaClave);

            if(clienteAConsultar != null){
                System.out.println("Los datos del cliente ingresado son: \n" +clienteAConsultar.toString());
            }else{
                System.out.println("El cliente no se encuentra cargado en el sistema.");
            }
        }    
    }
    
    //-----------------------
    
    // 6 - CONSULTA SOBRE CIUDADES
    
    public static void menuConsultarCiudades(ArbolAVL ciudades){
        Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuConsCiud();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    consultarUnaCiudad(ciudades);
                    break;              
                case 2:
                    ciudadesEnRango(ciudades);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        }while(eleccion != 0);
    }
    
    public static void menuConsCiud(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Consultar datos de una ciudad.");
        System.out.println("2. Consultar los datos de las ciudades que tiene el mismo prefijo.");
        
    }
    
    //CONSULTAR DATOS DE UNA CIUDAD
    
    public static void consultarUnaCiudad(ArbolAVL ciudades){
        Scanner sc = new Scanner(System.in);
        int cp, eleccion;
        System.out.println("CONSULTAR DATOS DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el numero postal.");
            cp = sc.nextInt();

            Ciudad unaCiudad = ciudades.obtenerDato(cp);

            if(unaCiudad != null){

                System.out.println("Datos de la ciudad: \n" +unaCiudad.toString()); 

            }else{

                System.out.println("La ciudad no se encuentra cargada en el sistema.");

            }
        }    
    }
    
    //CONSULTAR RANGO DE CIUDADES
    
    public static void ciudadesEnRango(ArbolAVL ciudades){
        Scanner sc = new Scanner(System.in);
        int prefijo, eleccion;
        System.out.println("CONSULTAR CIUDADES CARGADAS POR PREFIJO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese un preijo para conocer todas las ciudades con el mismo(PRIMEROS 2 NUMEROS).");
            prefijo = sc.nextInt();
            if(prefijo >9 && prefijo <100){
                Lista ciudadesRango = ciudades.listarRango(prefijo*100, (prefijo*100)+99);
                if(!ciudadesRango.esVacia()){
                    System.out.println("Ciudades en el rango seleccionado: ");
                    System.out.println(ciudadesRango.toString());
                }else{
                    System.out.println("No hay ciudades cargas con el prefijo seleccionado.");
                }
            }else{
                System.out.println("El prefijo ingresado es invalido.");
            }
        }    
    }
    
    //-----------------------
    
    //7 - CONSULTAS SOBRE VIAJES
    
    public static void consultarViajes(GrafoEtiquetado rutas){
        Scanner sc = new Scanner(System.in);
        int eleccion;
        do{
            menuConsultarViajes();
            eleccion = sc.nextInt();
            
            switch(eleccion){
                
                case 0:
                    break;
                case 1:
                    caminoMenosCiudades(rutas);
                    break;              
                case 2:
                    caminoMenosKm(rutas);
                    break; 
                default:
                    System.out.println("Ingrese una opcion valida");
                    
            }

        }while(eleccion != 0);
    
    }
    
    private static void menuConsultarViajes(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Obtener el camino que llegue de A a B que pase por menos ciudades.");
        System.out.println("2. Obtener el camino que llegue de A a B de menor distancia en kilómetros.");
          
    }
    
    //CAMINO QUE PASA POR MENOS CIUDADES
    public static void caminoMenosCiudades(GrafoEtiquetado rutas){
        Scanner sc = new Scanner(System.in);
        int eleccion, origen, destino;
        
        System.out.println("OBTENER CAMINO QUE PASA POR MENOS CIUDADES.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese la ciudad de origen");
            origen = sc.nextInt();
            System.out.println("Ingrese la ciudad de destino");
            destino = sc.nextInt();
            
            Lista camino = rutas.caminoMasCorto(origen, destino);
            
            if(!camino.esVacia()){
                System.out.println("El camino que pasa por menos ciudades es:");
                System.out.println(camino.toString());
            }else{
                System.out.println("ERROR. No existe camino");
            }
            
        }
    }
    
    //CAMINO MAS RAPIDO
    public static void caminoMenosKm(GrafoEtiquetado rutas){
        Scanner sc = new Scanner(System.in);
        int eleccion, origen, destino;
        
        System.out.println("OBTENER CAMINO MAS RAPIDO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese la ciudad de origen");
            origen = sc.nextInt();
            System.out.println("Ingrese la ciudad de destino");
            destino = sc.nextInt();
            
            HashMap camino = rutas.caminoMasRapido(origen, destino);
            
            if(!camino.isEmpty()){
                System.out.println("El camino que pasa por menos ciudades es:");
                Lista elCamino = (Lista)camino.get("caminoMasRapido");
                System.out.println(elCamino.toString());
                System.out.println("Con un total de km de: "+(double)camino.get("distancia"));
                
            }else{
                System.out.println("ERROR. No existe camino");
            }
            
        }
    }
    
    //-----------------------
    
    // 8 - VERIFICAR VIAJE 
    
    public static void verificarViaje(HashMapeoAMuchos solicitudes, GrafoEtiquetado rutas){
        Scanner sc = new Scanner(System.in);
        int eleccion;
        do{
            menuVerificaViaje();
            eleccion = sc.nextInt();
            
            switch(eleccion){
                
                case 0:
                    break;
                case 1:
                    muestraPedidos(solicitudes);
                    break;              
                case 2:
                    caminoPerfecto(solicitudes, rutas);
                    break; 
                default:
                    System.out.println("Ingrese una opcion valida");
                    
            }

        }while(eleccion != 0);
    
    }
    
    private static void menuVerificaViaje(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Mostrar pedidos entre una ciduad A y B. Calcula el espacio necesario del camion.");
        System.out.println("2. Camino perfecto.");
        
        
    }
    
    
    //PEDIDOS ENTRE UNA CIUDAD A Y B, MAS EL ESPACIO NECESARIO
    
    public static void muestraPedidos(HashMapeoAMuchos solicitudes){
        Scanner sc = new Scanner(System.in);
        int eleccion, origen, destino;
        
        System.out.println("MOSTRAR SOLICITUDES ENTRE 2 CIUDADES Y EL ESPACIO NECESARIO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
 
            System.out.println("Ingrese la ciudad de origen");
            origen = sc.nextInt();
            System.out.println("Ingrese la ciudad de destino");
            destino = sc.nextInt();
            
            

            ClaveSolicitud unaClave = new ClaveSolicitud(origen, destino);
            
            if(solicitudes.solisPendientes(unaClave)){

                System.out.println(solicitudes.solisYCargaCamion(unaClave));
            
            }else{
                
                System.out.println("ERROR. No existen solicitudes.");
                
            }
        }
        
    }
    
    //CAMINO PERFECTO
    
    public static void caminoPerfecto(HashMapeoAMuchos solicitudes, GrafoEtiquetado rutas){
        
        Scanner sc = new Scanner(System.in);
        int eleccion;
        System.out.println("CONSULTAR SI EXISTE UN CAMINO PERFECTO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            Lista listaCiudades = new Lista();
            int datoCiudad = 1;
            double capacidadM3 = 0;
            
            //ingreso de datos de la lista de ciudades.
            while(datoCiudad != 0){
                System.out.println("Para dejar ingresar ciudades seleccione '0'.");
                System.out.println("Ingrese el C.P de la ciudad.");
                datoCiudad = sc.nextInt();
                listaCiudades.insertar(datoCiudad, listaCiudades.longitud()+1);
            }
            
            System.out.println("Ingrese los m3 de capacidad que tiene el camion.");
            capacidadM3 = sc.nextDouble();
            
            boolean caminoPerf = false;
            
            //verifica que existan al menos 2 ciudades en la lista
            if(listaCiudades.longitud()>1){
                caminoPerf = caminoPerfectoAux(solicitudes, rutas, listaCiudades, capacidadM3);
            }
            
            if(caminoPerf){
                System.out.println("Existe un camino perfecto para los datos ingresados.");
            }else{
                System.out.println("No existe un camino perfecto para los datos ingresados.");
            }
        }
    }
    
    private static boolean caminoPerfectoAux(HashMapeoAMuchos solicitudes, GrafoEtiquetado rutas, Lista ls, double capacidadM3) {
        int i = 1;
        int j = 2;
        int longLis = ls.longitud();
        double contM3 = 0;
        boolean existe = true;
        while (j <= longLis && existe) {
            int ciudad1 = (int) ls.recuperar(i);
            int ciudad2 = (int) ls.recuperar(j);
            //si existe una ruta entre la primer ciudad y la siguiente
            if (rutas.existeArco(ciudad1, ciudad2)) {
                int c2 = j;
                boolean existeSolicitud = false;
                //busca que haya algun pedido entre la primer ciudad y la siguientes
                //y verifica que el espacio sea suficiente
                while (c2 <= longLis && contM3<=capacidadM3) {
                    //crea clave con la primer ciudad y las que siguen para buscar solicitudes
                    ClaveSolicitud claveBuscada = new ClaveSolicitud(ciudad1, (int) ls.recuperar(c2));
                    if (solicitudes.solisPendientes(claveBuscada)) {
                        existeSolicitud = true;
                        contM3 += solicitudes.calculaEspacio(claveBuscada);
                    }
                    c2++;
                }
                //si no se econtro una solicitud entre la primer ciudad y la siguientes
                // o la carga supera la capacidad, no existe camino perfecto
                if(!existeSolicitud || contM3 >= capacidadM3){
                    existe = false;
                }

            } else {
                existe = false;
            }
            i++;
            j++;
        }

        return existe;
    }
    
    //-----------------------
    
    // 9 - MOSTRAR SISTEMA
    public static void mostrarSistema(ArbolAVL ciudades, HashMapeoAMuchos solicitudes,GrafoEtiquetado rutas, HashAbierto clientes){
        Scanner sc = new Scanner(System.in);
        int eleccion;
        
        System.out.println("MOSTRAR EL SISTEMA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println(mostrarSistemaAux(ciudades, solicitudes, rutas, clientes));
        }
    }
    
    private static String mostrarSistemaAux(ArbolAVL ciudades, HashMapeoAMuchos solicitudes,GrafoEtiquetado rutas, HashAbierto clientes){
        String txt = "AVL DE CIUDADES: \n\n" +
                ciudades.toString() + "\n\n" +
                "HASH ABIERTO DE CLIENTES: \n\n" +
                clientes.toString() + "\n\n" +
                "HASH MAPEO A MUCHOS DE SOLICITUDES: \n\n" +
                solicitudes.toString() + "\n\n" +
                "GRAFO ETIQUETADO DE RUTAS: \n\n" +
                rutas.toString();
        return txt;

    }
    
    //-----------------------
    
    //ESCRIBE EN ARCHIVO DE SALIDA
    public static void registrarMovimiento(String datosLinea) {
        try {
            FileWriter archivoRegistros = new FileWriter("SELECCIONE RUTA", true);
            try (BufferedWriter bufEscritura = new BufferedWriter(archivoRegistros)) {
                bufEscritura.write(datosLinea);
                bufEscritura.newLine();
            }
        } catch (IOException e) {
            System.out.println("El registro no ha podido hacerse");
        }
    }
    
}
