import request from '@/utils/request'

// 查询积分统计列表
export function listPointsStatistics(query) {
  return request({
    url: '/YDOnlineTaxi/PointsStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询积分统计详细
export function getPointsStatistics(driverPhoneNumber) {
  return request({
    url: '/YDOnlineTaxi/PointsStatistics/' + driverPhoneNumber,
    method: 'get'
  })
}

// 新增积分统计
export function addPointsStatistics(data) {
  return request({
    url: '/YDOnlineTaxi/PointsStatistics',
    method: 'post',
    data: data
  })
}

// 修改积分统计
export function updatePointsStatistics(data) {
  return request({
    url: '/YDOnlineTaxi/PointsStatistics',
    method: 'put',
    data: data
  })
}

// 删除积分统计
export function delPointsStatistics(driverPhoneNumber) {
  return request({
    url: '/YDOnlineTaxi/PointsStatistics/' + driverPhoneNumber,
    method: 'delete'
  })
}

// 导出积分统计
export function exportPointsStatistics(query) {
  return request({
    url: '/YDOnlineTaxi/PointsStatistics/export',
    method: 'get',
    params: query
  })
}