<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    /* 엑셀 파일 업로드 */
    String savePath = request.getRealPath("/") + "upload";

    out.print("파일 Upload 경로 : " + savePath);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>GIS | UTMK 변환</title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="map" style="width:500px;height:400px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3859fd64ebba1e32b7d14ee609ba5fab"></script>
<script>
    var polygonPath = [
        new kakao.maps.LatLng(33.45133510810506, 126.57159381623066),
        new kakao.maps.LatLng(33.44955812811862, 126.5713551811832),
        new kakao.maps.LatLng(33.449986291544086, 126.57263296172184),
        new kakao.maps.LatLng(33.450682513554554, 126.57321034054742),
        new kakao.maps.LatLng(33.451346760004206, 126.57235740081413)
    ];

    var container = document.getElementById('map');
    var options = {
        // center: new kakao.maps.LatLng(37.48786736066203, 126.76958909537207),
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
    };

    var map = new kakao.maps.Map(container, options);

    var polygon = new kakao.maps.Polygon({
        path:polygonPath, // 그려질 다각형의 좌표 배열입니다
        strokeWeight: 1, // 선의 두께입니다
        strokeColor: '#39DE2A', // 선의 색깔입니다
        strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'longdash', // 선의 스타일입니다
        fillColor: '#A2FF99', // 채우기 색깔입니다
        fillOpacity: 0.7 // 채우기 불투명도 입니다
    });

    // 지도에 다각형을 표시합니다
    polygon.setMap(map);
</script>

</body>
</html>
