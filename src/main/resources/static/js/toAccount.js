let app = angular.module('to', []).config(function ($httpProvider) {
    csrftoken = $("meta[name='_csrf']").attr("content");
    csrfheader = $("meta[name='_csrf_header']").attr("content");
    $httpProvider.defaults.headers.common["X-CSRF-TOKEN"] = csrftoken;
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(csrfheader, csrftoken);
        xhr.setRequestHeader("X-XSRF-TOKEN", csrftoken);
    });
});

app.controller("ToController", function ($scope, $http) {

    $scope.getOrders = function () {
        $http.get('/public/rest/client/topage/order').success(function (data, status, headers, config) {
            $scope.orders = data;
        });
    };

    $scope.cancelOrder = function (orderId){
        $http.post('/public/rest/client/topage/cancelOrder/'+orderId).success(function (data, status, headers, config) {
            $scope.orders = data;
        });
    }

    $scope.confirmOrder = function (orderId){
        $http.post('/public/rest/client/topage/confirmOrder/'+orderId).success(function (data, status, headers, config) {
            $scope.orders = data;
        });
    }

    $scope.getInfo = function () {
        $http.get('/public/rest/client/topage/info').success(function (data, status, headers, config) {
            $scope.info = data;
            if (data.children == "Да"){
                document.getElementById("childrenYes").checked = true;
            }
            if (data.children == "Нет") {
                document.getElementById("childrenNo").checked = true;
            }
            if (data.abroad == "Да"){
                document.getElementById("abroadYes").checked = true;
            }
            if (data.abroad == "Нет") {
                document.getElementById("abroadNo").checked = true;
            }
        });
    }

    $scope.editInfo = function () {
        const body = {
            name: $scope.info.name,
            sname: $scope.info.sname,
            pname: $scope.info.pname,
            email: $scope.info.email,
            desc: $scope.info.description,
        };
        $http.post('/public/rest/client/topage/editInfo/', body)
            .then($scope.getInfo, $scope.errorEditInfoCallback);
    }

    $scope.editTransport = function () {
        const body = {
            id: $scope.transport.id,
            model: $scope.transport.model,
            transportClass: $scope.transport.transportClass,
            number: $scope.info.transport.number,
            description: $scope.transport.description,
            image: $scope.transport.image,
        };
        $http.post('/public/rest/client/topage/editTransport/', body)
            .then($scope.getTransport, $scope.errorEditInfoCallback);
    }

    $scope.editTariff = function () {
        $http.get('/public/rest/client/topage/editTariff/'+$scope.info.children+'/'+$scope.info.abroad)
            .then($scope.getInfo, $scope.errorEditInfoCallback);
    }

    $scope.errorEditInfoCallback = function (error) {
        console.log(error);
        if (error.status === 405) {
            $scope.errormessage = "You do not have permissions to do that";
        } else
        if (error.status === 403) {
            $scope.errormessage = "You do not have permissions to do that";
        } else
            $scope.errormessage = "Impossible edit info about transport operator";
    };

    $scope.getTransport = function (){
        $http.get('/public/rest/client/topage/transport').success(function (data, status, headers, config) {
            $scope.transports = data;
        });
    }

    $scope.addTransport = function () {
        const body = {model: $scope.inputModel, tclass: $scope.inputClass, number: $scope.inputNumber,year: $scope.inputYear, desc: $scope.inputDesc};
        $http.post('/public/rest/client/topage/addTransport/', body)
            .then($scope.successAddTransportCallback, $scope.errorAddTransportCallback);
    };

    $scope.successAddTransportCallback = function (response) {
        $http.get('/public/rest/client/topage/transport');
        $scope.errormessage="";
    };

    $scope.errorAddTransportCallback = function (error) {
        console.log(error);
        if (error.status === 405) {
            $scope.errormessage = "You do not have permissions to do that";
        } else
        if (error.status === 403) {
            $scope.errormessage = "You do not have permissions to do that";
        } else
            $scope.errormessage = "Impossible to add new transport; check if it's name is unique";
    };
});