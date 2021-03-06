<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<html lang="en">
<head>
<title>CalZone</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css"
	media="screen">
<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/themes/css/lumen/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/themes/css/dashboard.css"
	rel="stylesheet">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
	<![endif]-->
<style>
Body,HTML {
	height: 100%;
	margin: 0;
}
</style>
</head>
<body>
	<!-- Wrap all page content here -->
	<jsp:include page="NavigationBar.jsp" />

	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="page-header">
					<h1 id="type">
						<spring:message code="passwordforgot.title.text" />
					</h1>
				</div>
			</div>
		</div>

		<div class="row">
			<form:form commandName="email" class="bs-example form-horizontal">
				<div id="wrap">
					<div id='myoutercontainer' class="container ">
						<div class="col-lg-4">&nbsp;</div>
						<div class="col-lg-4 box-top box-shadow">
							<fieldset>
								<a href="${pageContext.request.contextPath}" class="left"><spring:message
										code="navigation.back" /></a>
								<div class="form-group">
									<div class="col-lg-12">
										<form:input path="email" class="form-control"
											placeholder="Email" />
										<form:errors path="email" cssClass="error"></form:errors>
									</div>
								</div>
							</fieldset>
						</div>
					</div>
				</div>

				<div id="footer">
					<div class="container">
						<div class="col-lg-4">&nbsp;</div>
						<div class="col-lg-4 box-bottom box-shadow">
							<div class="bs-example form-horizontal">
								<fieldset>
									<div class="form-group">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-primary full-width">
												<spring:message code="navigation.next" />
											</button>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootswatch.js"></script>
</body>
</html>