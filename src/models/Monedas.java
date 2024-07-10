package models;

import com.google.gson.annotations.SerializedName;

public class Monedas {
    @SerializedName("time_last_update_utc")
    private String date;
    @SerializedName("target_code")
    private String name;
    @SerializedName("conversion_rate")
    private double valorDeMoneda;
    @SerializedName("base_code")
    private String monedaACambiar;
    @SerializedName("conversion_result")
    private  double dineroCambiado;

    public Monedas(String date, String name, double valorDeMoneda, String monedaACambiar, double dineroCambiado) {
        this.date = date;
        this.name = name;
        this.valorDeMoneda = valorDeMoneda;
        this.monedaACambiar = monedaACambiar;
        this.dineroCambiado = dineroCambiado;
    }

    @Override
    public String toString() {
        return "\nA la fecha de: "+ date
                + "\nEl valor de cambio por una unidad de '" + monedaACambiar
                + "' es de: " + valorDeMoneda
                + " por una unidad de la base a cambiar '" + name
                + "'.\nPor lo que el resultado del cambio es de: " +dineroCambiado
                + " " +name;
    }
}
