var clientInfo = angular.module('clientInfo',[]);
clientInfo.controller('clientInfoController',function($scope,$http){

        $scope.search = {};
        $scope.doSearch = function () {
            $http({
                method: 'GET',
                url: '/ssl/clientInfos',
                headers: {'Content-Type': 'text/html;charset=UTF-8'}
            }).success(function (response) {
                console.log(response);
                $scope.client = response;
            })
        }

        $scope.searchByName = function () {
            var params = $scope.search;
            $http({
                method: 'GET',
                url: '/ssl/clientByName',
                params: params,
                headers: {'Content-Type': 'text/html;charset=UTF-8'}
            }).success(function (data) {
                console.log(data);
                $scope.people = data;
            })
        }
    }
);
