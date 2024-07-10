import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Monedas;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Scanner teclado = new Scanner(System.in);
        List<Monedas> conversion = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        System.out.println("\n*********************************** ¡Bienvenido! **************************************");
        System.out.println("Bienvenido al sistema de cambio monetario");

        while(true){
            System.out.println("\n¿Que tipo de dinero tienes?");
            System.out.println("""
                    1)MXN       2)ARS       3)USD
                    4)GBP       5)EUR       6)CAD
                    Presiona cualquier otro número para salir
                    """);
            System.out.print("Opción: ");
            int cambio = teclado.nextInt();

            if (cambio >=7 | cambio <= 0) {
                break;
            } else {

                String monedaReferente="";
                String monedaCambio="";

                System.out.println("\nCuanto diero desea convertir? ");
                double dinero =  teclado.nextDouble();

                switch (cambio)
                {
                    case 1:
                        monedaReferente ="MXN";
                        break;
                    case 2:
                        monedaReferente ="ARS";
                        break;
                    case 3:
                        monedaReferente ="USD";
                        break;
                    case 4:
                        monedaReferente ="GBP";
                        break;
                    case 5:
                        monedaReferente ="EUR";
                        break;
                    case 6:
                        monedaReferente ="CAD";
                        break;
                    default:
                        System.out.println("Esa no era una opción, por favor elige otra");
                }

                System.out.println("\n¿A que moneda deseas convertir?");
                System.out.println("""
                    1)MXN       2)ARS       3)USD
                    4)GBP       5)EUR       6)CAD
                    """);
                System.out.print("Opción: ");
                int monedaACambiar = teclado.nextInt();


                switch (monedaACambiar)
                {
                    case 1:
                        monedaCambio ="MXN";
                        break;
                    case 2:
                        monedaCambio ="ARS";
                        break;
                    case 3:
                        monedaCambio ="USD";
                        break;
                    case 4:
                        monedaCambio ="GBP";
                        break;
                    case 5:
                        monedaCambio ="EUR";
                        break;
                    case 6:
                        monedaCambio ="CAD";
                        break;
                    default:
                        System.out.println("Esa no era una opción, por favor elige otra");
                }

//The key Should be in the place of the asterisks
                String direccion = "https://v6.exchangerate-api.com/v6/***********/pair/"
                                    +monedaReferente+"/"+monedaCambio+"/"+dinero;

                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(direccion))
                            .build();
                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    String json = response.body();
//                    System.out.println(json);
                    Monedas moneda = gson.fromJson(json, Monedas.class);

                    System.out.println(moneda + " por la cantidad de: " + dinero + " '"+ monedaReferente +"' que deseas cambiar.");
                    conversion.add(moneda);
                    System.out.println("\n¿Deseas hacer otra operación?");

                } catch (NumberFormatException e) {
                    System.out.println("Ocurrió un error: ");
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error en la URI, verifique la dirección.");
                }
            }
        }

        System.out.println("\nLa lista completa de las consultas fue: \n" + conversion + "\n");
        FileWriter escritura = new FileWriter("conversionesRealizadas.json");
        escritura.write(gson.toJson(conversion));
        escritura.close();
        System.out.println("\nGracias por hacer uso de este sistema de conversión bancaria");
    }
}
