import Vue from 'vue'
import Vuex from 'vuex'
import { auth } from './auth.module'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    auth // 外部で定義した認証のためのvuexモジュールを設定
  }
})
