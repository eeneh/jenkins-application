<%@ page import="com.demo.app.VersionUtil" %>
<%
    VersionUtil versionUtil = new VersionUtil();
    String version = versionUtil.getApplicationVersion();
    String appName = versionUtil.getApplicationName();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= appName %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            padding: 40px;
        }
        .card {
            max-width: 700px;
            margin: auto;
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.15);
        }
        h1 {
            color: #1f4e79;
        }
        .version {
            font-size: 22px;
            color: green;
            margin-top: 20px;
        }
        .info {
            margin-top: 25px;
            line-height: 1.8;
        }
        .health {
            margin-top: 25px;
        }
        a {
            color: #0066cc;
        }
    </style>
</head>
<body>
<div class="card">
    <h1><%= appName %></h1>
    <p>This application is deployed through Jenkins Declarative Pipeline.</p>

    <div class="version">
        Current Version: <strong><%= version %></strong>
    </div>

    <div class="info">
        <p><strong>Build Type:</strong> Maven WAR Build</p>
        <p><strong>Deployment Target:</strong> Apache Tomcat on Ubuntu Server</p>
        <p><strong>CI/CD Tool:</strong> Jenkins</p>
    </div>

    <div class="health">
        <p>Health Check URL:
            <a href="health">Click here</a>
        </p>
    </div>
</div>
</body>
</html>
