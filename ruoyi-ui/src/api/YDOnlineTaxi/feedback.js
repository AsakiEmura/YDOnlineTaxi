import request from '@/utils/request'

// 查询用户反馈列表
export function listFeedback(query) {
  return request({
    url: '/YDOnlineTaxi/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询用户反馈详细
export function getFeedback(id) {
  return request({
    url: '/YDOnlineTaxi/feedback/' + id,
    method: 'get'
  })
}

// 修改用户反馈
export function updateFeedback(data) {
  return request({
    url: '/YDOnlineTaxi/feedback',
    method: 'put',
    data: data
  })
}

// 删除用户反馈
export function delFeedback(id) {
  return request({
    url: '/YDOnlineTaxi/feedback/' + id,
    method: 'delete'
  })
}

// 导出用户反馈
export function exportFeedback(query) {
  return request({
    url: '/YDOnlineTaxi/feedback/export',
    method: 'get',
    params: query
  })
}
