<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg }">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" />
        <h1 v-else class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.sidebarTitle : variables.sidebarLightTitle }">{{ title }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo" />
        <h1 class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.sidebarTitle : variables.sidebarLightTitle }">{{ title }} </h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
import logoImg from '@/assets/logo/logo.png'
import variables from '@/assets/styles/variables.scss'

export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  created() {
    this.initWebsocket()
  },
  destroyed() {
    this.webSocket.close();
  },
  methods:{
    /** 创建websocket连接*/
    initWebsocket() {
      let wsUrl = "ws://" + "106.14.126.147" + ":9090/websocket/A"
      try{
        this.webSocket = new WebSocket(wsUrl);
      }catch (e) {
        this.ReConnect()
      }

      //连接触发
      this.webSocket.onopen = function () {
        console.log('WebSocket 已连接')
        this.socketStatus = 'connected';
      }
      //出错触发
      this.webSocket.onerror = this.wsError
      //断开触发
      this.webSocket.onclose = this.wsClose
      //监听服务器推送的消息
      this.webSocket.onmessage = this.wsMessage
    },
    /** 断开回调*/
    wsClose(){
      console.log('WebSocket 已断开')
      this.socketStatus = 'closed'
      this.ReConnect()
    },
    /** 出错回调*/
    wsError(){
      console.log('WebSocket 连接出错')
      this.socketStatus = 'closed'
      this.ReConnect()
    },
    /** 监听消息*/
    wsMessage(event){
      let data = event.data
      this.$notify.info({
        title: '消息',
        message: data,
        duration:0
      });
    },
    /** 重连*/
    ReConnect(){
      //如果锁被锁住就直接返回
      if (this.lockReconnect)
        return
      console.log("重新连接...")
      this.lockReconnect = true
      //没有连接上会一直重连，为了防止请求次数过多一般设置等待时间
      this.wsCreateHandler && clearTimeout(this.wsCreateHandler)
      this.wsCreateHandler = setTimeout(()=>{
        this.initWebsocket()
        this.lockReconnect = false
      },2000)
    },

  },
  computed: {
    variables() {
      return variables;
    },
	sideTheme() {
      return this.$store.state.settings.sideTheme
    }
  },
  data() {
    return {
      //socket数据
      webSocket: null,
      socketStatus: 'closed', //记录websocket连接状态
      lockReconnect: false,//重连锁，防止重复连接
      wsCreateHandler: null,//重连时间句柄
      title: '优登管理系统',
      logo: logoImg
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  line-height: 50px;
  background: #2b2f3a;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 14px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
