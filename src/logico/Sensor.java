package logico;

import java.util.Date;

public class Sensor {

    private String sensorId;
    private int precipitacion;
    private int gradosViento;
    private int velocidadViento;
    private Date fecha = new Date();

    public Sensor(String id){
        setSensorId(id);
        int valorPrecipitacion = (int) (Math.random() * (3000 - 50 + 1)) + 50;
        int valorVelocidadViento = (int) (Math.random() * (25 - 15 + 1)) + 15;
        int valorGrado = (int) (Math.random() * (360 - 0 + 1)) + 0;
        setPrecipitacion(valorPrecipitacion);
        setGradosViento(valorGrado);
        setVelocidadViento(valorVelocidadViento);
    }

    public int getGradosViento() {
		return gradosViento;
	}

	public void setGradosViento(int gradosViento) {
		this.gradosViento = gradosViento;
	}

	public int getVelocidadViento() {
		return velocidadViento;
	}

	public void setVelocidadViento(int velocidadViento) {
		this.velocidadViento = velocidadViento;
	}

	public float getPrecipitacion() {
        return precipitacion;
    }

    public void setPrecipitacion(int precipitacion) {
        this.precipitacion = precipitacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}