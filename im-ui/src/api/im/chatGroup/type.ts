export interface ChatGroupData {
  id: string; // 请注意，这里使用字符串代替Long，因为TypeScript中没有内置的Long类型。
  groupLeaderId: string; // 同上，使用字符串作为ID类型。
  groupName: string;
  remark: string;
  userIds: string[]; // 用户ID列表，使用字符串类型。
}


export interface ChatGroupForm extends PageQuery {
  id: string;
  groupLeaderId: string;
  groupName: string;
  remark: string;
  userIds: string[];
  userName: string
}

