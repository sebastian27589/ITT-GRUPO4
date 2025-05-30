package sqlconnection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabaseConnection {
	
	private static Session sshSession;
	
    public static void setupSshTunnel(String sshUser, String sshPassword, String sshHost, int sshPort,
            int localPort, String remoteHost, int remotePort) throws Exception {
	JSch jsch = new JSch();
	sshSession = jsch.getSession(sshUser, sshHost, sshPort);
	sshSession.setPassword(sshPassword);
	sshSession.setConfig("StrictHostKeyChecking", "no");
	sshSession.connect();
	sshSession.setPortForwardingL(localPort, remoteHost, remotePort);
	}

    public static Connection getConexion() { 
    	
		String connectionUrl =
//                "jdbc:sqlserver://192.168.100.169:1433;"
//                        + "database=proyectoitt;"
//                        + "user=cristopher;"
//                        + "password=patata123456;"
//                        + "encrypt=true;"
//                        + "trustServerCertificate=true;"
//                        + "loginTimeout=30;";
				
				"jdbc:mysql://localhost:3307/proyectoitt?useSSL=false&serverTimezone=UTC";
        
		

//        try {
//        	Connection conexion = DriverManager.getConnection(connectionUrl);
//        	return conexion;
//
//        } 
//        catch (SQLException e) {
//                System.out.println(e.toString());
//                return null;
//        }
        
        try {
            return DriverManager.getConnection(connectionUrl, "cristopher", "patata123456");
        } catch (SQLException e) {
            System.out.println("Error de conexión a MySQL: " + e);
            return null;
        }
        
    }
    
    public static void closeConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public static void closeSshTunnel() {
        if (sshSession != null && sshSession.isConnected()) {
            sshSession.disconnect();
        }
    }
}
