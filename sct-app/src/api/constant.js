import request from '@/utils/request'

export function doc() {
  return request({
    url: '/admin/swagger-ui.html',
    method: 'get',
    params: {
      accept: 'text/html, text/plain',
    }
  })
}

//Spring Boot Admin
export const admin = 'http://localhost:3000/'

//ZipKin
export const zipkin = 'http://localhost:3001/'

//Eureka
export const eureka = 'http://localhost:8761/'

//Swagger2
export const swagger = process.env.VUE_APP_BASE_API + '/admin/swagger-ui.html'
