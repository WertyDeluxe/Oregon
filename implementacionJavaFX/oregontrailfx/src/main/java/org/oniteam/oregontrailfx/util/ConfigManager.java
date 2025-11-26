package org.oniteam.oregontrailfx.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gestor de configuración del juego.
 * Lee las propiedades desde config.properties.
 */
public class ConfigManager {

    private static ConfigManager instance;
    private Properties properties;

    private ConfigManager() {
        properties = new Properties();
        cargarConfiguracion();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Carga el archivo config.properties desde resources.
     */
    private void cargarConfiguracion() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                System.err.println("⚠️ No se encontró config.properties");
                return;
            }

            properties.load(input);
            System.out.println("✅ Configuración cargada exitosamente");

        } catch (IOException e) {
            System.err.println("❌ Error cargando configuración: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la API Key de Gemini.
     * @return API Key o null si no está configurada
     */
    public String getGeminiApiKey() {
        String key = properties.getProperty("gemini.api.key");

        if (key == null || key.equals("TU_API_KEY_AQUI")) {
            System.err.println("⚠️ API Key de Gemini no configurada en config.properties");
            return null;
        }

        return key;
    }

    /**
     * Obtiene la URL de la API de Gemini.
     */
    public String getGeminiApiUrl() {
        return properties.getProperty("gemini.api.url",
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent");
    }

    /**
     * Obtiene cualquier propiedad por su clave.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Obtiene una propiedad con valor por defecto.
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Verifica si la API Key está configurada correctamente.
     */
    public boolean isGeminiConfigured() {
        String key = getGeminiApiKey();
        return key != null && !key.isEmpty() && !key.equals("TU_API_KEY_AQUI");
    }
}