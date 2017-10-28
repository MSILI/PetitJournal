<!DOCTYPE html>
<c:if test="${empty sessionScope.userSession }">
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
<title>Modifier article</title>
</head>
<body>

	<c:import url="../commun/entete.jsp"></c:import>
	<div class="row">
		<div class="container well">
			<br>
			<h4 class="titre">
				<span class="glyphicon glyphicon-file"></span> Modifier article
			</h4>
			<hr>
			<form class="form" method="post" id="form"
				action='<c:url value="/article?action=edit"></c:url>'>
				<div class="row">
					<div class="form-group">
						<label for="titre" class="col-lg-2">Titre <span
							class="requis">*</span></label>
						<div class="col-lg-3 col-sm-3 ">
							<input id="titre" name="titre" type="text" class="form-control"
								placeholder="Titre de l'article"
								value='<c:out value="${sessionScope.article.titre}"></c:out>'>
							<input id="idArt" name="idArt" type="hidden" class="form-control"
								value='<c:out value="${sessionScope.article.id}"></c:out>'>
						</div>
						<div class="col-lg-5 col-sm-5 ">
							<span class="erreur"><c:out
									value="${form.erreurs['titre']}"></c:out></span>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="form-group">
						<label for="text" class="col-lg-2">Text <span
							class="requis">*</span></label>
						<div class="col-lg-3 col-sm-2 ">
							<textarea id="text" name="text" class="form-control"
								placeholder="Texte de l'article"><c:out
									value="${sessionScope.article.text}"></c:out></textarea>
						</div>
						<div class="col-lg-5 col-sm-5 ">
							<span class="erreur"><c:out
									value="${form.erreurs['text']}"></c:out></span>
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
			<br>
			<br>
			<br>
		</div>
	</div>
	<c:import url="/WEB-INF/views/commun/pied.jsp"></c:import>

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