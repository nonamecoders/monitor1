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
        //파일 업로드 했는지 체크
        function checkForm() {
            if (formUpload.file.value == "") {
                alert("파일을 업로드해주세요.");
                return false;
            } else if (!checkFileType(formUpload.file.value)) {
                alert("엑셀파일만 업로드 해주세요.");
                return false;
            }
            return true;
        }

        //엑셀(xls, xlsx) 파일 체크
        function checkFileType(filePath) {

            var fileFormat = filePath.split(".");
            if (fileFormat.indexOf("xls") > -1) {
                return true;
            } else if (fileFormat.indexOf("xlsx") > -1) {
                return true;
            } else {
                return false;
            }

        }

        // 파일 리스트 번호
        var fileIndex = 0;
        // 등록할 전체 파일 사이즈
        var totalFileSize = 0;
        // 파일 리스트
        var fileList = new Array();
        // 파일 사이즈 리스트
        var fileSizeList = new Array();
        // 등록 가능한 파일 사이즈 MB
        var uploadSize = 50;
        // 등록 가능한 총 파일 사이즈 MB
        var maxUploadSize = 500;

        $(function () {
            fileDropDown();
        });

        //파일 드롭 다운
        function fileDropDown() {
            var dropZone = $("#dropZone");

            dropZone.on('dragenter',function (e) {
                e.stopPropagation();
                e.preventDefault();
                //드롭 다운 영역 css
                dropZone.css('background-color','#E3F2FC')
                
            });

            dropZone.on('dragleave',function (e) {
                e.stopPropagation();
                e.preventDefault();
                //드롭 다운 영역 css
                dropZone.css('background-color','#FFFFFF')

            });

            dropZone.on('dragover',function (e) {
                e.stopPropagation();
                e.preventDefault();
                //드롭 다운 영역 css
                dropZone.css('background-color','#E3F2FC')

            });

            dropZone.on('drop',function (e) {
                e.stopPropagation();
                e.preventDefault();
                //드롭 다운 영역 css
                dropZone.css('background-color','#FFFFFF')

                var files = e.originalEvent.dataTransfer.files;
                if(files != null){
                    if(files.length < 1){
                        alert("폴더 업로드 불가");
                        return;
                    }
                    selectFile(files)
                }else {
                    alert("ERROR");
                }
            });
        }

        // 파일 선택시
        function selectFile(files){
            // 다중파일 등록
            if(files != null){
                for(var i = 0; i < files.length; i++){
                    // 파일 이름
                    var fileName = files[i].name;
                    var fileNameArr = fileName.split("\.");
                    // 확장자
                    var ext = fileNameArr[fileNameArr.length - 1];
                    // 파일 사이즈(단위 :MB)
                    var fileSize = files[i].size / 1024 / 1024;

                    if($.inArray(ext, ['exe', 'bat', 'sh', 'java', 'jsp', 'html', 'js', 'css', 'xml']) >= 0){
                        // 확장자 체크
                        alert("등록 불가 확장자");
                        break;
                    }else if(fileSize > uploadSize){
                        // 파일 사이즈 체크
                        alert("용량 초과\n업로드 가능 용량 : " + uploadSize + " MB");
                        break;
                    }else{
                        // 전체 파일 사이즈
                        totalFileSize += fileSize;

                        // 파일 배열에 넣기
                        fileList[fileIndex] = files[i];

                        // 파일 사이즈 배열에 넣기
                        fileSizeList[fileIndex] = fileSize;

                        // 업로드 파일 목록 생성
                        addFileList(fileIndex, fileName, fileSize);

                        // 파일 번호 증가
                        fileIndex++;
                    }
                }
            }else{
                alert("ERROR");
            }
        }

        // 업로드 파일 목록 생성
        function addFileList(fIndex, fileName, fileSize){
            var html = "";
            html += "<tr id='fileTr_" + fIndex + "'>";
            html += "    <td class='left' >";
            html +=         fileName + " / " + fileSize + "MB "  + "<a href='#' onclick='deleteFile(" + fIndex + "); return false;' class='btn small bg_02'>삭제</a>"
            html += "    </td>"
            html += "</tr>"

            $('#fileTableTbody').append(html);
        }

        // 업로드 파일 삭제
        function deleteFile(fIndex){
            // 전체 파일 사이즈 수정
            totalFileSize -= fileSizeList[fIndex];

            // 파일 배열에서 삭제
            delete fileList[fIndex];

            // 파일 사이즈 배열 삭제
            delete fileSizeList[fIndex];

            // 업로드 파일 테이블 목록에서 삭제
            $("#fileTr_" + fIndex).remove();
        }

        // 파일 등록
        function uploadFile(){
            // 등록할 파일 리스트
            var uploadFileList = Object.keys(fileList);

            // 파일이 있는지 체크
            if(uploadFileList.length == 0){
                // 파일등록 경고창
                alert("파일이 없습니다.");
                return;
            }

            // 용량을 500MB를 넘을 경우 업로드 불가
            if(totalFileSize > maxUploadSize){
                // 파일 사이즈 초과 경고창
                alert("총 용량 초과\n총 업로드 가능 용량 : " + maxUploadSize + " MB");
                return;
            }

            if(confirm("등록 하시겠습니까?")){
                // 등록할 파일 리스트를 formData로 데이터 입력
                var form = $('#uploadForm');
                var formData = new FormData(form);
                for(var i = 0; i < uploadFileList.length; i++){
                    formData.append('files', fileList[uploadFileList[i]]);
                }

                $.ajax({
                    url:"/uploadActionExcel/multi",
                    data:formData,
                    type:'POST',
                    enctype:'multipart/form-data',
                    processData:false,
                    contentType:false,
                    dataType:'json',
                    cache:false,
                    success:function(result){
                        if(result.data.length > 0){
                            alert("성공");
                            location.reload();
                        }else{
                            alert("실패");
                            location.reload();
                        }
                    }
                });
            }
        }

        function gisExcelUpload(){
            var upFile = $('#file')[0];
            // upFile.addEventListener('change', function() {
                var form = $('#formUpload')[0];
                var frmData = new FormData(form);

                $.ajax({
                    enctype: 'multipart/form-data',
                    type: 'POST',
                    url: '/uploadExcel',
                    // url: '/uploadActionExcel',
                    processData: false,
                    contentType: false,
                    cache: false,
                    data: frmData,
                    dataType: 'json',
                    async: false,
                    success: function(data) {
                        console.log(data);
                        var code = data.code;
                        if(code == "S0000") {
                            alert("upload success.");
                            location.reload();
                        } else {
                            alert("upload fail.");
                        }
                    },
                    error: function(e,data) {
                        console.log(data);
                        alert('파일업로드 실패');
                        location.href ="/";
                    }
                });
            // });
        }
    </script>
</head>
<body>
<div>
    <h1> 연결 된 DB : ${linkedServer} </h1>
</div>
<div style="padding:20px">
    <h2>step1. gis_pizzahut_border_history DB 입력</h2>
<%-- name to id, action="uploadActionExcel" method="post" "  --%>
    <form id="formUpload" enctype="multipart/form-data">
        <div style="margin:20px 0;">
            <div style="padding:10px;border: 1px solid #c4c4c4;border-radius:10px;vertical-align:middle;">
                <label style="display:inline-block;margin-bottom:10px;font-size:10pt;vertical-align:middle;">엑셀파일 업로드 : </label>
                <input type="file" id="file" name="file" style="width:100%;" />
            </div>
        </div>
        <div style="text-align:right;">
            <input onclick="gisExcelUpload()" value="업로드" style="display: inline-block;padding: 10px 25px;line-height: 18px;background: #4c4c4c;border: 1px solid #c4c4c4;border-radius: 3px;text-align: center;vertical-align: middle;box-sizing: border-box;color:#fff;cursor:pointer;"/>
        </div>
    </form>
<%--멀티 업로드--%>
<div>
    <form name="formUpload" id="uploadForm" enctype="multipart/form-data" method="post">
        <table class="table" width="100%" style="padding:10px;border: 1px solid #c4c4c4;border-radius:10px;vertical-align:middle;" id="dropZone">
            <tbody id="fileTableTbody">
            <tr>
                <td>
                    파일을 드래그 하세요
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
    <div onclick="uploadFile(); return false;" class="btn bg_01" style="display: inline-block;padding: 10px 25px;line-height: 18px;background: #4c4c4c;border: 1px solid #c4c4c4;border-radius: 3px;text-align: center;vertical-align: middle;box-sizing: border-box;color:#fff;cursor:pointer;">파일 업로드</div>
</div>

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
