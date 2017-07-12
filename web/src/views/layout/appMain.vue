<template>
  <section class="app-main">
    <div class="level-bar-wrapper">
      <v-level-bar></v-level-bar>
    </div>
    <transition name="fade" mode="out-in">
      <router-view :key="key"></router-view>
    </transition>
  </section>
</template>

<script type="text/ecmascript-6">
  import Levelbar from './levelbar.vue';
  export default {
    name: 'AppMain',
    components: {
      'v-level-bar': Levelbar
    },
    computed: {
      key() {
        return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date();
      }
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .level-bar-wrapper
    border-bottom: 1px solid #ddd
</style>
