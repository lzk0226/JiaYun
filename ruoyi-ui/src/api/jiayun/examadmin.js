import request from '@/utils/request'

// 查询考试记录列表
export function listExamadmin(query) {
  return request({
    url: '/jiayun/examadmin/list',
    method: 'get',
    params: query
  })
}

// 查询考试记录详细
export function getExamadmin(id) {
  return request({
    url: '/jiayun/examadmin/' + id,
    method: 'get'
  })
}

// 新增考试记录
export function addExamadmin(data) {
  return request({
    url: '/jiayun/examadmin',
    method: 'post',
    data: data
  })
}

// 修改考试记录
export function updateExamadmin(data) {
  return request({
    url: '/jiayun/examadmin',
    method: 'put',
    data: data
  })
}

// 删除考试记录
export function delExamadmin(id) {
  return request({
    url: '/jiayun/examadmin/' + id,
    method: 'delete'
  })
}
