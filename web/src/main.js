// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import ElementUI from 'element-ui';
import './common/css/reset.css';
import 'element-ui/lib/theme-default/index.css';
import './common/stylus/index.styl';
import axios from 'axios';
import App from './App';
import router from './router';
import store from './store/';

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.prototype.$axios = axios;
/* eslint-disable no-new */
new Vue({
  router,
  store,
  template: '<App/>',
  components: {App}
}).$mount('#app');
