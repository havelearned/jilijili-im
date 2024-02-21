
package org.dromara.im.domain.vo.github;

import lombok.Data;

import java.util.Date;

@Data
public class GithubUserInfo {

    private String login;
    private long id;
    private String node_id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private String twitter_username;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private int private_gists;
    private int total_private_repos;
    private int owned_private_repos;
    private long disk_usage;
    private int collaborators;
    private boolean two_factor_authentication;

}
