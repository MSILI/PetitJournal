<!DOCTYPE html>
<c:if test="${sessionScope.userSession.userType.id != 1}">
	<c:redirect url="/sessionUtilisateur?tache=conn"></c:redirect>
</c:if>
<html>
<head>
<meta charset="utf-8">
<link href="<c:url value="/assets/css/bootstrap.css"/>" rel="stylesheet">
<link rel="shortcut icon" href="<c:url value="/images/logo.png"/>">
<link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initialscale=1.0">
<title>Nouveau utilisateur</title>
</head>
<body>

	<c:import url="../commun/entete.jsp"></c:import>
	<div class="row">
		<div class="container well">
			<h4 class="titre">
				<span class="glyphicon glyphicon-plus"></span> Nouveau utilisateur
			</h4>
			<hr>
			<form class="form" method="post" id="form"
				action='<c:url value="/compte?action=new"></c:url>'>
				<div class="row">
					<div class="form-group">
						<label for="nom" class="col-lg-2">Type de l'utilisateur<span
							class="requis">*</span></label>
						<div class="col-lg-3 col-sm-2 ">
							<select name="userType" id="userType" class="form-control">
								<option value="" selected="selected">Selectionnez</option>
								<option value="1">Administrateur</option>
								<option value="2">Simple utilisateur</option>
							</select>
						</div>
						<div class="col-lg-4 col-sm-2 ">
							<span class="erreur"><c:out
									value="${form.erreurs['userType']}"></c:out></span>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="form-group">
						<label for="nom" class="col-lg-2">Nom <span class="requis">*</span></label>
						<div class="col-lg-3 col-sm-2 ">
							<input id="nom" name="nom" type="text" class="form-control"
								placeholder="Nom"
								value='<c:out value="${form.valeurs['nom']}"></c:out>'>
						</div>
						<div class="col-lg-4 col-sm-2 ">
							<span class="erreur"><c:out value="${form.erreurs['nom']}"></c:out></span>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="form-group">
						<label for="prenom" class="col-lg-2">Votre prénom <span
							class="requis">*</span></label>
						<div class="col-lg-3 col-sm-2 ">
							<input id="prenom" name="prenom" type="text" class="form-control"
								placeholder="Prénom"
								value='<c:out value="${form.valeurs['prenom']}"></c:out>'>
						</div>
						<div class="col-lg-4 col-sm-2 ">
							<span class="erreur"><c:out
									value="${form.erreurs['prenom']}"></c:out></span>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="form-group">
						<label for="login" class="col-lg-2">Login <span
							class="requis">*</span></label>
						<div class="col-lg-3 col-sm-2 ">
							<input id="login" name="login" type="text" class="form-control"
								placeholder="Login"
								value='<c:out value="${form.valeurs['login']}"></c:out>'>
						</div>
						<div class="col-lg-4 col-sm-2 ">
							<span class="erreur"><c:out
									value="${form.erreurs['login']}"></c:out></span>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="form-group">
						<label for="password" class="col-lg-2">Password <span
							class="requis">*</span></label>
						<div class="col-lg-3 col-sm-2 ">
							<input id="password" name="password" type="password"
								class="form-control" placeholder="Mot de passe"
								value='<c:out value=""></c:out>'>
						</div>
						<div class="col-lg-4 col-sm-2 ">
							<span class="erreur"><c:out
									value="${form.erreurs['password']}"></c:out></span>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div
						class="form-group col-lg-offset-2 col-lg-2 col-xs-offset-2 col-xs-8  col-sm-offset-5 col-sm-3  ">
						<button class="btn btn-lg btn-default" type="submit" name="submit"
							id="submit">
							<span class="glyphicon glyphicon-floppy-disk"></span> Enregistrer
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<c:import url="../commun/pied.jsp"></c:import>

	<script src="<c:url value="/assets/js/jquery-1.12.0.min.js"></c:url>"
		charset="UTF-8"></script>
	<script src="<c:url value="/assets/js/bootstrap.min.js" ></c:url>"
		charset="UTF-8"></script>
	<script src="<c:url value="/assets/js/jquery.sticky.js" ></c:url>"
		charset="UTF-8"></script>
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