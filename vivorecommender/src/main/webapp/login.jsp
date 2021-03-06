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

<!-- vivorec styles -->
<link href="css/vivorec.css" rel="stylesheet">
 <link href="css/bootstrap-responsive.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon"
	href="img/sonic.ico">
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
				<h3>Identify Yourself</h3>
				<br><br>
				<input type="text" name="userEmail" class="span3"
					placeholder="example@something.com...">
				<p>
					<span class="help-inline">Helps provide better
						recommendations.</span>
				</p>
				<button type="submit" class="btn btn-primary" onclick="loadModal()">Submit</button>
				<%
					String message = (String) request.getAttribute("message");
				%>
				<%
					if (message != null) {
				%><p>
				    <div class="alert alert-error"><%=message%></div>
				</p>
				<%
					}
				%>
			</form>
		</center>
		<div class="modal hide fade" id="waitModal" style="position: relative; top: auto; left: auto; margin: 0 auto">
			<div class="modal-body">
				<center><p>
					<h3><img src="img/wait.gif" />  Please wait while we process your query...</h3>
				</p></center>
			</div>
		</div>
		<footer> </footer>

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
	   
		function loadModal() {
			$('#waitModal').modal({show: true, 
		        backdrop: 'static'});
		}
		
	</script>
</body>

</html>
