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
      <el-form-item label="科目ID" prop="subjectId">
        <el-input
          v-model="queryParams.subjectId"
          placeholder="请输入科目ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已完成课时" prop="completedLessons">
        <el-input
          v-model="queryParams.completedLessons"
          placeholder="请输入已完成课时"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进度百分比" prop="progressPercent">
        <el-input
          v-model="queryParams.progressPercent"
          placeholder="请输入进度百分比"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="${comment}" prop="createdAt">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.createdAt"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="请选择${comment}">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="${comment}" prop="updatedAt">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.updatedAt"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="请选择${comment}">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
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
          v-hasPermi="['jiayun:learning_progressadmin:add']"
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
          v-hasPermi="['jiayun:learning_progressadmin:edit']"
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
          v-hasPermi="['jiayun:learning_progressadmin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jiayun:learning_progressadmin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="learning_progressadminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="进度ID" align="center" prop="id" />
      <el-table-column label="学员ID" align="center" prop="studentId" />
      <el-table-column label="科目ID" align="center" prop="subjectId" />
      <el-table-column label="已完成课时" align="center" prop="completedLessons" />
      <el-table-column label="进度百分比" align="center" prop="progressPercent" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === 'not_started' ? 'info'
            : scope.row.status === 'in_progress' ? 'warning'
            : 'success'">
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
            v-hasPermi="['jiayun:learning_progressadmin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jiayun:learning_progressadmin:remove']"
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

    <!-- 添加或修改学习进度对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学员ID" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学员ID" />
        </el-form-item>
        <el-form-item label="科目ID" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入科目ID" />
        </el-form-item>
        <el-form-item label="已完成课时" prop="completedLessons">
          <el-input v-model="form.completedLessons" placeholder="请输入已完成课时" />
        </el-form-item>
        <el-form-item label="进度百分比" prop="progressPercent">
          <el-input v-model="form.progressPercent" placeholder="请输入进度百分比" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="未开始" value="not_started" />
            <el-option label="进行中" value="in_progress" />
            <el-option label="已完成" value="completed" />
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
import { listLearning_progressadmin, getLearning_progressadmin, delLearning_progressadmin, addLearning_progressadmin, updateLearning_progressadmin } from "@/api/jiayun/learning_progressadmin"

export default {
  name: "Learning_progressadmin",
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
      // 学习进度表格数据
      learning_progressadminList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: null,
        subjectId: null,
        completedLessons: null,
        progressPercent: null,
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
        subjectId: [
          { required: true, message: "科目ID不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询学习进度列表 */
    getList() {
      this.loading = true
      listLearning_progressadmin(this.queryParams).then(response => {
        this.learning_progressadminList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    statusText(value) {
      const map = {
        not_started: '未开始',
        in_progress: '进行中',
        completed: '已完成'
      }
      return map[value] || value
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
        subjectId: null,
        completedLessons: null,
        progressPercent: null,
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
      this.title = "添加学习进度"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getLearning_progressadmin(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改学习进度"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLearning_progressadmin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addLearning_progressadmin(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除学习进度编号为"' + ids + '"的数据项？').then(function() {
        return delLearning_progressadmin(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('jiayun/learning_progressadmin/export', {
        ...this.queryParams
      }, `learning_progressadmin_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
