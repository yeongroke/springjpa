var geotiffLayer = new ol.layer.Image();
geotiffLayer.set('name' , 'geotiffLayer');
var map = "";
var layers = {};
layers['satellite'] = new ol.layer.Tile({
    title: 'VWorld Gray Map',
    visible: true,
    type: 'base',
    source: new ol.source.XYZ({
        url: 'http://xdworld.vworld.kr:8080/2d/Satellite/201612/{z}/{x}/{y}.jpeg'
    })
});
window.addEventListener('DOMContentLoaded', event => {

    map = new ol.Map({
        target: 'map',
        layers: [
            layers['vworld'] = new ol.layer.Tile({
                source: new ol.source.OSM()
            }),
            geotiffLayer
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([129.5,36.6]),
            zoom: 7
        })
    });

});


var currZoom = "";
var currCenter = "";
if(map != ""){
    currZoom = map.getView().getZoom();
    currCenter = map.getView().getCenter();
    map.on('moveend', function (e) {
        var newZoom = map.getView().getZoom();
        var newLonLat = map.getView().getCenter();
        if (currZoom != newZoom) {
            console.log('zoom end, new zoom: ' + newZoom);
            currZoom = newZoom;
        }
        if(currCenter != newLonLat){
            console.log('LonLat end, new LonLat: ' + newLonLat);
            currCenter = newLonLat;
        }
    });
}
function baseChange(data) {
    if (data == "satellite") {
        $("#base-layer").text("지도");
        $("#base-layer").attr("onclick", "baseChange('vworld')");

    } else {
        $("#base-layer").text("위성");
        $("#base-layer").attr("onclick", "baseChange('satellite')");
    }

    var layer = layers[data];
    if (layer) {
        layer.setOpacity(1);
        map.getLayers().setAt(0, layer);
    }
};

function map_Default_Set(set_Lon , set_Lat , zoom_Level) {
    map.getView().setCenter(returnLonLatSet(set_Lon , set_Lat ,"EPSG:4326" , "EPSG:3857").getCoordinates());
    map.getView().setZoom(zoom_Level);
};

function map_Chart_Overlay_Set(set_Lon , set_Lat , zoom_Level , jsonarr , dataJsonType) {
    var locaarr = [];
    var label_arr = new Array();
    var data_arr = new Array();
    var data_index = 0;

    var korea = returnLonLatSet(set_Lon , set_Lat ,"EPSG:4326" , "EPSG:3857").getCoordinates();

    var myView = new ol.View({
        center: korea,
        zoom: zoom_Level
    }); //뷰 객체를 전역변수로 뺀다.

    var popup = new ol.Overlay({
        element: document.getElementById('popup')
    });

    var jsonUrl = "../../data/case1.json";
    var areare = areaCodeData(jsonUrl);
    var sourceFeatures = new ol.source.Vector(),
        layerFeatures = new ol.layer.Vector({ source: sourceFeatures });
    let addIndex = (areare.length/100 >= 30) ? 10 : 1;
    for (var i = 0; i < areare.length; i+=addIndex) {
        var indcolor = "";
        var rcolor = "";
        if (Number(areare[i].values_.Velocity) > 3) {
            indcolor = 'rgba( 248, 105, 109 ,1.0)';
            rcolor = 'marker1';
        } else if (Number(areare[i].values_.Velocity) > 2) {
            indcolor = 'rgba( 251, 189, 140 ,1.0)';
            rcolor = 'marker2';
        } else if (Number(areare[i].values_.Velocity) > 1.5) {
            indcolor = 'rgba( 249, 229, 116 ,1.0)';
            rcolor = 'marker3';
        } else if (Number(areare[i].values_.Velocity) > 1) {
            indcolor = 'rgba( 223, 223, 125 ,1.0)';
            rcolor = 'marker4';
        } else {
            indcolor = 'rgba( 88, 185, 116 ,1.0)';
            rcolor = 'marker5';
        }
        var style1 = [
            new ol.style.Style({
                image: new ol.style.Icon(({
                    scale: 0.9,
                    rotateWithView: false,
                    anchor: [0.5, 1],
                    anchorXUnits: 'fraction',
                    opacity: 0,
                    src: '../../img/common/' + rcolor + '.png'
                })),
                text: new ol.style.Text({
                    text: '' + i + '',
                    scale: 0,
                    fill: new ol.style.Fill({
                        color: '#000000'
                    }),
                    offsetX: 0,
                    offsetY: -32,
                }),
                zIndex: 5
            }),
            new ol.style.Style({
                image: new ol.style.Circle({
                    radius: 5,
                    fill: new ol.style.Fill({
                        color: indcolor
                    }),
                    stroke: new ol.style.Stroke({
                        color: 'rgba(0,0,0,1)'
                    })
                })
            })
        ];
        var feature = new ol.Feature({
            type: 'click',
            desc: areare[i].values_,
            geometry: returnLonLatSet(areare[i].values_.Lon, areare[i].values_.Lat, 'EPSG:4326', 'EPSG:3857')
        });
        feature.setStyle(style1);

        sourceFeatures.addFeature(feature);
    }



    layerFeatures.set('name','layerFeatures');
    map.getView().setCenter(returnLonLatSet(set_Lon , set_Lat ,"EPSG:4326" , "EPSG:3857").getCoordinates());
    map.getView().setZoom(zoom_Level);
    map.addLayer(layerFeatures);
    map.addOverlay(popup);


    map.on('singleclick', function (evt) {
        label_arr = new Array();
        data_arr = new Array();
        data_index = 0;
        var f = map.forEachFeatureAtPixel(evt.pixel, function (ft, layer) {
            return ft;
        });
        if (evt.dragging) {
            return;
        }
        var feature = map.forEachFeatureAtPixel(map.getEventPixel(evt.originalEvent), function (feature) {
            return feature;
        });

        if (feature && f && f.get('type') == 'click') {
            var geometry = f.getGeometry();
            var coord = geometry.getCoordinates();
            data_arr.push(new Array(coord));
            var pop_count = 0;
            var cord_count = 0;

            for (var i = 0; i < data_index; i++) {
                if (data_arr[i][0][0] == coord[0] && data_arr[i][0][1] == coord[1]) {
                    cord_count++;
                    pop_count++;
                }
            }
            if (pop_count == 1) {
                data_arr.pop();
            }
            pop_count = 0;
            if (cord_count < 1) {
                data_arr[data_index].push(f.getStyle()[1].image_.fill_.color_);
                data_arr[data_index].push(f.getStyle()[0].text_.text_);
                for (var key in f.get('desc')) {
                    if (key.substring(0, 1) == "D") {
                        label_arr.push(key.substring(2));
                        data_arr[data_index].push(f.get('desc')[key]);
                        cord_count = 0;
                    }
                }
                data_index++;
            }
            $("#popup").empty();
            $("#popup").append("<canvas id='line-chart"+f.getStyle()[0].text_.text_+"'></canvas>");
            popup.setPosition([coord[0], coord[1]]);
            $('.chart_reset').show();
            $("#popup").show();
            if (cord_count < 1) {
                create_linechart(f.getStyle()[0].text_.text_, label_arr, data_arr);
            }
        } else {
            $("#popup").hide();
        }
    });
};


function baseChange(data) {
    if (data == "satellite") {
        $("#base-layer").text("지도");
        $("#base-layer").attr("onclick", "baseChange('vworld')");

    } else {
        $("#base-layer").text("위성");
        $("#base-layer").attr("onclick", "baseChange('satellite')");
    }

    var layer = layers[data];
    if (layer) {
        layer.setOpacity(1);
        map.getLayers().setAt(0, layer);
    }
};

// 각 tiff 컨텐츠 경계값 구분 값마다 다 달라서 어쩔수 없는 선택..
const contentBoundaryMinMaxValue = {
    "Sentinel-1_L1_GRD" : [ -16 , 0 ],
    "SWDI" : [ -10 , 0 ],
    "Soil_moisture_content" : [ 5 , 40 ],
    "LakeSoyang" : [ -3.14159 , 3.14159 ],
    "05" : [ 0 , 1],
    "10" : [ 0 , 1],
    "15" : [ 0 , 1],
    "LULC" : [ 0 , 1],
    "S1GRD" : [ 0 , 1],
    "S1APV" : [ 0 , 1]
};


async function onGeotiffLoaded(data) {
    var returnTiffValue = [];
    await data.ArrayBuf.then((redata) => {
        // 원시 이진 데이터 버퍼 파싱
        const tiff = GeoTIFF.parse(redata);
        const image = tiff.getImage();
        const rawBox = image.getBoundingBox();
        const box = [rawBox[0], rawBox[1] - (rawBox[3] - rawBox[1]), rawBox[2], rawBox[1]];
        const bands = image.readRasters();
        returnTiffValue.push(returnLonLatSet((rawBox[0]+rawBox[2]+Math.pow(9, 6))/2,(rawBox[1]*3-rawBox[3])/2,'EPSG:3857', 'EPSG:4326'));

        //create an offscreen canvas for rendering
        let the_canvas = document.createElement('canvas');
        var minValue = contentBoundaryMinMaxValue[data.DataType][0];
        var maxValue = contentBoundaryMinMaxValue[data.DataType][1];
        if (data.DataType == "Sentinel-1_L1_GRD") {
            for(let value of bands[0]){
                if(maxValue < value){
                    maxValue = value;
                }
                if(minValue > value && value != -9999){
                    minValue = value;
                }
            }
        } else if(data.DataType == "LakeSoyang") {
            maxValue = 3.14159;
            minValue = -3.14159;
        }
        let colorScale = 'greys';
        //흑백 tif파일은 bands.length == 1, RGB tif파일은 bands.length == 3(R,G,B)
        const plot = new plotty.plot({
            canvas: the_canvas,
            data: bands[0], width: image.getWidth(), height: image.getHeight(),
            domain: [minValue, maxValue], clampLow: true, clampHigh: true, colorScale: colorScale
        });
        plot.render();
        const imgSource = new ol.source.ImageStatic({
            url: the_canvas.toDataURL("image/png"),
            imageExtent: box,
            projection: 'EPSG:3857' //to enable on-the-fly raster reprojection
        });

        geotiffLayer.setSource(imgSource);
    });
    return returnTiffValue;
};

async function tiffLoad(fetchFileName , fileDataType) {
    var responObj = {};
    var returnLonLat = new Array();
    await fetch(fetchFileName).then(
        function (response) {
            if(response.status == 200){
                $("#file_name").text(fetchFileName.split("/").pop());
                geotiffLayer.setSource();
                responObj.ArrayBuf = response.arrayBuffer();
                responObj.DataType = fileDataType;
                return responObj;
            } else if(response.status == 404) {
                $("#file_name").text("");
                swal("자료가 없습니다.", "다른 날짜로 조회해주세요.");
                return response;
            } else {
                $("#file_name").text("");
                swal("표출 에러", "다른 날짜로 조회해주세요." +response.status);
                return response
            }
        }
    ).then(onGeotiffLoaded).then(returndata => {
            returnLonLat = returndata;
        }
    ).catch(function (error) {
        console.log("자료가 없습니다"+error);
    });
    return returnLonLat;
}

function returnLonLatSet(new_set_Lon , new_set_Lat , fromEPSG , toEPSG){
    var pnt = new ol.geom.Point(ol.proj.transform([new_set_Lon, new_set_Lat],
        fromEPSG , toEPSG));
    return pnt;
};

function areaJsonData() {
    var areajsondataValue = [];
    $.ajax({
        url: '../../data/case2.json',
        type: 'get',
        async: false,
        contentType: 'text',
        success: function (data) {
            var format = new ol.format.GeoJSON({   //포멧할 GeoJSON 객체 생성
                featureProjection: 'EPSG:3857'
            });
            var parsing = format.readFeatures(data);
            for(let value of parsing) {
                areajsondataValue.push(value.values_);
            }
        },error: function (jqXHR, textStatus, errorThrown) {

        }
    });
    return areajsondataValue;
}


function areaCodeData(jsonUrl) {
    var areadata = [];
    $.ajax({
        url: jsonUrl,
        type: 'get',
        async: false,
        contentType: 'text',
        success: function (data) {
            var format = new ol.format.GeoJSON({   //포멧할 GeoJSON 객체 생성
                featureProjection: 'EPSG:3857'
            });
            var parsing = format.readFeatures(data);  //읽어온 데이터 파싱

            areadata = parsing;
        }
    });
    return areadata;
}

function create_linechart(chart_Id,label_arr, data_arr) {
    var new_data = new Array();
    for (var j = 3; j < data_arr[0].length; j++) {
        new_data.push(data_arr[0][j]);
    }
    var lichart = new Chart(document.getElementById("line-chart"+chart_Id+""), {
        type: 'line',
        data: {
            labels: label_arr,
            datasets: [
                {
                    data: new_data,
                    label: data_arr[0][2],
                    borderColor: data_arr[0][1],
                    fill: false
                }
            ]
        },
        options: {
            title: {
                display: true,
                text: '시계열 범위 그래프',
            },
            legend: {
                position: 'right'
            },
            scales: {
                yAxes: [{
                    ticks: {
                        max: 25,
                        min: -25
                    }
                }]
            }
        }
    });
    for (var i = 1; i < data_arr.length; i++) {
        new_data = new Array();
        for (var j = 3; j < data_arr[i].length; j++) {
            new_data.push(data_arr[i][j]);
        }
        lichart.data.datasets.push({
            data: new_data,
            label: data_arr[i][2],
            borderColor: data_arr[i][1],
            fill: false
        });
        lichart.update();
    }
}

