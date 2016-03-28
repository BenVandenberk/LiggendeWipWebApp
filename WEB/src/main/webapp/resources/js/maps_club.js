$(function () {
    google.maps.event.addDomListener(window, 'load', initAutocomplete);
});

function initAutocomplete() {
    var brusselLat = 50.850340;
    var brusselLng = 4.351710;

    var kampLat = Number($("#frm_kamppagina\\:tabs\\:input_lat").val());
    var kampLng = Number($("#frm_kamppagina\\:tabs\\:input_lng").val());

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

    // Create the search box and link it to the UI element.
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });


    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function (place) {
            var icon = {
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(25, 25)
            };

            // Create a marker for each place.
            markers.push(new google.maps.Marker({
                map: map,
                icon: icon,
                title: place.name,
                position: place.geometry.location
            }));

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }

            // EIGEN CODE
            $("#frm_kamppagina\\:tabs\\:input_lat").val(place.geometry.location.lat());
            $("#frm_kamppagina\\:tabs\\:input_lng").val(place.geometry.location.lng());
        });
        map.fitBounds(bounds);
    });
}

