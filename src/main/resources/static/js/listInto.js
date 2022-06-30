let app = angular.module('recs', []).config(function ($httpProvider) {
    csrftoken = $("meta[name='_csrf']").attr("content");
    csrfheader = $("meta[name='_csrf_header']").attr("content");
    $httpProvider.defaults.headers.common["X-CSRF-TOKEN"] = csrftoken;
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(csrfheader, csrftoken);
        xhr.setRequestHeader("X-XSRF-TOKEN", csrftoken);
    });
});

app.controller("RecsController", function ($scope, $http) {

    let curTo = 0;
    let cur=0;
    let all;
    $http.post('/public/rest/rec/count').success(function (data, status, headers, config) {
        all=Math.ceil(data/2)-1;
    });

    $scope.getRecs = function (par=0) {
        $http.get('/public/rest/rec/into/'+par).success(function (data, status, headers, config) {
            $scope.recsList = data;
            cur = par;
            for (var i = 0; i < $scope.recsList.length; i++) {
                $scope.recsList[i].edit = 0;
            }

            if (all == 4){
                $scope.countList = [1, 2, 3, 4, 5];
            }else if (all > 4 && cur > 1 && cur < all-2 ) {
                $scope.countList = [1,'...', cur - 1, cur, cur + 1,'...', all];
            }else if(all > 3 && cur < 2){
                $scope.countList = [1, 2, 3,'...', all];
            }else if(all > 3 && cur >= all -1){
                $scope.countList = [1,'...', all-2, all-1, all];
            }else if(all == 3){
                $scope.countList = [1, 2, 3,4];
            }else if(all == 2){
                $scope.countList = [1, 2, 3];
            }else if(all == 1){
                $scope.countList = [1, 2];
            }else if(all == 0){
                $scope.countList = [1];
            }
        }).error(function (data, status, headers, config) {
            if (data.message === 'Time is out') {
                $scope.finishByTimeout();
            }
        });
    };

    $scope.getNumbers = function (par) {
        $http.get('/public/rest/rec/into/'+par).success(function (data, status, headers, config) {
            $scope.recsList = data;
            cur = par;
            for (var i = 0; i < $scope.recsList.length; i++) {
                $scope.recsList[i].edit = 0;
            }
        })
    };
});