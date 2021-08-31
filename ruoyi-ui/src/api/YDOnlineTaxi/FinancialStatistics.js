import request from '@/utils/request'

// 查询财务统计列表
export function listFinancialStatistics(query) {
  return request({
    url: '/YDOnlineTaxi/FinancialStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询财务统计详细
export function getFinancialStatistics(driverPhoneNumber) {
  return request({
    url: '/YDOnlineTaxi/FinancialStatistics/' + driverPhoneNumber,
    method: 'get'
  })
}

// 新增财务统计
export function addFinancialStatistics(data) {
  return request({
    url: '/YDOnlineTaxi/FinancialStatistics',
    method: 'post',
    data: data
  })
}

// 修改财务统计
export function updateFinancialStatistics(data) {
  return request({
    url: '/YDOnlineTaxi/FinancialStatistics',
    method: 'put',
    data: data
  })
}

// 删除财务统计
export function delFinancialStatistics(driverPhoneNumber) {
  return request({
    url: '/YDOnlineTaxi/FinancialStatistics/' + driverPhoneNumber,
    method: 'delete'
  })
}

// 导出财务统计
export function exportFinancialStatistics(query) {
  return request({
    url: '/YDOnlineTaxi/FinancialStatistics/export',
    method: 'get',
    params: query
  })
}