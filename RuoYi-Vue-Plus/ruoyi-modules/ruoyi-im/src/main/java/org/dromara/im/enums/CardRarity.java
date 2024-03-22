package org.dromara.im.enums;

import lombok.Getter;

/**
 * 卡片稀有度
 */
@Getter
public enum CardRarity {
    PALE("灰白"),
    WHITE("白色"),
    GREEN("绿色"),
    BLUE("蓝色"),
    PURPLE("紫色"),
    GOLDEN("金黄"),
    RED("红色"),
    DARK("暗黑");

    private final String chineseName;

    CardRarity(String chineseName) {
        this.chineseName = chineseName;
    }

}
