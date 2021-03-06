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
                <input type="file" multiple id="file" name="file" style="width:100%;" required/>
                <div id="preview"></div>
                <p></p>
            </div>
        </div>
        <div style="text-align:right;">
            <input onclick="uploadExcel()" value="업로드" style="display: inline-block;padding: 10px 25px;line-height: 18px;background: #4c4c4c;border: 1px solid #c4c4c4;border-radius: 3px;text-align: center;vertical-align: middle;box-sizing: border-box;color:#fff;cursor:pointer;"/>
        </div>
    </form>

<%--멀티 업로드--%>
<%--    html += "<tr id='fileTr_" + fIndex + "'>";--%>
<%--    html += "    <td class='left' >";--%>
<%--        html +=         fileName + " / " + fileSize + "MB "  + "<a href='#' onclick='deleteFile(" + fIndex + "); return false;' class='btn small bg_02'>삭제</a>"--%>
<%--        html += "    </td>"--%>
<%--    html += "</tr>"--%>
<%--<div>--%>
<%--    <form name="formUpload" id="uploadForm" enctype="multipart/form-data" method="post">--%>
<%--        <table class="table" width="100%" style="padding:10px;border: 1px solid #c4c4c4;border-radius:10px;vertical-align:middle;" id="dropZone">--%>
<%--            <tbody id="fileTableTbody">--%>
<%--            <tr>--%>
<%--                <td>--%>
<%--                    파일을 드래그 하세요--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </form>--%>
<%--</div>--%>
<%--    <div onclick="uploadFile(); return false;" class="btn bg_01" style="display: inline-block;padding: 10px 25px;line-height: 18px;background: #4c4c4c;border: 1px solid #c4c4c4;border-radius: 3px;text-align: center;vertical-align: middle;box-sizing: border-box;color:#fff;cursor:pointer;">파일 업로드</div>--%>
</div>

<div id="map" style="width:500px;height:400px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3859fd64ebba1e32b7d14ee609ba5fab"></script>
<script type="text/javascript">
    // const input = document.querySelector('input[type="file"]');
    // const preview = document.querySelector('.preview');
    // input.addEventListener('change',showExcelFile);

    const handler = {
        init() {
            const fileInput = document.querySelector('#file');
            const preview = document.querySelector('#preview');
            fileInput.addEventListener('change', () => {

                const files = Array.from(fileInput.files);

                files.forEach(file => {
                    preview.innerHTML += "<p id =" +file.lastModified+ ">" + file.name + "<button data-index=" + file.lastModified+ " class='file-remove'>X</button>"
                });
            });
        },

        removeFile: () => {
            document.addEventListener('click', (e) => {
                console.log(e.target.className);
                if(e.target.className !== 'file-remove') return;
                const removeTargetId = e.target.dataset.index;
                const removeTarget = document.getElementById(removeTargetId);
                const files = document.querySelector('#file').files;
                const dataTranster = new DataTransfer();


                Array.from(files)
                    .filter(file => file.lastModified != removeTargetId)
                    .forEach(file => {
                        dataTranster.items.add(file);
                    });

                document.querySelector('#file').files = dataTranster.files;


                removeTarget.remove();
            })
        }
    }

    handler.init();
    handler.removeFile();

    //엑셀파일명 노출
    // function showExcelFile() {
    //     const selectedFiles = input.files;
    //     console.log(input);
    //     console.log(selectedFiles);
    //     const list = document.createElement('ul');
    //     preview.appendChild(list);
    //
    //     let fIndex=0;
    //     for(const file of selectedFiles) {
    //         const listItem = document.createElement('li');
    //         let file_id = "file_id_"+ fIndex;
    //         listItem.setAttribute("id",file_id);
    //         if(checkFileType(file)) {
    //             console.log(file);
    //             console.log(file.name);
    //             const summary = document.createElement('div');
    //             summary.textContent = "파일명 : " + file.name;
    //
    //             // "<a href='#' onclick='deleteFile(" + fIndex + "); return false;' class='btn small bg_02'>삭제</a>
    //             let html = '삭제';
    //             const deleteAtag = document.createElement('a');
    //             deleteAtag.textContent = html;
    //             deleteAtag.onclick =
    //             function deleteFile(fIndex) {
    //                 // 전체 파일 사이즈 수정
    //                 console.log(totalFileSize);
    //                 totalFileSize -= fileSizeList[fIndex];
    //
    //                 // 파일 배열에서 삭제
    //                 console.log(fileList);
    //                 delete fileList[fIndex];
    //
    //                 // 파일 사이즈 배열 삭제
    //                 console.log(fileSizeList);
    //                 delete fileSizeList[fIndex];
    //
    //                 // 업로드 파일 테이블 목록에서 삭제
    //                 let remeve_file_id = document.getElementById(file_id);
    //                 $(remeve_file_id).remove();
    //
    //                 //MDN Data Transfer FileList
    //                 const dataTransfer = new DataTransfer();
    //
    //                 Array.from(selectedFiles).filter(file => file.lastModified != fIndex)
    //                                         .forEach(file => {dataTransfer.items.add(file)
    //                                         });
    //                 document.querySelector('file').files = dataTransfer.files;
    //
    //             };
    //
    //             summary.appendChild(deleteAtag);
    //             listItem.appendChild(summary);
    //
    //         } else {
    //             alert('엑셀파일을 업로드해주세요.');
    //             return;
    //         }
    //         list.appendChild(listItem);
    //
    //         fIndex++;
    //     }
    // }

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
        let name = filePath.name
        let fileFormat = name.split(".");
        if (fileFormat.indexOf("xls") > -1) {
            return true;
        } else if (fileFormat.indexOf("xlsx") > -1) {
            return true;
        } else {
            return false;
        }
    }

    function validFileType(file){
        console.log(file.type);
        let fileTypes = ['xlsx','xls'];
        fileTypes.includes(file.type);
    }



    // // 파일 리스트 번호
    // var fileIndex = 0;
    // // 등록할 전체 파일 사이즈
    // var totalFileSize = 0;
    // // 파일 리스트
    // var fileList = new Array();
    // // 파일 사이즈 리스트
    // var fileSizeList = new Array();
    // // 등록 가능한 파일 사이즈 MB
    // var uploadSize = 50;
    // // 등록 가능한 총 파일 사이즈 MB
    // var maxUploadSize = 500;
    //
    // $(function () {
    //     fileDropDown();
    // });
    //
    // //파일 드롭 다운
    // function fileDropDown() {
    //     var dropZone = $("#dropZone");
    //
    //     dropZone.on('dragenter',function (e) {
    //         e.stopPropagation();
    //         e.preventDefault();
    //         //드롭 다운 영역 css
    //         dropZone.css('background-color','#E3F2FC')
    //
    //     });
    //
    //     dropZone.on('dragleave',function (e) {
    //         e.stopPropagation();
    //         e.preventDefault();
    //         //드롭 다운 영역 css
    //         dropZone.css('background-color','#FFFFFF')
    //
    //     });
    //
    //     dropZone.on('dragover',function (e) {
    //         e.stopPropagation();
    //         e.preventDefault();
    //         //드롭 다운 영역 css
    //         dropZone.css('background-color','#E3F2FC')
    //
    //     });
    //
    //     dropZone.on('drop',function (e) {
    //         e.stopPropagation();
    //         e.preventDefault();
    //         //드롭 다운 영역 css
    //         dropZone.css('background-color','#FFFFFF')
    //
    //         var files = e.originalEvent.dataTransfer.files;
    //         if(files != null){
    //             if(files.length < 1){
    //                 alert("폴더 업로드 불가");
    //                 return;
    //             }
    //             selectFile(files)
    //         }else {
    //             alert("ERROR");
    //         }
    //     });
    // }

    // // 파일 선택시
    // function selectFile(files){
    //     // 다중파일 등록
    //     if(files != null){
    //         for(var i = 0; i < files.length; i++){
    //             // 파일 이름
    //             var fileName = files[i].name;
    //             var fileNameArr = fileName.split("\.");
    //             // 확장자
    //             var ext = fileNameArr[fileNameArr.length - 1];
    //             // 파일 사이즈(단위 :MB)
    //             var fileSize = files[i].size / 1024 / 1024;
    //
    //             if($.inArray(ext, ['exe', 'bat', 'sh', 'java', 'jsp', 'html', 'js', 'css', 'xml']) >= 0){
    //                 // 확장자 체크
    //                 alert("등록 불가 확장자");
    //                 break;
    //             }else if(fileSize > uploadSize){
    //                 // 파일 사이즈 체크
    //                 alert("용량 초과\n업로드 가능 용량 : " + uploadSize + " MB");
    //                 break;
    //             }else{
    //                 // 전체 파일 사이즈
    //                 totalFileSize += fileSize;
    //
    //                 // 파일 배열에 넣기
    //                 fileList[fileIndex] = files[i];
    //
    //                 // 파일 사이즈 배열에 넣기
    //                 fileSizeList[fileIndex] = fileSize;
    //
    //                 // 업로드 파일 목록 생성
    //                 addFileList(fileIndex, fileName, fileSize);
    //
    //                 // 파일 번호 증가
    //                 fileIndex++;
    //             }
    //         }
    //     }else{
    //         alert("ERROR");
    //     }
    // }

    // // 업로드 파일 목록 생성
    // function addFileList(fIndex, fileName, fileSize){
    //     var html = "";
    //     html += "<tr id='fileTr_" + fIndex + "'>";
    //     html += "    <td class='left' >";
    //     html +=         fileName + " / " + fileSize + "MB "  + "<a href='#' onclick='deleteFile(" + fIndex + "); return false;' class='btn small bg_02'>삭제</a>";
    //     html += "    </td>";
    //     html += "</tr>";
    //
    //     $('#fileTableTbody').append(html);
    // }

    // 업로드 파일 삭제
    // function deleteFile(fIndex){
    //     // 전체 파일 사이즈 수정
    //     totalFileSize -= fileSizeList[fIndex];
    //
    //     // 파일 배열에서 삭제
    //     delete fileList[fIndex];
    //
    //     // 파일 사이즈 배열 삭제
    //     delete fileSizeList[fIndex];
    //
    //     // 업로드 파일 테이블 목록에서 삭제
    //     $("#fileTr_" + fIndex).remove();
    // }

    // // 파일 등록
    // function uploadFile(){
    //     // 등록할 파일 리스트
    //     var uploadFileList = Object.keys(fileList);
    //
    //     // 파일이 있는지 체크
    //     if(uploadFileList.length == 0){
    //         // 파일등록 경고창
    //         alert("파일이 없습니다.");
    //         return;
    //     }
    //
    //     // 용량을 500MB를 넘을 경우 업로드 불가
    //     if(totalFileSize > maxUploadSize){
    //         // 파일 사이즈 초과 경고창
    //         alert("총 용량 초과\n총 업로드 가능 용량 : " + maxUploadSize + " MB");
    //         return;
    //     }
    //
    //     if(confirm("등록 하시겠습니까?")){
    //         // 등록할 파일 리스트를 formData로 데이터 입력
    //         var form = $('#uploadForm');
    //         var formData = new FormData(form);
    //         for(var i = 0; i < uploadFileList.length; i++){
    //             formData.append('files', fileList[uploadFileList[i]]);
    //         }
    //
    //         $.ajax({
    //             url:"/uploadActionExcel/multi",
    //             data:formData,
    //             type:'POST',
    //             enctype:'multipart/form-data',
    //             processData:false,
    //             contentType:false,
    //             dataType:'json',
    //             cache:false,
    //             success:function(result){
    //                 if(result.data.length > 0){
    //                     alert("성공");
    //                     location.reload();
    //                 }else{
    //                     alert("실패");
    //                     location.reload();
    //                 }
    //             }
    //         });
    //     }
    // }

    const uploadExcel = async () =>{
        let formData = new FormData();
        let fileField2 = document.querySelector('input[type="file"]');

        for(const item of  fileField2.files){
            console.log(item);
            formData.append('file',item);
        }

        console.log(fileField2.files);

        console.log(formData.getAll('file'));


        try {
            const response = await fetch("http://localhost:8080/uploadExcel", {
                method: 'POST'
                , body: formData
            });

            const json = await response.json();
            let alertMessage = "";
            if(json.result_code === 's0000'){
                console.log(json);
                for (const data of json.result_data) {
                    if(data.result_code ==='s0000') {
                        alertMessage += "파일명 : " + data.result_data + " : ";
                        alertMessage += data.result_message + "\n";
                    } else {
                        alertMessage += "파일명 : " + data.result_data + "\n";
                        alertMessage += data.result_message + "\n";
                    }
                }

                alert(alertMessage);

                location.reload();
            } else {
                alert(json.result_message);
            }

        } catch (error) {
            console.log(error);
        }
    }

    //카카오 지도
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
