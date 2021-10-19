<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出发地" prop="departure">
        <el-input
          v-model="queryParams.departure"
          placeholder="请输入出发地"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到达地" prop="destination">
        <el-input
          v-model="queryParams.destination"
          placeholder="请输入到达地"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="额外订单积分" prop="extraOrderPoints">
        <el-input
          v-model="queryParams.extraOrderPoints"
          placeholder="请输入额外订单积分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求类型" prop="requirementTypes">
        <el-select v-model="queryParams.requirementTypes" placeholder="请选择需求类型" clearable size="small">
          <el-option label="接站" value="接站" />
          <el-option label="送站" value="送站" />
          <el-option label="全包" value="全包" />
          <el-option label="半包" value="半包" />
          <el-option label="市内单程" value="市内单程" />
          <el-option label="市内往返" value="市内往返" />
          <el-option label="外地单程" value="外地单程" />
          <el-option label="外地往返" value="外地往返" />
        </el-select>
      </el-form-item>
      <el-form-item label="用车类型" prop="carType">
        <el-select v-model="queryParams.carType" placeholder="请选择用车类型" clearable size="small">
          <el-option label="舒适型" value="舒适型" />
          <el-option label="豪华型" value="豪华型" />
          <el-option label="商务型" value="商务型" />
          <el-option label="豪华商务型" value="豪华商务型" />
        </el-select>
      </el-form-item>
      <el-form-item label="用车时间" prop="transportTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.transportTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择用车时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="额外积分申请状态" prop="extraPointsStatus">
        <el-select v-model="queryParams.extraPointsStatus" placeholder="请选择额外积分申请状态" clearable size="small">
          <el-option label="待审核" value="待审核" />
          <el-option label="未审核" value="未审核" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['YDOnlineTaxi:audit_information:remove']"
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
          v-hasPermi="['YDOnlineTaxi:audit_information:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="audit_informationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单号" align="center" prop="orderId" />
      <el-table-column label="出发地" align="center" prop="departure" />
      <el-table-column label="到达地" align="center" prop="destination" />
      <el-table-column label="需求类型" align="center" prop="requirementTypes" />
      <el-table-column label="用车类型" align="center" prop="carType" />
      <el-table-column label="额外订单积分" align="center" prop="extraOrderPoints" />
      <el-table-column label="备注" align="center" prop="notes" />
      <el-table-column label="拒绝理由" align="center" prop="refuseReason" />
      <el-table-column label="额外积分申请状态" align="center" prop="extraPointsStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['YDOnlineTaxi:audit_information:edit']"
            v-if="scope.row.extraPointsStatus === '待审核'"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['YDOnlineTaxi:audit_information:remove']"
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

    <!-- 添加或修改到达审核信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单号" prop="orderId">
          <span>{{form.orderId}}</span>
        </el-form-item>
        <el-form-item label="出发地" prop="departure">
          <span>{{form.departure}}</span>
        </el-form-item>
        <el-form-item label="到达地" prop="destination">
          <span>{{form.destination}}</span>
        </el-form-item>
        <el-form-item label="申请额外订单积分" prop="extraOrderPoints">
          <span>{{form.extraOrderPoints}}</span>
        </el-form-item>
        <el-form-item label="需求类型" prop="requirementTypes">
          <span>{{form.requirementTypes}}</span>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <span>{{form.notes}}</span>
        </el-form-item>
        <el-form-item label="用车类型" prop="carType">
          <span>{{form.carType}}</span>
        </el-form-item>
        <el-form-item label="用车时间" prop="transportTime">
          <span>{{form.transportTime}}</span>
        </el-form-item>
        <el-form-item label="证明照片1">
          <imageUpload
            v-model="form.proofPhoto1"
            :limit="1"
          />
        </el-form-item>
<!--        <el-form-item label="证明照片2">-->
<!--          <imageUpload-->
<!--            v-model="form.proofPhoto2"-->
<!--            :limit="1"-->
<!--          />-->
<!--        </el-form-item>-->
        <el-form-item label="审核结果" prop="refuseReason">
          <el-select v-model="form.extraPointsStatus" placeholder="请指定申请结果" clearable size="small">
            <el-option label="审核通过" value="审核通过" />
            <el-option label="审核不通过" value="审核不通过" />
          </el-select>
        </el-form-item>
        <el-form-item label="拒绝理由" prop="refuseReason">
          <el-input v-model="form.refuseReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAudit_information, getAudit_information, delAudit_information, addAudit_information, updateAudit_information, exportAudit_information } from "@/api/YDOnlineTaxi/audit_information";
export default {
  name: "Audit_information",
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
      // 到达审核信息表格数据
      audit_informationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        departure: null,
        destination: null,
        extraOrderPoints: null,
        requirementTypes: null,
        notes: null,
        carType: null,
        proofPhoto1: null,
        transportTime: null,
        proofPhoto2: null,
        refuseReason: null,
        extraPointsStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        departure: [
          { required: true, message: "出发地不能为空", trigger: "blur" }
        ],
        destination: [
          { required: true, message: "到达地不能为空", trigger: "blur" }
        ],
        extraOrderPoints: [
          { required: true, message: "额外订单积分不能为空", trigger: "blur" }
        ],
        extraPointsStatus: [
          { required: true, message: "额外积分申请状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询到达审核信息列表 */
    getList() {
      this.loading = true;
      listAudit_information(this.queryParams).then(response => {
        this.audit_informationList = response.rows;
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
        orderId: null,
        departure: null,
        destination: null,
        extraOrderPoints: null,
        requirementTypes: null,
        notes: null,
        carType: null,
        proofPhoto1: null,
        transportTime: null,
        proofPhoto2: null,
        refuseReason: null,
        extraPointsStatus: null
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加到达审核信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getAudit_information(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改到达审核信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateAudit_information(this.form).then(response => {
            this.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const orderIds = row.orderId || this.ids;
      this.$confirm('是否确认删除到达审核信息编号为"' + orderIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAudit_information(orderIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有到达审核信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportAudit_information(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
