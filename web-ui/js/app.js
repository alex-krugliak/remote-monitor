(function () {
    angular.module('mainApp', [
        'ui.router',
        'ui.bootstrap',
        'blockUI',
        'ncy-angular-breadcrumb',
        'toaster',
        'ngSanitize'
        
    ]);

    angular.module('mainApp').constant('cfg', {
        version: '1.0.0'
    });
})();

