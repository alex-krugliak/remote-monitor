<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">

        <title>Remote Monitor</title>
        <link href="public/css/main.css" rel="stylesheet"/>



    </head>

    <body>

    <script type="text/javascript" src="public/js/vendor.js"></script>
    <script type="text/javascript" src="public/js/main.js"></script>

    <div id="home" ui-view autoscroll ></div>

    <script type="text/javascript">
debugger;
        $(document).ready(function () {
            angular.module('mainApp').constant('cfg', {
                version: '1.0.0',
                username: '${userName}'
            });

            angular.bootstrap(document, ['mainApp']);
        });

    </script>



    </body>
</html>