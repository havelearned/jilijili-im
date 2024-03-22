import {route} from 'quasar/wrappers';
import {createMemoryHistory, createRouter, createWebHashHistory, createWebHistory,} from 'vue-router';

import routes from './routes';
import {getToken} from '@/utils/auth';

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory);

  const Router = createRouter({
    scrollBehavior: () => ({left: 0, top: 0}),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(process.env.VUE_ROUTER_BASE),
  });


  Router.beforeEach((to, from, next) => {
    let token = getToken();
      if (to.path !== '/login' && !token) {
          // 如果用户未登录且访问的不是登录页，则重定向到登录页
          next('/login');
      } else {
          // 已登录或者访问的是登录页，则正常跳转
          next();
      }

  });


  return Router;
});
