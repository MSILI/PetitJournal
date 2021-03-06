<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="<c:url value="/assets/css/bootstrap.css"/>" rel="stylesheet">
<link rel="shortcut icon" href="<c:url value="/images/logo.png"/>">
<link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initialscale=1.0">
<title>Liste des articles</title>
</head>
<body>
	<c:import url="/WEB-INF/views/commun/entete.jsp"></c:import>
	<div class="row">
		<div class="container well">
			<h4 style="color: rgb(228, 79, 80); font-weight: bold;">
				<span class="glyphicon glyphicon-list"></span> Liste des articles
			</h4>
			<hr>
			<c:if test="${empty listeArticles }">
				<p class="errorlogin">Il n'existe aucun article pour l'instant !</p>
			</c:if>
			<c:if test="${!empty listeArticles }">
				<div class="row">
					<div class="col-lg-3">
						<input class="form-control" type="text" id="search"
							placeholder="Recherche"><br>
					</div>
				</div>
				<div class="table-responsive">
					<table id="table"
						class="table table-striped table-bordered table-hover table-condensed ">
						<tbody class="searchable">
							<c:forEach items="${ listeArticles }" var="listArt"
								varStatus="boucle">
								<tr>
									<td><c:if test="${!empty sessionScope.userSession}">
											<div class="btn-group pull-right">
												<button class="btn btn-link dropdown-toggle"
													data-toggle="dropdown">
													action <span class="caret"></span>
												</button>
												<ul class="dropdown-menu">
													<c:choose>
														<c:when test="${sessionScope.userSession.userType.id !=1}">
															<c:choose>
																<c:when
																	test="${sessionScope.userSession.id != listArt.user.id}">
																	<li class="not-active disabled"><a href=""><span
																			class="glyphicon glyphicon-pencil"></span> Modifier</a></li>
																	<li class="not-active disabled"><a href="#"><span
																			class="glyphicon glyphicon-trash"></span> Supprimer</a></li>
																</c:when>
																<c:otherwise>
																	<li class=""><a
																		href="<c:url value="/article?action=edit"><c:param name="idArt" value="${listArt.id}"></c:param></c:url>"><span
																			class="glyphicon glyphicon-pencil"></span> Modifier</a></li>
																	<li class=""><a
																		data-confirm="Etes-vous certain de vouloir supprimer?"
																		href='<c:url value="/article?action=delete"><c:param name="idArt" value="${listArt.id}"></c:param></c:url>'><span
																			class="glyphicon glyphicon-trash"></span> Supprimer</a></li>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>

															<li class=""><a
																href="<c:url value="/article?action=edit"><c:param name="idArt" value="${listArt.id}"></c:param></c:url>"><span
																	class="glyphicon glyphicon-pencil"></span> Modifier</a></li>
															<li class=""><a
																data-confirm="Etes-vous certain de vouloir supprimer?"
																href='<c:url value="/article?action=delete"><c:param name="idArt" value="${listArt.id}"></c:param></c:url>'><span
																	class="glyphicon glyphicon-trash"></span> Supprimer</a></li>

														</c:otherwise>
													</c:choose>
													<li class="divider"></li>
													<li><a
														href='<c:url value="/illustration?action=add"><c:param name="idArt" value="${listArt.id }"/> </c:url>'><span
															class="glyphicon glyphicon-picture"></span> Nouvelle
															image</a></li>
												</ul>
											</div>
										</c:if>
										<h1 class="titre">
											<c:out value="${listArt.titre }"></c:out>
										</h1>

										<p class="info-art">
											Auteur :
											<c:out value="${listArt.user.nom }"></c:out>
											<c:out value="${listArt.user.prenom }"></c:out>
											| Publi� le :
											<c:out value="${listArt.date }"></c:out>
										</p>
										<div class="row">
											<p class="col-lg-8 col-sm-8 col-xs-5 txt-art">
												<span class="comment more"> <c:out
														value="${listArt.text}"></c:out></span> <br> <br> <a
													class="btn btn-default"
													href='<c:url value="/illustration?action=list"><c:param name= "idArt" value="${listArt.id}"></c:param> </c:url>'><span
													class="glyphicon glyphicon-picture"></span> voir images</a>
											</p>
										</div></td>
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
													'<div id="dataConfirmModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button><h3 id="dataConfirmLabel">Merci de confirmer</h3></div><div class="modal-body"></div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">Non</button><a class="btn btn-info" id="dataConfirmOK">Oui</a></div></div></div></div>');
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