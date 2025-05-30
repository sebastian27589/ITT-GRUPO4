package logico;

import java.sql.*;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;

/**
 * 
 */
public class Publicador {

    private static final String BROKER_URL = "tcp://mqtt.eict.ce.pucmm.edu.do:1883";
    private MqttClient client;

    public Publicador(String id){
        String clientId = id;
        try {
            client = new MqttClient(BROKER_URL, clientId);
            
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 
     * @param topic
     * @param mensaje
     */
    public void enviarMensaje(String topic, String mensaje){
        System.out.println("Enviando Información Topic: "+ topic);
        try {
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setAutomaticReconnect(true);
            connectOptions.setCleanSession(false);
            connectOptions.setUserName("itt363-grupo4");
            connectOptions.setPassword("Kshq8UuveRLC".toCharArray());
            client.connect(connectOptions);
            client.publish(topic, mensaje.getBytes(), 2, false);
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }

    public static void iniciarPrueba() throws Exception {
        Thread hilo1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Gson gson = new Gson();
                    Sensor sensor1 = new Sensor("estacion-1");
                    new Publicador("estacion-1").enviarMensaje("/itt363-grupo4/estacion-1/sensores", gson.toJson(sensor1));
                    insertarLectura(sensor1);
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo1.start();

        Thread hilo2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Gson gson = new Gson();
                    Sensor sensor2 = new Sensor("estacion-2");
                    new Publicador("estacion-2").enviarMensaje("/itt363-grupo4/estacion-2/sensores", gson.toJson(sensor2));
                    insertarLectura(sensor2);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo2.start();

    }
    
    private static void insertarLectura(Sensor sensor) {
        String url = "jdbc:mysql://localhost:3307/proyectoitt";
        String user = "cristopher";
        String password = "patata123456";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO Lectura (ID_Lectura, Velocidad_Viento, Fecha_Hora_Lectura, Direccion_Viento, Precipitacion, ID_Estacion) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, generarNuevoIdLectura(conn)); // Generar nuevo ID
            stmt.setDouble(2, sensor.getVelocidadViento());
            stmt.setTimestamp(3, new Timestamp(sensor.getFecha().getTime()));
            stmt.setString(4, gradosAViento(sensor.getGradosViento()));
            stmt.setDouble(5, sensor.getPrecipitacion());
            stmt.setInt(6, sensor.getSensorId().equals("estacion-1") ? 1 : 2);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static int generarNuevoIdLectura(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(ID_Lectura) FROM Lectura");
        int id = 1;
        if (rs.next()) {
            id = rs.getInt(1) + 1;
        }
        rs.close();
        stmt.close();
        return id;
    }

    private static String gradosAViento(int grados) {
        if (grados >= 337.5 || grados < 22.5) return "N";
        if (grados < 67.5) return "NE";
        if (grados < 112.5) return "E";
        if (grados < 157.5) return "SE";
        if (grados < 202.5) return "S";
        if (grados < 247.5) return "SO";
        if (grados < 292.5) return "O";
        return "NO";
    }
    
}