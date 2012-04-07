<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>VIVO Recommender</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="css/vivorec.css" rel="stylesheet">
<style>
body {
	padding-top: 60px; /* When using the navbar-top-fixed */
}
</style>
<link href="css/bootstrap-responsive.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon"
	href="http://twitter.github.com/bootstrap/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="http://sonic.northwestern.edu/">SONIC</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li><a href="index.jsp"><i
								class="icon-home icon-white"></i> Home</a></li>
						<li><a href="about"><i
								class="icon-star-empty icon-white"></i> About</a></li>
					</ul>
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">My Account <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="preference"><i
										class="icon-cog icon-black"></i> Preferences</a></li>
								<li class="divider"></li>
								<li><a href="logout"><i class="icon-off icon-black"></i>
										Logout</a></li>
							</ul></li>
					</ul>
				</div>
				<!-- /.nav-collapse -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.navbar-inner -->
	</div>
	<!-- /.navbar -->

	<div class="container">
	<center>
		<form class="well" method="POST" action="">
			<label>Identify yourself</label> <input type="text" name="userEmail" class="span3"
				placeholder="example@something.com...">
			<%
				String message = (String) request.getAttribute("message");
			%>
				<%if(message!=null){ %><p><span class="help-inline"><%=message%></span></p> <% } %>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
   </center>

		<footer> </footer>

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>VIVO Recommender</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="css/vivorec.css" rel="stylesheet">
<style>
body {
	padding-top: 60px; /* When using the navbar-top-fixed */
}
</style>
<link href="css/bootstrap-responsive.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon"
	href="http://twitter.github.com/bootstrap/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="http://sonic.northwestern.edu/">SONIC</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li><a href="index.jsp"><i
								class="icon-home icon-white"></i> Home</a></li>
						<li><a href="about"><i
								class="icon-star-empty icon-white"></i> About</a></li>
					</ul>
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">My Account <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="preference"><i
										class="icon-cog icon-black"></i> Preferences</a></li>
								<li class="divider"></li>
								<li><a href="logout"><i class="icon-off icon-black"></i>
										Logout</a></li>
							</ul></li>
					</ul>
				</div>
				<!-- /.nav-collapse -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.navbar-inner -->
	</div>
	<!-- /.navbar -->

	<div class="container">
	<center>
		<form class="well" method="POST" action="">
			<label>Identify yourself</label> <input type="text" name="userEmail" class="span3"
				placeholder="example@something.com...">
			<%
				String message = (String) request.getAttribute("message");
			%>
				<%if(message!=null){ %><p><span class="help-inline"><%=message%></span></p> <% } %>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
   </center>

		<footer> </footer>

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>

</html>