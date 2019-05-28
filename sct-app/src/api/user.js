import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/oauth/token',
    method: 'post',
    params: {
      username: data.username,
      password: data.password,
      grant_type: 'password'
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/admin/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/auth/token/logout',
    method: 'delete'
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

export function upload(file) {
  return request({
    url: '/admin/storage/local/upload',
    method: 'post',
    headers: { "Content-Type": "multipart/form-data" },
    params: file
  })
}
