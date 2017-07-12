<template>
  <el-breadcrumb class="app-level-bar" separator="/">
    <el-breadcrumb-item v-for="(item, index) in levelList" :key="item">
      <router-link v-if="item.redirect !== undefined || index == levelList.length - 1"
                   :to="item.path" class="no-redirect">{{item.meta}}>
      </router-link>
      <router-link v-else :to="item.path">{{item.meta}}</router-link>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script type="text/ecmascript-6">
  export default {
    created() {
      this.getBreadcrumb();
    },
    data() {
      return {
        levelList: null
      };
    },
    methods: {
      getBreadcrumb() {
        let matched = this.$route.matched.filter(item => item.meta);
        const first = matched[0];
        if (first && (first.meta !== '首页' || first.path !== '')) {
          matched = [{name: 'index', path: '/', meta: '首页'}].concat(matched);
        }
        this.levelList = matched;
      }
    },
    watch: {
      $route() {
        this.getBreadcrumb();
      }
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>
  .app-level-bar.el-breadcrumb
    display: inline-block
    font-size: 14px
    line-height: 37px
    margin-left: 10px
    .no-redirect
      color: #97a8be
      cursor: text
</style>
