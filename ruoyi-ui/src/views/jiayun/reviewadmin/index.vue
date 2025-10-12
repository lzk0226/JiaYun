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
      <el-form-item label="学员姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入学员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分" prop="rating">
        <el-input
          v-model="queryParams.rating"
          placeholder="请输入评分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评价日期" prop="reviewDate">
        <el-date-picker clearable
          v-model="queryParams.reviewDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择评价日期">
        </el-date-picker>
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
          v-hasPermi="['jiayun:reviewadmin:add']"
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
          v-hasPermi="['jiayun:reviewadmin:edit']"
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
          v-hasPermi="['jiayun:reviewadmin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jiayun:reviewadmin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reviewadminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="评价ID" align="center" prop="id" />
      <el-table-column label="学员ID" align="center" prop="studentId" />
      <el-table-column label="教练ID" align="center" prop="coachId" />
      <el-table-column label="学员姓名" align="center" prop="studentName" />
      <el-table-column label="评分" align="center" prop="rating" />
      <el-table-column label="评价内容" align="center" prop="content" />
      <el-table-column label="评价日期" align="center" prop="reviewDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jiayun:reviewadmin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jiayun:reviewadmin:remove']"
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

    <!-- 添加或修改评价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学员ID" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学员ID" />
        </el-form-item>
        <el-form-item label="教练ID" prop="coachId">
          <el-input v-model="form.coachId" placeholder="请输入教练ID" />
        </el-form-item>
        <el-form-item label="学员姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学员姓名" />
        </el-form-item>
        <el-form-item label="评分" prop="rating">
          <el-input v-model="form.rating" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评价内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="评价日期" prop="reviewDate">
          <el-date-picker clearable
            v-model="form.reviewDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择评价日期">
          </el-date-picker>
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
import { listReviewadmin, getReviewadmin, delReviewadmin, addReviewadmin, updateReviewadmin } from "@/api/jiayun/reviewadmin"

export default {
  name: "Reviewadmin",
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
      // 评价表格数据
      reviewadminList: [],
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
        studentName: null,
        rating: null,
        content: null,
        reviewDate: null,
        createdAt: null
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
        studentName: [
          { required: true, message: "学员姓名不能为空", trigger: "blur" }
        ],
        rating: [
          { required: true, message: "评分不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "评价内容不能为空", trigger: "blur" }
        ],
        reviewDate: [
          { required: true, message: "评价日期不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询评价列表 */
    getList() {
      this.loading = true
      listReviewadmin(this.queryParams).then(response => {
        this.reviewadminList = response.rows
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
        studentName: null,
        rating: null,
        content: null,
        reviewDate: null,
        createdAt: null
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
      this.title = "添加评价"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getReviewadmin(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改评价"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReviewadmin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addReviewadmin(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除评价编号为"' + ids + '"的数据项？').then(function() {
        return delReviewadmin(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('jiayun/reviewadmin/export', {
        ...this.queryParams
      }, `reviewadmin_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
