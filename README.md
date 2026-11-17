# Algoritmo Genético aplicado ao Problema do Caixeiro Viajante (TSP)

Implementação em **Java** de uma metaheurística baseada em **Algoritmos Genéticos (AG)** para resolver o **Problema do Caixeiro Viajante (*Travelling Salesman Problem* - TSP)**, buscando rotas eficientes entre cidades do estado do Espírito Santo.

> Projeto desenvolvido na disciplina de **Inteligência Artificial** do curso de Bacharelado em Ciência da Computação — **Instituto Federal Sul-rio-grandense (IFSul)**.

---

## Sobre o Projeto

O Problema do Caixeiro Viajante (TSP) é um problema clássico de otimização combinatória NP-difícil, cujo objetivo é encontrar o circuito fechado de menor distância total visitando todas as $N$ cidades exatamente uma vez antes de retornar à origem. Como métodos exatos possuem complexidade fatorial ($O(N!)$) e tornam-se inviáveis para instâncias maiores, este projeto emprega **Algoritmos Genéticos** para evoluir uma população de rotas candidatas ao longo de gerações.

### Principais Características Implementadas
- **Representação Cromossômica:** Permutação de índices de cidades mantendo a cidade de origem (`Vitória`, índice `0`) fixa.
- **Cálculo de Distâncias:** Suporte ao cálculo geodésico via **Fórmula de Haversine** (em km) a partir de latitude e longitude.
- **Operadores de Seleção:** Seleção por **Torneio** ($K = 3$) e seleção por **Roleta** proporcional à aptidão (*fitness*).
- **Recombinação (Crossover):** **Order Crossover (OX)**, preservando segmentos de ordem relativa entre rotas pais sem duplicar cidades.
- **Operadores de Mutação:**
  - **Swap:** Troca aleatória da posição entre duas cidades.
  - **Inversão:** Inversão da ordem de um subsegmento contínuo da rota.
- **Elitismo e Controle de Estagnação:** Preservação dos melhores indivíduos entre gerações e parada antecipada caso não haja melhoria após um limite configurado.

---

## Estrutura do Código

O projeto segue princípios de Programação Orientada a Objetos no pacote `com.mycompany.caixeiro_viajante`:

| Classe / Enum | Responsabilidade |
| :--- | :--- |
| `Caixeiro_viajante.java` | Classe principal (`main`) com a definição das cidades, configuração dos hiperparâmetros e execução do experimento. |
| `AlgoritmoGeneticoTSP.java` | Motor evolutivo responsável pela inicialização da população, avaliação, elitismo, cruzamento, mutação e critério de estagnação. |
| `Rota.java` | Modelo de indivíduo (cromossomo) contendo a permutação de cidades, cálculo do *fitness* (`avaliar()`) e operadores `crossoverOX()` e `mutar()`. |
| `Cidade.java` | Entidade imutável representando cada cidade (`id`, `nome`, `latitude` e `longitude`). |
| `Distancia.java` | Utilitário para cálculo da distância Haversine em quilômetros e construção da matriz de distâncias simétrica. |
| `Selecao.java` | `Enum` que implementa as estratégias de seleção (`TORNEIO` e `ROLETA`). |

---

## Parâmetros e Resultados Experimentais

Os testes foram conduzidos com um conjunto de **10 cidades do Espírito Santo** (*Vitória, Guarapari, Cachoeiro de Itapemirim, Linhares, Colatina, Aracruz, São Mateus, Nova Venécia, São Gabriel da Palha e Alegre*) sob a seguinte configuração:

- **Tamanho da População:** `200`
- **Máximo de Gerações:** `500`
- **Elitismo:** `4` indivíduos
- **Probabilidade de Crossover (OX):** `95%`
- **Taxa de Mutação (Swap / Inversão):** `20%` / `15%`
- **Método de Seleção:** Torneio ($K = 3$)
- **Limite de Estagnação:** `50` gerações
- **Semente Aleatória (`seed`):** `42` (para reprodutibilidade)

### Evolução da Convergência
- **Geração 1:** `879,150 km`
- **Geração 2:** `813,942 km`
- **Geração 3:** `728,959 km`
- **Melhor Rota (convergência na Geração 12):** **`660,123 km`**

```text
Vitória -> Aracruz -> Linhares -> São Mateus -> Nova Venécia -> São Gabriel da Palha -> Colatina -> Alegre -> Cachoeiro de Itapemirim -> Guarapari -> Vitória
```

## Como Executar

### Pré-requisitos
- **Java JDK 11+** instalado.

### Compilação e Execução via Terminal
Na pasta raiz dos arquivos fonte (`src/main/java` ou diretório contendo o pacote `com/mycompany/caixeiro_viajante`):

```bash
# Compilar as classes
javac com/mycompany/caixeiro_viajante/*.java

# Executar a classe principal
java com.mycompany.caixeiro_viajante.Caixeiro_viajante
```

---

## Autores

- **Pedro Pizzolato Mello** — [GitHub](https://github.com/PedroPMello)
- **Bernardo Carniel Burin** — [GitHub](https://github.com/bernardoburin)
