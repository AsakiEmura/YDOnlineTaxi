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
      <el-form-item label="手机号" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车型" prop="motorcycleType">
        <el-input
          v-model="queryParams.motorcycleType"
          placeholder="请输入车型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车牌号" prop="licensePlateNumber">
        <el-input
          v-model="queryParams.licensePlateNumber"
          placeholder="请输入车牌号"
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
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['YDOnlineTaxi:DriverAccount:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['YDOnlineTaxi:DriverAccount:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="DriverAccountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="司机姓名" align="center" prop="driverName"/>
      <el-table-column label="身份证号" align="center" prop="idNumber"/>
      <el-table-column label="手机号" align="center" prop="phoneNumber"/>
      <el-table-column label="紧急联系电话" align="center" prop="emergencyContactNumber"/>
      <el-table-column label="地址" align="center" prop="address"/>
      <el-table-column label="车型" align="center" prop="motorcycleType"/>
      <el-table-column label="车牌号" align="center" prop="licensePlateNumber"/>
      <el-table-column label="审核状态" align="center" prop="status"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['YDOnlineTaxi:DriverAccount:edit']"
          >审核
          </el-button>
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
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['YDOnlineTaxi:DriverAccount:remove']"
          >删除
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

    <!-- 添加或修改司机详细信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="司机姓名" prop="driverName">
          <span>{{ form.driverName }}</span>
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <span>{{ form.idNumber }}</span>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNumber">
          <span>{{ form.phoneNumber }}</span>
        </el-form-item>
        <el-form-item label="紧急电话" prop="emergencyContactNumber">
          <span>{{ form.emergencyContactNumber }}</span>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <span>{{ form.address }}</span>
        </el-form-item>
        <el-form-item label="车型" prop="motorcycleType">
          <span>{{ form.motorcycleType }}</span>
        </el-form-item>
        <el-form-item label="车牌号" prop="licensePlateNumber">
          <span>{{ form.licensePlateNumber }}</span>
        </el-form-item>
        <el-form-item label="身份证人像面">
          <imageUpload
            v-model="form.idPhotoFront"
            :limit="1"
          />
        </el-form-item>
        <el-form-item label="身份证国旗面">
          <imageUpload
            v-model="form.idPhotoBack"
            :limit="1"
          />
        </el-form-item>
        <el-form-item label="驾驶证">
          <imageUpload
            v-model="form.vehicleLicensePhoto"
            :limit="1"
          />
        </el-form-item>
        <el-form-item label="行驶证">
          <imageUpload
            v-model="form.driverLicencePhoto"
            :limit="1"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">审核通过</el-button>
        <el-button @click="cancel">拒绝通过</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="refuseTitle" :visible.sync="refuseOpen" width="600px" append-to-body>
      <el-input
        type="textarea"
        :autosize="{ minRows: 4, maxRows: 6}"
        placeholder="请输入内容"
        v-model="refuseDate">
      </el-input>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="reSubmitForm">确 定</el-button>
        <el-button @click="reCancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  delDriverAccount,
  exportDriverAccount,
  getDriverAccount,
  listDriverAccount,
  refuseDriver,
  resetUserPwd,
  updateDriverAccount
} from '@/api/YDOnlineTaxi/DriverAccount'

export default {
  name: 'DriverAccount',
  data() {
    return {
      //socket数据
      webSocket: null,
      socketStatus: 'closed', //记录websocket连接状态
      lockReconnect: false,//重连锁，防止重复连接
      wsCreateHandler: null,//重连时间句柄

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
      // 司机详细信息表格数据
      DriverAccountList: [],
      //是否为修改操作
      update: true,
      // 弹出层标题
      title: '',
      refuseTitle: '拒绝理由',
      // 是否显示弹出层
      open: false,
      refuseOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        driverName: null,
        phoneNumber: null,
        motorcycleType: null,
        licensePlateNumber: null
      },
      // 表单参数
      form: {},
      //拒绝理由
      rePhone: '',
      refuseDate: '',
      // 表单校验
      rules: {
        driverName: [
          {required: true, message: '司机姓名不能为空', trigger: 'blur,change'},
          {pattern: /^[\u4E00-\u9FA5]{2,6}$/, message: "请输入正确的姓名!"}
        ],
        idNumber: [
          {required: true, message: '身份证号不能为空', trigger: 'blur'},
          {min: 18, max: 18, message: "请输入18位身份证号码!", trigger: "blur"},
          {
            pattern: /^(([1-9][0-9]{5}(19|20)[0-9]{2}((0[1-9])|(1[0-2]))([0-2][1-9]|10|20|30|31)[0-9]{3}([0-9]|X|x))|([1-9][0-9]{5}[0-9]{2}((0[1-9])|(1[0-2]))([0-2][1-9]|10|20|30|31)[0-9]{3}))$/,
            message: "请输入正确的身份证号码"
          }
        ],
        phoneNumber: [
          {required: true, message: '手机号不能为空', trigger: 'blur'},
          {min: 11, max: 11, message: "请输入11位电话号码", trigger: "blur"},
          {pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: "请输入正确的电话号码",}
        ],
        emergencyContactNumber: [
          {required: false, message: '选填', trigger: 'blur'},
          {min: 11, max: 11, message: "请输入11位电话号码", trigger: "blur"},
          {pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: "请输入正确的电话号码",}
        ],
        address: [
          {required: true, message: '地址不能为空', trigger: 'blur'}
        ],
        motorcycleType: [
          {required: true, message: '车型不能为空', trigger: 'blur'}
        ],
        licensePlateNumber: [
          {required: true, message: '车牌号不能为空', trigger: 'blur'},
          {
            pattern: /^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[a-zA-Z](([DF]((?![IO])[a-zA-Z0-9](?![IO]))[0-9]{4})|([0-9]{5}[DF]))|[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1})$/,
            message: "请输入正确的车牌号!"
          }
        ],
        idPhotoFront: [
          {required: true, message: '身份证照片人像面不能为空', trigger: 'blur'}
        ],
        idPhotoBack: [
          {required: true, message: '身份证照片国旗面不能为空', trigger: 'blur'}
        ],
        vehicleLicensePhoto: [
          {required: true, message: '驾驶证照片不能为空', trigger: 'blur'}
        ],
        driverLicencePhoto: [
          {required: true, message: '行驶证照片不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '司机审核状态不能为空', trigger: 'blur'}
        ],
        driverPassword: [
          {
            required: true,
            message: '司机账户密码不能为空,长度必须介于 8 和 20 之间,且必须包含一个大写字母,一个小写字母,一个数字和一个特殊字符',
            pattern: /(?=^.{8,20}$)(?=(?:.*?\d){1})(?=.*[a-z])(?=(?:.*?[A-Z]){1})(?=(?:.*?[`·~!@#$%^&*()_+}{|:;'",<.>/?\=\[\]\-\\]){1})(?!.*\s)[0-9a-zA-Z`·~!@#$%^&*()_+}{|:;'",<.>/?\=\[\]\-\\]*$/,
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created() {
    this.getList()

  },
  methods: {
    /** 查询司机详细信息列表 */
    getList() {
      this.loading = true
      listDriverAccount(this.queryParams).then(response => {
        this.DriverAccountList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.refuseOpen = true
      this.reset()
    },
    // 取消拒绝
    reCancel() {
      this.refuseOpen = false
    },
    // 表单重置
    reset() {
      this.form = {
        driverName: null,
        idNumber: null,
        phoneNumber: null,
        emergencyContactNumber: null,
        address: null,
        motorcycleType: null,
        licensePlateNumber: null,
        idPhotoFront: null,
        idPhotoBack: null,
        vehicleLicensePhoto: null,
        driverLicencePhoto: null,
        status: '0',
        driverPassword: null,
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.idNumber)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加司机详细信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const idNumber = row.idNumber || this.ids
      getDriverAccount(idNumber).then(response => {
        this.form = response.data
        this.update = false
        this.open = true
        this.rules.driverPassword[0].required = false
        this.title = '修改司机详细信息'
        this.rePhone = this.form.phoneNumber;
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.form.status = "审核通过"
      this.$refs['form'].validate(valid => {
        updateDriverAccount(this.form).then(response => {
          this.msgSuccess('审核成功')
          this.form.driverPassword = null
          this.open = false
          this.getList()
        })
      })
    },
    /** 提交按钮 */
    reSubmitForm() {
      const reData = {
        'phoneNumber': this.rePhone,
        'refuseReason': this.refuseDate
      };
      console.log(reData)
      refuseDriver(reData).then(response => {
        this.open = false;
        this.refuseOpen = false;
        this.refuseDate = '';
        this.getList();
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const idNumbers = row.idNumber || this.ids
      this.$confirm('是否确认删除司机详细信息编号为"' + idNumbers + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delDriverAccount(idNumbers)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有司机详细信息数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.exportLoading = true
        return exportDriverAccount(queryParams)
      }).then(response => {
        this.download(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
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
  }
}
</script>
