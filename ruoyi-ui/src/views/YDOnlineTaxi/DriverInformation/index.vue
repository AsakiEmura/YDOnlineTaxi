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
      <el-form-item label="电话" prop="driverPhoneNumber">
        <el-input
          v-model="queryParams.driverPhoneNumber"
          placeholder="请输入电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="紧急电话" prop="driverEmergencyContactPhoneNumber">
        <el-input
          v-model="queryParams.driverEmergencyContactPhoneNumber"
          placeholder="请输入紧急电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车牌号" prop="driverCarId">
        <el-input
          v-model="queryParams.driverCarId"
          placeholder="请输入车牌号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车型" prop="driverCarType">
        <el-select v-model="queryParams.driverCarType" placeholder="请选择车型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="汽车型号" prop="carModel">
        <el-input
          v-model="queryParams.carModel"
          placeholder="请输入汽车型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="等级" prop="driverLevel">
        <el-select v-model="queryParams.driverLevel" placeholder="请选择等级" clearable size="small">
          <el-option label="请选择字典生成" value="" />
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
          v-hasPermi="['YDOnlineTaxi:DriverInformation:remove']"
        >移入黑名单
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="DriverInformationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="driverName" />
      <el-table-column label="电话" align="center" prop="driverPhoneNumber" />
      <el-table-column label="紧急电话" align="center" prop="driverEmergencyContactPhoneNumber" />
      <el-table-column label="车牌号" align="center" prop="driverCarId" />
      <el-table-column label="车型" align="center" prop="driverCarType" />
      <el-table-column label="汽车型号" align="center" prop="carModel" />
      <el-table-column label="车辆颜色" align="center" prop="carColor" />
      <el-table-column label="已完成单数" align="center" prop="driverCompleteOrderNumber" />
      <el-table-column label="本月完成单数" align="center" prop="driverCompleteOrderNumberMonthly" />
      <el-table-column label="等级" align="center" prop="driverLevel" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-key"
            @click="handleResetPwd(scope.row)"
            v-hasPermi="['YDOnlineTaxi:DriverAccount:edit']"
          >重置密码
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['YDOnlineTaxi:PointsStatistics:edit']"
          >修改等级
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body style="text-align: center">
      <el-select v-model="rank" placeholder="请选择司机等级">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  delDriverInformation,
  getDriverInformation,
  listDriverInformation,
  updateDriverInformation
} from "@/api/YDOnlineTaxi/DriverInformation";
import {resetUserPwd} from "@/api/YDOnlineTaxi/DriverAccount";

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
      showSearch: false,
      // 总条数
      total: 0,
      // 司机线上账户信息表格数据
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
        driverCarType: null,
        carModel: null,
        driverLevel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        driverName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        driverPhoneNumber: [
          { required: true, message: "电话不能为空", trigger: "blur" }
        ],
        driverCarId: [
          { required: true, message: "车牌号不能为空", trigger: "blur" }
        ],
        driverCarType: [
          { required: true, message: "车型不能为空", trigger: "change" }
        ],
        carModel: [
          { required: true, message: "汽车型号不能为空", trigger: "blur" }
        ],
        carColor: [
          { required: true, message: "车辆颜色不能为空", trigger: "blur" }
        ],
        driverCompleteOrderNumber: [
          {required: true, message: "已完成单数不能为空", trigger: "blur"}
        ],
        driverCompleteOrderNumberMonthly: [
          {required: true, message: "本月完成单数不能为空", trigger: "blur"}
        ],
        driverLevel: [
          {required: true, message: "等级不能为空", trigger: "change"}
        ]
      },
      // 司机等级
      options: [{
        value: '黄金司机',
        label: '黄金司机'
      }, {
        value: '钻石司机',
        label: '钻石司机'
      }, {
        value: '王者司机',
        label: '王者司机'
      }],
      rank: '',
    };
  },
  mounted() {
    this.getList();
    // this.$emit("queryTable");
  },
  watch: {
    $route() {
      this.getList()
    }
  },
  methods: {
    /** 查询司机线上账户信息
     列表 */
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
        driverEmergencyContactPhoneNumber: null,
        driverCarId: null,
        driverCarType: null,
        carModel: null,
        carColor: null,
        driverCompleteOrderNumber: null,
        driverCompleteOrderNumberMonthly: null,
        driverLevel: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改司机等级操作 */
    handleUpdate(row) {
      this.reset();
      const driverPhoneNumber = row.driverPhoneNumber || this.ids
      getDriverInformation(driverPhoneNumber).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改司机等级";
        this.rank = this.form.driverLevel;
      });
    },
    /** 提交新的司机等级按钮 */
    submitForm() {
      this.form.driverLevel = this.rank;
      updateDriverInformation(this.form).then(response => {
        this.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入用户:' + row.driverName + ' 的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /(?=^.{8,20}$)(?=(?:.*?\d){1})(?=.*[a-z])(?=(?:.*?[A-Z]){1})(?=(?:.*?[`·~!@#$%^&*()_+}{|:;'",<.>/?\=\[\]\-\\]){1})(?!.*\s)[0-9a-zA-Z`·~!@#$%^&*()_+}{|:;'",<.>/?\=\[\]\-\\]*$/,
        inputErrorMessage: "用户密码长度必须介于 8 和 20 之间,且必须包含一个大写字母,一个小写字母,一个数字和一个特殊字符",
      }).then(({value}) => {
        resetUserPwd(row.idNumber, value).then(response => {
          this.msgSuccess("修改成功，新密码是：" + value);
        });
      }).catch(() => {
      });
    },
    /** 黑名单按钮操作 */
    handleDelete(row) {
      const driverPhoneNumbers = row.driverPhoneNumber || this.ids;
      this.$confirm('是否确认删除司机线上账户信息编号为"' + driverPhoneNumbers + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDriverInformation(driverPhoneNumbers);
      }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
  }
};
</script>
