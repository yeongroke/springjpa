
const geotiffLayer = new ol.layer.Image();

var map = "";

document.addEventListener("DOMContentLoaded", function(){
    openlayer_chart_overlay(129.5,36.6);
});

function map_reset(){
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
};




function openlayer_chart_overlay(set_Lon , set_Lat){
    var locaarr = [];
    var label_arr = new Array();
    var data_arr = new Array();
    var data_index = 0;

    var pnt = new ol.geom.Point(ol.proj.transform([set_Lon, set_Lat],
        'EPSG:4326', 'EPSG:3857'));
    var korea = pnt.getCoordinates();

    var myView = new ol.View({
        center: korea,
        zoom: 12
    }); //뷰 객체를 전역변수로 뺀다.

    var projection = ol.proj.get('EPSG:4326');

    var popup = new ol.Overlay({
        element: document.getElementById('popup')
    });

    var parsing = areaJsonData();  //읽어온 데이터 파싱

    var areare = areaCodeData();
    locaarr = [];
    for (var i = 0; i < areare.length; i++) {
        locaarr.push([parsing[i].j.Lon, parsing[i].j.Lat, parsing[i].j.Velocity]);
    }
    var sourceFeatures = new ol.source.Vector(),
        layerFeatures = new ol.layer.Vector({ source: sourceFeatures });

    for (var i = 0; i < locaarr.length; i++) {

        var indcolor = "";
        var rcolor = "";
        if (Number(locaarr[i][2]) > 3) {
            indcolor = 'rgba( 248, 105, 109 ,1.0)';
            rcolor = 'marker1';
        } else if (Number(locaarr[i][2]) > 2) {
            indcolor = 'rgba( 251, 189, 140 ,1.0)';
            rcolor = 'marker2';
        } else if (Number(locaarr[i][2]) > 1.5) {
            indcolor = 'rgba( 249, 229, 116 ,1.0)';
            rcolor = 'marker3';
        } else if (Number(locaarr[i][2]) > 1) {
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
            desc: areare[i],
            geometry: new ol.geom.Point(ol.proj.transform([locaarr[i][0], locaarr[i][1]], 'EPSG:4326', 'EPSG:3857'))
        });
        feature.setStyle(style1);

        sourceFeatures.addFeature(feature);
    }

    map = new ol.Map({
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            }),
            layerFeatures
        ],
        overlays: [popup],
        target: 'map',
        controls: ol.control.defaults({
            attributionOptions: ({
                collapsible: false
            })
        }),
        view: myView
    });
    var currZoom = map.getView().getZoom();
    map.on('moveend', function (e) {
        var newZoom = map.getView().getZoom();
        if (currZoom != newZoom) {
            console.log('zoom end, new zoom: ' + newZoom);
            currZoom = newZoom;
        }
    });
    /*
    var c1, c2, z1, z2;
    map1.on('moveend', function (e) {
        c1 = map1.getView().getCenter();
        z1 = map1.getView().getZoom();
        map.getView().setCenter(c1);
        map.getView().setZoom(z1+2);
        //map2.zoomTo(z1);
        e.preventDefault();
    });
    map.on('moveend', function (e) {
        c2 = map.getView().getCenter();
        z2 = map.getView().getZoom();
        map1.getView().setCenter(c2);
        map1.getView().setZoom(z2-2);
        //map1.zoomTo(z2);
    });
*/
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
}
var layers = {};
layers['satellite'] = new ol.layer.Tile({
    title: 'VWorld Gray Map',
    visible: true,
    type: 'base',
    source: new ol.source.XYZ({
        url: 'http://xdworld.vworld.kr:8080/2d/Satellite/201612/{z}/{x}/{y}.jpeg'
    })
});


function baseChange(data) {
    if (data == "satellite") {
        $("#base-layer").text("위성");
        $("#base-layer").attr("onclick", "baseChange('vworld')");

    } else {
        $("#base-layer").text("지도");
        $("#base-layer").attr("onclick", "baseChange('satellite')");
    }

    var layer = layers[data];
    if (layer) {
        layer.setOpacity(1);
        map.getLayers().setAt(0, layer);
    }
}

function areaJsonData() {
    var areajsondata = "";
    $.ajax({
        url: '../../data/case2.json',
        type: 'get',
        async: false,
        contentType: 'text',
        success: function (data) {
            console.log(data);
            var format = new ol.format.GeoJSON({   //포멧할 GeoJSON 객체 생성
                featureProjection: 'EPSG:3857'
            });
            areajsondata = format.readFeatures(data);
            console.log(areajsondata);
        }
    });
    return areajsondata;
}

function areaCodeData() {
    var areadata = [];
    $.ajax({
        url: '../../data/case1.json',
        type: 'get',
        async: false,
        contentType: 'text',
        success: function (data) {
            var format = new ol.format.GeoJSON({   //포멧할 GeoJSON 객체 생성
                featureProjection: 'EPSG:3857'
            });
            var parsing = format.readFeatures(data);  //읽어온 데이터 파싱
            let vectorSource = new ol.source.Vector({  //벡터의 구조를 파싱한 데이터로 넣기
                features: parsing
            });
            let vectorLayer = new ol.layer.Vector({ source: vectorSource });
            for (var i = 0; i < parsing.length; i++) {
                areadata.push(parsing[i].values_);
            }
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


