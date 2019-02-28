<template>
  <div class="base-input">
    <div class="input-bar">
      <input
        class="input"
        :value="value"
        @input="input"
        @change="change"
        :type="type"
        :maxlength="maxlength"
        :placeholder="placeholder"
        @focus="focus"
        @blur="blur"
      >
      <i
        class="iconfont icon-close btn"
        @click="btnClick"
        v-if="showDeleteBtn"
      ></i>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    //  配合sync使用 :value.sync="value"
    value: {
      type: String,
      required: true
    },
    type: {
      type: String,
      default: 'text'
    },
    placeholder: {
      type: String,
      default: '占位符'
    },
    maxlength: {
      type: Number,
      default: 500
    },
    validtor: {
      type: Function,
      default: function() {
        return true
      }
    }
  },
  data() {
    return {
      isFocus: false
    }
  },
  computed: {
    showDeleteBtn() {
      return this.value.length > 0
    }
  },
  watch: {
    value(n, o) {
      let checkResult = this.validtor(n)
    }
  },
  methods: {
    focus() {
      this.isFocus = true
    },
    blur() {},
    input(e) {
      let value = e.target.value
      this.syncUpdate(value)
    },
    change(e) {},
    btnClick(e) {
      this.syncUpdate('')
    },
    syncUpdate(value) {
      this.$emit('update:value', value)
    }
  }
}
</script>
<style lang="stylus" scoped>
$height = 26px;
$padding = 15px;

.label {
  line-height: $height;
  height: $height;
  transition: all 0.4s;
  position: absolute;
  display: block;
  border: 1px solid transparent;
  padding-left: $padding;
  color: #9a9a9a;
}

.input-active {
  opacity: 1;
  font-size: 12px;
  padding-left: 0;
  transform: translateY(-100%);
}

.input-bar {
  position: relative;
  border: 1px solid #cacaca;
  overflow: hidden;
  padding: 0 $padding;

  .input {
    border: none;
    line-height: $height;
    height: $height;
    background: none;
    outline: none;
    width: 100%;
  }

  .input:focus+ .btn {
    opacity: 1;
  }

  .btn {
    font-size: 16px;
    position: absolute;
    top: 50%;
    right: 0;
    opacity: 0;
    transform: translate(-50%, -50%);
  }
}
</style>


