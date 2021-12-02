import request from '@/utils/request'

// 查询订单信息列表
export function listOrderInformation(query) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/list',
    method: 'get',
    params: query
  })
}

// 查询自定司机信息列表
export function listDriverInformation(query) {
  return request({
    url: '/YDOnlineTaxi/DriverInformation/orderDriver',
    method: 'get',
    params: query
  })
}

// 指定司机
export function orderDriver(data) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/orderDriver',
    method: 'put',
    data: data
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

// 查询已接单司机未出发司机已出发状态订单信息列表
export function receivedListList(query) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/receivedList',
    method: 'get',
    params: query
  })
}

export function oneSettlement(data) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/oneSettlement',
    method: 'put',
    data: data
  })
}

export function settlement() {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/settlement',
    method: 'put',
  })
}

// 查询审核结算订单信息列表
export function auditSettlementList(query) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/auditSettlementList',
    method: 'get',
    params: query
  })
}

// 查询到达审核信息详细
export function getArrival_information(orderid) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/arrival_information/' + orderid,
    method: 'get',
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
    url: '/YDOnlineTaxi/OrderInformation/',
    method: 'put',
    data: data
  })
}

// 超时订单重新发布
export function resetOrderStatus(data) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/resetOrderStatus',
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
