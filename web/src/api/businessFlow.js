/**
 * Created by john on 2017/7/5.
 */
import axios from 'axios';
import {address} from './address';
export function businessConfigListByPage (queryParam) {
  return axios({
    method: 'get',
    url: address + 'businessconfig/listByPage',
    params: queryParam
  });
}
export function businessConfigSave (businessConfig) {
  return axios({
    method: 'post',
    url: address + 'businessconfig/save',
    params: businessConfig
  });
}
export function businessConfigDelete (businessConfig) {
  return axios({
    method: 'post',
    url: address + 'businessconfig/delete',
    params: businessConfig
  });
}
export function businessConfigBatchDelete (selections) {
  return axios({
    method: 'post',
    url: address + 'businessconfig/batchDelete',
    params: selections
  });
}
export function businessRoleListByPage (queryParam) {
  return axios({
    method: 'get',
    url: address + 'businessRole/list',
    params: queryParam
  });
}
export function businessRoleSaveOrUpdate (businessRole) {
  return axios({
    method: 'post',
    url: address + 'businessRole/saveOrUpdate',
    params: businessRole
  });
}
export function businessRoleDelete (businessRole) {
  return axios({
    method: 'get',
    url: address + 'businessRole/delete',
    params: businessRole
  });
}
export function businessRoleCommonData() {
  return axios({
    method: 'get',
    url: address + 'businessRole/getSelectInfo'
  });
}
export function businessSystemlistByPage(queryParam) {
  return axios({
    method: 'get',
    url: address + 'businesssystem/listByPage',
    params: queryParam
  });
}
export function businessSystemSaveOrUpdate(businessSystem) {
  return axios({
    method: 'post',
    url: address + 'businesssystem/save',
    params: businessSystem
  });
}
export function businessSystemDelete(businessSystem) {
  return axios({
    method: 'get',
    url: address + 'businesssystem/delete',
    params: businessSystem
  });
}
export function businessSystemCommonData() {
  return axios({
    method: 'get',
    url: address + 'businesssystem/getSelectInfo'
  });
}
export function orgRoleListByPage(queryParam) {
  return axios({
    method: 'get',
    url: address + 'orgRole/list',
    params: queryParam
  });
}
export function orgRoleSaveOrUpdate(businessOrgRole) {
  return axios({
    method: 'post',
    url: address + 'orgRole/save',
    params: businessOrgRole
  });
}
export function orgRoleDelete(businessOrgRole) {
  return axios({
    method: 'get',
    url: address + 'orgRole/delete',
    params: businessOrgRole
  });
}
export function departmentTree() {
  return axios({
    method: 'get',
    url: address + 'departments/getTreeData'
  });
}
export function orgRoleCommonData() {
  return axios.all([departmentTree(), businessRoleCommonData()]);
}

export function personQueryByPage (queryParam) {
  return axios({
    method: 'get',
    url: address + 'user/listByPage',
    params: queryParam
  });
}
export function orgRoleUserSave(param) {
  return axios({
    method: 'post',
    url: address + 'orgRoleUser/saveOrgRoleUser',
    params: param
  });
}
