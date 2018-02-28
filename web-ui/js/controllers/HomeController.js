(function () {
    'use strict';

    angular.module('mainApp').controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$rootScope', 'cfg', 'LoginService', '$state', 'MainService', 'currentData'];
    function HomeController($scope, $rootScope, cfg, LoginService, $state, MainService, currentData) {

        /* jshint validthis: true */
        var vm = this;

        vm.bundles = cfg.bundles;
        vm.username = cfg.username;
        vm.MainService = MainService;


        vm.logout = logout;
        vm.isConnectionOk = isConnectionOk;
        vm.isHardwareConnectionOk = isHardwareConnectionOk;
        vm.setStatuses = setStatuses;
        vm.resetStatuses = resetStatuses;
        vm.setHardwareStatus = setHardwareStatus;

        if (currentData != null) {
            vm.setHardwareStatus(currentData.hardwareConnected);
            vm.serverConnection = vm.bundles['server.status.ok'];
            vm.connectionStatusOk = true;

        } else {
            vm.resetStatuses();
        }


        $rootScope.$on('wsClosed', function (event) {
            $scope.$apply(function () {
                vm.resetStatuses();
            });
        });

        $rootScope.$on('wsNewMessage', function (event, data) {
            vm.setStatuses(data);
        });

        function logout() {
            LoginService.logout();
            $state.go('login');
        }

        function isConnectionOk() {
            return vm.connectionStatusOk ? "success-text" : "error-text";
        }

        function isHardwareConnectionOk() {
            return vm.connectionHardwareStatusOk ? "success-text" : "error-text";
        }

        function setStatuses(data) {
            if (data) {
                var currentData = vm.MainService.processCurrentData(JSON.parse(data));
                $scope.$apply(function () {

                    vm.setHardwareStatus(currentData.hardwareConnected);
                    vm.serverConnection = vm.bundles['server.status.ok'];
                    vm.connectionStatusOk = true;

                });

            } else {
                $scope.$apply(function () {
                    vm.resetStatuses();

                });
            }
        }

        function resetStatuses() {
            vm.serverConnection = vm.bundles['server.status.error'];
            vm.serverHardwareConnection = vm.bundles['server.hardware.status.error'];
            vm.connectionStatusOk = false;
            vm.connectionHardwareStatusOk = false;
        }

        function setHardwareStatus(hardwareConnected) {
            if (hardwareConnected) {
                vm.serverHardwareConnection = vm.bundles['server.hardware.status.ok'];
                vm.connectionHardwareStatusOk = true;
            } else {
                vm.serverHardwareConnection = vm.bundles['server.hardware.status.error'];
                vm.connectionHardwareStatusOk = false;
            }
        }
    }
})();