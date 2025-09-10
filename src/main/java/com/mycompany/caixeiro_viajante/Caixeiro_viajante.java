/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.caixeiro_viajante;

import java.util.List;
/**
 *
 * @author bernardoburin
 */
public class Caixeiro_viajante {

    public static void main(String[] args) {
        // Lista de cidades utilizadas no exemplo (ordem fixa para correspondência com matriz)
        List<Cidade> cidades = List.of(
            new Cidade(0, "Vitória", -20.3155, -40.3128),
            new Cidade(1, "Guarapari", -20.6772, -40.4977),
            new Cidade(2, "Cachoeiro de Itapemirim", -20.8489, -41.1129),
            new Cidade(3, "Linhares", -19.3946, -40.0643),
            new Cidade(4, "Colatina", -19.5393, -40.6300),
            new Cidade(5, "Aracruz", -19.8193, -40.2730),
            new Cidade(6, "São Mateus", -18.7167, -39.8589),
            new Cidade(7, "Nova Venécia", -18.7150, -40.4050),
            new Cidade(8, "São Gabriel da Palha", -19.0188, -40.5362),
            new Cidade(9, "Alegre", -20.7631, -41.5333)
        );

        // Matriz de distâncias aproximadas (em km) entre as cidades.
        // Fonte: pode ser calculada com Haversine ou extraída de ferramentas externas.
        // Aqui incluímos valores fictícios/exemplificativos.
        double[][] matrizDistancias = {
            {  0,  54, 136, 132, 134,  83, 219, 230, 213, 188 },
            { 54,   0,  95, 186, 187, 120, 264, 276, 257, 142 },
            {136,  95,   0, 262, 210, 201, 331, 342, 323,  76 },
            {132, 186, 262,   0,  79,  85, 126, 144, 136, 338 },
            {134, 187, 210,  79,   0,  69, 158, 119, 104, 263 },
            { 83, 120, 201,  85,  69,   0, 161, 172, 154, 267 },
            {219, 264, 331, 126, 158, 161,   0,  91, 102, 407 },
            {230, 276, 342, 144, 119, 172,  91,   0,  54, 391 },
            {213, 257, 323, 136, 104, 154, 102,  54,   0, 355 },
            {188, 142,  76, 338, 263, 267, 407, 391, 355,   0 }
        };

        // Configuração do algoritmo genético
        AlgoritmoGeneticoTSP.Config cfg = new AlgoritmoGeneticoTSP.Config(
            200,            // tamanho da população
            500,            // número máximo de gerações
            4,              // elitismo
            0.95,           // probabilidade de crossover
            0.20,           // taxa de mutação swap
            0.15,           // taxa de mutação por inversão
            Selecao.TORNEIO, // método de seleção
            50,             // limite de estagnação
            42L             // semente para reprodutibilidade
        );

        // Execução do algoritmo genético com a matriz de distâncias fornecida
        AlgoritmoGeneticoTSP ag = new AlgoritmoGeneticoTSP(cidades, cfg) {
            protected double[][] getMatrizDistancias() {
                return matrizDistancias;
            }
        };

        Rota melhor = ag.executar(true);

        // Exibe o melhor resultado encontrado
        System.out.println("\nMelhor rota encontrada:");
        System.out.println(melhor);
    }
}

