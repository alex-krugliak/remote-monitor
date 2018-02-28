<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">

    <title>MainApp</title>
    <link href="<c:url value="/resources/build/css/main.css"/>" rel="stylesheet"/>



</head>

<body>

    <script type="text/javascript" src="<c:url value="/resources/build/js/vendor.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/build/js/main.js"/>"></script>

    <div id="home" ui-view autoscroll ></div>

    <script type="text/javascript">
        window.contextPath = '${pageContext.request.contextPath}';

        angular.bootstrap(document, ['mainApp']);


    </script>



</body>
</html>