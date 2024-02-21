export interface MsgActionVO {
  /**
   * id
   */
  id: string | number;

  /**
   * 聊天id
   */
  msgId: string | number;

  /**
   * 赞同量
   */
  endorseCount: number;

  /**
   * 满分量
   */
  fullCount: number;

  /**
   * 点赞量
   */
  likeCount: number;

  /**
   * 备注
   */
  remark: string;

}

export interface MsgActionForm extends BaseEntity {
  /**
   * id
   */
  id?: string | number;

  /**
   * 聊天id
   */
  msgId?: string | number;

  /**
   * 赞同量
   */
  endorseCount?: number;

  /**
   * 满分量
   */
  fullCount?: number;

  /**
   * 点赞量
   */
  likeCount?: number;

  /**
   * 备注
   */
  remark?: string;

}

export interface MsgActionQuery extends PageQuery {

  /**
   * 聊天id
   */
  msgId?: string | number;

  /**
   * 赞同量
   */
  endorseCount?: number;

  /**
   * 满分量
   */
  fullCount?: number;

  /**
   * 点赞量
   */
  likeCount?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



