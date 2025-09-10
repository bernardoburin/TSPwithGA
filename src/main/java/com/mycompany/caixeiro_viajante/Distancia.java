/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixeiro_viajante;

/**
 *
 * @author bernardoburin
 */
import java.util.List;

/** Utilitários de distância. */
public final class Distancia {
    private Distancia() {}

    private static final double RAIO_TERRA_KM = 6371.0088;

    /** Distância Haversine em quilômetros. */
    public static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAIO_TERRA_KM * c;
    }

    /** Constrói uma matriz de distâncias (simétrica) para a lista de cidades. */
    public static double[][] construirMatriz(List<Cidade> cidades) {
        int n = cidades.size();
        double[][] d = new double[n][n];
        for (int i = 0; i < n; i++) {
            d[i][i] = 0.0;
            for (int j = i + 1; j < n; j++) {
                Cidade a = cidades.get(i), b = cidades.get(j);
                double dij = haversineKm(a.getLat(), a.getLon(), b.getLat(), b.getLon());
                d[i][j] = d[j][i] = dij;
            }
        }
        return d;
    }
}
