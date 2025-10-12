<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学员ID" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学员ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教练ID" prop="coachId">
        <el-input
          v-model="queryParams.coachId"
          placeholder="请输入教练ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科目ID" prop="subjectId">
        <el-input
          v-model="queryParams.subjectId"
          placeholder="请输入科目ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约日期" prop="reservationDate">
        <el-date-picker clearable
                        v-model="queryParams.reservationDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择预约日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="待进行" value="upcoming" />
          <el-option label="已完成" value="completed" />
          <el-option label="已取消" value="cancelled" />
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jiayun:reservationadmin:add']"
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
          v-hasPermi="['jiayun:reservationadmin:edit']"
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
          v-hasPermi="['jiayun:reservationadmin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jiayun:reservationadmin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reservationadminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预约ID" align="center" prop="id" />
      <el-table-column label="学员ID" align="center" prop="studentId" />
      <el-table-column label="教练ID" align="center" prop="coachId" />
      <el-table-column label="科目ID" align="center" prop="subjectId" />
      <el-table-column label="车辆ID" align="center" prop="vehicleId" />
      <el-table-column label="预约日期" align="center" prop="reservationDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reservationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时间段" align="center" prop="timeSlot" />
      <el-table-column label="备注" align="center" prop="remarks" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 'upcoming'" type="warning">待进行</el-tag>
          <el-tag v-else-if="scope.row.status === 'completed'" type="success">已完成</el-tag>
          <el-tag v-else-if="scope.row.status === 'cancelled'" type="info">已取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jiayun:reservationadmin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleStatusChange(scope.row, 'completed')"
            v-if="scope.row.status === 'upcoming'"
            v-hasPermi="['jiayun:reservationadmin:edit']"
          >完成</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleStatusChange(scope.row, 'cancelled')"
            v-if="scope.row.status === 'upcoming'"
            v-hasPermi="['jiayun:reservationadmin:edit']"
          >取消</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jiayun:reservationadmin:remove']"
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

    <!-- 添加或修改预约对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学员ID" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学员ID" />
        </el-form-item>
        <el-form-item label="教练ID" prop="coachId">
          <el-input v-model="form.coachId" placeholder="请输入教练ID" />
        </el-form-item>
        <el-form-item label="科目ID" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入科目ID" />
        </el-form-item>
        <el-form-item label="车辆ID" prop="vehicleId">
          <el-input v-model="form.vehicleId" placeholder="请输入车辆ID" />
        </el-form-item>
        <el-form-item label="预约日期" prop="reservationDate">
          <el-date-picker clearable
                          v-model="form.reservationDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择预约日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="时间段" prop="timeSlot">
          <el-select v-model="form.timeSlot" placeholder="请选择时间段" style="width: 100%">
            <el-option label="08:00-10:00" value="08:00-10:00" />
            <el-option label="10:00-12:00" value="10:00-12:00" />
            <el-option label="14:00-16:00" value="14:00-16:00" />
            <el-option label="16:00-18:00" value="16:00-18:00" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
            <el-option label="待进行" value="upcoming" />
            <el-option label="已完成" value="completed" />
            <el-option label="已取消" value="cancelled" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注信息" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入备注信息" />
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
import { listReservationadmin, getReservationadmin, delReservationadmin, addReservationadmin, updateReservationadmin } from "@/api/jiayun/reservationadmin"

export default {
  name: "Reservationadmin",
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
      // 预约记录表格数据
      reservationadminList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: null,
        coachId: null,
        subjectId: null,
        vehicleId: null,
        reservationDate: null,
        timeSlot: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentId: [
          { required: true, message: "学员ID不能为空", trigger: "blur" }
        ],
        coachId: [
          { required: true, message: "教练ID不能为空", trigger: "blur" }
        ],
        subjectId: [
          { required: true, message: "科目ID不能为空", trigger: "blur" }
        ],
        reservationDate: [
          { required: true, message: "预约日期不能为空", trigger: "blur" }
        ],
        timeSlot: [
          { required: true, message: "时间段不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询预约记录列表 */
    getList() {
      this.loading = true
      listReservationadmin(this.queryParams).then(response => {
        this.reservationadminList = response.rows
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
        studentId: null,
        coachId: null,
        subjectId: null,
        vehicleId: null,
        reservationDate: null,
        timeSlot: null,
        remarks: null,
        status: 'upcoming',
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
      this.title = "添加预约记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getReservationadmin(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改预约记录"
      })
    },
    /** 快速修改状态 */
    handleStatusChange(row, status) {
      const statusText = status === 'completed' ? '完成' : '取消'
      this.$modal.confirm('是否确认将该预约标记为"' + statusText + '"状态？').then(() => {
        const data = {
          id: row.id,
          studentId: row.studentId,
          coachId: row.coachId,
          subjectId: row.subjectId,
          vehicleId: row.vehicleId,
          reservationDate: row.reservationDate,
          timeSlot: row.timeSlot,
          remarks: row.remarks,
          status: status
        }
        return updateReservationadmin(data)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("状态修改成功")
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReservationadmin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addReservationadmin(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除预约记录编号为"' + ids + '"的数据项？').then(function() {
        return delReservationadmin(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('jiayun/reservationadmin/export', {
        ...this.queryParams
      }, `reservationadmin_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
