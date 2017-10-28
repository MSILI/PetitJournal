<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="<c:url value="/assets/css/bootstrap.css"/>" rel="stylesheet">
<link rel="shortcut icon" href="<c:url value="/images/logo.png"/>">
<link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initialscale=1.0">
<title>Connectez-vous</title>
</head>
<body>

	<c:import url="entete.jsp"></c:import>
	<div class="row">
		<div class="container well">

			<form method="post" class="form form-signin" role="form"
				action='<c:url value="/sessionUtilisateur?tache=conn"/>' id="connexion">
				<br> <br> <br>
				<h3 class="form-signin-heading">
					<span class="glyphicon glyphicon-globe"></span> Connexion
				</h3>
				<hr>
				<p class="errorlogin">
					<c:out value="${message}" />
				</p>
				<input type="text" id="login" name="login" class="form-control"
					placeholder="Login" autofocus> <input type="password"
					id="password" name="password" class="form-control"
					placeholder="Mot de passe">
				<button class="btn btn-lg btn-default btn-block" id="submit"
					name="submit" type="submit">
					<span class="glyphicon glyphicon-ok-sign"></span> Connexion
				</button>
				<br>
				<p>
					<b>Remarque:</b> si vous n'avez pas de compte, vous pouvez vous
					inscrire <a href='<c:url value="/compte?action=insc"/>'>ici </a>
				</p>
				<br> <br> <br>
			</form>
		</div>
	</div>
	<c:import url="pied.jsp"></c:import>

	<script src="<c:url value="/assets/js/jquery-1.12.0.min.js"></c:url>"
		charset="UTF-8"></script>
	<script src="<c:url value="/assets/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/assets/js/jquery.sticky.js"/>"></script>
	<script>
		$(document).ready(function() {
			$("#stiker").sticky({
				topSpacing : 0
			});

		});
	</script>
	<script>
		var timerIn = 200;
		var timerOut = 200;
		$('ul.nav li.dropdown').hover(
				function() {
					$(this).find('> .dropdown-menu').stop(true, true).fadeIn(
							timerIn);
					$(this).addClass('open');
				},
				function() {
					$(this).find('> .dropdown-menu').stop(true, true).fadeOut(
							timerOut);
					$(this).removeClass('open');
				});
	</script>
</body>
</html>