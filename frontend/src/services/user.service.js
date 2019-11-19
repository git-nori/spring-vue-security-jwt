import axios from 'axios'
import authHeader from './auth-header'

const API_URL = 'http://localhost:8080/api/test/'

// 各リソースへのアクセスを定義
class UserService {
  getPublicContent () {
    return axios.get(API_URL + 'all')
  }

  // 認証が必要なリソースへのアクセスの際はヘッダーに認証トークンを付与してGETを行う
  getUserBoard () {
    return axios.get(API_URL + 'user', { headers: authHeader() })
  }

  getModeratorBoard () {
    return axios.get(API_URL + 'mod', { headers: authHeader() })
  }

  getAdminBoard () {
    return axios.get(API_URL + 'admin', { headers: authHeader() })
  }
}

export default new UserService