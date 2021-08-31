<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="司机姓名
" prop="driverName">
        <el-input
          v-model="queryParams.driverName"
          placeholder="请输入司机姓名
"
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
      <el-form-item label="总收入" prop="totalIncome">
        <el-input
          v-model="queryParams.totalIncome"
          placeholder="请输入总收入"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="月收入" prop="monthIncome">
        <el-input
          v-model="queryParams.monthIncome"
          placeholder="请输入月收入"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="未支付报酬" prop="unpaidRemuneration">
        <el-input
          v-model="queryParams.unpaidRemuneration"
          placeholder="请输入未支付报酬"
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
          v-hasPermi="['YDOnlineTaxi:FinancialStatistics:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="FinancialStatisticsList"
      row-key="monthIncome"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="司机姓名
" align="center" prop="driverName" />
      <el-table-column label="总收入" align="center" prop="totalIncome" />
      <el-table-column label="月收入" align="center" prop="monthIncome" />
      <el-table-column label="未支付报酬" align="center" prop="unpaidRemuneration" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['YDOnlineTaxi:FinancialStatistics:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['YDOnlineTaxi:FinancialStatistics:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['YDOnlineTaxi:FinancialStatistics:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改财务统计对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="司机姓名
" prop="driverName">
          <el-input v-model="form.driverName" placeholder="请输入司机姓名
" />
        </el-form-item>
        <el-form-item label="总收入" prop="totalIncome">
          <treeselect v-model="form.totalIncome" :options="FinancialStatisticsOptions" :normalizer="normalizer" placeholder="请选择总收入" />
        </el-form-item>
        <el-form-item label="月收入" prop="monthIncome">
          <el-input v-model="form.monthIncome" placeholder="请输入月收入" />
        </el-form-item>
        <el-form-item label="未支付报酬" prop="unpaidRemuneration">
          <el-input v-model="form.unpaidRemuneration" placeholder="请输入未支付报酬" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFinancialStatistics, getFinancialStatistics, delFinancialStatistics, addFinancialStatistics, updateFinancialStatistics, exportFinancialStatistics } from "@/api/YDOnlineTaxi/FinancialStatistics";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "FinancialStatistics",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 财务统计表格数据
      FinancialStatisticsList: [],
      // 财务统计树选项
      FinancialStatisticsOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        driverName: null,
        driverPhoneNumber: null,
        totalIncome: null,
        monthIncome: null,
        unpaidRemuneration: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        driverName: [
          { required: true, message: "司机姓名不能为空", trigger: "blur" }
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
    /** 查询财务统计列表 */
    getList() {
      this.loading = true;
      listFinancialStatistics(this.queryParams).then(response => {
        this.FinancialStatisticsList = this.handleTree(response.data, "monthIncome", "totalIncome");
        this.loading = false;
      });
    },
    /** 转换财务统计数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.monthIncome,
        label: node.driverName,
        children: node.children
      };
    },
	/** 查询财务统计下拉树结构 */
    getTreeselect() {
      listFinancialStatistics().then(response => {
        this.FinancialStatisticsOptions = [];
        const data = { monthIncome: 0, driverName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "monthIncome", "totalIncome");
        this.FinancialStatisticsOptions.push(data);
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
        totalIncome: null,
        monthIncome: null,
        unpaidRemuneration: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.monthIncome) {
        this.form.totalIncome = row.monthIncome;
      } else {
        this.form.totalIncome = 0;
      }
      this.open = true;
      this.title = "添加财务统计";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.totalIncome = row.monthIncome;
      }
      getFinancialStatistics(row.driverPhoneNumber).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改财务统计";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.driverPhoneNumber != null) {
            updateFinancialStatistics(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFinancialStatistics(this.form).then(response => {
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
      this.$confirm('是否确认删除财务统计编号为"' + row.driverPhoneNumber + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delFinancialStatistics(row.driverPhoneNumber);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    }
  }
};
</script>
