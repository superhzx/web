import Vue from 'vue';
import Router from 'vue-router';
import Layout from '../views/layout/layout.vue';
import BusinessConfig from '../views/businessFlow/businessConfig.vue';
import BusinessRole from '../views/businessFlow/businessRole.vue';
import BusinessSystem from '../views/businessFlow/businessSystem.vue';
import OrgRole from '../views/businessFlow/orgRole.vue';
import UserInfo from '../views/sysManage/userInfo.vue';
import DeptInfo from '../views/sysManage/deptInfo.vue';
import PositionInfo from '../views/sysManage/positionInfo.vue';
import RoleInfo from '../views/sysManage/roleInfo.vue';
import PermissionInfo from '../views/sysManage/permissionInfo.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      component: Layout,
      name: 'index',
      meta: '首页'
    },
    {
      path: '/workflow',
      component: Layout,
      name: 'workflow',
      meta: '流程管理',
      redirect: '/workflow/config',
      children: [
        {
          path: '/workflow/config',
          component: BusinessConfig,
          name: 'BusinessConfig',
          meta: '业务流程配置'
        },
        {
          path: '/workflow/role',
          component: BusinessRole,
          name: 'BusinessRole',
          meta: '业务角色管理'
        },
        {
          path: '/workflow/system',
          component: BusinessSystem,
          name: 'BusinessSystem',
          meta: '业务系统管理'
        },
        {
          path: '/workflow/orgrole',
          component: OrgRole,
          name: 'OrgRole',
          meta: '组织角色管理'
        }
      ]
    },
    {
      path: '/sysManage',
      component: Layout,
      name: 'sysManage',
      meta: '系统管理',
      redirect: '/sysManage/userInfo',
      children: [
        {
          path: '/sysManage/userInfo',
          component: UserInfo,
          name: 'userInfo',
          meta: '用户信息'
        },
        {
          path: '/sysManage/deptInfo',
          component: DeptInfo,
          name: 'deptInfo',
          meta: '部门信息'
        },
        {
          path: '/sysManage/positionInfo',
          component: PositionInfo,
          name: 'positionInfo',
          meta: '岗位信息'
        },
        {
          path: '/sysManage/roleInfo',
          component: RoleInfo,
          name: 'roleInfo',
          meta: '角色信息'
        },
        {
          path: '/sysManage/permissionInfo',
          component: PermissionInfo,
          name: 'permissionInfo',
          meta: '权限信息'
        }
      ]
    }
  ]
});
