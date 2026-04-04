package com.demo.app;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HealthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VersionUtil versionUtil = new VersionUtil();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.write("{\"status\":\"UP\",\"application\":\"" + versionUtil.getApplicationName()
                + "\",\"version\":\"" + versionUtil.getApplicationVersion() + "\"}");
        out.flush();
    }
}
