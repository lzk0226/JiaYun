import request from '@/utils/request'

// 查询统计数据列表
export function listStatisticsadmin(query) {
  return request({
    url: '/jiayun/statisticsadmin/list',
    method: 'get',
    params: query
  })
}

// 查询统计数据详细
export function getStatisticsadmin(id) {
  return request({
    url: '/jiayun/statisticsadmin/' + id,
    method: 'get'
  })
}

// 新增统计数据
export function addStatisticsadmin(data) {
  return request({
    url: '/jiayun/statisticsadmin',
    method: 'post',
    data: data
  })
}

// 修改统计数据
export function updateStatisticsadmin(data) {
  return request({
    url: '/jiayun/statisticsadmin',
    method: 'put',
    data: data
  })
}

// 删除统计数据
export function delStatisticsadmin(id) {
  return request({
    url: '/jiayun/statisticsadmin/' + id,
    method: 'delete'
  })
}
