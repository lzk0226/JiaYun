import request from '@/utils/request'

// 查询评价列表
export function listReviewadmin(query) {
  return request({
    url: '/jiayun/reviewadmin/list',
    method: 'get',
    params: query
  })
}

// 查询评价详细
export function getReviewadmin(id) {
  return request({
    url: '/jiayun/reviewadmin/' + id,
    method: 'get'
  })
}

// 新增评价
export function addReviewadmin(data) {
  return request({
    url: '/jiayun/reviewadmin',
    method: 'post',
    data: data
  })
}

// 修改评价
export function updateReviewadmin(data) {
  return request({
    url: '/jiayun/reviewadmin',
    method: 'put',
    data: data
  })
}

// 删除评价
export function delReviewadmin(id) {
  return request({
    url: '/jiayun/reviewadmin/' + id,
    method: 'delete'
  })
}
