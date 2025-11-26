package org.oniteam.oregontrailfx.model;

import org.oniteam.oregontrailfx.util.ConfigManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Servicio para interactuar con Gemini API.
 * RF18: Sistema de diálogo con IA.
 */
public class GeminiService {

    private static GeminiService instance;
    private final ConfigManager config;
    private String apiKey;
    private String apiUrl;

    private GeminiService() {
        config = ConfigManager.getInstance();
        this.apiKey = config.getGeminiApiKey();
        this.apiUrl = config.getGeminiApiUrl();
    }

    public static GeminiService getInstance() {
        if (instance == null) {
            instance = new GeminiService();
        }
        return instance;
    }

    /**
     * Genera texto usando Gemini API.
     *
     * @param prompt El prompt para enviar a Gemini
     * @return El texto generado o mensaje de error
     */
    public String generarTexto(String prompt) {
        if (!config.isGeminiConfigured()) {
            return "API Key de Gemini no configurada. " +
                    "Por favor configura tu API";
        }

        try {
            String fullUrl = apiUrl + "?key=" + apiKey;
            URL url = new URL(fullUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000); // 10 segundos timeout
            conn.setReadTimeout(10000);

            // Construir JSON del request
            String jsonRequest = construirJsonRequest(prompt);

            // Enviar request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer respuesta
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return leerRespuesta(conn);
            } else {
                String errorMsg = leerError(conn);
                System.err.println("Error en Gemini API: " + errorMsg);
                return "Error al generar diálogo (código: " + responseCode + ")";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error de conexión: " + e.getMessage();
        }
    }

    /**
     * Construye el JSON del request para Gemini.
     */
    private String construirJsonRequest(String prompt) {
        // Escapar comillas en el prompt
        String escapedPrompt = prompt.replace("\"", "\\\"")
                .replace("\n", "\\n");

        return String.format(
                "{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}",
                escapedPrompt
        );
    }

    /**
     * Lee la respuesta exitosa de Gemini.
     */
    private String leerRespuesta(HttpURLConnection conn) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
        );

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        return extraerTextoDeJson(response.toString());
    }

    /**
     * Lee el mensaje de error de Gemini.
     */
    private String leerError(HttpURLConnection conn) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8)
            );

            StringBuilder error = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                error.append(line);
            }
            br.close();

            return error.toString();
        } catch (Exception e) {
            return "No se pudo leer el error";
        }
    }

    /**
     * Extrae el texto generado del JSON de respuesta.
     */
    private String extraerTextoDeJson(String json) {
        try {
            int textIndex = json.indexOf("\"text\":");
            if (textIndex == -1) {
                return "No se encontró texto en la respuesta";
            }

            int startQuote = json.indexOf("\"", textIndex + 7);
            int endQuote = buscarComillaCierre(json, startQuote + 1);

            if (startQuote != -1 && endQuote != -1) {
                String texto = json.substring(startQuote + 1, endQuote);
                return desescapar(texto);
            }

            return "Error parseando respuesta";

        } catch (Exception e) {
            System.err.println("Error extrayendo texto: " + e.getMessage());
            return "Error procesando respuesta de Gemini";
        }
    }

    /**
     * Busca la comilla de cierre considerando escapes.
     */
    private int buscarComillaCierre(String json, int desde) {
        for (int i = desde; i < json.length(); i++) {
            if (json.charAt(i) == '\"' && json.charAt(i - 1) != '\\') {
                return i;
            }
        }
        return -1;
    }

    /**
     * Desescapa caracteres especiales del JSON.
     */
    private String desescapar(String texto) {
        return texto.replace("\\n", "\n")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\t", "\t");
    }

    /**
     * Genera diálogo de un enemigo al morir.
     */
    public String dialogoEnemigoDerrotado(String tipoEnemigo) {
        String prompt = String.format(
                "Genera un último mensaje dramático de máximo 2 líneas que diría un %s " +
                        "del juego Oregon Trail al ser derrotado por el jugador. " +
                        "Debe ser breve, dramático y en español. Sin formato especial.",
                tipoEnemigo
        );

        return generarTexto(prompt);
    }

    /**
     * Genera diálogo de un NPC amigable.
     */
    public String dialogoNPCAmigable() {
        String prompt =
                "Genera un saludo corto (máximo 3 líneas) de un viajero del Oregon Trail en 1848 " +
                        "que ofrece un consejo útil sobre el viaje. Debe ser en español, " +
                        "amigable y contextualizado históricamente. Sin formato especial.";

        return generarTexto(prompt);
    }

    /**
     * Genera descripción de un evento aleatorio.
     */
    public String dialogoEventoAleatorio(String tipoEvento) {
        String prompt = String.format(
                "Genera una descripción breve (máximo 3 líneas) de un evento de tipo '%s' " +
                        "que ocurre durante el viaje en el Oregon Trail. " +
                        "Debe ser en español y dramático. Sin formato especial.",
                tipoEvento
        );

        return generarTexto(prompt);
    }

    /**
     * Genera diálogo de comerciante en tienda.
     */
    public String dialogoComerciante() {
        String prompt =
                "Genera un saludo de 2 líneas de un comerciante en Independence, Missouri en 1848 " +
                        "que vende suministros para el viaje a Oregón. " +
                        "Debe ser en español y persuasivo. Sin formato especial.";

        return generarTexto(prompt);
    }

    /**
     * Verifica si el servicio está correctamente configurado.
     */
    public boolean isConfigured() {
        return config.isGeminiConfigured();
    }
}