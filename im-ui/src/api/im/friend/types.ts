export interface FriendVO {
  /**
   * 主键id
   */
  id: string ;

  /**
   * 用户
   */
  userId: string;

  /**
   * 好友
   */
  friendId: string;

  /**
   * 好友关系
   */
  relationStatus: number;

  /**
   * 应用简介
   */
  remark: string;

  friendUser: any;

}

export interface FriendForm extends BaseEntity {
  /**
   * 主键id
   */
  id?: string | number;

  /**
   * 用户
   */
  userId?: string | number;

  /**
   * 好友
   */
  friendId?: string | number;

  /**
   * 好友关系
   */
  relationStatus?: number;

  /**
   * 应用简介
   */
  remark?: string;

}

export interface FriendQuery extends PageQuery {
  /**
   * 用户
   */
  userId?: string | number;

  /**
   * 好友
   */
  friendId?: string | number;

  /**
   * 好友关系 1 待验证 2 已通过 3 黑名单
   */
  relationStatus?: number;

  /**
   * 创建时间
   */
  createTime?: string;

  userName?: string;

}
