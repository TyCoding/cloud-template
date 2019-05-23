import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/auth/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/auth/user/logout',
    method: 'get'
  })
}

export function getList(data) {
  return request({
    url: '/admin/user/list',
    method: 'post',
    data
  })
}
