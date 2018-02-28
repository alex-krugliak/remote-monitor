(function () {
    'use strict';

    angular.module('mainApp').factory('WebSocketService', WebSocketService);

    WebSocketService.$inject = ['$http', '$q', '$rootScope'];
    function WebSocketService($http, $q, $rootScope) {

        var socket = null;

        return {
            initWebsocket: initWebsocket,
            closeWebsocket: closeWebsocket
        };

        function initWebsocket(token) {
            if (socket === null) {

                socket = new SockJS(window.contextPath + '/webSocketHandler', null, {
                    transports: ['xhr-streaming','xhr-polling'],
                    headers: {'Auth-Token': token}
                });
                

                socket.onopen = function () {
                    console.log("WebSocket open");
                };

                socket.onclose = function (e) {
                    console.log("WebSocket close");
                    $rootScope.$broadcast('wsClosed');
                    socket = null;
                };

                socket.onmessage = function (e) {
                    $rootScope.$broadcast('wsNewMessage', e.data);

                    //webSocket.send("response");
                };

            }

        }

        function closeWebsocket() {
            if (socket)socket.close(null, 'logout');

        }
    }
})();