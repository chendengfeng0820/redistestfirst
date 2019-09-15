package com;

import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CodeVerifyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CodeVerifyServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取手机号和验证码
        String telephone = request.getParameter("telephone");
        String verifycode = request.getParameter("verify_code");

        // 判断手机号和验证码是否为null
        if (telephone == null || verifycode == null) {
            return;
        }

        // 查询redis中的验证码
        Jedis jedis = new Jedis("172.16.116.100", 6379);
//        String phonenum=jedis.hkeys();
        String phoneCode = jedis.get(telephone);
        jedis.close();

        // 判断用户输入的验证码和redis中的验证码是否一致
        if (verifycode.equals(phoneCode)) {
            // 响应
            response.getWriter().print(true);
        }

    }
}
