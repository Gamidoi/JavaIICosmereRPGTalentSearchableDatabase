
<%@include file="includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    <c:forEach var="talent" items="${talentResults}">
        <h1>${talent.name}</h1>
        <p>${talent.description}</p>
        <p>${talent.flavorText}</p>
    </c:forEach>

<%@include file="includes/footer.jsp" %>

