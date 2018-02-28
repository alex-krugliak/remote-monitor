(function () {
    'use strict';

    angular.module('mainApp').controller('MainController', MainController);

    MainController.$inject = ['$scope', '$rootScope', 'cfg', '$state', 'MainService', 'currentData'];
    function MainController($scope, $rootScope, cfg, $state, MainService, currentData) {
        /* jshint validthis: true */
        var vm = this;

        vm.bundles = cfg.bundles;
        vm.MainService = MainService;

        $rootScope.$on('wsClosed', function (event) {
            $scope.$apply(function () {
                vm.currentData = vm.connectionFailed();
            });
        });

        $rootScope.$on('wsNewMessage', function (event, data) {

            if (data) {
                var currentData = vm.MainService.processCurrentData(JSON.parse(data));
                $scope.$apply(function () {
                    vm.currentData = currentData;

                });

            } else {
                $scope.$apply(function () {
                    vm.currentData = vm.connectionFailed();
                });
            }

        });


        vm.connectionFailed = connectionFailed;


        if (currentData != null) {
            vm.currentData = currentData;

        } else {
            vm.currentData = vm.connectionFailed();
        }


        function connectionFailed() {
            return {
                line1OnOff: '##',
                withMaterialLine1: '##',
                expenditureOfMaterialLine1: '##',
                periodWorkWithMaterialLine1: "##",
                downtimeLine1: "##",
                turnOnTimeTodayLine1: "##",
                turnOffTimeLine1: "##",
                currentSpeedLine1: "##",

                line2OnOff: '##',
                withMaterialLine2: '##',
                expenditureOfMaterialLine2: '##',
                periodWorkWithMaterialLine2: "##",
                downtimeLine2: "##",
                turnOnTimeTodayLine2: "##",
                turnOffTimeLine2: "##",
                currentSpeedLine2: "##"
            }
        }

    }
})();