drop table if exists im_msg_record;
create table im_msg_record
(
    id          bigint primary key comment 'id消息',
    sender_id   bigint       null comment '发送者id',
    to_id       bigint       null comment '接收者id(不适用群聊)',
    to_group_id bigint       null comment '群聊id',
    to_app_id   bigint       null comment '频道id',
    content     text         not null comment '消息内容',
    msg_type    tinyint      null comment '内容类型: 1文本 2图片 3视频 4音频',
    to_type     tinyint      null comment '消息类型:1用户 2群聊 3频道',
    status      tinyint      not null default 1 comment '消息状态:0撤回 1正常',
    unread      tinyint      not null default 0 comment '读取状态:0未读 已读',
    ref_msg_id  bigint       null comment '引用|回复消息id',
    create_dept bigint       null comment '创建部门',
    create_by   bigint       null comment '创建者',
    create_time datetime     null comment '创建时间',
    update_by   bigint       null comment '更新者',
    update_time datetime     null comment '更新时间',
    remark      varchar(500) null comment '备注',
    INDEX idx_sender_id (sender_id),
    INDEX idx_to_id (to_id),
    INDEX idx_to_group_id (to_group_id),
    INDEX idx_to_app_id (to_app_id),
    INDEX idx_status (status),
    INDEX idx_unread (unread),
    INDEX idx_create_time (create_time)
) comment '聊天消息记录表';

drop table if exists im_msg_action;
create table im_msg_action
(
    id            bigint primary key comment 'id',
    msg_id        bigint comment '聊天id',
    endorse_count int comment '赞同量',
    full_count    int comment '满分量',
    like_count    int comment '点赞量',
    create_dept   bigint       null comment '创建部门',
    create_by     bigint       null comment '创建者',
    create_time   datetime     null comment '创建时间',
    update_by     bigint       null comment '更新者',
    update_time   datetime     null comment '更新时间',
    remark        varchar(500) null comment '备注'
) comment '聊天动作表';

