/**
 * TypeScript接口对应于Java的AppboxApplicationVo类
 */
export interface AppboxApplicationVo {

  /**
   * id
   */
  id?: number; // Java中的Long在TypeScript中通常映射为number

  /**
   * 上级id
   */
  parentId?: number; // 同上

  /**
   * 名称
   */
  appName?: string; // Java中的String在TypeScript中映射为string

  /**
   * 图标
   */
  iconUrl?: string; // 同上

  /**
   * 热度
   */
  popularity?: number; // 同上

  /**
   * 页面访问量
   */
  pv?: number; // 同上

  /**
   * 独立访客数
   */
  uv?: number; // 同上

  /**
   * 频道状态:1应用 2频道
   */
  channelStatus?: number; // 同上

  /**
   * 应用简介
   */
  remark?: string; // 同上

  /**
   * 创建时间
   */
  createTime?: Date; // Java中的Date在TypeScript中映射为Date

  cover?:string;
  coverUrl?:string;

}


// @ts-ignore
export interface AppboxApplicationFrom extends BaseEntity{

  /**
   * id
   */
  id?: number;

  /**
   * 上级id
   */
  parentId?: number;

  /**
   * 名称
   */
  appName?: string;

  /**
   * 分类id
   */
  classesId?:string,

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
   * 独立访客数
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
  /**
   * 创建时间
   */
  createTime?: Date;

  /**
   * 封面
   */
  cover: string;
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
   * 分类id
   */
  classesId?:string|number;

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
