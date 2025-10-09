import request from '@/utils/request'

// 查询学员列表
export function listStudentadmin(query) {
  return request({
    url: '/jiayun/studentadmin/list',
    method: 'get',
    params: query
  })
}

// 查询学员详细
export function getStudentadmin(id) {
  return request({
    url: '/jiayun/studentadmin/' + id,
    method: 'get'
  })
}

// 新增学员
export function addStudentadmin(data) {
  return request({
    url: '/jiayun/studentadmin',
    method: 'post',
    data: data
  })
}

// 修改学员
export function updateStudentadmin(data) {
  return request({
    url: '/jiayun/studentadmin',
    method: 'put',
    data: data
  })
}

// 删除学员
export function delStudentadmin(id) {
  return request({
    url: '/jiayun/studentadmin/' + id,
    method: 'delete'
  })
}
