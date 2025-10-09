import request from '@/utils/request'

// 查询学员课程关联列表
export function listStudent_courseadmin(query) {
  return request({
    url: '/jiayun/student_courseadmin/list',
    method: 'get',
    params: query
  })
}

// 查询学员课程关联详细
export function getStudent_courseadmin(id) {
  return request({
    url: '/jiayun/student_courseadmin/' + id,
    method: 'get'
  })
}

// 新增学员课程关联
export function addStudent_courseadmin(data) {
  return request({
    url: '/jiayun/student_courseadmin',
    method: 'post',
    data: data
  })
}

// 修改学员课程关联
export function updateStudent_courseadmin(data) {
  return request({
    url: '/jiayun/student_courseadmin',
    method: 'put',
    data: data
  })
}

// 删除学员课程关联
export function delStudent_courseadmin(id) {
  return request({
    url: '/jiayun/student_courseadmin/' + id,
    method: 'delete'
  })
}
