package org.dromara.common.recommendation;

import java.util.Map;

public interface RecommendationStrategy {

    double similarityCalculator(Map<Double, Double> user1, Map<Double, Double> user2);

}
