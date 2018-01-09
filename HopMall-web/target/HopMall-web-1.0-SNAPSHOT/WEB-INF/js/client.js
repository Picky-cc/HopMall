// var app = angular.module('myapp',[]);
// app.controller('clientController',function ($scope,$http) {
//         $scope.doSearch = function () {
//             $http({
//                 method: 'POST',
//                 url: '/ssl/client',
//                 headers: {'Content-Type': 'text/html;charset=UTF-8'}
//             }).then(function success(response) {
//                 $scope.client = response.data.clients;
//             });
//         }
//     }
// );

$controller('clientController',function ($scope,$http) {
        $scope.doSearch = function () {
            $http({
                method: 'POST',
                url: '/ssl/client',
                headers: {'Content-Type': 'text/html;charset=UTF-8'}
            }).then(function success(response) {
                $scope.client = response.data.clients;
            });
        }
    }
);