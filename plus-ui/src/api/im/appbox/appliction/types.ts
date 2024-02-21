export interface AppboxapplicationVO {
  /**
   * id
   */
  id: string | number;

  /**
   * 上级id
   */
  parentId: string | number;

  /**
   * 配置id
   */
  configId: string | number;

  /**
   * 名称
   */
  appName: string;


  /**
   * 图标
   */
  iconUrl: string;
  /**
   * 封面
   */
  cover: string;

  /**
   * 热度
   */
  popularity: number;

  /**
   * 页面访问量
   */
  pv: number;

  /**
   *  独立访客数
   */
  uv: number;

  /**
   * 频道状态:1应用 2频道
   */
  channelStatus: number;

  /**
   * 应用简介
   */
  remark: string;

  /**
   * 子对象
   */
  children: AppboxapplicationVO[];
}

export interface AppboxapplicationForm extends BaseEntity {
  /**
   * id
   */
  id?: string | number;

  /**
   * 上级id
   */
  parentId?: string | number;

  /**
   * 配置id
   */
  configId?: string | number;

  /**
   * 名称
   */
  appName?: string;

  /**
   * 封面
   */
  cover: string;

  /**
   * 图标
   */
  iconUrl?: string;

  /**
   * 热度
   */
  popularity?: number;

  /**
   * 页面访问量
   */
  pv?: number;

  /**
   *  独立访客数
   */
  uv?: number;

  /**
   * 频道状态:1应用 2频道
   */
  channelStatus?: number;

  /**
   * 应用简介
   */
  remark?: string;
}

export interface AppboxapplicationQuery {
  /**
   * 上级id
   */
  parentId?: string | number;

  /**
   * 配置id
   */
  configId?: string | number;

  /**
   * 名称
   */
  appName?: string;

  /**
   * 图标
   */
  iconUrl?: string;

  /**
   * 热度
   */
  popularity?: number;

  /**
   * 页面访问量
   */
  pv?: number;

  /**
   *  独立访客数
   */
  uv?: number;

  /**
   * 频道状态:1应用 2频道
   */
  channelStatus?: number;

  /**
   * 创建时间
   */
  createTime?: string;
}
