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
	href="/img/sonic.ico">
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
	<div class="row">
		
				 <div class="span4 offset4">
				 	<table class="table table-striped table-bordered pull-right">
				 		<thead>
				 			<tr>
				 				<th><center>Team</center></th>
				 			</tr>
				 		</thead>
				 		<tbody>
				 			<tr>
				 				
				 				<td>
				 				            <center>
				 				            <blockquote>
				 				            <p>Dr. Noshir Contractor</p>
				 				            <small>Jane S. & William J. White Professor of Behavioral Sciences</small>
				 				            </blockquote>
				 				            </center>
					                        <dl class="dl-horizontal">
												<dt>Email</dt>
												<dd>nosh@northwestern.edu</dd>
												<dt>Website</dt>
												<dd>http://nosh.northwestern.edu</dd>
											</dl>
											
				 					</td>	
				 			</tr>
				 			<tr>
				 				<td>
				 				            <center>
				 				            <blockquote>
				 				            <p>Dr. Willem Pieterson</p>
				 				            <small>General Manager of the SONIC Research Group</small>
				 				            </blockquote>
				 				            </center>
					                        <dl class="dl-horizontal">
												<dt>Email</dt>
												<dd>wpieterson@gmail.com</dd>
												<dt>Phone</dt>
												<dd>847-532-0625</dd>
											</dl>
											
				 					</td>	
				 			</tr>
				 			<tr>
				 				<td>
				 				            <center>
				 				            <blockquote>
				 				            <p>Dr. Hugh Devlin</p>
				 				            <small>Postdoctoral Fellow</small>
				 				            </blockquote>
				 				            </center>
					                        <dl class="dl-horizontal">
												<dt>Email</dt>
												<dd>hdevlin@northwestern.edu</dd>
												<dt>Phone</dt>
												<dd>847-467-2196</dd>
											</dl>
											
				 					</td>	
				 			</tr>
				 			<tr>
				 				<td>
				 				            <center>
				 				            <blockquote>
				 				            <p>Anup Sawant</p>
				 				            <small>Research Programmer</small>
				 				            </blockquote>
				 				            </center>
					                        <dl class="dl-horizontal">
												<dt>Email</dt>
												<dd>anup.sawant@northwestern.edu</dd>
												<dt>Phone</dt>
												<dd>864-650-6368</dd>
											</dl>
											
				 					</td>	
				 			</tr>
				 			<tr>
				 				<td>
				 				            <center>
				 				            <blockquote>
				 				            <p>Joe Gilborne</p>
				 				            <small>Research Programmer</small>
				 				            </blockquote>
				 				            </center>
					                        <dl class="dl-horizontal">
												<dt>Email</dt>
												<dd>jgilborne@gmail.com</dd>
												<dt>Phone</dt>
												<dd>847-467-2565</dd>
											</dl>
											
				 					</td>	
				 			</tr>
				 		</tbody>
				 	</table>
				 </div>
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