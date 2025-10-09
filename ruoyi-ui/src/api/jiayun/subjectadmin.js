import request from '@/utils/request'

// 查询科目列表
export function listSubjectadmin(query) {
  return request({
    url: '/jiayun/subjectadmin/list',
    method: 'get',
    params: query
  })
}

// 查询科目详细
export function getSubjectadmin(id) {
  return request({
    url: '/jiayun/subjectadmin/' + id,
    method: 'get'
  })
}

// 新增科目
export function addSubjectadmin(data) {
  return request({
    url: '/jiayun/subjectadmin',
    method: 'post',
    data: data
  })
}

// 修改科目
export function updateSubjectadmin(data) {
  return request({
    url: '/jiayun/subjectadmin',
    method: 'put',
    data: data
  })
}

// 删除科目
export function delSubjectadmin(id) {
  return request({
    url: '/jiayun/subjectadmin/' + id,
    method: 'delete'
  })
}
