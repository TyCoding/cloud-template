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

export function findById(id) {
  return request({
    url: '/admin/user/' + id,
    method: 'get'
  })
}

export function save(data) {
  return request({
    url: '/admin/user',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/admin/user/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/admin/user/edit',
    method: 'put',
    data
  })
}
