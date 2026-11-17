# Algoritmo Genético aplicado ao Problema do Caixeiro Viajante (TSP)

Implementação em **Java** de uma metaheurística baseada em **Algoritmos Genéticos (AG)** para resolver o **Problema do Caixeiro Viajante (*Travelling Salesman Problem* - TSP)**, buscando rotas eficientes entre cidades do estado do Espírito Santo.

> Projeto desenvolvido na disciplina de **Inteligência Artificial** do curso de Bacharelado em Ciência da Computação — **Instituto Federal Sul-rio-grandense (IFSul)**[cite: 2, 6].

---

## Sobre o Projeto

O Problema do Caixeiro Viajante (TSP) é um problema clássico de otimização combinatória NP-difícil, cujo objetivo é encontrar o circuito fechado de menor distância total visitando todas as $N$ cidades exatamente uma vez antes de retornar à origem[cite: 7, 15]. Como métodos exatos possuem complexidade fatorial ($O(N!)$) e tornam-se inviáveis para instâncias maiores, este projeto emprega **Algoritmos Genéticos** para evoluir uma população de rotas candidatas ao longo de gerações[cite: 8, 15].

### Principais Características Implementadas
- **Representação Cromossômica:** Permutação de índices de cidades mantendo a cidade de origem (`Vitória`, índice `0`) fixa[cite: 10, 20, 23].
- **Cálculo de Distâncias:** Suporte ao cálculo geodésico via **Fórmula de Haversine** (em km) a partir de latitude e longitude[cite: 16, 22].
- **Operadores de Seleção:** Seleção por **Torneio** ($K = 3$) e seleção por **Roleta** proporcional à aptidão (*fitness*)[cite: 16, 24].
- **Recombinação (Crossover):** **Order Crossover (OX)**, preservando segmentos de ordem relativa entre rotas pais sem duplicar cidades[cite: 10, 15, 23].
- **Operadores de Mutação:**
  - **Swap:** Troca aleatória da posição entre duas cidades[cite: 10, 15, 23].
  - **Inversão:** Inversão da ordem de um subsegmento contínuo da rota[cite: 10, 15, 23].
- **Elitismo e Controle de Estagnação:** Preservação dos melhores indivíduos entre gerações e parada antecipada caso não haja melhoria após um limite configurado[cite: 15, 19].

---

## Estrutura do Código

O projeto segue princípios de Programação Orientada a Objetos no pacote `com.mycompany.caixeiro_viajante`[cite: 18, 19]:

| Classe / Enum | Responsabilidade |
| :--- | :--- |
| `Caixeiro_viajante.java` | Classe principal (`main`) com a definição das cidades, configuração dos hiperparâmetros e execução do experimento[cite: 9, 16, 20]. |
| `AlgoritmoGeneticoTSP.java` | Motor evolutivo responsável pela inicialização da população, avaliação, elitismo, cruzamento, mutação e critério de estagnação[cite: 9, 16, 19]. |
| `Rota.java` | Modelo de indivíduo (cromossomo) contendo a permutação de cidades, cálculo do *fitness* (`avaliar()`) e operadores `crossoverOX()` e `mutar()`[cite: 9, 10, 23]. |
| `Cidade.java` | Entidade imutável representando cada cidade (`id`, `nome`, `latitude` e `longitude`)[cite: 9, 16, 21]. |
| `Distancia.java` | Utilitário para cálculo da distância Haversine em quilômetros e construção da matriz de distâncias simétrica[cite: 9, 16, 22]. |
| `Selecao.java` | `Enum` que implementa as estratégias de seleção (`TORNEIO` e `ROLETA`)[cite: 9, 16, 24]. |

---

## Parâmetros e Resultados Experimentais

Os testes foram conduzidos com um conjunto de **10 cidades do Espírito Santo** (*Vitória, Guarapari, Cachoeiro de Itapemirim, Linhares, Colatina, Aracruz, São Mateus, Nova Venécia, São Gabriel da Palha e Alegre*)[cite: 17, 20] sob a seguinte configuração[cite: 16, 20]:

- **Tamanho da População:** `200`[cite: 16, 20]
- **Máximo de Gerações:** `500`[cite: 16, 20]
- **Elitismo:** `4` indivíduos[cite: 16, 20]
- **Probabilidade de Crossover (OX):** `95%`[cite: 16, 20]
- **Taxa de Mutação (Swap / Inversão):** `20%` / `15%`[cite: 16, 20]
- **Método de Seleção:** Torneio ($K = 3$)[cite: 16, 20, 24]
- **Limite de Estagnação:** `50` gerações[cite: 16, 20]
- **Semente Aleatória (`seed`):** `42` (para reprodutibilidade)[cite: 16, 20]

### Evolução da Convergência
- **Geração 1:** `879,150 km`[cite: 17]
- **Geração 2:** `813,942 km`[cite: 17]
- **Geração 3:** `728,959 km`[cite: 17]
- **Melhor Rota (convergência na Geração 12):** **`660,123 km`**[cite: 17]

```text
Vitória -> Aracruz -> Linhares -> São Mateus -> Nova Venécia -> São Gabriel da Palha -> Colatina -> Alegre -> Cachoeiro de Itapemirim -> Guarapari -> Vitória
