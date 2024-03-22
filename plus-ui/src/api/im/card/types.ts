export interface CardVO {
  /**
   * id
   */
  id: string | number;

  /**
   * 卡片名称
   */
  cardName: string;

  /**
   * 卡片的价格
   */
  price: number;

  /**
   * 稀有度
   */
  rarity: string;

  /**
   * 卡片状态
   */
  cardStatus: number;

  /**
   * 备注
   */
  remark: string;

}

export interface CardForm extends BaseEntity {
  /**
   * id
   */
  id?: string | number;

  /**
   * 卡片名称
   */
  cardName?: string;

  /**
   * 卡片的价格
   */
  price?: number;

  /**
   * 稀有度
   */
  rarity?: string;

  /**
   * 卡片状态
   */
  cardStatus?: number;

  /**
   * 备注
   */
  remark?: string;

}

export interface CardQuery extends PageQuery {

  /**
   * 卡片名称
   */
  cardName?: string;

  /**
   * 卡片的价格
   */
  price?: number;

  /**
   * 稀有度
   */
  rarity?: string;

  /**
   * 卡片状态
   */
  cardStatus?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



