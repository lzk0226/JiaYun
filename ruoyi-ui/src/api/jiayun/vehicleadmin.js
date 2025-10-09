import request from '@/utils/request'

// 查询车辆列表
export function listVehicleadmin(query) {
  return request({
    url: '/jiayun/vehicleadmin/list',
    method: 'get',
    params: query
  })
}

// 查询车辆详细
export function getVehicleadmin(id) {
  return request({
    url: '/jiayun/vehicleadmin/' + id,
    method: 'get'
  })
}

// 新增车辆
export function addVehicleadmin(data) {
  return request({
    url: '/jiayun/vehicleadmin',
    method: 'post',
    data: data
  })
}

// 修改车辆
export function updateVehicleadmin(data) {
  return request({
    url: '/jiayun/vehicleadmin',
    method: 'put',
    data: data
  })
}

// 删除车辆
export function delVehicleadmin(id) {
  return request({
    url: '/jiayun/vehicleadmin/' + id,
    method: 'delete'
  })
}
