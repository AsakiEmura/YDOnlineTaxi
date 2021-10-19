import request from '@/utils/request'

// 查询额外积分申请列表
export function listAudit_information(query) {
  return request({
    url: '/YDOnlineTaxi/order/list',
    method: 'get',
    params: query
  })
}

// 查询额外积分申请详细
export function getAudit_information(orderId) {
  return request({
    url: '/YDOnlineTaxi/order/' + orderId,
    method: 'get'
  })
}

// 查询额外积分申请详细
export function getArrival_Audit_information(orderId) {
  return request({
    url: '/YDOnlineTaxi/AuditOrder/' + orderId,
    method: 'get'
  })
}

// 修改额外积分申请信息
export function updateAudit_information(data) {
  return request({
    url: '/YDOnlineTaxi/order/audit',
    method: 'put',
    data: data
  })
}

//审核额外积分结果更新
export function updateExtraOrder(data) {
  return request({
    url: '/YDOnlineTaxi/AuditOrder/audit',
    method: 'put',
    data:data
  })
}

// 删除额外积分申请信息
export function delAudit_information(orderId) {
  return request({
    url: '/YDOnlineTaxi/order/' + orderId,
    method: 'delete'
  })
}

// 导出额外积分申请信息
export function exportAudit_information(query) {
  return request({
    url: '/YDOnlineTaxi/order/export',
    method: 'get',
    params: query
  })
}
