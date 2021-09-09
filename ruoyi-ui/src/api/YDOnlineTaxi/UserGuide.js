import request from '@/utils/request'

// 查询订单信息详细
export function getUserGuide() {
  return request({
    url: '/YDOnlineTaxi/OrderUserGuide/getUserGuide',
    method: 'get'
  })
}

// 修改订单信息
export function updateUserGuide(data) {
  return request({
    url: '/YDOnlineTaxi/OrderUserGuide/updataUserGuide',
    method: 'get',
    data: data
  })
}
