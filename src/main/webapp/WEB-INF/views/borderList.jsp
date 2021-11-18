<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="bootstrap.jsp" %>

    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List</title>
    </head>
    <body>

    <div class="container">
        <div class="col-xs-12">
            <table class="table table-hover">
                <tr>
                    <th>BRAND_CD</th>
                    <th>IDX</th>
                    <th>IS_VALID</th>
                    <th>REG_DATE</th>
                </tr>
                <c:forEach var="l" items="${list}">
                    <tr>
                        <td>${l.brand_cd}</td>
                        <td>${l.idx}</td>
                        <td>${l.is_valid}</td>
                        <td>${l.reg_date}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    </body>
    </html>