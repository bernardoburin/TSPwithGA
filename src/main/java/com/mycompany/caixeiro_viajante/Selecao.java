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

/** Estratégias de seleção (minimização da distância). */
public enum Selecao {
    ROLETA {
        @Override public int escolher(List<Rota> pop, Random rng) {
            double pior = 0.0;
            for (Rota r : pop) pior = Math.max(pior, r.getDistancia());
            double soma = 0.0;
            for (Rota r : pop) soma += (pior + 1e-9) - r.getDistancia();
            double alvo = rng.nextDouble() * soma;
            double acum = 0.0;
            for (int i = 0; i < pop.size(); i++) {
                acum += (pior + 1e-9) - pop.get(i).getDistancia();
                if (acum >= alvo) return i;
            }
            return pop.size() - 1;
        }
    },
    TORNEIO {
        private static final int K = 3;
        @Override public int escolher(List<Rota> pop, Random rng) {
            int melhor = rng.nextInt(pop.size());
            for (int i = 1; i < K; i++) {
                int cand = rng.nextInt(pop.size());
                if (pop.get(cand).compareTo(pop.get(melhor)) < 0) melhor = cand;
            }
            return melhor;
        }
    };

    public abstract int escolher(List<Rota> pop, Random rng);
}

