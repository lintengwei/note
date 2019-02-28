<template>
  <div>
    <base-mask
      :show.sync="show"
      @on-mask-click="cancel"
    ></base-mask>
    <transition name="modal-slide">
      <div
        v-if="show"
        class="tm__modal"
      >
        <div class="title">{{title}}</div>
        <div class="content">{{message}}</div>
        <div class="btns">
          <button
            class="btn cancel"
            @click="cancel"
          >{{cancelText}}</button>
          <button
            class="btn confirm"
            @click="confirm"
          >{{confirmText}}</button>
        </div>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  data() {
    return {
      show: false,
      title: '',
      message: '',
      cancelText: '',
      confirmText: ''
    }
  },
  methods: {
    cancel() {
      this.$emit('cancel')
    },
    confirm() {
      this.$emit('confirm')
    }
  },
  beforeDestroy() {
    console.log('modal destroy')
  }
}
</script>
<style lang="stylus" scoped>
.tm__modal {
  position: fixed;
  width: 80%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  border: 1px solid black;
  border-radius: 3px;
  padding: 2px;
  background: white;

  .title {
    line-height: 2.6;
  }

  .content {
    padding: 10px 0;
  }

  .btns {
    display: flex;
    border-top: 1px solid #cacaca;

    .btn {
      background: none;
      border: none;
      outline: none;
      flex-grow: 1;
      line-height: 2.4;

      &:first-of-type {
        border-right: 1px solid #cacaca;
      }
    }

    .cancel {
      color: #9a9a9a;

      &:active {
        color: black;
      }
    }
  }
}
</style>


