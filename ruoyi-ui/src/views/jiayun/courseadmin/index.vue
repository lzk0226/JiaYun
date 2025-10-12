<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主要科目ID" prop="subjectId">
        <el-input
          v-model="queryParams.subjectId"
          placeholder="请输入主要科目ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="副标题" prop="subtitle">
        <el-input
          v-model="queryParams.subtitle"
          placeholder="请输入副标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标签" prop="badge">
        <el-input
          v-model="queryParams.badge"
          placeholder="请输入标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课时数" prop="duration">
        <el-input
          v-model="queryParams.duration"
          placeholder="请输入课时数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成周期" prop="period">
        <el-input
          v-model="queryParams.period"
          placeholder="请输入完成周期"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="热度值" prop="popular">
        <el-input
          v-model="queryParams.popular"
          placeholder="请输入热度值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报名人数" prop="studentsCount">
        <el-input
          v-model="queryParams.studentsCount"
          placeholder="请输入报名人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否联报课程：1-是，0-否" prop="isCombined">
        <el-input
          v-model="queryParams.isCombined"
          placeholder="请输入是否联报课程：1-是，0-否"
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
          v-hasPermi="['jiayun:courseadmin:add']"
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
          v-hasPermi="['jiayun:courseadmin:edit']"
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
          v-hasPermi="['jiayun:courseadmin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jiayun:courseadmin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseadminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="课程ID" align="center" prop="id" />
      <el-table-column label="课程名称" align="center" prop="name" />
      <el-table-column label="主要科目ID" align="center" prop="subjectId" />
      <el-table-column label="课程类型" align="center" prop="type" />
      <el-table-column label="副标题" align="center" prop="subtitle" />
      <el-table-column label="标签" align="center" prop="badge" />
      <el-table-column label="课时数" align="center" prop="duration" />
      <el-table-column label="完成周期" align="center" prop="period" />
      <el-table-column label="课程特点" align="center" prop="features" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="热度值" align="center" prop="popular" />
      <el-table-column label="报名人数" align="center" prop="studentsCount" />
      <el-table-column label="是否联报课程：1-是，0-否" align="center" prop="isCombined" />
      <el-table-column label="状态：1-启用，0-停用" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jiayun:courseadmin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jiayun:courseadmin:remove']"
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

    <!-- 添加或修改课程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="主要科目ID" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入主要科目ID" />
        </el-form-item>
        <el-form-item label="副标题" prop="subtitle">
          <el-input v-model="form.subtitle" placeholder="请输入副标题" />
        </el-form-item>
        <el-form-item label="标签" prop="badge">
          <el-input v-model="form.badge" placeholder="请输入标签" />
        </el-form-item>
        <el-form-item label="课时数" prop="duration">
          <el-input v-model="form.duration" placeholder="请输入课时数" />
        </el-form-item>
        <el-form-item label="完成周期" prop="period">
          <el-input v-model="form.period" placeholder="请输入完成周期" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="热度值" prop="popular">
          <el-input v-model="form.popular" placeholder="请输入热度值" />
        </el-form-item>
        <el-form-item label="报名人数" prop="studentsCount">
          <el-input v-model="form.studentsCount" placeholder="请输入报名人数" />
        </el-form-item>
        <el-form-item label="是否联报课程：1-是，0-否" prop="isCombined">
          <el-input v-model="form.isCombined" placeholder="请输入是否联报课程：1-是，0-否" />
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
import { listCourseadmin, getCourseadmin, delCourseadmin, addCourseadmin, updateCourseadmin } from "@/api/jiayun/courseadmin"

export default {
  name: "Courseadmin",
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
      // 课程表格数据
      courseadminList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        subjectId: null,
        type: null,
        subtitle: null,
        badge: null,
        duration: null,
        period: null,
        features: null,
        price: null,
        popular: null,
        studentsCount: null,
        isCombined: null,
        status: null,
        createdAt: null,
        updatedAt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "课程名称不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "课程类型不能为空", trigger: "change" }
        ],
        duration: [
          { required: true, message: "课时数不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "价格不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询课程列表 */
    getList() {
      this.loading = true
      listCourseadmin(this.queryParams).then(response => {
        this.courseadminList = response.rows
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
        name: null,
        subjectId: null,
        type: null,
        subtitle: null,
        badge: null,
        duration: null,
        period: null,
        features: null,
        price: null,
        popular: null,
        studentsCount: null,
        isCombined: null,
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
      this.title = "添加课程"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCourseadmin(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改课程"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCourseadmin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCourseadmin(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除课程编号为"' + ids + '"的数据项？').then(function() {
        return delCourseadmin(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('jiayun/courseadmin/export', {
        ...this.queryParams
      }, `courseadmin_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
