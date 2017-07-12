<template>
  <div>
    <template v-for="item in routes">
      <router-link v-if="!item.children"
                   :to="item.path">
        <el-menu-item :index="item.path">
          {{item.name}}
        </el-menu-item>
      </router-link>
      <el-submenu :index="item.name" v-else>
        <template slot="title">
          <i class="el-icon-message"></i>
          {{item.name}}
        </template>
        <template v-for="child in item.children" v-if='!child.hidden'>
          <sidebar-item class='menu-indent' v-if='child.children&&child.children.length>0'
                        :routes='[child]'></sidebar-item>
          <router-link v-else class="menu-indent" :to="child.path">
            <el-menu-item :index="child.path">
              {{child.name}}
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>
    </template>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'SidebarItem',
    props: {
      routes: {
        type: Array
      }
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>
  .menu-indent
    display: block
    text-indent: 0
</style>
