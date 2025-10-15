<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="车牌号" prop="plateNumber">
        <el-input
          v-model="queryParams.plateNumber"
          placeholder="请输入车牌号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车辆型号" prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入车辆型号"
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
          v-hasPermi="['jiayun:vehicleadmin:add']"
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
          v-hasPermi="['jiayun:vehicleadmin:edit']"
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
          v-hasPermi="['jiayun:vehicleadmin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jiayun:vehicleadmin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="vehicleadminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车辆ID" align="center" prop="id" />
      <el-table-column label="车牌号" align="center" prop="plateNumber" />
      <el-table-column label="车辆型号" align="center" prop="model" />
      <el-table-column label="车辆图片base64编码" align="center" prop="vehicleImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.vehicleImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="车辆状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === 'available' ? 'success'
            : scope.row.status === 'maintenance' ? 'warning'
            : 'info'">
            {{ statusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jiayun:vehicleadmin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jiayun:vehicleadmin:remove']"
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

    <!-- 添加或修改车辆对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车牌号" prop="plateNumber">
          <el-input v-model="form.plateNumber" placeholder="请输入车牌号" />
        </el-form-item>
        <el-form-item label="车辆型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入车辆型号" />
        </el-form-item>
        <el-form-item label="车辆图片base64编码" prop="vehicleImage">
          <image-upload v-model="form.vehicleImage"/>
        </el-form-item>
        <el-form-item label="车辆状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择车辆状态">
            <el-option label="可用" value="available" />
            <el-option label="维护中" value="maintenance" />
            <el-option label="已退役" value="retired" />
          </el-select>
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
import { listVehicleadmin, getVehicleadmin, delVehicleadmin, addVehicleadmin, updateVehicleadmin } from "@/api/jiayun/vehicleadmin"

export default {
  name: "Vehicleadmin",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 车辆表格数据
      vehicleadminList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        plateNumber: null,
        model: null,
        vehicleImage: null,
        status: null,
        createdAt: null,
        updatedAt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        plateNumber: [
          { required: true, message: "车牌号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询车辆列表 */
    getList() {
      this.loading = true
      listVehicleadmin(this.queryParams).then(response => {
        this.vehicleadminList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        plateNumber: null,
        model: null,
        vehicleImage: null,
        status: null,
        createdAt: null,
        updatedAt: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    statusText(value) {
      const map = {
        available: '可用',
        maintenance: '维护中',
        retired: '已退役'
      }
      return map[value] || value
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加车辆"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getVehicleadmin(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改车辆"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateVehicleadmin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addVehicleadmin(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除车辆编号为"' + ids + '"的数据项？').then(function() {
        return delVehicleadmin(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('jiayun/vehicleadmin/export', {
        ...this.queryParams
      }, `vehicleadmin_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
