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
      <el-form-item label="手机号" prop="driverPhoneNumber">
        <el-input
          v-model="queryParams.driverPhoneNumber"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="未支付" prop="notPaid">
        <el-input
          v-model="queryParams.notPaid"
          placeholder="请输入未支付"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已支付" prop="paid">
        <el-input
          v-model="queryParams.paid"
          placeholder="请输入已支付"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="未结算" prop="noSettlement">
        <el-input
          v-model="queryParams.noSettlement"
          placeholder="请输入未结算"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已结算" prop="settlemented">
        <el-input
          v-model="queryParams.settlemented"
          placeholder="请输入已结算"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="月积分" prop="monthPoints">
        <el-input
          v-model="queryParams.monthPoints"
          placeholder="请输入月积分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总积分" prop="totalPoints">
        <el-input
          v-model="queryParams.totalPoints"
          placeholder="请输入总积分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="奖惩积分" prop="rewardsPunishmentPoints">
        <el-input
          v-model="queryParams.rewardsPunishmentPoints"
          placeholder="请输入奖惩积分"
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
          v-hasPermi="['YDOnlineTaxi:PointsStatistics:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="PointsStatisticsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="driverName" />
      <el-table-column label="手机号" align="center" prop="driverPhoneNumber" />
      <el-table-column label="未支付" align="center" prop="notPaid" />
      <el-table-column label="已支付" align="center" prop="paid" />
      <el-table-column label="未结算" align="center" prop="noSettlement" />
      <el-table-column label="已结算" align="center" prop="settlemented" />
      <el-table-column label="月积分" align="center" prop="monthPoints" />
      <el-table-column label="总积分" align="center" prop="totalPoints" />
      <el-table-column label="奖惩积分" align="center" prop="rewardsPunishmentPoints" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['YDOnlineTaxi:PointsStatistics:edit']"
          >奖惩
          </el-button>
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

    <!-- 添加或修改积分统计对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="奖惩积分" prop="rewardsPunishmentPoints" style="text-align: center">
          <el-input-number v-model="rpPoint" :min="-10000" :max="10000" label="请输入奖惩积分(如-30)"></el-input-number>
        </el-form-item>
      </el-form>
      <el-input
        type="textarea"
        :autosize="{ minRows: 4, maxRows: 6}"
        placeholder="请输入奖惩原因"
        v-model="rpReason">
      </el-input>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  exportPointsStatistics,
  getPointsStatistics,
  listPointsStatistics,
  reLog,
  updatePointsStatistics
} from "@/api/YDOnlineTaxi/PointsStatistics";

export default {
  name: "PointsStatistics",
  data() {
    return {
      //奖惩积分
      rpPoint: null,
      //奖惩理由
      rpReason: '',
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
      // 积分统计表格数据
      PointsStatisticsList: [],
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
        notPaid: null,
        paid: null,
        noSettlement: null,
        settlemented: null,
        monthPoints: null,
        totalPoints: null,
        rewardsPunishmentPoints: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        driverName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        driverPhoneNumber: [
          { required: true, message: "手机号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询积分统计列表 */
    getList() {
      this.loading = true;
      listPointsStatistics(this.queryParams).then(response => {
        this.PointsStatisticsList = response.rows;
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
        notPaid: null,
        paid: null,
        noSettlement: null,
        settlemented: null,
        monthPoints: null,
        totalPoints: null,
        rewardsPunishmentPoints: null
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
      this.title = "添加积分统计";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const driverPhoneNumber = row.driverPhoneNumber || this.ids
      getPointsStatistics(driverPhoneNumber).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "奖惩设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.rewardsPunishmentPoints = Number(this.form.totalPoints) + Number(this.rpPoint);
      updatePointsStatistics(this.form).then(response => {
        this.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
      const reTempLog = {
        phoneNumber: this.form.phoneNumber,
        driverName: this.form.driverName,
        rpReason: this.rpReason,
        operatingTime: '',
        operatingPeople: '',
      }
      reLog(reTempLog);
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有积分统计数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportPointsStatistics(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
