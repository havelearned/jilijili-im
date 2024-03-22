package org.dromara.common.recommendation;

import java.util.*;

/**
 * <p>
 * description: 余弦相似度
 * </p>
 *
 * @author luojiaju
 * @date 22/03/2024
 */
public class CosineSimilarity implements RecommendationStrategy {
    @Override
    public double similarityCalculator(Map<Double, Double> vector1, Map<Double, Double> vector2) {
        // Step 1: 求向量的并集（包含所有词汇）
        Set<Double> unionKeys = new TreeSet<>(vector1.keySet());
        unionKeys.addAll(vector2.keySet());

        // Step 2: 计算向量的模长
        double normA = Math.sqrt(vector1.values().stream().mapToDouble(value -> value * value).sum());
        double normB = Math.sqrt(vector2.values().stream().mapToDouble(value -> value * value).sum());

        // Step 3: 计算点积
        double dotProduct = unionKeys.stream()
            .mapToDouble(key -> {
                double value1 = vector1.getOrDefault(key, 0.0);
                double value2 = vector2.getOrDefault(key, 0.0);
                return value1 * value2;
            })
            .sum();

        // Step 4: 计算余弦相似度
        return dotProduct / (normA * normB);
    }

    public static void main(String[] args) {

        Random random = new Random();

        // 创建两个随机向量
        Map<Double, Double> vector1 = generateRandomVector(100, random);
        Map<Double, Double> vector2 = generateRandomVector(10, random);



        // 调用余弦相似度计算方法
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        double similarity = cosineSimilarity.similarityCalculator(vector1, vector2);

        System.out.println("Cosine Similarity: " + similarity);
    }


    private static Map<Double, Double> generateRandomVector(int size, Random random) {
        Map<Double, Double> vector = new HashMap<>();
        for (int i = 0; i < size; i++) {
            double key = random.nextDouble() * 100; // 假设关键词范围在0-10之间
            double value = random.nextDouble(); // 值范围在0-1之间
            vector.put(key, value);
        }
        return vector;
    }
}
