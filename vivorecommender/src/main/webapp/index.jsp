<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="edu.northwestern.sonic.model.User" %>
<html lang="en">
<head>
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
						<li class="active"><a href="index.jsp"><i
								class="icon-home icon-white"></i> Home</a></li>
						<li><a href="about"><i class="icon-star-empty icon-white"></i>
								About</a></li>
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
			<div class="row">
				<div class="span4 offset4">
					<center>
						<hgroup>
						<h1>Semantic Recommender</h1>
						<h3>
							Recommending <a href="http://vivo.ufl.edu">VIVO</a> experts for
							your research topics
						</h3>
						</hgroup>
					</center>
				</div>
			</div>
			<div class="row">
				<div class="span4 offset4">
					<center>
						<form class="form-search" method="GET" action="recommend">
							<input id="search" name="search" data-provide="typeahead"
								data-items="4" type="text" placeholder="Research Topic"
								class="search-query" autocomplete="off">
							<button type="submit" class="btn btn-primary" <%if(ego!=null){ %>onclick="loadModal()"<%}%>>Recommend</button>
						</form>
					</center>
				</div>
			</div>
		</div>
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
	<script src="js/keywords.js"></script>
	<script type="text/javascript">
	
	jQuery(document).ready(function() {
		$('#search').typeahead({
			source : keywords
		});
	});
	
	function loadModal() {
		$('#waitModal').modal({show: true, 
	        backdrop: 'static'});
	}
</script>
</body>
</html>