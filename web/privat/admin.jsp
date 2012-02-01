<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<body>Place your content here</body>
<br>
<a href="/admin/lst">ListServlet</a><br>
<a href="/admin/get">MainServlet</a><br>
<a href="/admin/del">DelAllServlet</a><br>
<a href="/img?key=xxxx">ImageServlet</a><br>
<a href="/public/root.html">Main</a><br>
<br> Paragraphs <br>
<a href="/admin/paragraph?print">Print All Paragraphs </a><br>
<a href="/admin/paragraph?add">Add All Paragraph Servlet</a><br>
<a href="/admin/paragraph?del">Del All Paragraph Servlet</a><br>
<br>
<br>TCaseTypes<br>
<a href="/admin/tctype?print" >Print All TCaseTypes </a><br>
<a href="/admin/tctype?add" >Add All TCaseTypes </a><br>
<a href="/admin/tctype?del" >Del All  TCaseTypes </a><br>
<br>
<br>TCases<br>
<a href="/admin/addTc?add" >Add All TCasesServlet</a><br>
<a href="/admin/addTc?print" >Print All TCasesServlet</a><br>
<a href="/admin/addTc?del" >Del All TCasesServlet</a><br>
</html>