(function () {
    'use strict';

    angular.module('mainApp').factory('HomeService', HomeService);

    HomeService.$inject = ['$http'];
    function HomeService($http) {

        return {
            getBundles: getBundles

        };

        function getBundles() {
            return $http.get('getBundles')
        }
    }
})();