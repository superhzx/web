<template>
  <el-menu style="height: 100%" mode="vertical" :default-active="$route.path">
    <sidebar-item :routes='menu'></sidebar-item>
  </el-menu>
</template>

<script type="text/ecmascript-6">
  import { mapGetters } from 'vuex';
  import SidebarItem from './SidebarItem';
  export default {
    components: { SidebarItem },
    computed: {
      ...mapGetters([
        'menu'
      ])
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>
</style>
