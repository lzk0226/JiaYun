<template>
  <div :class="{'has-logo':showLogo}" class="sidebar-glass">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        class="sidebar-menu"
        :text-color="settings.sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
        :unique-opened="true"
        :active-text-color="settings.theme"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="(route, index) in sidebarRouters"
          :key="route.path  + index"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex"
import Logo from "./Logo"
import SidebarItem from "./SidebarItem"
import variables from "@/assets/styles/variables.scss"

export default {
  components: { SidebarItem, Logo },
  computed: {
    ...mapState(["settings"]),
    ...mapGetters(["sidebarRouters", "sidebar"]),
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebar-glass {
  height: 100%;
  /* 渐变彩色玻璃效果 */
  background: linear-gradient(135deg, rgba(255, 0, 150, 0.25), rgba(0, 200, 255, 0.25));
  backdrop-filter: blur(12px); /* 毛玻璃 */
  -webkit-backdrop-filter: blur(12px);
  border-right: 1px solid rgba(255, 255, 255, 0.2);
}

.sidebar-menu {
  background: transparent !important; /* 保持透明，显示玻璃效果 */
  border-right: none;
}
</style>
