<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="driverName">
        <el-input
          v-model="queryParams.driverName"
          placeholder="请输入司机真实姓名
"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="次数" prop="pushTimes">
        <el-input
          v-model="queryParams.pushTimes"
          placeholder="请输入可推送次数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="wxWithDriversList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="司机真实姓名" align="center" prop="driverName" />
      <el-table-column label="手机号" align="center" prop="phoneNumber" />
      <el-table-column label="可推送次数" align="center" prop="pushTimes" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listWxWithDrivers, getOpenId, exportWxWithDrivers } from "@/api/YDOnlineTaxi/wxWithDrivers";

export default {
  name: "WxWithDrivers",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 微信司机连接表格数据
      wxWithDriversList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        driverName: null,
        phoneNumber: null,
        openId: null,
        pushTimes: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        driverName: [
          { required: true, message: "司机真实姓名不能为空", trigger: "blur" }
        ],
        phoneNumber: [
          { required: true, message: "手机号不能为空", trigger: "blur" }
        ],
        openId: [
          { required: true, message: "微信用户唯一标识不能为空", trigger: "blur" }
        ],
        pushTimes: [
          { required: true, message: "可推送次数不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询微信司机连接列表 */
    getList() {
      this.loading = true;
      listWxWithDrivers(this.queryParams).then(response => {
        this.wxWithDriversList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        driverName: null,
        phoneNumber: null,
        openId: null,
        pushTimes: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.driverName)
      this.single = selection.length!==1
      this.multiple = !selection.length
    }
  }
};
</script>
