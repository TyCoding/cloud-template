<template>
  <el-card>
    <iframe width="400" id="iframe" frameborder="0" scrolling="auto" :src="url"></iframe>
  </el-card>
</template>

<script>
  import {eureka} from '@/api/constant'

  export default {
    name: "eureka",
    data() {
      return {
        url: eureka
      }
    },
    mounted() {
      /**
       * iframe-宽高自适应显示
       */
      const oIframe = document.getElementById('iframe');
      const deviceWidth = document.documentElement.clientWidth;
      const deviceHeight = document.documentElement.clientHeight;
      oIframe.style.width = (Number(deviceWidth) - 255) + 'px'; //数字是页面布局宽度差值
      oIframe.style.height = (Number(deviceHeight) - 20) + 'px'; //数字是页面布局高度差
    },
  }
</script>

<style scoped>

</style>
