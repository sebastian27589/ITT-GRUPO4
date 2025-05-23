package logico;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Representa el suscriptor
 */
public class Suscriptor {

    public static final String BROKER_URL = "tcp://mqtt.eict.ce.pucmm.edu.do:1883";
    private MqttClient client;

    public Suscriptor() {

        String clientId = "suscriptor-1";
        try {
            client = new MqttClient(BROKER_URL, clientId);
        }
        catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Inicio de la 
     */
    public void start() {

        try {
            client.setCallback(new SuscriptorCallback());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setAutomaticReconnect(true);
            connectOptions.setCleanSession(false);  
            connectOptions.setUserName("itt363-grupo4");
            connectOptions.setPassword("Kshq8UuveRLC".toCharArray());
            client.connect(connectOptions);
            client.subscribe("/itt363-grupo4/#"); 
        }
        catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
