/*
 * @Descripttion:
 * @Author: zhengminjie
 * @Date: 2021-01-12 11:27:27
 * @LastEditors: zhengminjie
 * @LastEditTime: 2021-01-12 11:27:28
 */
import request from '@/utils/request'

// 主页面
export function DataValue() {
    return request({
      url: '/itaxbill/index/DataValue',
      method: 'get'
    })
  }
// 主页面 表格数据
export function LineChartData() {
  return request({
    url: '/itaxbill/index/LineChartData',
    method: 'get'
  })
}
