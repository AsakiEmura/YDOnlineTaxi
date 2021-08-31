<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="司机姓名" prop="driverName">
        <el-input
          v-model="queryParams.driverName"
          placeholder="请输入司机姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话" prop="driverPhoneNumber">
        <el-input
          v-model="queryParams.driverPhoneNumber"
          placeholder="请输入司机电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="紧急电话" prop="driverEmergencyContactPhoneNumber">
        <el-input
          v-model="queryParams.driverEmergencyContactPhoneNumber"
          placeholder="请输入司机紧急联系电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车牌号" prop="driverCarId">
        <el-input
          v-model="queryParams.driverCarId"
          placeholder="请输入司机车牌号"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['YDOnlineTaxi:DriverInformation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['YDOnlineTaxi:DriverInformation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['YDOnlineTaxi:DriverInformation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['YDOnlineTaxi:DriverInformation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="DriverInformationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="司机姓名" align="center" prop="driverName" />
      <el-table-column label="电话" align="center" prop="driverPhoneNumber" />
      <el-table-column label="车型" align="center" prop="driverCarType" />
      <el-table-column label="紧急联系电话" align="center" prop="driverEmergencyContactPhoneNumber" />
      <el-table-column label="车牌号" align="center" prop="driverCarId" />
      <el-table-column label="已完成单子" align="center" prop="driverCompleteOrderNumber" />
      <el-table-column label="本月已完成单子" align="center" prop="driverCompleteOrderNumberMonthly" />
      <el-table-column label="等级" align="center" prop="driverLevel" />
      <el-table-column label="评分" align="center" prop="driverRateNumber" />
      <el-table-column label="汽车型号" align="center" prop="carModel" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['YDOnlineTaxi:DriverInformation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['YDOnlineTaxi:DriverInformation:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改司机账户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDriverInformation, getDriverInformation, delDriverInformation, addDriverInformation, updateDriverInformation, exportDriverInformation } from "@/api/YDOnlineTaxi/DriverInformation";

export default {
  name: "DriverInformation",
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
      // 司机账户信息表格数据
      DriverInformationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        driverName: null,
        driverPhoneNumber: null,
        driverEmergencyContactPhoneNumber: null,
        driverCarId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        driverName: [
          { required: true, message: "司机姓名不能为空", trigger: "blur" }
        ],
        driverPhoneNumber: [
          { required: true, message: "司机电话不能为空", trigger: "blur" }
        ],
        driverCarType: [
          { required: true, message: "司机车型不能为空", trigger: "blur" }
        ],
        driverCarId: [
          { required: true, message: "司机车牌号不能为空", trigger: "blur" }
        ],
        carModel: [
          { required: true, message: "汽车型号不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询司机账户信息列表 */
    getList() {
      this.loading = true;
      listDriverInformation(this.queryParams).then(response => {
        this.DriverInformationList = response.rows;
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
        driverPhoneNumber: null,
        driverCarType: null,
        driverEmergencyContactPhoneNumber: null,
        driverCarId: null,
        driverCompleteOrderNumber: null,
        driverCompleteOrderNumberMonthly: null,
        driverLevel: null,
        driverRateNumber: null,
        carModel: null
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
      this.ids = selection.map(item => item.driverPhoneNumber)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加司机账户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const driverPhoneNumber = row.driverPhoneNumber || this.ids
      getDriverInformation(driverPhoneNumber).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改司机账户信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.driverPhoneNumber != null) {
            updateDriverInformation(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDriverInformation(this.form).then(response => {
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
      const driverPhoneNumbers = row.driverPhoneNumber || this.ids;
      this.$confirm('是否确认删除司机账户信息编号为"' + driverPhoneNumbers + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDriverInformation(driverPhoneNumbers);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有司机账户信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportDriverInformation(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
