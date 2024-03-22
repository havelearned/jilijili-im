package org.dromara.im.domain.vo.github;


import lombok.Data;

@Data
public class GithubTokenInfo {

    private String access_token;
    private String scope;
    private String token_type;
    private Integer expires_in;

    private String refresh_token;

    private String created_at;


}
