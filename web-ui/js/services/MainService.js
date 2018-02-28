(function () {
    'use strict';

    angular.module('mainApp').factory('MainService', MainService);

    MainService.$inject = ['$http', '$q'];
    function MainService($http, $q) {

        var longToTimeString = function (time) {
            var sec = time / 1000;
            var hours = Math.floor(sec / 3600);
            var minutes = Math.floor(sec % 3600 / 60);
            return hours + ' ч., ' + minutes + ' мин.';
        };

        return {
            getCurrentData: getCurrentData,
            getStatistic: getStatistic,
            processCurrentData: processCurrentData
        };


        function getCurrentData() {

            return $q(function (resolve, reject) {
                $http({
                    url: 'data/current',
                    method: "GET"
                }).then(function (response) {
                    var data = response.data;
                    data = processCurrentData(data);

                    resolve(data);

                }, function (reason) {
                    resolve(null);
                })
            });

        }

        function getStatistic(lineName, dateFrom, dateTo) {

            return $q(function (resolve, reject) {
                $http({
                    url: 'statistic',
                    method: "GET",
                    params: {
                        lineName: lineName,
                        dateFrom: dateFrom,
                        dateTo: dateTo
                    }
                }).then(function (response) {
                    var data = response.data;

                    data.forEach(function (item) {
                        item.downtime = longToTimeString(item.downtime);
                        item.periodWorkWithMaterial = longToTimeString(item.periodWorkWithMaterial);
                        item.timePowerOff = longToTimeString(item.timePowerOff);
                        if(item.averageSpeed) {
                            item.averageSpeed = parseFloat(Math.round(item.averageSpeed * 100) / 100).toFixed(2);
                        }
                        if(item.expenditureOfMaterial) {
                            item.expenditureOfMaterial = parseFloat(Math.round(item.expenditureOfMaterial * 100) / 100).toFixed(2);
                        }
                    });

                    resolve(data);

                }, function (reason) {
                    resolve(null);
                })
            });

        }

        function processCurrentData(currentData) {

            currentData.periodWorkWithMaterialLine1 = longToTimeString(currentData.periodWorkWithMaterialLine1);
            currentData.downtimeLine1 = longToTimeString(currentData.downtimeLine1);
            currentData.line1OnOff = currentData.line1OnOff ? 'ВКЛ.' : 'ВЫКЛ.';
            currentData.withMaterialLine1 = currentData.withMaterialLine1 ? 'ДА' : 'НЕТ';

            currentData.periodWorkWithMaterialLine2 = longToTimeString(currentData.periodWorkWithMaterialLine2);
            currentData.downtimeLine2 = longToTimeString(currentData.downtimeLine2);
            currentData.line2OnOff = currentData.line2OnOff ? 'ВКЛ.' : 'ВЫКЛ.';
            currentData.withMaterialLine2 = currentData.withMaterialLine2 ? 'ДА' : 'НЕТ';

            currentData.timePowerOff = longToTimeString(currentData.timePowerOff);

            if(currentData.currentSpeedLine1) {
                currentData.currentSpeedLine1 = parseFloat(Math.round(currentData.currentSpeedLine1 * 100) / 100).toFixed(2);
            }
            if(currentData.currentSpeedLine2) {
                currentData.currentSpeedLine2 = parseFloat(Math.round(currentData.currentSpeedLine2 * 100) / 100).toFixed(2);
            }
            if(currentData.expenditureOfMaterialLine1) {
                currentData.expenditureOfMaterialLine1 = parseFloat(Math.round(currentData.expenditureOfMaterialLine1 * 100) / 100).toFixed(2);
            }
            if(currentData.expenditureOfMaterialLine2) {
                currentData.expenditureOfMaterialLine2 = parseFloat(Math.round(currentData.expenditureOfMaterialLine2 * 100) / 100).toFixed(2);
            }

            return currentData;
        }


    }
})();
