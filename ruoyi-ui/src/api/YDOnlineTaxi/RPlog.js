import request from '@/utils/request'

// 查询积分奖惩日志列表
export function listRPlog(query) {
  return request({
    url: '/YDOnlineTaxi/RPlog/list',
    method: 'get',
    params: query
  })
}

// 查询积分奖惩日志详细
export function getRPlog(phoneNumber) {
  return request({
    url: '/YDOnlineTaxi/RPlog/' + phoneNumber,
    method: 'get'
  })
}

// 新增积分奖惩日志
export function addRPlog(data) {
  return request({
    url: '/YDOnlineTaxi/RPlog',
    method: 'post',
    data: data
  })
}

// 修改积分奖惩日志
export function updateRPlog(data) {
  return request({
    url: '/YDOnlineTaxi/RPlog',
    method: 'put',
    data: data
  })
}

// 删除积分奖惩日志
export function delRPlog(phoneNumber) {
  return request({
    url: '/YDOnlineTaxi/RPlog/' + phoneNumber,
    method: 'delete'
  })
}

// 导出积分奖惩日志
export function exportRPlog(query) {
  return request({
    url: '/YDOnlineTaxi/RPlog/export',
    method: 'get',
    params: query
  })
}