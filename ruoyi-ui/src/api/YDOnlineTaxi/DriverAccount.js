import request from '@/utils/request'

// 查询司机详细信息列表
export function listDriverAccount(query) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/list',
    method: 'get',
    params: query
  })
}

// 查询黑名单司机详细信息列表
export function blacklistDriverAccount(query) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/Blacklist',
    method: 'get',
    params: query
  })
}

// 移出黑名单司机
export function pushOut(query) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/pushOut',
    method: 'put',
    data: query
  })
}

// 查询司机详细信息详细
export function getDriverAccount(idNumber) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/' + idNumber,
    method: 'get'
  })
}

// 新增司机详细信息
export function addDriverAccount(data) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount',
    method: 'post',
    data: data
  })
}

// 修改司机详细信息
export function updateDriverAccount(data) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount',
    method: 'put',
    data: data
  })
}

// 修改司机详细信息
export function audit(data) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/audit',
    method: 'put',
    data: data
  })
}

// 删除司机详细信息
export function delDriverAccount(idNumber) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/' + idNumber,
    method: 'delete'
  })
}

// 导出司机详细信息
export function exportDriverAccount(query) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/export',
    method: 'get',
    params: query
  })
}

// 用户密码重置
export function resetUserPwd(idNumber, driverPassword) {
  const data = {
    idNumber,
    driverPassword
  }
  return request({
    url: '/YDOnlineTaxi/DriverAccount/resetPwd',
    method: 'put',
    data: data
  })
}

//refuseDriver:拒绝申请
// 新增司机详细信息
export function refuseDriver(data) {
  return request({
    url: '/YDOnlineTaxi/DriverAccount/refuseDriver',
    method: 'post',
    data: data
  })
}
