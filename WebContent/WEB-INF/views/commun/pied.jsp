
<%@page import="java.util.Calendar"%>
<div class="">
</div>
<div class="foot">
	<br> <br>
	<div class="container">

		<div class="col-lg-4">
			<h4>Contactez-Nous</h4>
			<p>
				<span class="glyphicon glyphicon-phone-alt"></span> : 06 69 09 52 32
				<br>
		</div>
		<div class="col-lg-4">
			<h4>Suivez-Nous sur</h4>
			<p>
				<a href="#"><img
					src='<c:url value="/images/facebook.png"></c:url>' /></a> <a href="#"><img
					src='<c:url value="/images/Gmail.png"></c:url>' /></a> <a href="#"><img
					src='<c:url value="/images/twitter-32.png"></c:url>' /></a>
			</p>
		</div>
		<div class="col-lg-4">
			<br> <img alt="logo" class=""
				src="<c:url value="/images/icon-70-32.png"/>">PETIT JOURNAL<br>
			<%=Calendar.getInstance().get(Calendar.YEAR)%>
			&copy; Tout les droits sont réservés

		</div>
	</div>
	<br>
	<br>
</div>