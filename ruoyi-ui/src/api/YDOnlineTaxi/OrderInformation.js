import request from '@/utils/request'

// 查询订单信息列表
export function listOrderInformation(query) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息列表
export function timeOutList(query) {
  return request({
    url: '/YDOnlineTaxi/OrderInformation/timeOutList',
    method: 'get',
    params: query
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
