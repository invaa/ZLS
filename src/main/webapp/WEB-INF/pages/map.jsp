<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet" />
    <script src="http://maps.google.com/maps/api/js?sensor=true"></script>
</head>
<body>

<div id="zipCode">Zip code:</div>
<div id="state">State:</div>
<div id="cityName">City:</div>

<div class="container">
    <h2>Zip Locator Service</h2>
    <p>Provide the address with zip to pin (enter two capital letters for state)</p>
    <p>example: Aguada PR 00602</p>

    <div>
        <input type="text" placeholder="enter your address" id="search-string" value="">
		<span>
			<button id="button-search" type="button">Pin</button>
		</span>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/scripts/jquery-1.8.2.min.js" />"></script>

<script>

$(document).ready(function() {

    //first important values, declaring as global variables
    var zipCode = ""; //first number of 5 symbols
    var state = ""; //first validated state
    var cityName = ""; //first string streak of two or more characters that is not state, atfer beginning of the search query and before zip or state

    $("#button-search").click(function() {

        updateMap(); //zipCode, state, cityName are global variables

    });

    var lastEditedWordIndex = -1;
    var wordsArray = new Array();

    $("#search-string").keyup(function() {
        zipCode = ""; //first number of 5 symbols
        state = ""; //first validated state
        cityName = ""; //first string streak of two or more characters that is not state, after beginnig of the search query and before zip or state
        document.getElementById("zipCode").innerHTML = "Zip code:";
        document.getElementById("state").innerHTML = "State:";
        document.getElementById("cityName").innerHTML = "City:";

        var currentWordIndex = -1;
        var currentWordsArray = new Array();

        var searchQueryString = $("#search-string").val();
        // Create regular expression pattern with a global flag.
        var re = /\w+/g;

        document.getElementById("result").innerHTML = "";

        var isFirstZipInQuery = true;
        var isFirstStateInQuery = true;
        var isFirstCityInQuery = true;

        // Get the next word, starting at the position of lastindex.
        var arr;
        while ((arr = re.exec(searchQueryString)) != null)
        {
            document.getElementById("result").innerHTML += ("<br />");

            var value = arr[0];
            currentWordsArray.push(value);

            document.getElementById("result").innerHTML += (value);
            if (isZipCode(value)) {
                //document.getElementById("result").innerHTML += " - zip code";
                if (value.length == 5 && isFirstZipInQuery) {
                    zipCode = value;
                    document.getElementById("zipCode").innerHTML = "Zip code:" + zipCode;

                    //updating for first zip only
                    isFirstZipInQuery = false;
                }
            }
            if (isStateCode(value) && isFirstStateInQuery) {
                //document.getElementById("result").innerHTML += " - state code";
                state = value;
                document.getElementById("state").innerHTML = "State:" + state;

                //updating for first state only
                isFirstStateInQuery = false;
            }
            if (isCityPart(value)) {
                document.getElementById("result").innerHTML += " - city";
            }
            <%--if (isStateCodeOrCity(value) && !isCityPart(value) && !isStateCode(value)) {--%>
            <%--if (isFirstStateInQuery) {--%>
            <%--$.getJSON("${pageContext.request.contextPath}/checkIfAState",--%>
            <%--{--%>
            <%--state: value--%>
            <%--},--%>
            <%--function (data) {--%>

            <%--var data = JSON.stringify(data);--%>

            <%--if (data == "true" && isFirstStateInQuery) {--%>
            <%--state = value;--%>
            <%--//document.getElementById("result").innerHTML += " - state code";--%>
            <%--document.getElementById("state").innerHTML = "State:" + state;--%>

            <%--//updating for first state only--%>
            <%--isFirstStateInQuery = false;--%>
            <%--} else {--%>
            <%--//document.getElementById("result").innerHTML += " - part of the city";--%>
            <%--//$("#city").html("City:" + state);--%>
            <%--}--%>

            <%--})--%>
            <%--.done(function () {--%>

            <%--})--%>
            <%--.fail(function () {--%>
            <%--})--%>
            <%--.complete(function () {--%>
            <%--});--%>
            <%--} else {--%>
            <%--//document.getElementById("result").innerHTML += " - part of the city";--%>
            <%--}--%>
            <%--}--%>
        }

        //Identify first changed word index (are we editing the same word?)
        if (wordsArray.length == currentWordsArray.length) {
            for (index = 0; index < currentWordsArray.length; index++) {
                if (currentWordsArray[index] != wordsArray[index]) {
                    currentWordIndex = index;
                    break;
                };
            }
        }

        var addCityParts = false;
        //get city name
        for (index = 0; index < currentWordsArray.length; index++) {

            if ((currentWordsArray[index] == zipCode
                    || currentWordsArray[index] == state)&& !addCityParts) {
                addCityParts = true;
                //skip this word
                continue;
            };

            if (currentWordsArray[index] != zipCode
                    && currentWordsArray[index] != state && !addCityParts) {
                addCityParts = true;
            };

            if ((currentWordsArray[index] == zipCode
                    || currentWordsArray[index] == state) && addCityParts) {

                //leaving loop
                if (cityName.length > 0) {
                    addCityParts = false;
                    break;
                } else {
                    addCityParts = true;
                    //skip this word
                    continue;
                }
            };

            if (addCityParts &&
                    (isCityPart(currentWordsArray[index]) || isStateCodeOrCity(currentWordsArray[index]))) {
                cityName += " " + currentWordsArray[index];
            };

        }

        document.getElementById("cityName").innerHTML = "City:" + cityName;

        if (wordsArray.length != currentWordsArray.length || lastEditedWordIndex != currentWordIndex) {
            //set the values
            lastEditedWordIndex = currentWordIndex;
            wordsArray = currentWordsArray;

            updateMap();
        }

        //TODO: if state - suggest cities + suggest zips
        //TODO: if City (more than two letters) if one then suggest zip and region
        //TODO: if City and more than two options suggest state + city + zip
    });

    function isZipCode(value) {

        var re = /^[0-9]{1,5}$/;

        var arr;
        while ((arr = re.exec(value)) != null) {
            return true
        }

        return false
    }

    function isStateCode(value) {

        var re = /^[A-Z]{2}$/;

        var arr;
        while ((arr = re.exec(value)) != null) {
            return true
        }

        return false
    }

    function isCityPart(value) {

        var re = /^[A-Za-z][A-Za-z][A-Za-z]+$/;

        var arr;
        while ((arr = re.exec(value)) != null) {
            return true
        }

        return false
    }

    function isStateCodeOrCity(value) {

        var re = /^[A-Za-z][A-Za-z]+$/;

        var arr;
        while ((arr = re.exec(value)) != null) {
            return true
        }

        return false
    }

    var map;

    function showMap(latitude,longitude) {

        var googleLatandLong = new google.maps.LatLng(latitude,longitude);

        var mapOptions = {
            zoom: 5,
            center: googleLatandLong,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        var mapDiv = document.getElementById("map");
        map = new google.maps.Map(mapDiv, mapOptions);

        var title = "Zip code location";
        addMarker(map, googleLatandLong, title, "");

    }

    function showMapByAddress(address) {

        if (address == "") {
            return;
        }

        var latlng = new google.maps.LatLng(0, 0);
        var myOptions = {zoom: 15,center: latlng,scrollwheel: true,	scaleControl: false,disableDefaultUI: false,mapTypeId: google.maps.MapTypeId.ROADMAP};
        var mapDiv = new google.maps.Map(document.getElementById("map"),myOptions);
        var geocoder = new google.maps.Geocoder();

        geocoder.geocode( { 'address': address}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                mapDiv.setCenter(results[0].geometry.location);
                var marker = new google.maps.Marker({map: mapDiv,	position: mapDiv.getCenter()});
            }
            else {alert("Geocode was not successful for the following reason: " + status);}
        });
    }

    function addMarker(map, latlong, title, content) {

        var markerOptions = {
            position: latlong,
            map: map,
            title: title,
            clickable: true
        };
        var marker = new google.maps.Marker(markerOptions);
    }

    function updateMap() {
        if (zipCode == "" && state == "" && cityName == "") {
            $("#map").html("Not enough data in search query")
            return;
        } else if (zipCode != "") {
            $.getJSON("${pageContext.request.contextPath}/getCityByZipStateCityName",
                    {
                        zipCode: zipCode,
                        state: state,
                        cityName: cityName
                    },
                    function (data) {

                        var data = JSON.stringify(data);
                        var json = JSON.parse(data);
                        //TODO: if nothing was show "No data"
                        showMap(json["latitude"], json["longitude"])
                        $("#result").html(data)

                    })
                    .done(function () {

                    })
                    .fail(function () {
                    })
                    .complete(function () {
                    });
        } else if (cityName != "" && state != "") {
                $.getJSON("${pageContext.request.contextPath}/getStateName",
                        {
                            stateCode: state
                        },
                        function (data) {

                            var stateName = JSON.stringify(data);
                            showMapByAddress(stateName + " " +cityName);
                        })
                        .done(function () {

                        })
                        .fail(function () {
                        })
                        .complete(function () {
                        });
        } else if (cityName != "" && state == "") {
            showMapByAddress(cityName);
        } else if (cityName == "" && state != "") {
            $.getJSON("${pageContext.request.contextPath}/getStateName",
                    {
                        stateCode: state
                    },
                    function (data) {

                        var stateName = JSON.stringify(data);
                        showMapByAddress(stateName);
                    })
                    .done(function () {

                    })
                    .fail(function () {
                    })
                    .complete(function () {
                    });
        }
    }

//    $("#search-string").autocomplete({
//        serviceUrl: '/getCityZips', // Страница для обработки запросов автозаполнения
//        minChars: 2, // Минимальная длина запроса для срабатывания автозаполнения
//        delimiter: /(,|;)\s*/, // Разделитель для нескольких запросов, символ или регулярное выражение
//        maxHeight: 400, // Максимальная высота списка подсказок, в пикселях
//        width: 300, // Ширина списка
//        zIndex: 9999, // z-index списка
//        deferRequestBy: 0, // Задержка запроса (мсек), на случай, если мы не хотим слать миллион запросов, пока пользователь печатает. Я обычно ставлю 300.
//        params: { country: 'Yes'}, // Дополнительные параметры
//        onSelect: function(data, value){ }, // Callback функция, срабатывающая на выбор одного из предложенных вариантов,
//        lookup: ['January', 'February', 'March', 'April', 'May'] // Список вариантов для локального автозаполнения
//    });
});



</script><br/>
<div id="result"></div>
<br/>
<div style="width:600px;height:400px" id="map"></div>

</body>
</html>


