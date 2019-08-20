package com.cstary.currency;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/test"}, asyncSupported = true,
        loadOnStartup = -1, name = "SimpleServlet", displayName = "ss"
)
public class TestServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
//        String first = request.getParameter("first");           //从前端获取数据first
//        String second = request.getParameter("second");         //从前端获取数据second
        System.out.println(MongoTest.makedata());                                      //用于测试 ，判断是否成功获取到数据；
        out.println(MongoTest.makedata().toString());
    }
}
