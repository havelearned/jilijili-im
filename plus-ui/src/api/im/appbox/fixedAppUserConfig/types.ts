export interface FixedAppUserConfigVO {
  /**
   * id
   */
  id: string | number;

  /**
   * 应用apo
   */
  appId: string | number;

  /**
   * 用户id
   */
  userId: string | number;

  /**
   * 标识
   */
  identity: string | number;

  /**
   * 头像小图标
   */
  iconUrl: string;

  /**
   * 备注
   */
  remark: string;

}

export interface FixedAppUserConfigForm extends BaseEntity {
  /**
   * id
   */
  id?: string | number;

  /**
   * 应用apo
   */
  appId?: string | number;

  /**
   * 用户id
   */
  userId?: string | number;
  userIds?: string | number;

  /**
   * 标识
   */
  identity?: string | number;

  /**
   * 头像小图标
   */
  iconUrl?: string;

  /**
   * 备注
   */
  remark?: string;

}

export interface FixedAppUserConfigQuery extends PageQuery {
  /**
   * id
   */
  id?: string | number;

  /**
   * 应用apo
   */
  appId?: string | number;

  /**
   * 用户id
   */
  userId?: string | number;

  /**
   * 标识
   */
  identity?: string | number;

  /**
   * 头像小图标
   */
  iconUrl?: string;

  /**
   * 备注
   */
  remark?: string;

  /**
   * 创建时间
   */
  createTime?: string;

}
