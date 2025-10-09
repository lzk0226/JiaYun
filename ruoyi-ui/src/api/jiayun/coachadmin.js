import request from '@/utils/request'

// 查询教练列表
export function listCoachadmin(query) {
  return request({
    url: '/jiayun/coachadmin/list',
    method: 'get',
    params: query
  })
}

// 查询教练详细
export function getCoachadmin(id) {
  return request({
    url: '/jiayun/coachadmin/' + id,
    method: 'get'
  })
}

// 新增教练
export function addCoachadmin(data) {
  return request({
    url: '/jiayun/coachadmin',
    method: 'post',
    data: data
  })
}

// 修改教练
export function updateCoachadmin(data) {
  return request({
    url: '/jiayun/coachadmin',
    method: 'put',
    data: data
  })
}

// 删除教练
export function delCoachadmin(id) {
  return request({
    url: '/jiayun/coachadmin/' + id,
    method: 'delete'
  })
}
