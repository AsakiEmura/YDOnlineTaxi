/*
 * @Descripttion:
 * @Author: zhengminjie
 * @Date: 2021-08-09 11:27:27
 * @LastEditors: zhengminjie
 * @LastEditTime: 2021-08-09 11:27:28
 */
import request from '@/utils/request'

//查询科目列表
export function subjectList(query){
    return request({
      url:'/itaxbill/subject/list',
      method: 'get',
      params: query
    })
  }
  
// 查询科目详细
export function getSubject(subjectId){
    return request({
      url:'/itaxbill/subject/detail?subjectId='+subjectId,
      method:'get'
    })
  }
  
// 设置用户默认科目抬头
export function setDefaultSubject(subjectId){
    return request({
      url:'/itaxbill/subject/setDefault?subjectId='+subjectId,
      method:'get'
    })
  }

// 新增科目
export function addSubject(data) {
    console.log("request",data);
    return request({
      url: '/itaxbill/subject/add',
      method: 'post',
      data: data
    })
  }

// 修改科目
export function editSubject(data) {
    return request({
      url: '/itaxbill/subject/edit',
      method: 'post',
      data: data
    })
  }

// 删除科目
export function delSubject(subjectId) {
    return request({
      url: '/itaxbill/subject/softdel',
      method: 'DELETE',
      data: {subjectId}
    })
  }