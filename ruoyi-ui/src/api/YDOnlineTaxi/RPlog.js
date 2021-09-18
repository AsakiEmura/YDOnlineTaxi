import request from '@/utils/request'

// 查询积分奖惩日志列表
export function listLog(query) {
  return request({
    url: '/YDOnlineTaxi/log/list',
    method: 'get',
    params: query
  })
}

// 查询积分奖惩日志详细
export function getLog(phoneNumber) {
  return request({
    url: '/YDOnlineTaxi/log/' + phoneNumber,
    method: 'get'
  })
}

// 新增积分奖惩日志
export function addLog(data) {
  return request({
    url: '/YDOnlineTaxi/log',
    method: 'post',
    data: data
  })
}

// 修改积分奖惩日志
export function updateLog(data) {
  return request({
    url: '/YDOnlineTaxi/log',
    method: 'put',
    data: data
  })
}

// 删除积分奖惩日志
export function delLog(phoneNumber) {
  return request({
    url: '/YDOnlineTaxi/log/' + phoneNumber,
    method: 'delete'
  })
}

// 导出积分奖惩日志
export function exportLog(query) {
  return request({
    url: '/YDOnlineTaxi/log/export',
    method: 'get',
    params: query
  })
}