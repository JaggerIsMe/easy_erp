import { createRouter, createWebHistory } from 'vue-router'
import VueCookies from 'vue-cookies'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      meta: {
        needLogin: true
      },
      component: () => import('../views/Home.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue'),
    }
  ],
});

router.beforeEach((to, form, next) => {
  const userInfo = VueCookies.get("loginUserInfo")
  if (to.meta.needLogin != null && to.meta.needLogin && userInfo == null) {
    router.push("/login");
  }
  next();
})

export default router
