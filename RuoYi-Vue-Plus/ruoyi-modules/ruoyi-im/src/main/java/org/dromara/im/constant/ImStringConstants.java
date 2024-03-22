package org.dromara.im.constant;

public interface ImStringConstants {

    String GITHUB_KEY = "000000:GITHUB_";

    String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s";

    String GITHUB_GET_USER = "https://api.github.com/user";

    String GITEE_TOKEN_URL = "https://gitee.com/oauth/token?" +
        "grant_type=authorization_code" +
        "&code=%s" +
        "&client_id=%s" +
        "&client_secret=%s"+
        "&redirect_uri=%s";

    String GITEE_GET_USER = "https://gitee.com/api/v5/user";

    String GITEE_KEY = "000000:GITEE_";


    String  ADD_FRIEND_LIMIT_KEY = "ADD_FRIEND_LIMIT_KEY:%s:%s";

}
