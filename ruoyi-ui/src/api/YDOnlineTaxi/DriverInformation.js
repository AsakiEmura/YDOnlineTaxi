import request from '@/utils/request'

// 查询司机线上账户信息列表
export function listDriverInformation(query) {
  return request({
    url: '/YDOnlineTaxi/DriverInformation/list',
    method: 'get',
    params: query
  })
}

// 查询司机线上账户信息详细
export function getDriverInformation(driverPhoneNumber) {
  return request({
    url: '/YDOnlineTaxi/DriverInformation/' + driverPhoneNumber,
    method: 'get'
  })
}

// 新增司机线上账户信息

export function addDriverInformation(data) {
  return request({
    url: '/YDOnlineTaxi/DriverInformation',
    method: 'post',
    data: data
  })
}

// 修改司机线上账户信息

export function updateDriverInformation(data) {
  return request({
    url: '/YDOnlineTaxi/DriverInformation',
    method: 'put',
    data: data
  })
}

// 删除司机线上账户信息

export function delDriverInformation(driverPhoneNumber) {
  return request({
    url: '/YDOnlineTaxi/DriverInformation/' + driverPhoneNumber,
    method: 'delete'
  })
}

// 导出司机线上账户信息

export function exportDriverInformation(query) {
  return request({
    url: '/YDOnlineTaxi/DriverInformation/export',
    method: 'get',
    params: query
  })
}
