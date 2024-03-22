package org.dromara.common.recommendation;

import lombok.Data;

import java.util.*;

/**
 * <p>
 * description: 皮尔逊相似度
 * </p>
 *
 * @author luojiaju
 * @date 22/03/2024
 */
public class PearsonCorrelation implements RecommendationStrategy {
    /**
     * 皮尔逊相似度计算
     *
     * @param user1 用户1的所有物品评分
     * @param user2 用户2的所有物品评分
     * @return
     */
    @Override
    public double similarityCalculator(Map<Double, Double> user1, Map<Double, Double> user2) {
        double sum1 = 0.0; // 用户1的所有评分之和
        double sum2 = 0.0; // 用户2的所有评分之和
        double sum1Sq = 0.0; // 用户1的所有评分的平方和
        double sum2Sq = 0.0; // 用户2的所有评分的平方和
        double pSum = 0.0; // 用户1和用户2共同评分的乘积之和
        int n = 0; // 用户1和用户2共同评分的数量

        // 遍历用户1的评分，同时检查用户2是否也评分了相同的项
        for (Map.Entry<Double, Double> entry : user1.entrySet()) {
            if (user2.containsKey(entry.getKey())) {
                double x = entry.getValue(); // 用户1的评分
                double y = user2.get(entry.getKey()); // 用户2的评分
                sum1 += x;
                sum2 += y;
                sum1Sq += x * x;
                sum2Sq += y * y;
                pSum += x * y;
                n++; // 共同评分项数量增加
            }
        }
        // 如果没有共同评分项，返回0
        if (n == 0) {
            return 0;
        }

        // 计算皮尔逊相关系数的分子和分母
        double num = pSum - (sum1 * sum2 / n);
        double den = Math.sqrt((sum1Sq - sum1 * sum1 / n) * (sum2Sq - sum2 * sum2 / n));

        // 如果分母为0，说明两个用户的评分完全相同，返回1
        if (den == 0) {
            return 1;
        }

        // 计算并返回皮尔逊相关系数
        return num / den;
    }

    /**
     * 定义一个推荐方法，根据协同过滤算法为指定用户推荐电影
     *
     * @param data   kye:[用户id] value:[key:物品id value 物品评分]
     * @param userId 目标用户id
     * @param k      目标用户对物品的评分
     * @return
     */
    public List<Double> recommend(Map<Double, Map<Double, Double>> data, Double userId, Double k) {
        // 获取目标用户的评分记录
        Map<Double, Double> targetUserRatings = data.get(userId);

        // 初始化一个优先队列，用于存储最相似的前k个用户的相似度信息
        PriorityQueue<UserSimilarity> topKNeighbors = new PriorityQueue<>(Comparator.comparingDouble(UserSimilarity::getSimilarity));

        // 初始化一个映射表，用于存储候选电影及其加权评分
        Map<Double, Double> candidateMovies = new HashMap<>();

        // 遍历所有用户（排除目标用户自己）
        for (Double otherUserId : data.keySet()) {
            if (otherUserId == userId) {
                continue;
            }
            // 计算目标用户与其他用户的相似度
            double similarity = this.similarityCalculator(targetUserRatings, data.get(otherUserId));

            // 将相似度最高的前k个用户添加到优先队列中
            if (topKNeighbors.size() < k) {
                topKNeighbors.add(new UserSimilarity(otherUserId, similarity));
            } else if (similarity > topKNeighbors.peek().getSimilarity()) {
                // 如果新计算出的相似度大于优先队列中最小的相似度，则替换并保持队列大小为k
                topKNeighbors.poll();
                topKNeighbors.add(new UserSimilarity(otherUserId, similarity));
            }
        }

        // 遍历最相似的前k个用户，收集他们给目标用户未评分过的电影的评分
        for (UserSimilarity userSimilarity : topKNeighbors) {
            Map<Double, Double> otherUserRatings = data.get(userSimilarity.getUserId());
            for (Double movieId : otherUserRatings.keySet()) {
                if (!targetUserRatings.containsKey(movieId)) {
                    // 对于目标用户未评分的电影，累加其在相似用户中的评分
                    candidateMovies.put(movieId, candidateMovies.getOrDefault(movieId, 0.0) + otherUserRatings.get(movieId));
                }
            }
        }

        // 将候选电影ID按照加权评分从高到低排序
        List<Double> recommendedMovies = new ArrayList<>(candidateMovies.keySet());
        recommendedMovies.sort((m1, m2) -> Double.compare(candidateMovies.get(m2), candidateMovies.get(m1)));

        // 返回按预测评分降序排列的推荐电影ID列表
        return recommendedMovies;
    }

    @Data
    private static class UserSimilarity {
        private final Double userId;
        private final double similarity;

        public UserSimilarity(Double userId, double similarity) {
            this.userId = userId;
            this.similarity = similarity;
        }

    }

    public static void main(String[] args) {
        Map<Double, Map<Double, Double>> testData = new HashMap<>();

        // 假设的用户评分数据
        testData.put(1.0, Map.of(1.0, 5.0, 2.0, 3.0, 3.0, 4.0));
        testData.put(2.0, Map.of(1.0, 4.5, 2.0, 3.5, 3.0, 4.5, 4.0, 5.0));
        testData.put(3.0, Map.of(2.0, 4.0, 3.0, 4.5, 4.0, 5.0, 5.0, 3.0));


        PearsonCorrelation correlation = new PearsonCorrelation();
        List<Double> recommend = correlation.recommend(testData, 1.0, 2.0);
        System.out.println("推荐的物品Id:" + recommend.toString());
    }
}
