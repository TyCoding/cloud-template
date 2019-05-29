<template>
  <el-dialog title="修改密码" :before-close="handleClose" :visible.sync="dialogVisible" width="40%">
    <el-form ref="form" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">
      <el-form-item label="登录密码" prop="password" label-width="120px">
        <el-input v-model="form.password" type="password" placeholder="请输入新密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="repassword" label-width="120px">
        <el-input v-model="form.repassword" type="password" placeholder="请确认新密码"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">
        Cancel
      </el-button>
      <el-button type="primary" @click="onSubmit('form')">
        Confirm
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {changePass} from '@/api/user'

  export default {
    name: "ChangePass",
    props: [ 'changeStatus' ],
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        dialogVisible: false,
        form: { password: null, repassword: null },
        rules: {
          password: [{required: true, trigger: 'blur', message: '请输入新密码'}],
          repassword: [{validator: validatePass, required: true, trigger: 'blur'}],
        },
      }
    },
    watch: {
      'changeStatus': function (newVal, oldVal) {
        this.dialogVisible = true;
      },
    },
    methods: {
      handleClose() {
        this.dialogVisible = false
      },
      async logout() {
        await this.$store.dispatch('user/logout')
        this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      },
      onSubmit(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            console.log('to changePass...');
            changePass(this.form).then(response => {
              if (response.code == 200) {
                //logout
                this.logout();
              }
            })
          } else {
            this.$message('error submit!!')
            return false;
          }
        });
      },
    }
  }
</script>

<style scoped>

</style>
