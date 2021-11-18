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
<style>
    .area {
        position: absolute;
        background: #fff;
        border: 1px solid #888;
        border-radius: 3px;
        font-size: 12px;
        top: -5px;
        left: 15px;
        padding:2px;
    }

    .info {
        font-size: 12px;
        padding: 5px;
    }
    .info .title {
        font-weight: bold;
    }
</style>
<body>

<div id="map" style="width:800px;height:600px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3859fd64ebba1e32b7d14ee609ba5fab"></script>
<script>

    // 지도에 폴리곤으로 표시할 영역데이터 배열입니다
    var areas = [];
    <c:forEach items="${poly}" var="item">
    var arr = new Array();

    <c:forEach items="${item.point}" var="item2">
    arr.push(
        new kakao.maps.LatLng(${item2.point_lat}, ${item2.point_long})
    )
    </c:forEach>
    areas.push({
        name : "${item.idx}",
        path : arr

    });
    </c:forEach>


    //pizzahut
    // var areas_pizzahut = [];
    <c:forEach items="${poly2}" var="item4">
    var arr2 = new Array();

    <c:forEach items="${item4.point}" var="item3">
    arr2.push(
        new kakao.maps.LatLng(${item3.point_lat}, ${item3.point_long})
    )
    </c:forEach>
    areas.push({
        name : "${item4.idx}" + "(wgs84->katech->wgs84)",
        path : arr2

    });
    </c:forEach>

    // areas.push({
    //     name: "0002A_WGS84",
    //     path :[
    //         new kakao.maps.LatLng(37.887834, 127.749568)
    //         ,new kakao.maps.LatLng(37.887203, 127.749618)
    //         ,new kakao.maps.LatLng(37.886558, 127.746292)
    //         ,new kakao.maps.LatLng(37.88728, 127.744651)
    //         ,new kakao.maps.LatLng(37.888659, 127.743108)
    //         ,new kakao.maps.LatLng(37.889277, 127.749366)
    //         ,new kakao.maps.LatLng(37.88955, 127.751768)
    //         ,new kakao.maps.LatLng(37.887969, 127.751024)
    //         ,new kakao.maps.LatLng(37.887834, 127.749568)
    //     ]
    // });

    var center_point_lat = ${center.center_lat};
    var center_point_long = ${center.center_long};
    <%--var center_point_lat = ${37.889277};--%>
    <%--var center_point_long = ${127.749568};--%>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(center_point_lat, center_point_long), // 지도의 중심좌표
            level: 7, // 지도의 확대 레벨
            disableDoubleClickZoom : true //더블클릭 확대 가능 여부 (true : 불가, false : 가능)
        };

    var map = new kakao.maps.Map(mapContainer, mapOption),
        customOverlay = new kakao.maps.CustomOverlay({}),
        infowindow = new kakao.maps.InfoWindow({removable: true});

    // 지도에 영역데이터를 폴리곤으로 표시합니다
    for (var i = 0, len = areas.length; i < len; i++) {
        displayArea(areas[i]);
    }

    // 다각형을 생상하고 이벤트를 등록하는 함수입니다
    function displayArea(area) {

        // 다각형을 생성합니다
        var polygon = new kakao.maps.Polygon({
            map: map, // 다각형을 표시할 지도 객체
            path: area.path,
            strokeWeight: 2,
            strokeColor: '#004c80',
            strokeOpacity: 0.8,
            fillColor: '#fff',
            fillOpacity: 0.7
        });

        // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
        // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
        kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
            polygon.setOptions({fillColor: '#09f'});

            customOverlay.setContent('<div class="area">' + area.name + '</div>');

            customOverlay.setPosition(mouseEvent.latLng);
            customOverlay.setMap(map);
        });

        // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
        kakao.maps.event.addListener(polygon, 'mousemove', function(mouseEvent) {

            customOverlay.setPosition(mouseEvent.latLng);
        });

        // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
        // 커스텀 오버레이를 지도에서 제거합니다
        kakao.maps.event.addListener(polygon, 'mouseout', function() {
            polygon.setOptions({fillColor: '#fff'});
            customOverlay.setMap(null);
        });
        // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
        kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
            var content = '<div class="info">' +
                '   <div class="title">' + area.name + '</div>' +
                '   <div class="size">총 면적 : 약 ' + Math.floor(polygon.getArea()) + ' m<sup>2</sup></area>' +
                '</div>';

            infowindow.setContent(content);
            infowindow.setPosition(mouseEvent.latLng);
            infowindow.setMap(map);
        });

    }

</script>
<div class="container">
    <div class="col-xs-12">
        <table class="table table-hover">
            <tr>
                <th>BRAND_CD</th>
                <th>IDX</th>
                <th>POLY</th>
                <th>IS_VALID</th>
                <th>REG_DATE</th>
            </tr>
            <c:forEach var="l" items="${list}">
                <tr>
                    <td>${l.brand_cd}</td>
                    <td>${l.idx}</td>
                    <td>${l.poly}</td>
                    <td>${l.is_valid}</td>
                    <td>${l.reg_date}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>