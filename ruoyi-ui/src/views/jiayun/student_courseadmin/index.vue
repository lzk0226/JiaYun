<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报名ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入报名ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学员ID" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学员ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程ID" prop="courseId">
        <el-input
          v-model="queryParams.courseId"
          placeholder="请输入课程ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指定教练ID" prop="coachId">
        <el-input
          v-model="queryParams.coachId"
          placeholder="请输入指定教练ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报名日期" prop="enrollDate">
        <el-date-picker clearable
          v-model="queryParams.enrollDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择报名日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="开课日期" prop="startDate">
        <el-date-picker clearable
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开课日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="已完成课时" prop="completedLessons">
        <el-input
          v-model="queryParams.completedLessons"
          placeholder="请输入已完成课时"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总课时" prop="totalLessons">
        <el-input
          v-model="queryParams.totalLessons"
          placeholder="请输入总课时"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进度百分比" prop="progress">
        <el-input
          v-model="queryParams.progress"
          placeholder="请输入进度百分比"
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
          v-hasPermi="['jiayun:student_courseadmin:add']"
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
          v-hasPermi="['jiayun:student_courseadmin:edit']"
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
          v-hasPermi="['jiayun:student_courseadmin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jiayun:student_courseadmin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="student_courseadminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报名ID" align="center" prop="id" />
      <el-table-column label="学员ID" align="center" prop="studentId" />
      <el-table-column label="课程ID" align="center" prop="courseId" />
      <el-table-column label="指定教练ID" align="center" prop="coachId" />
      <el-table-column label="报名日期" align="center" prop="enrollDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.enrollDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开课日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="已完成课时" align="center" prop="completedLessons" />
      <el-table-column label="总课时" align="center" prop="totalLessons" />
      <el-table-column label="进度百分比" align="center" prop="progress" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jiayun:student_courseadmin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jiayun:student_courseadmin:remove']"
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

    <!-- 添加或修改学员课程关联对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学员ID" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学员ID" />
        </el-form-item>
        <el-form-item label="课程ID" prop="courseId">
          <el-input v-model="form.courseId" placeholder="请输入课程ID" />
        </el-form-item>
        <el-form-item label="指定教练ID" prop="coachId">
          <el-input v-model="form.coachId" placeholder="请输入指定教练ID" />
        </el-form-item>
        <el-form-item label="报名日期" prop="enrollDate">
          <el-date-picker clearable
            v-model="form.enrollDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择报名日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开课日期" prop="startDate">
          <el-date-picker clearable
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开课日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="已完成课时" prop="completedLessons">
          <el-input v-model="form.completedLessons" placeholder="请输入已完成课时" />
        </el-form-item>
        <el-form-item label="总课时" prop="totalLessons">
          <el-input v-model="form.totalLessons" placeholder="请输入总课时" />
        </el-form-item>
        <el-form-item label="进度百分比" prop="progress">
          <el-input v-model="form.progress" placeholder="请输入进度百分比" />
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
import { listStudent_courseadmin, getStudent_courseadmin, delStudent_courseadmin, addStudent_courseadmin, updateStudent_courseadmin } from "@/api/jiayun/student_courseadmin"

export default {
  name: "Student_courseadmin",
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
      // 学员课程关联表格数据
      student_courseadminList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        studentId: null,
        courseId: null,
        coachId: null,
        enrollDate: null,
        startDate: null,
        completedLessons: null,
        totalLessons: null,
        progress: null,
        status: null,
        createdAt: null,
        updatedAt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentId: [
          { required: true, message: "学员ID不能为空", trigger: "blur" }
        ],
        courseId: [
          { required: true, message: "课程ID不能为空", trigger: "blur" }
        ],
        enrollDate: [
          { required: true, message: "报名日期不能为空", trigger: "blur" }
        ],
        totalLessons: [
          { required: true, message: "总课时不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询学员课程关联列表 */
    getList() {
      this.loading = true
      listStudent_courseadmin(this.queryParams).then(response => {
        this.student_courseadminList = response.rows
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
        courseId: null,
        coachId: null,
        enrollDate: null,
        startDate: null,
        completedLessons: null,
        totalLessons: null,
        progress: null,
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
      this.title = "添加学员课程关联"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStudent_courseadmin(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改学员课程关联"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStudent_courseadmin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStudent_courseadmin(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除学员课程关联编号为"' + ids + '"的数据项？').then(function() {
        return delStudent_courseadmin(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('jiayun/student_courseadmin/export', {
        ...this.queryParams
      }, `student_courseadmin_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
