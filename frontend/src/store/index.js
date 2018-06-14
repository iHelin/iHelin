import Vue from 'vue'
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        username: sessionStorage.getItem("username")
    },
    mutations: {
        SET_USERNAME: (state, username) => {
            state.username = username;
            sessionStorage.setItem("username", username);
        }
    },
    actions: {
        setUsername({commit}, username) {
            commit('SET_USERNAME', username);
        }
    }
});

export default store;
