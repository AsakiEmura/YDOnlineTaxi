<template>
  <el-upload
    class="basic-upload-field__uploader"
    drag ref="upload"
    :action="actionUrl"
    list-type="picture-card"
    :on-success="uploadSuccess"
    :before-upload="beforeUpload"
    :on-remove="removeHandler"
    :http-request="httpRequest"
  >
    <i class="el-icon-upload"></i>
    <div class="el-upload__text">{{$t('upload.drag')}}
      <em>{{$t('upload.select')}}</em>
    </div>
    <div class="el-upload__tip" slot="tip">{{$t('upload.tip')}}</div>
  </el-upload>

</template>

<script>


export default {
  name: 'basic-upload-field',
  data () {
    return {
      fileList: [],
      actionUrl: `${process.env.baseURL}/api/file/upload`,
      fileReader: '',
    }
  },

  methods: {
    httpRequest (options) {
      let file = options.file
      let filename = file.name
      if (file) {
        this.fileReader.readAsDataURL(file)
      }
      this.fileReader.onload = () => {
        let base64Str = this.fileReader.result
        let config = {
          url: '/api/file/upload/base64',
          method: 'post',
          // file: file,
          data: {
            base64Str: base64Str.split(',')[1],
            name: filename
          },
          timeout: 10000,
          onUploadProgress: function (progressEvent) {
            // console.log(progressEvent)
            progressEvent.percent = progressEvent.loaded / progressEvent.total * 100
            options.onProgress(progressEvent, file)
          },
        }
        axios(config)
          .then(res => {
            options.onSuccess(res, file)
          })
          .catch(err => {
            options.onError(err)
          })
      }
    },
    removeHandler (file, fileList) {
      let index = this.fileList.indexOf(file.key)
      this.fileList.splice(index, 1)
      console.log('current file list ==>', this.fileList)
      this.$store.commit('applicant/updateResume', this.fileList)
      axios.delete(`/api/file/delete?key=${file.key}`).then(res => {
        console.log(res)
      })
    },
    beforeUpload (file) {
      const isLt5M = file.size < 5 * 1024 * 1024
      if (this.fileList.length >= 3) {
        alert('At most 3 files')
        return false
      }
      if (!isLt5M) {
        alert('The max size is 5MB')
        return false
      }
    },
    uploadSuccess (res, file, fileList) {
      let data = res.data
      console.log('upload result:', res, file)
      file.key = data.key
      this.fileList.push(data.key)
    }
  },
  mounted () {
    if (!window.FileReader) {
      console.error('Your browser does not support FileReader API!')
    }
    this.fileReader = new FileReader()
  }
}
</script>

<style >
.basic-upload-field {
  padding: 16px;
  /* text-align: left; */
}
.basic-upload-field__uploader {
  padding: 16px;
  text-align: center;
}
.basic-upload-field__uploader .el-upload-dragger {
  min-width: 600px;
}
.basic-upload-field__uploader .el-upload-list {
  /* text-align: center; */
  margin: 0 auto;
  padding: 0 16px;
  min-width: 500px;
}
.basic-upload-field__uploader .el-upload-list__item-name {
  max-width: 600px;
}
</style>
