// LocalStorageから'user'を取得し、認証トークンを取得する
export default function authHeader () {
  let user = JSON.parse(localStorage.getItem('user'))

  if (user && user.accessToken) {
    return { Authorization: 'Bearer ' + user.accessToken }
  } else {
    return {}
  }
}