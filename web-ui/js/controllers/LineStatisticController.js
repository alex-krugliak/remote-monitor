(function () {
    'use strict';

    angular.module('mainApp').controller('LineStatisticController', LineStatisticController);

    LineStatisticController.$inject = ['$scope', '$rootScope', 'cfg', '$stateParams', 'MainService', 'statisticData'];
    function LineStatisticController($scope, $rootScope, cfg, $stateParams, MainService, statisticData) {
        /* jshint validthis: true */
        var vm = this;

        vm.stateParams = $stateParams;
        vm.bundles = cfg.bundles;
        vm.statisticData = statisticData;
        vm.datePickerIsOpen = false;
        vm.dt = new Date();

        var lineNumber = vm.stateParams.line == 'line1' ? '1' : '2';
        vm.lName = 'Статистика - Машина№' + lineNumber;

        vm.isNoData = isNoData;
        vm.open = open;
        vm.dateChanged = dateChanged;

        function isNoData() {
            return !statisticData || !statisticData.length > 0;
        }

        function open($event) {
            vm.datePickerIsOpen = true;
        }

        function dateChanged() {
            MainService.getStatistic(vm.stateParams.line, vm.dt.getTime(), 0).then(function (statistic) {
                vm.statisticData = statistic;
            });
        }
    }
})();