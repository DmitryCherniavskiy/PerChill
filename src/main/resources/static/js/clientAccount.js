let app = angular.module('client', []).config(function ($httpProvider) {
    csrftoken = $("meta[name='_csrf']").attr("content");
    csrfheader = $("meta[name='_csrf_header']").attr("content");
    $httpProvider.defaults.headers.common["X-CSRF-TOKEN"] = csrftoken;
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(csrfheader, csrftoken);
        xhr.setRequestHeader("X-XSRF-TOKEN", csrftoken);
    });
});

app.controller("ClientController", function ($scope, $http) {

    $scope.getOrders = function () {
        $http.get('/public/rest/client/mypage/order').success(function (data, status, headers, config) {
            $scope.orders = data;
        });
    };

    $scope.getInfo = function () {
        $http.get('/public/rest/client/mypage/info').success(function (data, status, headers, config) {
            $scope.info = data;
        });
    };

    $scope.editInfo = function () {
        const body = {
            name: $scope.info.name,
            sname: $scope.info.sname,
            pname: $scope.info.pname,
            email: $scope.info.email,
            image: $scope.info.image,
        };
        $http.post('/public/rest/client/mypage/editInfo/', body)
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

    $scope.addGrade = function(index){
        if (!isNaN($scope.orders[index].grade) && $scope.orders[index].grade<11){
            console.log($scope.orders[index].id);
            $http.get('/public/rest/client/mypage/addGrade/'+$scope.orders[index].id+ '/'+ $scope.orders[index].grade).then($scope.accesGradeCallback, $scope.errorGradeCallback);
        }else{
            console.log($scope.orders[index].grade);
            $scope.errormessage = "Оцените перевозчика по десятибальной шкале";
        }
    }

    $scope.accessGradeCallback = function(){
        $scope.errormessage = "";
    }

    $scope.accessGradeCallback = function(){
        $scope.errormessage = "Ошибка добавления";
    }
});