export interface MsgRecordVO {
  /**
   * id消息
   */
  id: string | number;

  /**
   * 发送者
   */
  senderId: string | number;

  /**
   * 接收者
   */
  toId: string | number;

  /**
   * 群聊
   */
  toGroupId: string | number;

  /**
   * 频道
   */
  toAppId: string | number;

  /**
   * 消息内容
   */
  content: string;

  /**
   * 内容类型
   */
  msgType: number;

  /**
   * 消息类型
   */
  toType: number;

  /**
   * 消息状态
   */
  status: number;

  /**
   * 读取状态
   */
  unread: number;

  /**
   * 引用|回复消息id
   */
  refMsgId: string | number;

  /**
   * 备注
   */
  remark: string;

}

export interface MsgRecordForm extends BaseEntity {
  /**
   * id消息
   */
  id?: string | number;

  /**
   * 发送者
   */
  senderId?: string | number;

  /**
   * 接收者
   */
  toId?: string | number;

  /**
   * 群聊
   */
  toGroupId?: string | number;

  /**
   * 频道
   */
  toAppId?: string | number;

  /**
   * 消息内容
   */
  content?: string;

  /**
   * 内容类型
   */
  msgType?: number;

  /**
   * 消息类型
   */
  toType?: number;

  /**
   * 消息状态
   */
  status?: number;

  /**
   * 读取状态
   */
  unread?: number;

  /**
   * 引用|回复消息id
   */
  refMsgId?: string | number;

  /**
   * 备注
   */
  remark?: string;

}

export interface MsgRecordQuery extends PageQuery {

  /**
   * 发送者
   */
  senderId?: string | number;

  /**
   * 接收者
   */
  toId?: string | number;

  /**
   * 群聊
   */
  toGroupId?: string | number;

  /**
   * 频道
   */
  toAppId?: string | number;

  /**
   * 消息内容
   */
  content?: string;

  /**
   * 内容类型
   */
  msgType?: number;

  /**
   * 消息类型
   */
  toType?: number;

  /**
   * 消息状态
   */
  status?: number;

  /**
   * 读取状态
   */
  unread?: number;

  /**
   * 引用|回复消息id
   */
  refMsgId?: string | number;

    /**
     * 日期范围参数
     */
    params?: any;
}



