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

public class AlgoritmoGeneticoTSP {
    public static final class Config {
        public final int tamPop;
        public final int maxGeracoes;
        public final int elitismo;
        public final double pCrossover;
        public final double pMutSwap;
        public final double pMutInverter;
        public final Selecao selecao;
        public final int limiteEstagnacao;
        public final long seed;

        public Config(int tamPop, int maxGeracoes, int elitismo,
                      double pCrossover, double pMutSwap, double pMutInverter,
                      Selecao selecao, int limiteEstagnacao, long seed) {
            if (tamPop < 2 || tamPop % 2 != 0) throw new IllegalArgumentException("tamPop par e >= 2");
            if (elitismo < 0 || elitismo >= tamPop) throw new IllegalArgumentException("elitismo inválido");
            this.tamPop = tamPop;
            this.maxGeracoes = maxGeracoes;
            this.elitismo = elitismo;
            this.pCrossover = pCrossover;
            this.pMutSwap = pMutSwap;
            this.pMutInverter = pMutInverter;
            this.selecao = Objects.requireNonNull(selecao);
            this.limiteEstagnacao = Math.max(0, limiteEstagnacao);
            this.seed = seed;
        }
    }

    private final List<Cidade> cidades;
    private final double[][] dist;
    private final Config cfg;
    private final Random rng;

    private List<Rota> populacao;
    private Rota melhorGlobal;

    public AlgoritmoGeneticoTSP(List<Cidade> cidades, Config cfg) {
        this.cidades = List.copyOf(cidades);
        this.dist = Distancia.construirMatriz(this.cidades);
        this.cfg = cfg;
        this.rng = new Random(cfg.seed);
        this.populacao = new ArrayList<>(cfg.tamPop);
    }

    public Rota getMelhorGlobal() { return melhorGlobal; }

    private void inicializar() {
        populacao.clear();
        for (int i = 0; i < cfg.tamPop; i++) populacao.add(new Rota(cidades, dist, rng));
        populacao.sort(Comparator.naturalOrder());
        melhorGlobal = populacao.get(0);
    }

    private void atualizarMelhor(Rota cand) {
        if (cand.compareTo(melhorGlobal) < 0) melhorGlobal = cand;
    }

    public Rota executar(boolean logProgresso) {
        inicializar();
        int estagnado = 0;
        double melhorAteAgora = melhorGlobal.getDistancia();

        for (int gen = 1; gen <= cfg.maxGeracoes; gen++) {
            List<Rota> prox = new ArrayList<>(cfg.tamPop);
            for (int i = 0; i < cfg.elitismo; i++) prox.add(populacao.get(i));

            while (prox.size() < cfg.tamPop) {
                Rota p1 = populacao.get(cfg.selecao.escolher(populacao, rng));
                Rota p2 = populacao.get(cfg.selecao.escolher(populacao, rng));

                List<Rota> filhos;
                if (rng.nextDouble() < cfg.pCrossover) filhos = Rota.crossoverOX(p1, p2, rng);
                else filhos = Arrays.asList(p1, p2);

                for (Rota filho : filhos) {
                    filho.mutar(cfg.pMutSwap, cfg.pMutInverter, rng);
                    prox.add(filho);
                    if (prox.size() == cfg.tamPop) break;
                }
            }

            populacao = prox;
            populacao.sort(Comparator.naturalOrder());
            atualizarMelhor(populacao.get(0));

            double melhorAtual = melhorGlobal.getDistancia();
            if (Math.abs(melhorAtual - melhorAteAgora) < 1e-9) estagnado++;
            else { estagnado = 0; melhorAteAgora = melhorAtual; }

            if (logProgresso && (gen % 10 == 0 || gen == 1)) {
                System.out.printf("G%4d | melhor=%.3f | atual=%.3f\n", gen, melhorGlobal.getDistancia(), populacao.get(0).getDistancia());
            }

            if (cfg.limiteEstagnacao > 0 && estagnado >= cfg.limiteEstagnacao) break;
        }
        return melhorGlobal;
    }
}

