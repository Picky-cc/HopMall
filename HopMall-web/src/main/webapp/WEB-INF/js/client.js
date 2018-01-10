var app = angular.module('myapp',[]);
app.controller('clientController',function ($scope,$http) {
        $scope.doSearch = function () {
            $http({
                method: 'POST',
                url: '/ssl/client',
                headers: {'Content-Type': 'text/html;charset=UTF-8'}
            }).success(function (response) {
                $scope.client = response;
            })
        }
        // $scope.doShow = function (id) {
        //     console.log(id);
        //         $scope.userid = id;
        // }
    }
);

