import request from '@/utils/request'

// 查询到达审核信息列表
export function listAudit_information(query) {
  return request({
    url: '/YDOnlineTaxi/audit_information/list',
    method: 'get',
    params: query
  })
}

// 查询到达审核信息详细
export function getAudit_information(orderId) {
  return request({
    url: '/YDOnlineTaxi/audit_information/' + orderId,
    method: 'get'
  })
}

// 新增到达审核信息
export function addAudit_information(data) {
  return request({
    url: '/YDOnlineTaxi/audit_information',
    method: 'post',
    data: data
  })
}

// 修改到达审核信息
export function updateAudit_information(data) {
  return request({
    url: '/YDOnlineTaxi/audit_information/audit',
    method: 'put',
    data: data
  })
}

// 删除到达审核信息
export function delAudit_information(orderId) {
  return request({
    url: '/YDOnlineTaxi/audit_information/' + orderId,
    method: 'delete'
  })
}

// 导出到达审核信息
export function exportAudit_information(query) {
  return request({
    url: '/YDOnlineTaxi/audit_information/export',
    method: 'get',
    params: query
  })
}
