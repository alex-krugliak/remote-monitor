(function () {
    'use strict';
    angular.module('mainApp').directive('customPagination', customPagination);

    customPagination.$inject = ['$q'];

    function customPagination($q) {
        var directive = {
            link: link,
            templateUrl: 'resources/js/templates/pagination.html',
            scope: {
                startPage: '=',
                formPopulate: '=',
                newSearch: "=",
                tableData: "=",
                totalPages: "=",
                notSelected: "=?"
            },
            restrict: 'EA'
        };
        return directive;


        function link(scope, element, attrs) {
            if (scope.startPage) {
                scope.pageData = {
                    inputPage: scope.startPage,
                    currentPage: scope.startPage
                };
            } else {
                var pageFromUrl =  window.location.hash.split(/page=(.*?)&/)[1];
                if (typeof pageFromUrl !== 'undefined' && isNumeric(pageFromUrl)) {
                    scope.pageData = {
                        inputPage: +pageFromUrl,
                        currentPage: +pageFromUrl
                    };
                } else {
                    scope.pageData = {
                        inputPage: 1,
                        currentPage: 1
                    };
                }
            }


            scope.newSearch = function (page) {
                if (!page) {
                    scope.selectPage(scope.pageData.currentPage);
                }
                else {
                    scope.selectPage(page);
                }

            };

            scope.enterPageNumber = function (event, page) {
                if (event.keyCode == 13 && !isNaN(page)) {
                    scope.selectPage(parseInt(page));
                }
            };

            scope.selectPage = function (page, type) {
                var currentPage = null;
                if (type == 'minus') {
                    currentPage = parseInt(page) - 1;
                } else if (type == 'plus') {
                    currentPage = parseInt(page) + 1;
                } else {
                    currentPage = parseInt(page);
                }
                if (typeof scope.totalPages === 'undefined' || scope.totalPages == 0) {
                    scope.totalPages = 1;
                }
                if (typeof currentPage !== 'undefined' && currentPage > 0 && currentPage <= scope.totalPages) {
                    scope.pageData.currentPage = currentPage;
                    scope.formPopulate(currentPage).then(function (result) {
                        if (result != null) {
                            scope.tableData = result.tableData;
                            scope.totalPages = result.totalPages;
                            hideShowPagenation();

                        }
                    });
                    resetInputValue(scope.pageData.currentPage);
                    scope.notSelected = true;
                }


            };

            function resetInputValue(value) {
                scope.pageData.inputPage = value;
            }

            function hideShowPagenation() {
                scope.showPagination = typeof scope.totalPages !== 'undefined' && scope.totalPages > 1;
            }

            hideShowPagenation();

        }

        function isNumeric(obj) {
            return !$.isArray(obj) && (obj - parseFloat(obj) + 1) >= 0;
        }

    }
})();