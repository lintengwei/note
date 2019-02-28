<template>
  <transition-group
    name='slide'
    tag="div"
    class="tm__toast_dm"
  >
    <p
      v-for="item in msgQueue"
      :key="item.id"
      class="msg-item"
    ><i
        class="iconfont"
        :class="{
          [item.icon]:true,
          [item.cs]:true
          }"
      ></i>{{item.msg}}</p>
  </transition-group>
</template>
<script>
export default {
  data() {
    return {
      msgQueue: [],
      iconObj: {
        info: '',
        success: 'icon-zhengque',
        warn: 'icon-icon-test',
        error: 'icon-cuowu'
      },
      cObj: {
        success: 'success',
        warn: 'warn',
        error: 'error'
      }
    }
  }
}
</script>
<style lang="stylus" scoped>
.msg-item {
  margin: 0;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: #000;
  opacity: 0.65;
  padding: 10px 20px;
  color: white;
  border-radius: 8px;
  display: flex;
  align-items: center;
  z-index: 9999;

  .iconfont {
    font-size: 28px;
  }
}

.success {
  color: #3bc433;
}

.warn {
  color: #f9d65d;
}

.error {
  color: #fd5055;
}

.slide-enter-active, .slide-leave-active {
  transition: all 0.6s;
}

.slide-enter {
  opacity: 0;
  transform: translate(-50%, -50px);
}

.slide-leave-to {
  opacity: 0;
  transform: translate(-50%, 50px);
}
</style>


