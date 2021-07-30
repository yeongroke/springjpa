var layer = new ol.layer.Tile({
    source : new ol.source.OSM()
});

var map = "";
var arrindex = 0;
$.ajax({
    url: '../../data/ALL_SIGUNGU.json',
    type: 'get',
    contentType: 'text',
    success: function (data) {
        var format = new ol.format.GeoJSON({   //포멧할 GeoJSON 객체 생성
            featureProjection:'EPSG:3857'
        });
        var parsing = format.readFeatures(data);  //읽어온 데이터 파싱
        var source = new ol.source.Vector({  //벡터의 구조를 파싱한 데이터로 넣기
            features : parsing
        });
        var geoVector = new ol.layer.Vector({ //벡터 레이어 생성
            source : source,
            style: (feature, resolution) => {
                // 모든 지역 이름
                let name = feature.j.adm_nm;
                // 선색상
                var indcolor = '';
                // 선채우기색상
                var rgbaco = '';

                if(arrindex % 10 <= 9 && arrindex % 10 >= 6){
                    indcolor = '#ff0000';
                    rgbaco = 'rgba( 255, 0, 0 ,0.2)';
                }else if(arrindex % 10 <= 5 && arrindex % 10 > 3){
                    indcolor = '#fa6464';
                    rgbaco = 'rgba( 250, 100, 100 ,0.2)';
                }else{
                    indcolor = '#66ff33';
                    rgbaco = 'rgba( 102, 255, 51 ,0.2)';
                }
                arrindex ++;
                return new ol.style.Style({
                    stroke: new ol.style.Stroke({ // 선 스타일
                        color: indcolor,
                        opacity: 1,
                        width: 1
                    }), fill: new ol.style.Fill({  //채우기 스타일
                        color: rgbaco,
                        opacity: 1
                    }),
                    text: new ol.style.Text({  //텍스트 스타일
                        text: name,
                        textAlign: 'center',
                        font: '15px roboto,sans-serif'
                    })
                })
            }
        });

        map = new ol.Map({
            target: 'map',
            layers: [
                layers['vworld'] = new ol.layer.Tile({
                    source: new ol.source.OSM()
                }),geoVector
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([127.5,36.6]),
                zoom: 5
            })
        });
        /*
        // 각 행정구역 마우스 오버시 하이라이팅
        // 마우스 오버시 스타일 지정
        var selectPointerMove = new ol.interaction.Select({
            condition: ol.events.condition.pointerMove,
            style: new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: 'blue',
                    width: 1
                }),
                fill: new ol.style.Fill({
                    color: 'rgba(60,225,251,0.6)'
                })
            })
        });
        // interaction 추가
        map.addInteraction(selectPointerMove);
        */


        var currZoom = map.getView().getZoom();
        map.on('moveend', function(e) {
            var newZoom = map.getView().getZoom();
            //console.log(newZoom);
            if (currZoom != newZoom) {
                console.log('zoom end, new zoom: ' + newZoom);
                currZoom = newZoom;
            }
        });
    }
});

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
