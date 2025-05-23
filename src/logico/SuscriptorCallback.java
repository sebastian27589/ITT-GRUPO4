package logico;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SuscriptorCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Conexi�n Perdida...");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format("--------------- Mensaje Recibido [%s] -----------------", topic));
        System.out.println(String.format("Mensaje Recibido: %s", message.toString()));
        System.out.println(String.format("--------------- Fin Mensaje Recibido [%s] -----------------", topic));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Informaci�n Recibida.");
    }
}
