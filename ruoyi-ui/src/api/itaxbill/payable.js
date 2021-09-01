/*
 * @Descripttion:
 * @Author: duyu
 * @Date: 2021-01-12 11:27:27
 * @LastEditors: duyu
 * @LastEditTime: 2021-01-12 11:27:28
 */
import request from '@/utils/request'

//查询发票列表
export function listPayable(query){
  return request({
    url:'/itaxbill/payable/list',
    method: 'get',
    params: query
  })
}

// 查询发票详细
export function getPayable(payableId){
  return request({
    url:'/itaxbill/payable/detail?payableId='+payableId,
    method:'get'
  })
}

// 获取用户默认发票抬头
export function getDefaultPayable(payableId){
  return request({
    url:'/itaxbill/payable/default?payableId='+payableId,
    method:'get'
  })
}

// 设置用户默认发票抬头
export function setDefaultPayable(payableId){
  return request({
    url:'/itaxbill/payable/setDefault?payableId='+payableId,
    method:'get'
  })
}
// 新增发票
export function addPayable(data) {
  console.log("request",data);
  return request({
    url: '/itaxbill/payable/add',
    method: 'post',
    data: data
  })
}

// 修改发票
export function updatePayable(data) {
  return request({
    url: '/itaxbill/payable/edit',
    method: 'post',
    data: data
  })
}


// 删除发票
export function delPayable(payableId) {
  return request({
    url: '/itaxbill/payable/softdel',
    method: 'DELETE',
    data: {payableId}
  })
}

// 导出抬头数据
export function exportPayable(query) {
  return request({
    url: '/itaxbill/payable/export',
    method: 'get',
    params: query
  })
}