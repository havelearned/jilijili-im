export interface NoticeVO {
  /**
   * id
   */
  id: string | number;

  /**
   * 用户id
   */
  userId: string | number;

  /**
   * 通知类型:1精选推送 2用户消息 3有人@你
   */
  noticeType: number;

  /**
   * 通知标题
   */
  title: string;

  /**
   * 是否已读:1已读 0未读
   */
  readStatus: number;

  /**
   * 通知内容
   */
  content: string;

  /**
   * 来源url,可前往查看
   */
  fromUrl: string;

  /**
   * 用户昵称
   */
  nickName: string;
}

export interface NoticeForm extends BaseEntity {
  /**
   * id
   */
  id?: string | number;

  /**
   * 用户id
   */
  userId?: string | number;

  /**
   * 通知类型:1精选推送 2用户消息 3有人@你
   */
  noticeType?: number;

  /**
   * 通知标题
   */
  title?: string;

  /**
   * 是否已读:1已读 0未读
   */
  readStatus?: number;

  /**
   * 通知内容
   */
  content?: string;

  /**
   * 来源url,可前往查看
   */
  fromUrl?: string;

  nickName: string;
}

export interface NoticeQuery extends PageQuery {
  /**
   * 用户id
   */
  userId?: string | number;

  /**
   * 通知类型:1精选推送 2用户消息 3有人@你
   */
  noticeType?: number;

  /**
   * 通知标题
   */
  title?: string;

  /**
   * 是否已读:1已读 0未读
   */
  readStatus?: number;

  /**
   * 通知内容
   */
  content?: string;

  /**
   * 来源url,可前往查看
   */
  fromUrl?: string;

  nickName: string;
}
