package steno;

import java.util.*;
import java.util.stream.Collectors;

public class SchemeOptimizer {
    private static final int POPULATION_SIZE = 100;
    private static final double SURVIVAL_RATE = 0.05;
    private static final double MUTATION_RATE = 0.01;
    private static final double ENTROPY_THRESHOLD = 0.99; // 18 : 21 is worst acceptable split for 39 arpabets and range 2
    private final int range;
    private List<Enum> bestEver;
    private double bestEverScore;

    public SchemeOptimizer(int range) {
        if (range > Arpabet.values().length) {
            throw new IllegalArgumentException("too large range");
        }
        this.range = range;
    }

    public Map<Arpabet, Enum> run(int numGenerations) {
        return run(numGenerations, randomPopulation(POPULATION_SIZE));
    }

    public Map<Arpabet, Enum> run(int numGenerations, List<List<Enum>> population) {
        if (population.size() != POPULATION_SIZE) {
            throw new RuntimeException("unexpected initial population size");
        }
        System.out.println("Generations to go: " + String.valueOf(numGenerations));
        numGenerations--;
        final Map<List<Enum>, Double> populationFitness = calculatePopulationFitness(population); // oops, lower is better

        final List<List<Enum>> fittest = populationFitness.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .limit(Math.max((long) Math.floor(SURVIVAL_RATE * POPULATION_SIZE), 2))
                .collect(Collectors.toList());

        final List<Enum> bestOfGeneration = fittest.get(0);
        final double bestOfGenerationScore = populationFitness.get(bestOfGeneration);

        if (this.bestEver == null || bestOfGenerationScore < bestEverScore) {
            this.bestEver = bestOfGeneration;
            this.bestEverScore = bestOfGenerationScore;
            System.out.println("New best: " + String.valueOf(bestEverScore));
        }

        if (numGenerations == 0) {
            return convertToArpabetMap(bestEver);
        } else {
            population = breed(fittest, POPULATION_SIZE - fittest.size());
            population.addAll(fittest); // TODO: cache scores for fittest, at least from last time
            population = population.stream().map(this::mutate).collect(Collectors.toList());
            return run(numGenerations, population);
        }
    }

    private List<Enum> mutate(List<Enum> individual) {
        return mutate(individual, MUTATION_RATE);
    }

    private List<Enum> mutate(List<Enum> individual, double rate) {
        return individual.stream()
                .map(gene -> Math.random() < rate ? randomGene() : gene)
                .collect(Collectors.toList());
    }

    private Enum randomGene() {
        return Arpabet.values()[(int) Math.floor(Math.random() * range)];
    }

    private Map<Arpabet, Enum> convertToArpabetMap(List<Enum> enums) {
        final List<Enum> copy = new ArrayList<>(enums);
        return Arrays.stream(Arpabet.values())
                .collect(Collectors.toMap(a -> a, a -> copy.remove(0)));
    }

    private List<List<Enum>> breed(List<List<Enum>> parents, int numOffspring) {
        Set<List<Enum>> offspring = new HashSet<>(numOffspring);
        int tries = 0;
        while (offspring.size() < numOffspring && tries < 2 * numOffspring) {
            final List<Enum> parentA = parents.get((int) Math.floor(parents.size() * Math.random()));
            List<Enum> parentB = null;
            while (parentB == null || parentB.equals(parentA)) {
                parentB = parents.get((int) Math.floor(parents.size() * Math.random()));
            }
            final List<Enum> child = mate(parentA, parentB);
            offspring.add(child);
            tries++;
        }
        offspring.addAll(randomPopulation(numOffspring - offspring.size()));

        offspring = offspring.stream().map(individual -> {
            int count = 0;
            while (entropy(individual) < ENTROPY_THRESHOLD) {
                individual = mutate(individual, 1);
                count++;
            }
            if (count > 10) {
                System.out.println("fixing entropy randomly sucks: " + count); // TODO: NOOOO
            }
            return individual;
        }).collect(Collectors.toSet());

        return new ArrayList<>(offspring);
    }

    private double entropy(List<Enum> child) {
        final Map<Enum, Integer> counts = new HashMap<>();
        for (Enum gene : child) {
            final Integer oldCount = counts.computeIfAbsent(gene, x -> 0);
            counts.put(gene, oldCount + 1);
        }
        final double total = child.size();
        return counts.values().stream()
                .mapToDouble(count -> -1 * (count / total) * (Math.log(count / total) / Math.log(2))).sum();
    }

    private List<Enum> mate(List<Enum> parentA, List<Enum> parentB) {
        final List<Enum> child = new ArrayList<>(parentA.size());
        for (int i = 0; i < parentA.size(); i++) {
            child.add(Math.random() < 0.5 ? parentA.get(i) : parentB.get(i));
        }
        return child;
    }

    private Map<List<Enum>, Double> calculatePopulationFitness(List<List<Enum>> population) {
        final Map<List<Enum>, Double> populationFitness = new HashMap<>();
        for (List<Enum> individual : population) {
            populationFitness.put(individual, calculateFitness(individual));
        }
        return populationFitness;
    }

    private double calculateFitness(List<Enum> individual) {
        return new Keyer(convertToArpabetMap(individual)).scoreText(Texts.THE_EGG).cost();
    }

    private List<List<Enum>> randomPopulation(int size) {
        final Set<List<Enum>> results = new HashSet<>(size);
        while (results.size() < size) {
            results.add(new ArrayList<>(Schemes.random(range).values()));
        }
        return new ArrayList<>(results);
    }
}
