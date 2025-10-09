import request from '@/utils/request'

// 查询教练科目关联列表
export function listCoach_subjectadmin(query) {
  return request({
    url: '/jiayun/coach_subjectadmin/list',
    method: 'get',
    params: query
  })
}

// 查询教练科目关联详细
export function getCoach_subjectadmin(id) {
  return request({
    url: '/jiayun/coach_subjectadmin/' + id,
    method: 'get'
  })
}

// 新增教练科目关联
export function addCoach_subjectadmin(data) {
  return request({
    url: '/jiayun/coach_subjectadmin',
    method: 'post',
    data: data
  })
}

// 修改教练科目关联
export function updateCoach_subjectadmin(data) {
  return request({
    url: '/jiayun/coach_subjectadmin',
    method: 'put',
    data: data
  })
}

// 删除教练科目关联
export function delCoach_subjectadmin(id) {
  return request({
    url: '/jiayun/coach_subjectadmin/' + id,
    method: 'delete'
  })
}
