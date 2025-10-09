import request from '@/utils/request'

// 查询学习进度列表
export function listLearning_progressadmin(query) {
  return request({
    url: '/jiayun/learning_progressadmin/list',
    method: 'get',
    params: query
  })
}

// 查询学习进度详细
export function getLearning_progressadmin(id) {
  return request({
    url: '/jiayun/learning_progressadmin/' + id,
    method: 'get'
  })
}

// 新增学习进度
export function addLearning_progressadmin(data) {
  return request({
    url: '/jiayun/learning_progressadmin',
    method: 'post',
    data: data
  })
}

// 修改学习进度
export function updateLearning_progressadmin(data) {
  return request({
    url: '/jiayun/learning_progressadmin',
    method: 'put',
    data: data
  })
}

// 删除学习进度
export function delLearning_progressadmin(id) {
  return request({
    url: '/jiayun/learning_progressadmin/' + id,
    method: 'delete'
  })
}
