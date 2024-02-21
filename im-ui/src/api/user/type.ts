import {SysUserVo} from "@/api/login/login.data";

export interface UserInfoVo {
  user: SysUserVo
  permissions: Array<string>
  roles: Array<string>
}

export interface UserQuery{
  userId:number,
  nickName:string,
  email:string,

}
