$(function () {
    google.maps.event.addDomListener(window, 'load', initAutocomplete);
});

function initAutocomplete() {
    var brusselLat = 50.850340;
    var brusselLng = 4.351710;

    var kampLat = Number($("#frm\\:input_lat").val());
    var kampLng = Number($("#frm\\:input_lng").val());

    var lat, lng;
    if (kampLat != 0 && kampLng != 0) {
        lat = kampLat;
        lng = kampLng;
    } else {
        lat = brusselLat;
        lng = brusselLng;
    }

    var map = new google.maps.Map(document.getElementById('googleMap'), {
        center: new google.maps.LatLng(lat, lng),
        zoom: 9,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });


    var markers = [];
    if (kampLat != 0 && kampLng != 0) {
        markers.push(new google.maps.Marker({
            map: map,
            position: new google.maps.LatLng({lat: lat, lng: lng})
        }));
    }

}

