/*
 * @Descripttion:
 * @Author: duyu
 * @Date: 2021-01-12 11:27:27
 * @LastEditors: duyu
 * @LastEditTime: 2021-01-12 11:27:28
 */
import request from '@/utils/request'

//查询发票列表
export function listInvoice(query){
  return request({
    url:'/itaxbill/invoice/list',
    method: 'get',
    params: query
  })
}

// 查询发票详细
export function getInvoice(invoiceId){
  return request({
    url:'/itaxbill/invoice/detail?invoiceId='+invoiceId,
    method:'get'
  })
}

// 新增发票
export function addInvoice(data) {
  return request({
    url: '/itaxbill/invoice/add',
    method: 'post',
    data: data
  })
}

// 修改发票
export function updateInvoice(data) {
  console.log("update Invoice data",data);
  return request({
    url: '/itaxbill/invoice/update',
    method: 'post',
    data: data
  })
}


// 删除发票
export function delInvoice(invoiceId) {
  return request({
    url: '/itaxbill/invoice/delete?invoiceId=' + invoiceId,
    method: 'get'
  })
}

// 导出发票数据
export function exportInvoice(invoiceCategory) {
  console.log(invoiceCategory);
  return request({
    url: '/itaxbill/invoice/export?invoiceCategory='+invoiceCategory,
    method: 'get',
  })
}