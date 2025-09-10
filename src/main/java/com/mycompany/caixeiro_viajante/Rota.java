/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixeiro_viajante;

/**
 *
 * @author bernardoburin
 */
import java.util.*;

/** Representa uma rota (permutação) no TSP. Fitness é a distância total (menor é melhor). */
public final class Rota implements Comparable<Rota> {
    private final double[][] dist;        // matriz de distâncias
    private final List<Cidade> cidades;   // referência às cidades (para exibição)
    private final int[] ordem;            // permutação de índices de cidades

    private double distanciaTotal;        // distância total
    private int geracao;

    public Rota(List<Cidade> cidades, double[][] dist, Random rng) {
        this.cidades = Objects.requireNonNull(cidades);
        this.dist = Objects.requireNonNull(dist);
        this.ordem = new int[cidades.size()];
        for (int i = 0; i < ordem.length; i++) ordem[i] = i;
        embaralharSubsequencia(1, ordem.length, rng); // mantém 0 como origem, permuta o restante
        this.geracao = 0;
        avaliar();
    }

    private Rota(List<Cidade> cidades, double[][] dist, int[] ordem, int geracao) {
        this.cidades = cidades;
        this.dist = dist;
        this.ordem = ordem.clone();
        this.geracao = geracao;
        avaliar();
    }

    public int tamanho() { return ordem.length; }
    public int[] getOrdem() { return ordem.clone(); }
    public int getGeracao() { return geracao; }
    public double getDistancia() { return distanciaTotal; }

    /** Embaralha uma subsequência [from, to) da permutação. */
    private void embaralharSubsequencia(int from, int to, Random rng) {
        for (int i = to - 1; i > from; i--) {
            int j = from + rng.nextInt(i - from + 1);
            int tmp = ordem[i]; ordem[i] = ordem[j]; ordem[j] = tmp;
        }
    }

    /** Calcula a distância total retornando à cidade de origem. */
    public void avaliar() {
        double soma = 0.0;
        for (int i = 0; i < ordem.length - 1; i++) soma += dist[ordem[i]][ordem[i + 1]];
        soma += dist[ordem[ordem.length - 1]][ordem[0]]; // retorna à origem
        this.distanciaTotal = soma;
    }

    /** Crossover OX (Order Crossover). Mantém a cidade 0 como origem fixa. */
    public static List<Rota> crossoverOX(Rota a, Rota b, Random rng) {
        int n = a.tamanho();
        int corte1 = 1 + rng.nextInt(n - 1);
        int corte2 = 1 + rng.nextInt(n - 1);
        if (corte1 > corte2) { int t = corte1; corte1 = corte2; corte2 = t; }

        int[] filho1 = new int[n];
        int[] filho2 = new int[n];
        Arrays.fill(filho1, -1);
        Arrays.fill(filho2, -1);
        filho1[0] = 0; filho2[0] = 0; // origem fixa

        for (int i = corte1; i <= corte2; i++) {
            filho1[i] = a.ordem[i];
            filho2[i] = b.ordem[i];
        }
        preencherOX(filho1, b.ordem, corte2);
        preencherOX(filho2, a.ordem, corte2);

        Rota f1 = new Rota(a.cidades, a.dist, filho1, a.geracao + 1);
        Rota f2 = new Rota(a.cidades, a.dist, filho2, a.geracao + 1);
        return Arrays.asList(f1, f2);
    }

    private static void preencherOX(int[] filho, int[] doador, int inicio) {
        int n = filho.length;
        int idx = (inicio + 1) % n;
        for (int k = 1; k < n; k++) {
            int gene = doador[(inicio + k) % n];
            if (gene == 0) continue; // mantém origem fixa
            if (!contem(filho, gene)) {
                while (filho[idx] != -1) idx = (idx + 1) % n;
                filho[idx] = gene;
            }
        }
        for (int i = 1; i < n; i++) if (filho[i] == -1) filho[i] = doador[i];
    }

    private static boolean contem(int[] arr, int v) {
        for (int x : arr) if (x == v) return true; return false;
    }

    /** Mutação: swap e inversão de segmento. */
    public void mutar(double pSwap, double pInverter, Random rng) {
        int n = tamanho();
        if (rng.nextDouble() < pSwap) {
            int i = 1 + rng.nextInt(n - 1);
            int j = 1 + rng.nextInt(n - 1);
            int t = ordem[i]; ordem[i] = ordem[j]; ordem[j] = t;
        }
        if (rng.nextDouble() < pInverter) {
            int i = 1 + rng.nextInt(n - 1);
            int j = 1 + rng.nextInt(n - 1);
            if (i > j) { int t = i; i = j; j = t; }
            while (i < j) { int t = ordem[i]; ordem[i] = ordem[j]; ordem[j] = t; i++; j--; }
        }
        avaliar();
    }

    @Override public int compareTo(Rota o) { return Double.compare(this.distanciaTotal, o.distanciaTotal); }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ger=%d dist=%.3f rota=", geracao, distanciaTotal));
        for (int idx : ordem) sb.append(cidades.get(idx).getNome()).append(" -> ");
        sb.append(cidades.get(ordem[0]).getNome()); // retorno
        return sb.toString();
    }
}
