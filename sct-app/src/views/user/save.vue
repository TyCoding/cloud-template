<template>
  <el-dialog :title="dialogTitle" :before-close="handleClose" :visible.sync="dialogVisible" width="40%">
    <el-form ref="form" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">
      <el-form-item v-if="form.id != null" label="用户编号" prop="id" label-width="120px">
        <el-input v-model="form.id" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="登录账户" prop="username" label-width="120px">
        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item v-if="dialogTitle != 'Edit'" label="登录密码" prop="password" label-width="120px">
        <el-input v-model="form.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone" label-width="120px">
        <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime" label-width="120px">
        <el-date-picker v-model="form.createTime" type="datetime" placeholder="选择日期时间" :disabled="true"
                        value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item label="个性头像" prop="avatar" label-width="120px">
        <el-upload class="avatar-uploader"
                   :action="localUpload"
                   :show-file-list="false"
                   :on-success="handleAvatarSuccess"
                   :before-upload="beforeAvatarUpload">
          <img v-if="imgURL" :src="imgURL" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          <div v-if="!imgURL" class="el-upload__text">点我上传本地头像</div>
        </el-upload>
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
  import {save, edit, upload} from '@/api/user'
  import {parseTime} from '@/utils/index'

  export default {
    //父组件向子组件传值，通过props获取。
    //一旦父组件改变了`sonData`对应的值，子组件的`sonData`会立即改变，通过watch函数可以实时监听到值的变化
    //`props`不属于data，但是`props`中的参数可以像data中的参数一样直接使用
    props: ['sonData'],

    data() {
      return {
        dialogVisible: false,
        dialogTitle: 'Add',
        localUpload: upload,
        imgURL: '',
        form: {
          id: '',
          username: '',
          password: '',
          phone: '',
          avatar: '',
          createTime: ''
        },
        rules: {
          username: [{required: true, trigger: 'blur', message: '请输入登录账户'}],
          password: [{required: true, trigger: 'blur', message: '请输入登录密码'}],
          phone: [{required: true, trigger: 'blur', message: '请输入联系电话'}],
          createTime: [{required: true, trigger: 'blur', message: '请选择创建时间'}],
          avatar: [{required: true, trigger: 'blur', message: '请上传个性头像'}]
        },
      }
    },
    watch: {
      'sonData': function (newVal, oldVal) {
        this.form = newVal
        this.imgURL = this.form.avatar
        this.dialogVisible = true
        if (newVal.id != null) {
          this.dialogTitle = 'Edit'
        } else {
          this.dialogTitle = 'Add'
        }
      },
    },
    methods: {
      _notify(message, type) {
        this.$message({
          message: message,
          type: type
        })
      },
      clearForm() {
        this.form.id = null
        this.form.username = null
        this.form.password = null
        this.form.phone = null
        this.form.avatar = null
        this.imgURL = null
        this.form.createTime = parseTime(new Date(), '')
      },
      handleClose() {
        this.clearForm();
        this.dialogVisible = false
      },
      onSubmit(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            if (this.form.id === null) {
              save(this.form).then(response => {
                if (response.code === 200) {
                  this._notify(response.msg, 'success')
                  this.clearForm()
                  this.$emit('sonStatus', true)
                  this.dialogVisible = false
                } else {
                  this._notify(response.msg, 'error')
                }
              })
            } else {
              edit(this.form).then(response => {
                if (response.code === 200) {
                  this._notify(response.msg, 'success')
                  this.clearForm()
                  this.$emit('sonStatus', true)
                  this.dialogVisible = false
                } else {
                  this._notify(response.msg, 'error')
                }
              })
            }
          } else {
            this.$message('error submit!!')
            return false;
          }
        });
      },
      //文件上传成功的钩子函数
      handleAvatarSuccess(res, file, fileList) {
        if (res.code == 200) {
          this._notify('图片上传成功', 'success');
          this.form.avatar = res.data.url;
          this.imgURL = res.data.url;
        }
      },
      //文件上传前的前的钩子函数
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isGIF = file.type === 'image/gif';
        const isPNG = file.type === 'image/png';
        const isBMP = file.type === 'image/bmp';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG && !isGIF && !isPNG && !isBMP) {
          this.$message.error('上传图片必须是JPG/GIF/PNG/BMP 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return (isJPG || isBMP || isGIF || isPNG) && isLt2M;
      },
    }
  }
</script>

<style lang="css">
  .line {
    text-align: center;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>


