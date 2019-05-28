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
export const admin = process.env.VUE_APP_BASE_API + '/monitor/'

//ZipKin
export const zipkin = process.env.VUE_APP_BASE_API + '/zipkin/'

//Eureka
export const eureka = process.env.VUE_APP_BASE_API + '/eureka/'

//本地文件上传接口
export const localUpload = process.env.VUE_APP_BASE_API + "/admin/storage/local/upload"

//Swagger2
export const swagger = process.env.VUE_APP_BASE_API + '/admin/swagger-ui.html'
