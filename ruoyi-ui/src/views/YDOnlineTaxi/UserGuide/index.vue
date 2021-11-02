<template>
  <div class="app-container">
    <div class="tag-group">
      <el-tag
        :type="''">
        用户须知
      </el-tag>
    </div>
    <el-input
    type="textarea"
    :autosize="{ minRows: 30, maxRows: 30}"
    placeholder="请输入内容"
    v-model="data"
    :disabled="true">
    </el-input>
    <div style="text-align: center; margin-top: 20px">
        <el-button type="primary" @click="updateGuide()">修改</el-button>
    </div>
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-input
      type="textarea"
      :autosize="{ minRows: 24, maxRows: 24}"
      placeholder="请输入内容"
      v-model="form.guide">
    </el-input>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getUserGuide, updateUserGuide } from "@/api/YDOnlineTaxi/UserGuide";

export default {
  name: "UserGuide",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      //用户须知内容
      form: {
        guide: "",
      },
      data: "",
      // 弹出层标题
      title: "修改用户须知",
      // 是否显示弹出层
      open: false,
    };
  },
  created() {
    this.getGuide();
  },
  methods: {
    /** 查询订单信息列表 */
    getGuide() {
      this.loading = true;
      getUserGuide().then(response => {
        this.form.guide = response;
        this.data = response;
        this.loading = false;
      });
    },
    //更新用户须知
    updateGuide() {
      this.open = true;
    },
    /** 提交按钮 */
    submitForm() {
      updateUserGuide(this.form.guide).then(response => {
        this.msgSuccess("修改成功");
        this.open = false;
        this.getGuide();
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.data = "";
    },
  }
};
</script>
