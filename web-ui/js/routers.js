(function () {
    'use strict';
    function AuthInterceptor($q, $rootScope, $injector) {
        return {

            responseError: function (res) {
                if (res.status === 401) {
                    console.log(res);
                    var LoginService = $injector.get('LoginService');
                    LoginService.clearLocalStorage();
                    $rootScope.$emit("authError", res);
                    $injector.get('$state').go('login');
                }

                return $q.reject(res);
            }
        }
    }

    angular.module('mainApp').factory('AuthInterceptor', AuthInterceptor);
    AuthInterceptor.$inject = ['$q', '$rootScope', '$injector'];


    angular.module('mainApp').config(MainRouter);

    MainRouter.$inject = ['$stateProvider', '$urlRouterProvider', '$httpProvider'];
    function MainRouter($stateProvider, $urlRouterProvider, $httpProvider) {

        $httpProvider.interceptors.push('AuthInterceptor');

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('home', {
                abstract: true,
                templateUrl: 'resources/js/templates/home.html',
                controller: 'HomeController',
                controllerAs: 'Home',
                resolve: {
                    bundles: ['HomeService', 'cfg', 'authorize', function (HomeService, cfg, authorize) {
                        if (!authorize) return false;

                        HomeService.getBundles().then(
                            function (result) {
                                cfg.bundles = result.data;
                                return true;
                            }
                        )
                    }],
                    authorize: ['LoginService', '$state', '$http', 'cfg', function (LoginService, $state, $http, cfg) {
                        return LoginService.getToken().then(function (result) {
                            $http.defaults.headers.common['Auth-Token'] = result.token;
                            cfg.username = result.userName;
                            LoginService.initWebSocket(result.token);
                            return true;
                        }).catch(function () {
                            $state.go('login');
                        });

                    }],
                    currentData: ['MainService', 'authorize', function (MainService, authorize) {
                        return MainService.getCurrentData();
                    }]


                }
            })
            .state('home.main', {
                url: '/',
                templateUrl: 'resources/js/templates/main.html',
                controller: 'MainController',
                controllerAs: 'Main',
                ncyBreadcrumb: {
                    label: 'Главная страница'
                },
                resolve: {
                    currentData: ['MainService', 'authorize', function (MainService, authorize) {
                        return MainService.getCurrentData();
                    }]
                }
            })
            .state('home.statistic', {
                url: '/statistic/:line',
                templateUrl: 'resources/js/templates/statistic.html',
                controller: 'LineStatisticController',
                controllerAs: 'LineStatistic',
                resolve: {
                    statisticData: ['MainService', 'authorize', '$stateParams', function (MainService, authorize, $stateParams) {
                        return MainService.getStatistic($stateParams.line, new Date().getTime(), 0);
                    }]
                },
                ncyBreadcrumb: {
                    parent: 'home.main',
                    label: '{{LineStatistic.lName}}'
                }

            })
            .state('login', {
                url: '/login?message',
                templateUrl: 'resources/js/templates/login.html',
                controller: 'LoginController',
                controllerAs: 'Login'
            })


    }
})();