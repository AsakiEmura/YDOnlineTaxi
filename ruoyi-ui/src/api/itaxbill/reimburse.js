/*
 * @Descripttion:
 * @Author: zhengminjie
 * @Date: 2021-01-12 11:27:27
 * @LastEditors: zhengminjie
 * @LastEditTime: 2021-01-12 11:27:28
 */
import request from '@/utils/request'

//查询报销发票列表
export function listReimburse(query){
  return request({
    url:'/itaxbill/reimburse/list',
    method: 'get',
    params: query
  })
}

//查询发票列表
export function listInvoice(query){
  return request({
    url:'/itaxbill/reimburse/invoiceList',
    method: 'get',
    params: query
  })
}

// 查询发票详细
export function getReimburse(ReimburseId){
  return request({
    url:'/itaxbill/reimburse/detail?reimburseId='+ReimburseId,
    method:'get'
  })
}

// 新增报销单
export function addReimburse(data) {
  console.log("request",data);
  return request({
    url: '/itaxbill/reimburse/add',
    method: 'post',
    data: data
  })
}

// 修改发票
export function updateReimburse(data) {
  return request({
    url: '/itaxbill/reimburse/edit',
    method: 'post',
    data: data
  })
}

// 确定报销
export function confirmReimburse(data) {
  return request({
    url: '/itaxbill/reimburse/confirm',
    method: 'post',
    data: data
  })
}

// 删除发票(软删除)
export function delReimburse(data) {
  return request({
    url: '/itaxbill/reimburse/softdel',
    method: 'DELETE',
    data: data
  })
}

// 导出报销单
export function exportReimburse(query) {
  return request({
    url: '/itaxbill/reimburse/export',
    method: 'get',
    params: query
  })
}