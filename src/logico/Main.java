package logico;

import java.util.Scanner;
import sqlconnection.SQLDatabaseConnection;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) throws Exception {
    	
    try {
        // Conexi�n SSH
        SQLDatabaseConnection.setupSshTunnel(
            "sistema",           // usuario del sistema en el servidor SSH
            "12345678",          // contrase�a SSH
            "192.168.100.169",        // host SSH
            22,                       // puerto SSH
            3307,                     // puerto local que ser� redirigido
            "localhost",              // host del servidor MySQL desde la perspectiva del SSH
            3306                      // puerto real de MySQL en el servidor
        );
        
        // Conexi�n a SQL Server
        Connection conn = SQLDatabaseConnection.getConexion();
        if (conn != null) {
            System.out.println("Conexi�n a la base de datos exitosa.");
        } else {
            System.out.println("No se pudo conectar a la base de datos.");
        }
            
        //Creando el subcriptor
        Suscriptor cliente = new Suscriptor();
        cliente.start();

        //iniciando el Publicador
        Publicador.iniciarPrueba();

        //
        System.out.println("Para salir presionar Enter");
        Scanner s = new Scanner(System.in);
        String dato = s.nextLine();
        System.exit(0);
        
        // Cierre de la conexi�n
        SQLDatabaseConnection.closeConexion(conn);
        SQLDatabaseConnection.closeSshTunnel();
        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
}
