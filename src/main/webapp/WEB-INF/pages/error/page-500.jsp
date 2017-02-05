<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="msapplication-tap-highlight" content="no"/>
  <meta name="msapplication-TileColor" content="#00bcd4"/>
  <meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/assets/images/favicon/mstile-144x144.png"/>
  <link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon/favicon-32x32.png" sizes="32x32"/>
  <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/images/favicon/apple-touch-icon-152x152.png"/>
  <title>500 Error Page</title>

  <link href="${pageContext.request.contextPath}/assets/css/materialize.css" rel="stylesheet" type="text/css" media="screen,projection"/>
  <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet" type="text/css" media="screen,projection"/>
  <link href="${pageContext.request.contextPath}/assets/css/custom/custom.min.css" rel="stylesheet" type="text/css" media="screen,projection"/>
  <link href="${pageContext.request.contextPath}/assets/js/plugins/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" type="text/css" media="screen,projection"/>
  <link href="${pageContext.request.contextPath}/assets/js/plugins/dropify/css/dropify.min.css" rel="stylesheet" type="text/css" media="screen,projection"/>
</head>

<body class="cyan">

  <div id="error-page">
    <div class="row">
      <div class="col s12">
        <div class="browser-window">
          <div class="top-bar">
            <div class="circles">
              <div id="close-circle" class="circle"></div>
              <div id="minimize-circle" class="circle"></div>
              <div id="maximize-circle" class="circle"></div>
            </div>
          </div>
          <div class="content">
            <div class="row">
              <div id="site-layout-example-top" class="col s12">
                <p class="flat-text-logo center white-text caption-uppercase">Internal server error</p>
              </div>
              <div id="site-layout-example-right" class="col s12 m12 l12" style="height: 350px">
                <div class="row center">
                  <h6 class="text-long-shadow col s12">500</h6>
                </div>
                <div class="row center">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script type="text/javascript">
    function goBack() {
      window.history.back();
    }
  </script>
</body>

</html>