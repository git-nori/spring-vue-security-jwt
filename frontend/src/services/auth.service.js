import axios from 'axios'

// 認証サービス
const API_URL = 'http://localhost:8080/api/auth/'

class AuthService {
  // POST { ユーザー名、パスワード } ＆JWTローカルストレージに保存
  login (user) {
    return axios
      .post(API_URL + 'signin', {
        username: user.username,
        password: user.password
      })
      .then(this.handleResponse)
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data))
        }

        return response.data
      })
  }

  // JWTローカルストレージから削除
  logout () {
    localStorage.removeItem('user')
  }

  // POST {ユーザー名、メール、パスワード}
  register (user) {
    return axios
      .post(API_URL + 'signup', {
        username: user.username,
        email: user.email,
        password: user.password
      })
  }

  // signin時の例外処理
  handleResponse (response) {
    // 401ステータスが帰ってきた場合、ログアウト処理を行いエラーを返す
    if (response.status === 401) {
      this.logout()
      location.reload(true)

      const error = response.data && response.data.message
      return Promise.reject(error)
    }

    return Promise.resolve(response)
  }
}

export default new AuthService