import request from '@/utils/request'

// 查询微信司机连接列表
export function listWxWithDrivers(query) {
  return request({
    url: '/YDOnlineTaxi/wxWithDrivers/list',
    method: 'get',
    params: query
  })
}

// 查询微信司机连接详细
export function getWxWithDrivers(driverName) {
  return request({
    url: '/YDOnlineTaxi/wxWithDrivers/' + driverName,
    method: 'get'
  })
}

// 查询微信id
export function getOpenId() {
  return request({
    url: '/YDOnlineTaxi/wxWithDrivers/getOpenId',
    method: 'get'
  })
}

// 新增微信司机连接
export function addWxWithDrivers(data) {
  return request({
    url: '/YDOnlineTaxi/wxWithDrivers',
    method: 'post',
    data: data
  })
}

// 修改微信司机连接
export function updateWxWithDrivers(data) {
  return request({
    url: '/YDOnlineTaxi/wxWithDrivers',
    method: 'put',
    data: data
  })
}

// 删除微信司机连接
export function delWxWithDrivers(driverName) {
  return request({
    url: '/YDOnlineTaxi/wxWithDrivers/' + driverName,
    method: 'delete'
  })
}

// 导出微信司机连接
export function exportWxWithDrivers(query) {
  return request({
    url: '/YDOnlineTaxi/wxWithDrivers/export',
    method: 'get',
    params: query
  })
}
