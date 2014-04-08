<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
	<br>
	<div class="col-lg-12">
		<div class="row">
			<button type="button" class="btn btn-primary" onclick="newItem()">Add
				new course</button>
		</div>
		<br>
		<div class="row">
			<div class="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>Course ID</th>
							<th>Name</th>
							<th>Edit</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${courseList}" var="course" varStatus="i">
							<tr>
								<td>${course.iD}</td>
								<td>${course.courseName}</td>
								<td><button type="button" class="btn btn-primary btn-sm"
										id="${course.iD}" onClick="edit(this.id)">
										<span class="glyphicon glyphicon-pencil"></span>
									</button>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootswatch.js"></script>

	<script type="text/javascript">
	function edit(courseId) {
		alert(courseId);
	}
	function newItem() {
		alert("TODO: Creating new traject page")
	}
	</script>
</body>
</html>