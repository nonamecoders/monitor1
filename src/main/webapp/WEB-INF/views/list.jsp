<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>List</title>
    <%@ include file="bootstrap.jsp" %>
</head>
<body>
<h2> 주문 목록 </h2>
<div class="container">
    <table class="table table-hover">
        <form id=""searchForm name="searchForm" action="/list">
            <input type="text" title="시작일입력" name="start_date" id="start_date" value="${param.start_date}" placeholder="시작일 입력" required="required">
            <input type="text" title="종료일입력" name="end_date" id="end_date" value="${param.end_date}" placeholder="종료일 입력" required="required">
            <button type="submit" class="button">검색</button>
        </form>
        <tr>
            <th>브랜드코드</th>
            <th>브랜드명</th>
            <th>쿠폰코드</th>
            <th>할인율/할인금액</th>
            <th>전체주문건수</th>
            <th>정상주문건수</th>
            <th>취소주문건수</th>
        </tr>
        <c:forEach var="l" items="${list}">
                <td>${l.brand_cd}</td>
                <td>${l.brand_nm}</td>
                <td>${l.coupon_id}</td>
                <td>${l.discount}</td>
                <td>${l.total_order}</td>
                <td>${l.real_order}</td>
                <td>${l.cancel_order}</td>
            </tr>
        </c:forEach>

    </table>
</div>



</body>
</html>
