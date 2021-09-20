import request from '@/utils/request'

// 查询订单信息列表
export function listOrderInformation(query) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/list',
    method: 'get',
    params: query
  })
}

// 查询一种状态订单信息列表
export function singleStatusList(data) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/singleStatusList',
    method: 'post',
    data: data
  })
}

// 查询多种状态订单信息列表
export function receivedListList() {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/receivedList',
    method: 'get',
  })
}

// 查询到达审核信息列表
export function listAudit_information(query) {
  return request({
    url: '/YDOnlineTaxi/audit_information/list',
    method: 'get',
    params: query
  })
}

// 查询到达审核信息详细
export function getAudit_information(orderid) {
  return request({
    url: '/YDOnlineTaxi/audit_information/' + orderid,
    method: 'get'
  })
}

// 查询订单信息详细
export function getOrderInformation(orderId) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/' + orderId,
    method: 'get'
  })
}

// 新增订单信息
export function addOrderInformation(data) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateOrderInformation(data) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation',
    method: 'put',
    data: data
  })
}

// 删除订单信息
export function delOrderInformation(orderId) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/' + orderId,
    method: 'delete'
  })
}

// 导出订单信息
export function exportOrderInformation(query) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/importTemplate',
    method: 'get'
  })
}
