import request from '@/utils/request'

// 查询课程科目关联列表
export function listCourse_subjectadmin(query) {
  return request({
    url: '/jiayun/course_subjectadmin/list',
    method: 'get',
    params: query
  })
}

// 查询课程科目关联详细
export function getCourse_subjectadmin(id) {
  return request({
    url: '/jiayun/course_subjectadmin/' + id,
    method: 'get'
  })
}

// 新增课程科目关联
export function addCourse_subjectadmin(data) {
  return request({
    url: '/jiayun/course_subjectadmin',
    method: 'post',
    data: data
  })
}

// 修改课程科目关联
export function updateCourse_subjectadmin(data) {
  return request({
    url: '/jiayun/course_subjectadmin',
    method: 'put',
    data: data
  })
}

// 删除课程科目关联
export function delCourse_subjectadmin(id) {
  return request({
    url: '/jiayun/course_subjectadmin/' + id,
    method: 'delete'
  })
}
