<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="edu.northwestern.sonic.model.User" %>
<%@ page import="java.util.*"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>VIVO Recommender</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="datareq" type="application/json" rel="exhibit/data" />

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

<script src="http://api.simile-widgets.org/exhibit/2.2.0/exhibit-api.js"
	type="text/javascript"></script>
</head>
<body>
	<%
		String[] egoDetails = (String[]) request.getAttribute("egoDetails");
		String researchTopic = (String) request
				.getAttribute("researchTopic");
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
						<li><a href="index.jsp"><i class="icon-home icon-white"></i> Home</a></li>
						<li><a href="about"><i class="icon-star-empty icon-white"></i>
								About</a></li>
					</ul>
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><%=egoDetails[1]%> <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li>
									<table class="table">
										<tr>
											<td><img class="thumbnail" alt=""
												src="<%=egoDetails[2]%>" width="75px" height="75px"></td>
											<td>
												<table class="table">
													<tr>
														<th><label><%=egoDetails[1]%></label>
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
				<div class="span2 offset5">
					 <img class="thumbnail" alt=""
						src="<%=egoDetails[2]%>">

				</div>
			</div>
			<div class="row">
				<div class="span4 offset4">
					<center>
						<hgroup>
						<h3>
							Recommendations for <%=egoDetails[1]%>
						</h3>
						</hgroup>

					</center>
				</div>
			</div>

			<div class="row">

				<div class="span8 offset2">
					<table class="table table-striped" width="50%">
						<tr valign="top">
							<td ex:role="viewPanel">
								<table ex:role="lens" class="faculty member">
									<tr>
										<div class="span2">
											<td><a class="thumbnail" ex:href-content=".uri"
												target="_blank"><img ex:src-content=".imageURL"
													width="75" height="75" /></a></td>
										<td><strong><div ex:content=".label"
													class="name"></div></strong>
											<div ex:if-exists=".heuristics" class="heuristics">
												Heuristics: <span ex:content=".heuristics"></span>
											</div></td>
										</div>
									</tr>
								</table>
							</td>
							<td width="25%">
								<table>
									<tr>
										<td><div ex:role="facet" ex:facetClass="TextSearch"></div></td>
										<td><i class="icon-search icon-black"></i></td>
									</tr>
								</table>
								<div ex:role="facet" ex:expression=".heuristics"
									ex:facetLabel="Heuristics"></div>
							</td>
						</tr>

					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="modal hide fade" id="rankdet">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">�</a>
			<h3>Ranking details</h3>
		</div>
		<div class="modal-body">
			<dl class="dl-horizontal">
				<dt>Score</dt>
				<dd>xxxxxxxxxxxxxxxxxxxxxxxxx</dd>
				<dt>Indegree centrality</dt>
				<dd>xxxxxxxxxxxxxxxxxxxxxxxxx</dd>
				<dt>Ranked through
				<dt>
				<dd>xxxxxxxxxxxxxxxxxxxxxxxxx</dd>
			</dl>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn secondary" onclick="closeDialog ();">Close</a>
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
		jQuery(document).ready(function() {
			$('#rankdet').bind('show');
		});
		
		function closeDialog () {
			$('#rankdet').modal('hide'); 
			};

		
	</script>
</body>
</html>
