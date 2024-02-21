package org.dromara.common.ai.domain.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)   //忽略传参时其他无用字段
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ModelBo {
    private String model;
    private List<ModelMessage> messages;
    private String request_id;
    private Boolean do_sample;
    private Boolean stream;
    private Float temperature;
    private Float top_p;
    private Integer max_tokens;
    private List<String> stop;
    private List<Tool> tools;
    private String type;
    private Function function;
    private Retrieval retrieval;
    private WebSearch web_search;

    // 构造函数、getters 和 setters 省略

    @Data
    public static class Tool {
        private String type;
    }

    @Data
    public static class Function {
        private String name;
        private String description;
        private Object parameters;
    }

    @Data
    public static class Retrieval {
        private String knowledge_id;
        private String prompt_template;
    }

    @Data
    public static class WebSearch {
        private Boolean enable;
        private String search_query;
    }
}
