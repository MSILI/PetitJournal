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
<title>Liste des utilisateurs</title>
</head>
<body>
	<c:import url="/WEB-INF/views/commun/entete.jsp"></c:import>
	<div class="row">
		<div class="container well">
			<h4 style="color: rgb(228, 79, 80); font-weight: bold;">
				<span class="glyphicon glyphicon-list"></span> Liste des
				utilisateurs
			</h4>
			<hr>
			<c:if test="${empty users }">
				<p class="errorlogin">Il n'existe aucun utlisateur pour
					l'instant !</p>
			</c:if>
			<c:if test="${!empty users }">
				<div class="row">
					<div class="col-lg-3">
						<input class="form-control" type="text" id="search"
							placeholder="Recherche"><br>
					</div>
				</div>
				<div class="table-responsive">
					<table id="tableusers"
						class="table table-striped table-bordered table-hover table-condensed ">
						<thead>
							<tr>
								<th><b>Nom</b></th>
								<th><b>Prénom</b></th>
								<th><b>Login</b></th>
								<th><b>Type</b></th>
								<th><b>Action</b></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ users }" var="listUsers" varStatus="boucle">
								<tr>
									<td><c:out value="${listUsers.nom }"></c:out></td>
									<td><c:out value="${listUsers.prenom }"></c:out></td>
									<td><c:out value="${listUsers.login }"></c:out></td>
									<td><c:out value="${listUsers.userType.libelle }"></c:out></td>
									<td><c:if
											test="${ listUsers.id != sessionScope.userSession.id}">
											<a class=""
												href="<c:url value="/compte?action=delete"><c:param name="idUser" value="${ listUsers.id }" /></c:url>"
												data-toggle="tooltip" data-placement="bottom"
												title="Supprimer"
												data-confirm="Etes-vous certain de vouloir supprimer?"><span
												class="glyphicon glyphicon-trash"></span></a>
											<a
												href="<c:url value="/compte?action=modif"><c:param name="idUser" value="${ listUsers.id }" /></c:url> "
												data-toggle="tooltip" data-placement="bottom"
												title="Modifier"><span
												class="glyphicon glyphicon-pencil"></span></a>
										</c:if></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>

	</div>
	<c:import url="/WEB-INF/views/commun/pied.jsp"></c:import>
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

		$(function() {
			$("[data-toggle='tooltip']").tooltip();
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
		// message de confirmation lors de la suppression
		$(function() {
			$('a[data-confirm]')
					.click(
							function(ev) {
								var href = $(this).attr('href');

								if (!$('#dataConfirmModal').length) {
									$('body')
											.append(
													'<div id="dataConfirmModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel">Merci de confirmer</h3></div><div class="modal-body"></div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">Non</button><a class="btn btn-info" id="dataConfirmOK">Oui</a></div></div></div></div>');
								}
								$('#dataConfirmModal').find('.modal-body')
										.text($(this).attr('data-confirm'));
								$('#dataConfirmOK').attr('href', href);
								$('#dataConfirmModal').modal({
									show : true
								});

								return false;
							});
		});
		/***********************aficher le reste du texte******************************/
		$(document)
				.ready(
						function() {
							var showChar = 300;
							var ellipsestext = "...";
							var moretext = " voir plus";
							var lesstext = " voir moins";
							$('.more')
									.each(
											function() {
												var content = $(this).html();

												if (content.length > showChar) {

													var c = content.substr(0,
															showChar);
													var h = content.substr(
															showChar - 1,
															content.length
																	- showChar);

													var html = c
															+ '<span class="moreellipses">'
															+ ellipsestext
															+ '&nbsp;</span><span class="morecontent"><span>'
															+ h
															+ '</span>&nbsp;&nbsp;<a href="" class="morelink">'
															+ moretext
															+ '</a></span>';

													$(this).html(html);
												}

											});

							$(".morelink").click(function() {
								if ($(this).hasClass("less")) {
									$(this).removeClass("less");
									$(this).html(moretext);
								} else {
									$(this).addClass("less");
									$(this).html(lesstext);
								}
								$(this).parent().prev().toggle();
								$(this).prev().toggle();
								return false;
							});
						});
		/***********************recherche dans un tableau***********************/
		var $rows = $('#table tr');
		$('#search').keyup(function() {
			var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();
			$rows.show().filter(function() {
				var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
				return !~text.indexOf(val);
			}).hide();
		});
	</script>
</body>
</html>