import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String ruta = "car_sales.json";
        JSONArray ventasCarros = new JSONArray();

        try {
            String contenido = new String(Files.readAllBytes(Paths.get(ruta)));
            ventasCarros = new JSONArray(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Map<String, Double> sumaPrecios = new HashMap<>();
        Map<String, Integer> cantidadCarros = new HashMap<>();

        for (int i = 0; i < ventasCarros.length(); i++) {
            JSONObject venta = ventasCarros.getJSONObject(i);
            String carro = venta.getString("carro");
            String precioStr = venta.getString("precio").replace("$", "").replace(",", "");

            try {
                double precio = Double.parseDouble(precioStr);
                sumaPrecios.put(carro, sumaPrecios.getOrDefault(carro, 0.0) + precio);
                cantidadCarros.put(carro, cantidadCarros.getOrDefault(carro, 0) + 1);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el precio: " + precioStr);
            }
        }

        for (String carro : sumaPrecios.keySet()) {
            double precioPromedio = sumaPrecios.get(carro) / cantidadCarros.get(carro);
            System.out.printf("Marca: %s, Precio Promedio: $%.2f%n", carro, precioPromedio);
        }
    }
}
