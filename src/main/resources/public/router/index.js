import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Arena from '../views/Arena.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/arena',
    name: 'arena',
    component: Arena,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;