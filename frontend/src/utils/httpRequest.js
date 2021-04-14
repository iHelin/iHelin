import axios from "axios";
import router from "@/router";
import { clearLoginInfo } from "@/utils";

const http = axios.create({
    timeout: 1000 * 30,
    withCredentials: true,
    headers: {
        "Content-Type": "application/json; charset=utf-8"
    }
});

/**
 * 请求拦截
 */
http.interceptors.request.use(
    config => {
        let token = localStorage.getItem("token");
        if (token) {
            config.headers["Authorization"] = "Bearer " + token; // 请求头带上token
        }
        config.url = "/api" + config.url;
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

/**
 * 响应拦截
 */
http.interceptors.response.use(
    response => {
        if (response.data && response.data.code === 401) {
            // 401, token失效
            clearLoginInfo();
            router.push({ name: "login" });
            return;
        }
        return response;
    },
    error => {
        return Promise.reject(error);
    }
);

export default http;
