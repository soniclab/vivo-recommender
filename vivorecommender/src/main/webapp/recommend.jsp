<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="edu.northwestern.sonic.model.User"%>
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

<!-- vivorec styles -->
<link href="css/vivorec.css" rel="stylesheet">
 <link href="css/bootstrap-responsive.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="img/sonic.ico">
<script src="http://api.simile-widgets.org/exhibit/2.2.0/exhibit-api.js"
	type="text/javascript"></script>
</head>
<body>
	<%
		String[] egoDetails = (String[]) request.getAttribute("egoDetails");
		String researchTopic = (String) request
				.getAttribute("researchTopic");
		HttpSession ses = (HttpSession) request.getSession();
		Map<String,List<String>> experts = (Map<String,List<String>>)ses.getAttribute("experts");
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
						<li><a href="index.jsp"><i class="icon-home icon-white"></i>
								Home</a></li>
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

		<% if(experts!=null && experts.size() > 0){ %>

		<div class="well">
			<div class="row">
				<div class="span4 offset4">
					<center>
						<form class="form-search" method="GET" action="recommend">
							<input id="search" name="search" data-provide="typeahead"
								data-items="4" type="text" placeholder="Research Topic"
								class="search-query" autocomplete="off" value="<%= researchTopic%>">
							<button type="submit" class="btn btn-primary" <%if(ego!=null){ %>
								onclick="loadModal()" <%}%>>Recommend</button>
						</form>
					</center>
				</div>
			</div>
			<div class="row">
				<div class="span2 offset5">
					<img class="thumbnail" alt="" src="<%=egoDetails[2]%>">

				</div>
			</div>
			<div class="row">
				<div class="span4 offset4">
					<center>
						<hgroup>
						<h3>
							Recommendations for
							<%=egoDetails[1]%>
						</h3>
					 
						<h5>
							<a href="vis.html">Results as graph</a>
						</h5>
						
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
		<%}else{ %>
		<div class="well">
			<div class="row">
				<div class="span4 offset4">
					<center>
						<form class="form-search" method="GET" action="recommend">
							<input id="search" name="search" data-provide="typeahead"
								data-items="4" type="text" placeholder="Research Topic"
								class="search-query" autocomplete="off" value="<%= researchTopic%>">
							<button type="submit" class="btn btn-primary" <%if(ego!=null){ %>
								onclick="loadModal()" <%}%>>Recommend</button>
						</form>
					</center>
					<br><br><br>
					<div class="alert alert-info">
						<h3>Your search did not match any experts.</h3>
						<p>Suggestions:
						<ul>
							<li>Make sure keywords are spelled correctly.</li>
							<li>Try different heuristic.</li>
							<li>Try different keyword.</li>
							<li>Try fewer keywords.</li>
						</ul>
						</p>
					</div>
				</div>
			</div>
		</div>
		<%} %>
	</div>

	<!-- /container -->

	<div class="modal hide fade" id="waitModal"
		style="position: relative; top: auto; left: auto; margin: 0 auto">
		<div class="modal-body">
			<center>
				<p>
				<h3>
					<img src="img/wait.gif" /> Please wait while we process your
					query...
				</h3>
				</p>
			</center>
		</div>
	</div>

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
			$('#waitModal').modal({
				show : true,
				backdrop : 'static'
			});
		}
	</script>
</body>
</html>
