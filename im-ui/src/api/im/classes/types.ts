export interface ClassesVO {
  /**
   * id
   */
  id: string | number;

  /**
   * 上级分类
   */
  parentId: string | number;

  /**
   * 分类名称
   */
  classesName: string;

  /**
   * 分类icon
   */
  icon: string;

  /**
   * 应用简介
   */
  remark: string;

  /**
   * 封面
   */
  cover: string;

  /**
   * 子对象
   */
  children: ClassesVO[];
}

export interface ClassesForm extends BaseEntity {
  /**
   * id
   */
  id?: string | number;

  /**
   * 上级分类
   */
  parentId?: string | number;

  /**
   * 分类名称
   */
  classesName?: string;

  /**
   * 分类icon
   */
  icon?: string;

  /**
   * 封面
   */
  cover?: string;

  /**
   * 应用简介
   */
  remark?: string;
}

export interface ClassesQuery {
  /**
   * 上级分类
   */
  parentId?: string | number;

  /**
   * 分类名称
   */
  classesName?: string;

  /**
   * 分类icon
   */
  icon?: string;
}
