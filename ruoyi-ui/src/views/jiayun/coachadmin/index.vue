<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入职称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教学经验" prop="experience">
        <el-input
          v-model="queryParams.experience"
          placeholder="请输入教学经验"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训学员数" prop="studentsCount">
        <el-input
          v-model="queryParams.studentsCount"
          placeholder="请输入培训学员数"
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
      <el-form-item label="评价数量" prop="reviewsCount">
        <el-input
          v-model="queryParams.reviewsCount"
          placeholder="请输入评价数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通过率" prop="passRate">
        <el-input
          v-model="queryParams.passRate"
          placeholder="请输入通过率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 按钮操作 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jiayun:coachadmin:add']"
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
          v-hasPermi="['jiayun:coachadmin:edit']"
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
          v-hasPermi="['jiayun:coachadmin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jiayun:coachadmin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 教练表格 -->
    <el-table v-loading="loading" :data="coachadminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="教练ID" align="center" prop="id" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="职称" align="center" prop="title" />
      <el-table-column label="徽章" align="center" prop="badge" />
      <el-table-column label="等级" align="center" prop="level" />
      <el-table-column label="教学经验" align="center" prop="experience" />
      <el-table-column label="培训学员数" align="center" prop="studentsCount" />
      <el-table-column label="评分" align="center" prop="rating" />
      <el-table-column label="评价数量" align="center" prop="reviewsCount" />
      <el-table-column label="通过率" align="center" prop="passRate" />
      <el-table-column label="教学特点标签" align="center" prop="features" />
      <el-table-column label="头像" align="center" prop="avatar" width="100">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.avatar"
            style="width: 60px; height: 60px"
            :src="scope.row.avatar"
            :preview-src-list="[scope.row.avatar]"
            fit="cover"
          >
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
          <span v-else>暂无图片</span>
        </template>
      </el-table-column>
      <el-table-column label="教练简介" align="center" prop="description" />
      <el-table-column label="状态：1-在职，0-离职" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jiayun:coachadmin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jiayun:coachadmin:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加/修改教练弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="form.title" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item label="教学经验" prop="experience">
          <el-input v-model="form.experience" placeholder="请输入教学经验" />
        </el-form-item>
        <el-form-item label="培训学员数" prop="studentsCount">
          <el-input v-model="form.studentsCount" placeholder="请输入培训学员数" />
        </el-form-item>
        <el-form-item label="评分" prop="rating">
          <el-input v-model="form.rating" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评价数量" prop="reviewsCount">
          <el-input v-model="form.reviewsCount" placeholder="请输入评价数量" />
        </el-form-item>
        <el-form-item label="通过率" prop="passRate">
          <el-input v-model="form.passRate" placeholder="请输入通过率" />
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :http-request="handleAvatarUpload"
            accept="image/*"
          >
            <img v-if="form.avatar" :src="form.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="upload-tip">支持jpg、png、gif格式,大小不超过2MB</div>
        </el-form-item>
        <el-form-item label="教练简介" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">在职</el-radio>
            <el-radio :label="0">离职</el-radio>
          </el-radio-group>
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
import { listCoachadmin, getCoachadmin, delCoachadmin, addCoachadmin, updateCoachadmin } from "@/api/jiayun/coachadmin"

export default {
  name: "Coachadmin",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      coachadminList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        title: null,
        badge: null,
        level: null,
        experience: null,
        studentsCount: null,
        rating: null,
        reviewsCount: null,
        passRate: null,
        features: null,
        avatar: null,
        description: null,
        status: null,
        createdAt: null,
        updatedAt: null
      },
      form: {},
      rules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        experience: [{ required: true, message: "教学经验不能为空", trigger: "blur" }],
        studentsCount: [{ required: true, message: "培训学员数不能为空", trigger: "blur" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCoachadmin(this.queryParams).then(res => {
        this.coachadminList = res.rows
        this.total = res.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        name: null,
        title: null,
        badge: null,
        level: null,
        experience: null,
        studentsCount: null,
        rating: null,
        reviewsCount: null,
        passRate: null,
        features: null,
        avatar: null,
        description: null,
        status: null,
        createdAt: null,
        updatedAt: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加教练"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCoachadmin(id).then(res => {
        this.form = res.data
        this.open = true
        this.title = "修改教练"
      })
    },
    /** 上传前校验 */
    beforeAvatarUpload(file) {
      const isImage = /^image\/(jpeg|jpg|png|gif)$/.test(file.type)
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isImage) {
        this.$message.error('上传头像图片只能是 JPG、PNG、GIF 格式!')
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
        return false
      }
      return true
    },
    /** 自定义上传方法 */
    handleAvatarUpload(param) {
      const file = param.file
      const reader = new FileReader()
      reader.onload = (e) => {
        this.form.avatar = e.target.result
        this.$message.success('图片上传成功')
      }
      reader.onerror = () => {
        this.$message.error('图片读取失败,请重试')
      }
      reader.readAsDataURL(file)
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) return
        if (this.form.id) {
          updateCoachadmin(this.form).then(() => {
            this.$message.success("修改成功")
            this.cancel()
            this.getList()
          })
        } else {
          addCoachadmin(this.form).then(() => {
            this.$message.success("添加成功")
            this.cancel()
            this.getList()
          })
        }
      })
    },
    handleDelete(row) {
      const ids = row ? [row.id] : this.ids
      this.$confirm("确定删除选中教练吗？", "提示", { type: "warning" }).then(() => {
        delCoachadmin(ids).then(() => {
          this.$message.success("删除成功")
          this.getList()
        })
      })
    },
    handleExport() {
      // 导出逻辑，可调用接口导出excel
    },
    resetForm(refName) {
      if (this.$refs[refName]) {
        this.$refs[refName].resetFields()
      }
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  display: inline-block;
}
.avatar-uploader >>> .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.3s;
}
.avatar-uploader >>> .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  display: block;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  object-fit: cover;
}
.upload-tip {
  font-size: 12px;
  color: #606266;
  margin-top: 8px;
  line-height: 1.5;
}
.image-slot {
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #c0c4cc;
}
</style>
