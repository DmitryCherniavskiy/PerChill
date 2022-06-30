var app = angular.module('recs', []);

app.controller("RecsController", function ($scope, $http) {

    $scope.getRecs = function () {
        $http.get('/public/rest/rec/into').success(function (data, status, headers, config) {
            $scope.recsList = data;
            $scope.recsList.sort(function(a, b){
                return b.grade - a.grade;
            })
            for (var i = 0; i < $scope.recsList.length; i++) {
                $scope.recsList[i].edit = 0;
            }
        }).error(function (data, status, headers, config) {
            if (data.message === 'Time is out') {
                $scope.finishByTimeout();
            }
        });
    };
});