var app = angular.module('myapp',[]);
app.controller('clientController',function ($scope,$http) {

        $scope.qr = {};

        $scope.doImage = function () {
            var paramData = $scope.qr;
            $http({
                method:'POST',
                url: '/ssl/QRImage',
                params: paramData,
                headers: {'Content-Type': 'text/html;charset=UTF-8'}
            }).success(function(response){
                $scope.qrimage = response.image;
            })
        }
    }
);


