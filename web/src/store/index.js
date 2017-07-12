/**
 * Created by john on 2017/6/28.
 */
import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './action';
import getters from './getters';

Vue.use(Vuex);

const state = {
  sidebar: {
    opened: true
  },
  name: '小李',
  avatar: '',
  menu: [
    {
      id: '0',
      icon: 'mima',
      noDropdown: false,
      hidden: false,
      name: '流程管理',
      path: '/',
      children: [
        {id: '1', path: '/workflow/config', icon: 'mima', noDropdown: false, hidden: false, name: '业务流程配置'},
        {id: '2', path: '/workflow/role', icon: 'mima', noDropdown: false, hidden: false, name: '业务角色管理'},
        {id: '3', path: '/workflow/system', icon: 'mima', noDropdown: false, hidden: false, name: '业务系统管理'},
        {id: '4', path: '/workflow/orgrole', icon: 'mima', noDropdown: false, hidden: false, name: '组织角色管理'}
      ]
    },
    {
      id: '0',
      icon: 'mima',
      noDropdown: false,
      hidden: false,
      name: '系统管理',
      path: '/',
      children: [
        {id: '1', path: '/sysManage/userInfo', icon: 'mima', noDropdown: false, hidden: false, name: '用户信息'},
        {id: '2', path: '/sysManage/deptInfo', icon: 'mima', noDropdown: false, hidden: false, name: '部门信息'},
        {id: '3', path: '/sysManage/positionInfo', icon: 'mima', noDropdown: false, hidden: false, name: '岗位信息'},
        {id: '4', path: '/sysManage/roleInfo', icon: 'mima', noDropdown: false, hidden: false, name: '角色信息'},
        {id: '5', path: '/sysManage/permissionInfo', icon: 'mima', noDropdown: false, hidden: false, name: '权限信息'}
      ]
    }
  ]
};

export default new Vuex.Store({
  state,
  getters,
  actions,
  mutations
});
