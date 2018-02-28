(function () {
    'use strict';

    angular.module('mainApp').controller('LoginController', LoginController);

    LoginController.$inject = ['cfg', 'LoginService', '$state', '$stateParams'];
    function LoginController(cfg, LoginService, $state, $stateParams) {

        /* jshint validthis: true */
        var vm = this;

        vm.bundles = cfg.bundles;
        vm.isSubmitted = false;

        vm.ok = ok;

        if (angular.isDefined($stateParams.message)) {
            vm.message = $stateParams.message;
            vm.errorIs = true;
        }
        else {
            vm.errorIs = false;
            vm.message = '';
        }


        function ok(isValid) {
            vm.isSubmitted = true;
            if(!isValid) return;
            
            var request = {
                login: vm.login,
                password: vm.password
            };

            vm.message = '';
            vm.errorIs = false;

            LoginService.sentAutentificationRequest(request).then(function (result) {
                vm.token = result.data;
                return LoginService.saveToken(result.data);

            }, function (reason) {
                vm.message = reason.data.message;
                vm.errorIs = true;

            }).then(function (result) {
                //$window.location.href = '/index/org.html';
                $state.go('home.main');
            }, function (reason) {
                vm.message = reason.message;
                vm.errorIs = true;

            });


        }

    }
})();