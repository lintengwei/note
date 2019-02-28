<template>
  <transition name="fade">
    <div
      v-if="show"
      class="tm__mask"
      @click="maskClick"
    ></div>
  </transition>
</template>
<script>
export default {
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    maskClick() {
      this.$emit('update:show', false)
      this.$emit('on-mask-click')
    }
  }
}
</script>

<style lang="stylus" scoped>
.tm__mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #000;
  opacity: 0.7;
}
</style>

