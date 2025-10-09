import request from '@/utils/request'

// 查询课程列表
export function listCourseadmin(query) {
  return request({
    url: '/jiayun/courseadmin/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getCourseadmin(id) {
  return request({
    url: '/jiayun/courseadmin/' + id,
    method: 'get'
  })
}

// 新增课程
export function addCourseadmin(data) {
  return request({
    url: '/jiayun/courseadmin',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourseadmin(data) {
  return request({
    url: '/jiayun/courseadmin',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourseadmin(id) {
  return request({
    url: '/jiayun/courseadmin/' + id,
    method: 'delete'
  })
}
