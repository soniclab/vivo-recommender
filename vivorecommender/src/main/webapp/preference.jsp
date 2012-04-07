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
    <link rel="shortcut icon" href="http://twitter.github.com/bootstrap/assets/ico/favicon.ico">

    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<body>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">

      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <a class="brand" href="http://sonic.northwestern.edu/">SONIC</a>
      <div class="nav-collapse">
        <ul class="nav">

          <li class="active"><a href="#"><i class="icon-home icon-white"></i> Home</a></li>
        </ul>
         <ul class="nav pull-right">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">My Account <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="preference"><i class="icon-cog icon-black"></i> Preferences</a></li>

              <li class="divider"></li>
              <li><a href="logout"><i class="icon-off icon-black"></i> Logout</a></li>
            </ul>
          </li>
          </ul>
      </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
  </div><!-- /.navbar-inner -->

</div><!-- /.navbar -->

    <div class="container">

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="well">
			  <div class="row">
				<div class="span12">
					  <center>
					  <h3>Recommendation Heuristics.</h3>

					  </center>
				</div>
			  </div>
    
			 <div class="row">
		
				 <div class="span4 offset4">
				 	<table class="table table-striped table-bordered pull-right">
				 		<thead>
				 			<tr>
				 				<th>#</th>

				 				<th colspan="2" >Heuristic</th>
				 				<th>Choose</th>
				 			</tr>
				 		</thead>
				 		<tbody>
				 			<tr>
				 				<td>1</td>

				 				<td>Affiliation</td>
				 				<td>
									<i id="hr1" data-content="The 'Affiliation' score is proportional to the number of experts present in same department at the seeker but haven't
															  done any collaboration in the past with the seeker." 
				 					rel="popover" data-original-title="Affiliation" class="icon-info-sign icon-black"></i>

				 					</td>	
				 				<td>
				 					<label class="checkbox">
										<input type="checkbox" value="affiliation" name="affiliation">
									</label>
									</td>
				 			</tr>
				 			<tr>
				 			    <td>2</td>
				 				<td>Most Qualified</td>
				 				<td>
									<i id="hr2" data-content="The 'most qualified' score is proportional to the expert's publication count citation count (self interest)." 
				 					rel="popover" data-original-title="Most Qualified" class="icon-info-sign icon-black"></i>
				 					</td>	
				 				<td>
				 					<label class="checkbox">
										<input type="checkbox" value="mqualified" name="mqualified">

									</label>
								</td>
								
				 			</tr>
				 			<tr>
				 			    <td>3</td>
				 				<td>Friend-of-a-friend</td>
				 				<td>
									<i id="hr3" data-content="The 'Friend-of-a-friend' score is proportional to the number of distinct paths through which the expert is indirectly
															  connected to the seeker, and favors experts close to the seeker, in the collaboration network (balance)." 
				 					rel="popover" data-original-title="Friend-of-a-friend" class="icon-info-sign icon-black"></i>
				 				</td>	

				 				<td>
				 					<label class="checkbox">
										<input type="checkbox" value="foaf" name="foaf">
									</label>
								</td>
								
				 			</tr>

				 			<tr>
				 			    <td>4</td>
				 				<td>Exchange</td>
				 				<td>
									<i id="hr4" data-content="The 'Exchange' score is proportional to number of articles authored by the expert which cite the seeker (reciprocity)." 
				 					rel="popover" data-original-title="Exchange" class="icon-info-sign icon-black"></i>
				 				</td>	
				 				<td>
				 					<label class="checkbox">
										<input type="checkbox" value="exchange" name="exchange">
									</label>
								</td>

								
				 			</tr>
				 			<tr>
				 			    <td>5</td>
				 				<td>Follow the Crowd</td>
				 				<td>
									<i id="hr5" data-content="The 'Follow the Crowd' score is proportional to the expert's overal popularity in terms of collaboration and being cited,
															  and favors experts close to the seeker in the collaboration network (contagion)." 
				 					rel="popover" data-original-title="Follow the Crowd" class="icon-info-sign icon-black"></i>
				 				</td>	
				 				<td>

				 					<label class="checkbox">
										<input type="checkbox" value="ftc" name="ftc">
									</label>
								</td>
								
				 			</tr>
				 			<tr>

				 			    <td>6</td>
				 				<td>Birds of a Feather</td>
				 				<td>

									<i id="hr6" data-content="The 'Birds of a Feather' score is proportional to the number of attributes shared between the seeker
															  and expert, such as gender, tenure status, affiliation and similarity in terms of citation by the same others (homophily)."
															  rel="popover" data-original-title="Birds of a Feather" class="icon-info-sign icon-black"></i>
				 				</td>	
				 				<td>
				 					<label class="checkbox">
										<input type="checkbox" value="boaf" name="boaf">
									</label>
								</td>
								
				 			</tr>
				 			<tr>
				 			    <td>7</td>
				 				<td>Mobilizing</td>
				 				<td>
									<i id="hr7" data-content="The 'Mobilizing' score favors experts who are brokers and close to the seeker in the union of the collaboration 
															  and citation networks (collective action)." data-original-title="Mobilizing" class="icon-info-sign icon-black"></i>
				 				</td>	
				 				<td>
				 					<label class="checkbox">

										<input type="checkbox" value="mobilizing" name="mobilizing">
									</label>
								</td>
								
				 			</tr>
				 			<tr>
				 			    <td>8</td>

				 				<td>I'm Feeling Lucky</td>
				 				<td>
									<i id="hr8" data-content="The 'Feeling Lucky' is an estimate of the probability of a collaboration using a p* (ERGM) model of scientific team 
															  formation (probabilistic model)." data-original-title="I'm Feeling Lucky" class="icon-info-sign icon-black"></i>

				 				</td>	
				 				<td>
				 					<label class="checkbox">
										<input type="checkbox" value="lucky" name="lucky">
									</label>
								</td>
								
				 			</tr>
				 		</tbody>
				 	</table>
				 </div>
			 </div>
      </div> <!-- hero-unit -->
      <footer>
      </footer>

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
     <script type="text/javascript">
        jQuery(document).ready(function() {
            $('#hr1').popover({animation:true, placement:'right', trigger:'hover', delay:0});
            $('#hr2').popover({animation:true, placement:'right', trigger:'hover', delay:0});
            $('#hr3').popover({animation:true, placement:'right', trigger:'hover', delay:0});
            $('#hr4').popover({animation:true, placement:'right', trigger:'hover', delay:0});
            $('#hr5').popover({animation:true, placement:'right', trigger:'hover', delay:0});
            $('#hr6').popover({animation:true, placement:'right', trigger:'hover', delay:0});
            $('#hr7').popover({animation:true, placement:'right', trigger:'hover', delay:0});
            $('#hr8').popover({animation:true, placement:'right', trigger:'hover', delay:0});
        });
    </script>

</body>
</html>