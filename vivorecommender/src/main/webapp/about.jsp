<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="edu.northwestern.sonic.model.User" %>
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
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<body>
<%
		HttpSession ses = (HttpSession) request.getSession();
		User ego = (User) ses.getAttribute("ego");
	%>
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
						<li class="active"><a href="about"><i
								class="icon-star-empty icon-white"></i> About</a></li>
					</ul>
					<% if(ego!=null){%>
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><%= ego.getName()%> <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li>
									<table class="table">
										<tr>
											<td><img class="thumbnail" alt=""
												src="<%= ego.getImageUrl() %>" width="75px" height="75px"></td>
											<td>
												<table class="table">
													<tr>
														<th><label><%= ego.getName() %></label>
															<center><%=ego.getEmail()%></center></th>
													</tr>
													<tr>
														<td><a href="<%=ego.getUri().toString()%>"><button
																	type="submit" class="btn btn-mini btn-primary">VIVO
																	Profile</button></a></td>
													</tr>
													<tr>
														<td><a href="preference"><i
																class="icon-cog icon-black"></i> Preferences</a></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</li>
								<li class="divider"></li>
								<li><a href="logout" class="pull right"><i
										class="icon-off icon-black"></i> Logout</a></li>
							</ul></li>
					</ul>
					<% } %>
				</div>
				<!-- /.nav-collapse -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.navbar-inner -->
	</div>
	<!-- /.navbar -->

	<div class="container">
	<div class="well">
		<h1>About Us</h1>
		<p>
		<h4>Principal Investigator</h4>
		<ul class="unstyled">
		<li>Dr. Noshir Contractor</li>
		</ul>
		
		<h4>Project team</h4>
		<ul class="unstyled">
		<li>Dr. Hugh Devlin</li>
		<li>Anup Sawant</li>
		<li>Joe Gilborne</li>
		</ul>
		
		<h4>Developer support</h4>
		<ul class="unstyled">
		<li>York Yao</li>
		<li>Jinling Li</li>
		<li>Dr. Willem Pieterson</li>
		</ul>
		</p>
	</div>
		<footer><p>NSF Grant no. : XXXXXXX</p> </footer>

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>

</html>