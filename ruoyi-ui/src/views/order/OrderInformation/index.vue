<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="预约号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入预约号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户属性" prop="passengerProperty">
        <el-input
          v-model="queryParams.passengerProperty"
          placeholder="请输入客户属性"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="passenger">
        <el-input
          v-model="queryParams.passenger"
          placeholder="请输入客户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="passengerSex">
        <el-select v-model="form.passengerSex" placeholder="请选择性别">
          <el-option label="女" value="女" />
          <el-option label="男" value="男" />
        </el-select>
      </el-form-item>
      <el-form-item label="联系方式" prop="passengerPhone">
        <el-input
          v-model="queryParams.passengerPhone"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="航班号" prop="flightNumber">
        <el-input
          v-model="queryParams.flightNumber"
          placeholder="请输入航班号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="日期" prop="creationDate">
        <el-date-picker clearable
                        v-model="queryParams.creationDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="出发时间" prop="transportTime">
        <el-date-picker clearable
                        v-model="queryParams.transportTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择出发时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上车地点" prop="departure">
        <el-input
          v-model="queryParams.departure"
          placeholder="请输入上车地点"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中途停靠" prop="intermediatePort">
        <el-input
          v-model="queryParams.intermediatePort"
          placeholder="请输入中途停靠"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下车地点" prop="destination">
        <el-input
          v-model="queryParams.destination"
          placeholder="请输入下车地点"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车型" prop="carType">
        <el-select v-model="form.carType" placeholder="请选择用车类型" clearable >
          <el-option label="舒适型" value="舒适型" />
          <el-option label="豪华型" value="豪华型" />
          <el-option label="商务型" value="商务型" />
        </el-select>
      </el-form-item>
      <el-form-item label="司机信息" prop="driverInformation">
        <el-input
          v-model="queryParams.driverInformation"
          placeholder="请输入司机信息"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-select v-model="form.requirementTypes" placeholder="请选择需求类型">
          <el-option label="半包" value="半包" />
          <el-option label="全包" value="全包" />
          <el-option label="接站" value="接站" />
          <el-option label="送站" value="送站" />
          <el-option label="接机" value="接机" />
          <el-option label="送机" value="送机" />
          <el-option label="市内单程" value="市内单程" />
          <el-option label="市内往返" value="市内往返" />
          <el-option label="外地单程" value="外地单程" />
          <el-option label="外地往返" value="外地往返" />
        </el-select>
      </el-form-item>
      <el-form-item label="司机积分" prop="driverBase">
        <el-input-number
          v-model="queryParams.driverBase"
          oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
          :precision="0"
          :min="0"
          :step="1"

          clearable

          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户积分" prop="passengerPrice">
        <el-input-number
          v-model="queryParams.passengerPrice"
          oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
          :precision="0"
          :step="1"
          :min="0"

          clearable

          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="停车积分" prop="parkingFees">
        <el-input-number
          v-model="queryParams.parkingFees"
          oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
          :precision="0"
          :step="1"
          :min="0"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="高速积分" prop="tollFees">
        <el-input-number
          v-model="queryParams.tollFees"
          oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
          :precision="0"
          :step="1"
          :min="0"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="积分已入" prop="points">
        <el-input-number
          v-model="queryParams.points"
          oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
          :precision="0"
          :step="1"
          :min="0"
          clearable
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
          v-hasPermi="['YDOnlineTaxi:OrderInformation:add']"
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
          v-hasPermi="['YDOnlineTaxi:OrderInformation:edit']"
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
          v-hasPermi="['YDOnlineTaxi:OrderInformation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['YDOnlineTaxi:OrderInformation:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['YDOnlineTaxi:OrderInformation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的订单数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-table v-loading="loading" :data="OrderInformationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预约号" align="center" prop="orderId" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" />
      <el-table-column label="客户属性" align="center" prop="passengerProperty" />
      <el-table-column label="客户姓名" align="center" prop="passenger" />
      <el-table-column label="性别" align="center" prop="passengerSex" />
      <el-table-column label="联系方式" align="center" prop="passengerPhone" />
      <el-table-column label="航班号" align="center" prop="flightNumber" />
      <el-table-column label="日期" align="center" prop="creationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.creationDate, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出发时间" align="center" prop="transportTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.transportTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上车地点" align="center" prop="departure" />
      <el-table-column label="中途停靠" align="center" prop="intermediatePort" />
      <el-table-column label="下车地点" align="center" prop="destination" />
      <el-table-column label="车型" align="center" prop="carType" />
      <el-table-column label="司机信息" align="center" prop="driverInformation" />
      <el-table-column label="司机积分" align="center" prop="driverBase" />
      <el-table-column label="客户积分" align="center" prop="passengerPrice" />
      <el-table-column label="停车积分" align="center" prop="parkingFees" />
      <el-table-column label="高速积分" align="center" prop="tollFees" />
      <el-table-column label="积分已入" align="center" prop="points" />
      <el-table-column label="备注" align="center" prop="note" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="orderDriver(scope.row)"
            v-hasPermi="['YDOnlineTaxi:OrderInformation:edit']"
            v-if="scope.row.orderStatus === '已派单' || scope.row.orderStatus === '未出发' || scope.row.orderStatus === '待派单' || scope.row.orderStatus === '已超时'"
          >指定司机</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:information:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:information:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="出发地" prop="departure">
          <el-input v-model="form.departure" placeholder="请输入出发地" />
        </el-form-item>
        <el-form-item label="到达地" prop="destination">
          <el-input v-model="form.destination" placeholder="请输入到达地" />
        </el-form-item>
        <el-form-item label="用车时间" prop="transportTime">
          <el-date-picker clearable
                          v-model="form.transportTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择用车时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="需求类型" prop="requirementTypes">
          <el-select v-model="form.requirementTypes" placeholder="请选择需求类型" clearable >
            <el-option label="半包" value="半包" />
            <el-option label="全包" value="全包" />
            <el-option label="接站" value="接站" />
            <el-option label="送站" value="送站" />
            <el-option label="接机" value="接机" />
            <el-option label="送机" value="送机" />
            <el-option label="市内单程" value="市内单程" />
            <el-option label="市内往返" value="市内往返" />
            <el-option label="外地单程" value="外地单程" />
            <el-option label="外地往返" value="外地往返" />
          </el-select>
        </el-form-item>
        <el-form-item label="用车类型" prop="carType">
          <span>{{form.carType}}</span>
        </el-form-item>
        <el-form-item label="乘客称呼" prop="passenger">
          <span>{{form.passenger}}</span>
        </el-form-item>
        <el-form-item label="乘客手机" prop="passengerPhone">
          <span>{{form.passengerPhone}}</span>
        </el-form-item>
        <el-form-item label="积分" prop="points">
          <el-input v-model="form.points" placeholder="请输入积分" />
        </el-form-item>
        <el-form-item label="订单备注" prop="note">
          <el-input v-model="form.note" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="预约号" prop="orderId" :disabled = "modify">
          <el-input v-model="form.orderId" placeholder="请输入预约号" />
        </el-form-item>
        <el-form-item label="客户属性" prop="passengerProperty">
          <el-input v-model="form.passengerProperty" placeholder="请输入客户属性" />
        </el-form-item>
        <el-form-item label="客户姓名" prop="passenger">
          <el-input v-model="form.passenger" placeholder="请输入客户姓名" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.passengerSex" placeholder="请选择性别">
            <el-option label="女" value="女" />
            <el-option label="男" value="男" />
          </el-select>
        </el-form-item>
          <el-form-item label="联系方式" prop="passengerPhone">
            <el-input v-model="form.passengerPhone" placeholder="请输入联系方式" />
          </el-form-item>
          <el-form-item label="航班号" prop="flightNumber">
            <el-input v-model="form.flightNumber" placeholder="请输入航班号" />
          </el-form-item>
          <el-form-item label="出发时间" prop="transportTime">
            <el-date-picker clearable
                            v-model="form.transportTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择出发时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="上车地点" prop="departure">
            <el-input v-model="form.departure" placeholder="请输入上车地点" />
          </el-form-item>
          <el-form-item label="中途停靠" prop="intermediatePort">
            <el-input v-model="form.intermediatePort" placeholder="请输入中途停靠" />
          </el-form-item>
          <el-form-item label="下车地点" prop="destination">
            <el-input v-model="form.destination" placeholder="请输入下车地点" />
          </el-form-item>
          <el-form-item label="车型" prop="carType">
            <el-select v-model="form.carType" placeholder="请选择用车类型">
              <el-option label="舒适型" value="舒适型" />
              <el-option label="豪华型" value="豪华型" />
              <el-option label="商务型" value="商务型" />
            </el-select>
          </el-form-item>
          <el-form-item label="司机信息" prop="driverInformation">
            <el-input v-model="form.driverInformation" placeholder="请输入司机信息" />
          </el-form-item>
          <el-form-item label="司机积分" prop="driverBase">
            <el-input v-model="form.driverBase" placeholder="请输入司机积分" />
          </el-form-item>
          <el-form-item label="客户积分" prop="passengerPrice">
            <el-input v-model="form.passengerPrice" placeholder="请输入客户积分" />
          </el-form-item>
          <el-form-item label="停车积分" prop="parkingFees">
            <el-input v-model="form.parkingFees" placeholder="请输入停车积分" />
          </el-form-item>
          <el-form-item label="高速积分" prop="tollFees">
            <el-input v-model="form.tollFees" placeholder="请输入高速积分" />
          </el-form-item>
          <el-form-item label="积分已入" prop="points">
            <el-input v-model="form.points" placeholder="请输入积分已入" />
          </el-form-item>
          <el-form-item label="备注" prop="note">
            <el-select v-model="form.requirementTypes" placeholder="请选择需求类型">
              <el-option label="半包" value="半包" />
              <el-option label="全包" value="全包" />
              <el-option label="接站" value="接站" />
              <el-option label="送站" value="送站" />
              <el-option label="接机" value="接机" />
              <el-option label="送机" value="送机" />
              <el-option label="市内单程" value="市内单程" />
              <el-option label="市内往返" value="市内往返" />
              <el-option label="外地单程" value="外地单程" />
              <el-option label="外地往返" value="外地往返" />
            </el-select>
          </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="order_open" width="500px" append-to-body>
      <span>手 机 号 ：&ensp; </span>
      <el-select id="phoneId" v-model="phoneNumber_temp"
                 filterable
                 placeholder="请选择"
                 @change = "phoneGetName()"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.value"
          :value="item.value" >
        </el-option>
      </el-select>
      <div style="margin-top: 20px">
        <span>{{"司机姓名："}}</span>
        <el-select v-model="driverName_temp"
                   filterable
                   placeholder="请选择"
                   @change = "nameGetPhone()"
        >
          <el-option
            v-for="item in options"
            :key="item.label"
            :label="item.label"
            :value="item.label">
          </el-option>
        </el-select>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="orderDriverSubmit">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listOrderInformation,
  getOrderInformation,
  delOrderInformation,
  addOrderInformation,
  updateOrderInformation,
  exportOrderInformation,
  importTemplate,
  singleStatusList, listDriverInformation, orderDriver
} from "@/api/YDOnlineTaxi/OrderInformation";
import { getToken } from "@/utils/auth";

export default {
  name: "OrderInformation",
  data() {
    return {
      //指定司机
      orderId_temp: null,
      driverName_temp: null,
      phoneNumber_temp: null,
      order_open: false,
      options: [],
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
      modify : false,
      // 总条数
      total: 0,
      // 订单信息表格数据
      OrderInformationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        passengerProperty: null,
        passenger: null,
        passengerSex: null,
        passengerPhone: null,
        flightNumber: null,
        creationDate: null,
        transportTime: null,
        departure: null,
        intermediatePort: null,
        destination: null,
        carType: null,
        driverInformation: null,
        driverBase: undefined,
        passengerPrice: undefined,
        parkingFees: undefined,
        tollFees: undefined,
        points: undefined,
        note: null,
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/YDOnlineTaxi/OrderInformation/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "预约号不能为空", trigger: "blur" }
        ],
        passengerProperty: [
          { required: true, message: "客户属性不能为空", trigger: "blur" }
        ],
        passenger: [
          { required: true, message: "客户姓名不能为空", trigger: "blur" }
        ],
        passengerSex: [
          { required: true, message: "性别不能为空", trigger: "blur" }
        ],
        passengerPhone: [
          { required: true, message: "联系方式不能为空", trigger: "blur" }
        ],
        transportTime: [
          { required: true, message: "出发时间不能为空", trigger: "blur" }
        ],
        departure: [
          { required: true, message: "上车地点不能为空", trigger: "blur" }
        ],
        destination: [
          { required: true, message: "下车地点不能为空", trigger: "blur" }
        ],
        carType: [
          { required: true, message: "车型不能为空", trigger: "change" }
        ],
        driverBase: [
          { required: true, message: "司机积分不能为空", trigger: "blur" }
        ],
        passengerPrice: [
          { required: true, message: "客户积分不能为空", trigger: "blur" }
        ],
        orderStatus: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.orderStatus = "待派单"
      listOrderInformation(this.queryParams).then(response => {
        this.OrderInformationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.order_open = false;
      this.reset();
    },
    /** 手机号动态锁定姓名 */
    phoneGetName(){
      for(let i=0;i<this.options.length;i++){
        if(this.phoneNumber_temp === this.options[i].value){
          this.driverName_temp = this.options[i].label;
        }
      }
    },
    /** 姓名动态锁定手机号 */
    nameGetPhone(){
      for(let i=0;i<this.options.length;i++){
        if(this.driverName_temp === this.options[i].label){
          this.phoneNumber_temp = this.options[i].value;
        }
      }
    },
    /** 指定司机操作 */
    orderDriver(row) {
      this.reset();
      this.orderId_temp = row.orderId || this.ids
      listDriverInformation().then(response => {
        this.options = [];
        for(let i=0; i<response.length;i++){
          let data = {
            value: response[i].driverPhoneNumber,
            label: response[i].driverName
          }
          this.options.push(data);
        }
        this.order_open = true;
        this.title = "指定司机";
      });
    },
    /** 提交按钮 */
    orderDriverSubmit() {
      let data = {
        orderId: this.orderId_temp,
        phoneNumber: this.phoneNumber_temp
      }
      orderDriver(data).then(response => {
        this.msgSuccess("修改成功");
        this.order_open = false;
        this.getList();
      });
      this.driverName_temp = null;
      this.phoneNumber_temp = null;
      this.orderId_temp = null;
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        departure: null,
        destination: null,
        transportTime: null,
        requirementTypes: null,
        carType: null,
        passenger: null,
        passengerPhone: null,
        points: null,
        note: null,
        orderStatus: null,
        refuseReason: null
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
      this.title = "添加订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.modify = true;
      this.reset();
      const orderId = row.orderId || this.ids
      getOrderInformation(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrderInformation(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrderInformation(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$confirm('是否确认删除订单信息编号为"' + orderIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOrderInformation(orderIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportOrderInformation(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "订单导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
    },
  }
};
</script>
