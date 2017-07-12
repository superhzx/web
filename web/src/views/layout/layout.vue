<template>
  <div class="app-wrapper">
    <div class="navbar-wrapper">
      <v-nav-bar></v-nav-bar>
    </div>
    <div class="main-container">
      <div class="sidebar-wrapper">
        <v-sidebar></v-sidebar>
      </div>
      <div class="appmain-wrapper">
        <v-app-main></v-app-main>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import { Navbar, Sidebar, AppMain } from '../layout';
  export default {
    name: 'layout',
    components: {
      'v-sidebar': Sidebar,
      'v-nav-bar': Navbar,
      'v-app-main': AppMain
    },
    computed: {
      sidebar() {
        return this.$store.state.sidebar;
      }
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>
  .app-wrapper
    height: 100%
    margin: 0
    display: flex
    flex-flow: column nowrap
    .navbar-wrapper
      flex:0 0 62px
      z-index: 2
    .main-container
      flex: auto
      display: flex
      flex-flow: row nowrap
      .sidebar-wrapper
        flex: 0 0 180px
      .appmain-wrapper
        flex: auto
        overflow: auto
        padding: 0 10px 10px

</style>
