package org.dromara.common.ai.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ModelVo {

    private String id;
    private String model;
    private String request_id;
    private long created;
    private List<Choice> choices;
    private Usage usage;


}

@Data
class Choice {
    private String finish_reason;
    private int index;
    private Message message;
}

@Data
class Message {
    private String role;
    private List<ToolCall> tool_calls;
}

@Data
class ToolCall {
    private String id;
    private int index;
    private String type;
    private Function function;
}

@Data
class Function {
    private String arguments;
    private String name;
}

@Data
class Usage {
    private int completion_tokens;
    private int prompt_tokens;
    private int total_tokens;
}
