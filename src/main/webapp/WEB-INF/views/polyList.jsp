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

    .area2 {
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

<div id style="width: 100% ">
    <div id="map" style="width: 600px;height:400px;float:left;"></div>
    <div id="map2" style="width: 600px;height:400px;float:left;padding: 10px;"></div>
    <div>
        <h3> 제거할 권역 리스트 </h3>
        <ul id="removeIdx">
        </ul>
        <div onclick="deleteGis()" style="display: inline-block;padding: 10px 25px;line-height: 18px;background: #4c4c4c;border: 1px solid #c4c4c4;border-radius: 3px;text-align: center;vertical-align: middle;box-sizing: border-box;color:#fff;cursor:pointer;"> 제거 </div>
    </div>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3859fd64ebba1e32b7d14ee609ba5fab"></script>
<script>

    let setA = new Set();
    let setB = new Set();
    //등록한 상권
    var areas = [];
    <c:forEach items="${polyListNew}" var="polyListNew">
    setA.add('${polyListNew.idx}');
    var arr = new Array();
        <c:forEach items="${polyListNew.point}" var="pointNew">
        arr.push(
            new kakao.maps.LatLng(${pointNew.point_lat}, ${pointNew.point_long})
        )
    </c:forEach>

    areas.push({
        name : "${polyListNew.idx}",
        path : arr

    });
    </c:forEach>

    //과거 상권
    var areas2 = [];
    <c:forEach items="${polyListOld}" var="polyListOld">
    // 제거상권 리스트
    setB.add('${polyListOld.idx}');
    var arr2 = new Array();
    <c:forEach items="${polyListOld.point}" var="pointOld">
        arr2.push(
            new kakao.maps.LatLng(${pointOld.point_lat}, ${pointOld.point_long})
        )
    </c:forEach>

    areas2.push({
        name : "${polyListOld.idx}",
        path : arr2

    });
    </c:forEach>

    // 제거할 상권 리스트
    let differenceSet = new Set(
        [...setB].filter(v => !setA.has(v))
    );

    let indexRemove = 0;
    for (let value of differenceSet) {
        let list = document.createElement("li");
        list.id= 'li'+indexRemove;
        let node = document.createTextNode(value);

        // let button = document.createElement("button");
        // let buttonText = document.createTextNode("삭제");
        // // button.id = 'id_'+indexRemove;
        // button.onclick = function() {
        //     alert("삭제된 권역 idx : " + value);
        //     document.getElementById(list.id).remove();
        // }
        // button.appendChild(buttonText);

        list.appendChild(node);
        // list.appendChild(button);

        let element = document.getElementById("removeIdx");
        element.appendChild(list);

        indexRemove++;
    }

    // 상권 제거 리스트 post
    const deleteGis= async () => {

        let brand_cd =$('#brand_cd').val();
        let idxList= [];
        $('#removeIdx li').each(function (){
            console.log($(this).text());
            idxList.push($(this).text());
        });
        try {
            const response = fetch("http://localhost:8080/deleteGis", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    brand_cd: brand_cd,
                    idx: idxList
                })
            });

            const json = await response.json();
            if(json.result_code==='s0000'){
                alert("상권 제거 성공");
               location.reload();
            } else {
                alert(json.result_message);
            }

        } catch (error) {
            console.log(error);
        }
    }

    let center_point_lat = ${center.center_lat};
    let center_point_long = ${center.center_long};

    console.log(center_point_lat);
    console.log(center_point_long);

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

    var mapContainer2 = document.getElementById('map2'), // 지도를 표시할 div
        mapOption2 = {
            center: new kakao.maps.LatLng(center_point_lat, center_point_long), // 지도의 중심좌표
            level: 7, // 지도의 확대 레벨
            disableDoubleClickZoom : true //더블클릭 확대 가능 여부 (true : 불가, false : 가능)
        };

    var map2 = new kakao.maps.Map(mapContainer2, mapOption2),
        customOverlay = new kakao.maps.CustomOverlay({}),
        infowindow = new kakao.maps.InfoWindow({removable: true});

    var areas3=[];
    // 지도에 영역데이터를 폴리곤으로 표시합니다 (구 상권)
    for (var i = 0, len = areas2.length; i < len; i++) {
            if(differenceSet.has(areas2[i].name)){
                areas3.push(areas[i]);
            }
            displayArea2(areas2[i]);
    }

    for(let value of differenceSet){
        displayArea2(areas3)
    }

    // 다각형을 생상하고 이벤트를 등록하는 함수입니다
    function displayArea2(area) {
        if(!differenceSet.has(area.name)){
            var polygon2 = new kakao.maps.Polygon({
                map: map2, // 다각형을 표시할 지도 객체
                path: area.path,
                strokeWeight: 2,
                strokeColor: '#004c80',
                strokeOpacity: 0.8,
                fillColor: '#fff',
                fillOpacity: 0.7
            });
        } else {
            // 다각형을 생성합니다
            var polygon2 = new kakao.maps.Polygon({
                map: map2, // 다각형을 표시할 지도 객체
                path: area.path,
                strokeWeight: 2,
                strokeColor: '#ff0000',
                strokeOpacity: 0.8,
                fillColor: '#fff',
                fillOpacity: 0.7,
                zIndex : 2
            });
        }

        // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
        // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
        kakao.maps.event.addListener(polygon2, 'mouseover', function(mouseEvent) {
            polygon2.setOptions({fillColor: '#09f'});

            customOverlay.setContent('<div class="area2">' + area.name + '</div>');

            customOverlay.setPosition(mouseEvent.latLng);
            customOverlay.setMap(map2);
        });

        // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
        kakao.maps.event.addListener(polygon2, 'mousemove', function(mouseEvent) {

            customOverlay.setPosition(mouseEvent.latLng);
        });

        // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
        // 커스텀 오버레이를 지도에서 제거합니다
        kakao.maps.event.addListener(polygon2, 'mouseout', function() {
            polygon2.setOptions({fillColor: '#fff'});
            customOverlay.setMap(null);
        });
        // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
        kakao.maps.event.addListener(polygon2, 'click', function(mouseEvent) {
            var content = '<div class="info">' +
                '   <div class="title">' + area.name + '</div>' +
                '   <div class="size">총 면적 : 약 ' + Math.floor(polygon2.getArea()) + ' m<sup>2</sup></area>' +
                '</div>';

            infowindow.setContent(content);
            infowindow.setPosition(mouseEvent.latLng);
            infowindow.setMap(map2);
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
    <div>
        <input type="hidden" id="brand_cd" value="${list.get(0).brand_cd}"/>
    </div>
</div>
</body>
</html>