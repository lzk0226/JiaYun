import request from '@/utils/request'

// 查询预约记录列表
export function listReservationadmin(query) {
  return request({
    url: '/jiayun/reservationadmin/list',
    method: 'get',
    params: query
  })
}

// 查询预约记录详细
export function getReservationadmin(id) {
  return request({
    url: '/jiayun/reservationadmin/' + id,
    method: 'get'
  })
}

// 新增预约记录
export function addReservationadmin(data) {
  return request({
    url: '/jiayun/reservationadmin',
    method: 'post',
    data: data
  })
}

// 修改预约记录
export function updateReservationadmin(data) {
  return request({
    url: '/jiayun/reservationadmin',
    method: 'put',
    data: data
  })
}

// 删除预约记录
export function delReservationadmin(id) {
  return request({
    url: '/jiayun/reservationadmin/' + id,
    method: 'delete'
  })
}
