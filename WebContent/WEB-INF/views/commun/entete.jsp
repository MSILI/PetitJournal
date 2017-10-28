<div class="row">
	<nav class="navbar navbar-inverse" role="navigation" id="stiker">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<div class="navbar-inner">
					<img alt="logo" class="logo-nav"
						src="<c:url value="/images/logo.png"/>"> <a
						href="<c:url value="/"/>" class="navbar-brand"> <b>PETIT
							JOURNAL</b></a>
				</div>
			</div>

			<div class="collapse navbar-collapse navbar-right">
				<ul class="nav navbar-nav">
					<li class=""><a href='<c:url value="/"/>' title="Accieul"><span
							class="glyphicon glyphicon-home"></span></a></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle "
						data-toggle="dropdown">ARTICLE <b class="caret"></b>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li
								<c:if test="${empty sessionScope.userSession}">class="not-active disabled"</c:if>><a
								href='<c:url value="/article?action=add"></c:url>'><span
									class="glyphicon glyphicon-plus"></span> Nouveau article</a></li>
							<li
								<c:if test="${empty sessionScope.userSession}">class="not-active disabled"</c:if>><a
								href="<c:url value="/article?action=list-minde"></c:url>"><span
									class="glyphicon glyphicon-list"></span> Mes articles</a></li>
						</ul></li>
					<c:if test="${sessionScope.userSession.userType.id == 1 }">
						<li class="dropdown"><a href="#" class="dropdown-toggle "
							data-toggle="dropdown">COMPTES <b class="caret"></b>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href='<c:url value="/compte?action=new"></c:url>'><span
										class="glyphicon glyphicon-plus"></span> Nouveau compte</a></li>
								<li><a href="<c:url value="/compte?action=list"></c:url>"><span
										class="glyphicon glyphicon-list"></span> Liste des Comptes</a></li>
							</ul></li>
					</c:if>
					<c:if test="${empty sessionScope.userSession}">
						<li class="dropdown"><a href="#" class="dropdown-toggle "
							data-toggle="dropdown"> <span
								class="glyphicon glyphicon-user"></span> <b class="caret"></b></a>
							<ul class="dropdown-menu">

								<li><a href='<c:url value="/compte?action=insc"/>'><span
										class="glyphicon glyphicon-pencil"></span> Inscription</a></li>
								<li><a
									href="<c:url value="/sessionUtilisateur?tache=conn"/>"><span
										class="glyphicon glyphicon-globe"></span> Connexion</a></li>
							</ul></li>
					</c:if>
					<c:if test="${!empty sessionScope.userSession}">
						<li class="dropdown"><a href="#" class="dropdown-toggle "
							data-toggle="dropdown"> <c:out
									value="${sessionScope.userSession.nom}" /> <c:out
									value="${sessionScope.userSession.prenom}" /> <b class="caret"></b></a>
							<ul class="dropdown-menu">

								<li><a href='<c:url value="/compte?action=param"/>'><span
										class="glyphicon glyphicon-cog"></span> Paramétrage</a></li>
								<li><a
									href="<c:url value="/sessionUtilisateur?tache=dec"/>"><span
										class="glyphicon glyphicon-off"></span> Déconnexion</a></li>
							</ul></li>
					</c:if>

				</ul>
			</div>
		</div>
	</nav>
</div>