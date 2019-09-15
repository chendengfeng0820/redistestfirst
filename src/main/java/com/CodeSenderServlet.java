package com;

import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Random;

public class CodeSenderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CodeSenderServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获取手机号，并校验手机号
        String telephone = request.getParameter("telephone");
        if (telephone == null) {
            return ;
        }

        // 2.生成6位随机验证码
        Random r=new Random(9999);
        String vertifycode =r.toString();
` `
        // 3.保存到redis服务器，并设置有效时间是2分钟
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.hset(hash,telephone,vertifycode);
        jedis.expire(vertifycode,120);
        //jedis.ping();
        jedis.close();

        // 4.发送短信给用户
        System.out.println("短信发送成功：" + vertifycode);

        // 5.响应true，注意不要使用println方法
        response.getWriter().print(true);
    }

    private String generatedCode(int len) {
        String code = "";
        for (int i = 0; i < len; i++) {
            int rand = new Random().nextInt(10);
            code += rand;
        }
        return code;
    }

}
