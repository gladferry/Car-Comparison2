<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>home</title>
<script>
	
</script>
</head>
<body>
<h2></h2>

<table id="contentTable" class="" style="border:solid 1px">
		<thead><tr><th>Make</th><th>Model</th><th>Color</th><th>Year</th><th>Price</th><th>TCC Rating</th><th>Hwy MPG</th><th>Full Consumption</th></tr></thead>
		<tbody style="border:solid 1px">
		
		 <c:forEach items="${lists}" var="varmodel">
			<tr style="border:solid 1px"> 
				<td >${varmodel.make}</td>
				<td>${varmodel.model}</td>
				<td>${varmodel.color}</td>
				<td>${varmodel.year}</td>
				<td>${varmodel.price}</td>
				<td>${varmodel.tCCRating}</td>
				<td>${varmodel.hwyMPG}</td>
				<td>${varmodel.fullconsumption}</td>
			</tr>
		</c:forEach> 
		</tbody>
	</table>
	
	<div>
		order by <a href="yearClick.html">Year</a>
				<a href="makerClick.html">Maker</a>
				<a href="priceClick.html">Price</a>
				<a href="tCCRatingClick.html">TCC Rating</a>
				
	</div>
	
	<br/>
	<form action="calculate.html">
	<div>
		Input Distance:&nbsp;&nbsp;	<input name="distance"/><input value="calculate" type="submit"/>
	</div>
	</form>
	<br/>
	<form action="calculate2.html">
	<div>
		Input A Year:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="inputayear"/><input value="calculate" type="submit"/>
		<br/>
		Average MPG:&nbsp;&nbsp;&nbsp;&nbsp;<input name="averageMPG" value="${avg }"/>
	</div>
	</form>
	<br/>
	<div>
	<a href="getRandom.html">Get Random Car</a>
		<table id="contentTable2" class="" style="border:solid 1px">
		<thead style="border:solid 1px"><tr><th>Make</th><th>Model</th><th>Color</th><th>Year</th><th>Price</th><th>TCC Rating</th><th>Hwy MPG</th><th>Full Consumption</th></tr></thead>
		<tbody style="border:solid 1px">
			<tr>
				<td>${e.make}</td>
				<td>${e.model}</td>
				<td>${e.color}</td>
				<td>${e.year}</td>
				<td>${e.price}</td>
				<td>${e.tCCRating}</td>
				<td>${e.hwyMPG}</td>
				<td>${e.fullconsumption}</td>
			</tr>
		</tbody>
	</table>
	</div>
	

</body>
</html>