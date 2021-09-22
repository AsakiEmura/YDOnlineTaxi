<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="driverName">
        <el-input
          v-model="queryParams.driverName"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="奖惩积分" prop="points">
        <el-input
          v-model="queryParams.points"
          placeholder="请输入奖惩积分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间" prop="operatingTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.operatingTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择操作时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="操作人" prop="operatingPeople">
        <el-input
          v-model="queryParams.operatingPeople"
          placeholder="请输入操作人"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['YDOnlineTaxi:RPlog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="RPlogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="手机号" align="center" prop="phoneNumber" />
      <el-table-column label="姓名" align="center" prop="driverName" />
      <el-table-column label="奖惩原因" align="center" prop="rpReason" />
      <el-table-column label="奖惩积分" align="center" prop="points" />
      <el-table-column label="操作时间" align="center" prop="operatingTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operatingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="operatingPeople" />
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
import { listRPlog, getRPlog, delRPlog, addRPlog, updateRPlog, exportRPlog } from "@/api/YDOnlineTaxi/RPlog";

export default {
  name: "RPlog",
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
      // 积分奖惩日志表格数据
      RPlogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        driverName: null,
        rpReason: null,
        points: null,
        operatingTime: null,
        operatingPeople: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rpReason: [
          { required: true, message: "奖惩原因不能为空", trigger: "blur" }
        ],
        points: [
          { required: true, message: "奖惩积分不能为空", trigger: "blur" }
        ],
        operatingTime: [
          { required: true, message: "操作时间不能为空", trigger: "blur" }
        ],
        operatingPeople: [
          { required: true, message: "操作人不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询积分奖惩日志列表 */
    getList() {
      this.loading = true;
      listRPlog(this.queryParams).then(response => {
        this.RPlogList = response.rows;
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
        phoneNumber: null,
        driverName: null,
        rpReason: null,
        points: null,
        operatingTime: null,
        operatingPeople: null
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
      this.ids = selection.map(item => item.phoneNumber)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加积分奖惩日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const phoneNumber = row.phoneNumber || this.ids
      getRPlog(phoneNumber).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改积分奖惩日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.phoneNumber != null) {
            updateRPlog(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRPlog(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const phoneNumbers = row.phoneNumber || this.ids;
      this.$confirm('是否确认删除积分奖惩日志编号为"' + phoneNumbers + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRPlog(phoneNumbers);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有积分奖惩日志数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportRPlog(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
