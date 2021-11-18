<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>매출전표 | 카카오 주문하기</title>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width">
    <meta name="format-detection" content="telephone=no">
    <link href="../images/meta/favicon/favicon_64x64.ico" rel="shortcut icon">
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">

</head>
<body>
<script>
    var error_message = '${error_message}';
    var brandCode = '${brandCode}';
    var channelCode = '${channelCode}';
    var data = '${data}';
    var cbPrice= '${cbPrice}';
    var channelOrderId = '${channelOrderId}';

    var url  = '${url}';

    if ( error_message.length > 0 ) {
        alert(error_message);
    } else {
        if ( data.length > 0 ) {
            location.replace(url+'&brandCode='+brandCode+'&channelCode='+channelCode+'&data=' + data);
        }
        else {
            alert('SPC 현금영수증 요청에 실패하였습니다.');

        }
    }

</script>
</body>
<%--url : ${url}--%>
<%--brandCode : ${brandCode}--%>
<%--channelCode : ${channelCode}--%>
<%--data : ${data}--%>
</html>