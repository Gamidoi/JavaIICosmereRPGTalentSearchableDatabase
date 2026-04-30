

<%@include file="includes/header.jsp" %>
<%@include file="includes/talentDescriptionSearchbars.jsp" %>

<c:choose>
    <c:when test="${talentResults.size() > 0}">
        <form id="sortForm" action="search" method="post">
        <input type="hidden" name="descriptionSearch" value="${descriptionSearch}">
        <input type="hidden" name="nameSearch" value="${nameSearch}">
        <input type="hidden" name="flavorSearch" value="${flavorSearch}">
        <input id="sortBy" type="hidden" name="sortBy" value="${sortBy}">
        <input id="ascending" type="hidden" name="ascending" value="${ascending}">

<%@include file="includes/resultTable.jsp" %>

        </form>
    </c:when>
<c:otherwise>
    <h1>The Search returned no results</h1>
</c:otherwise>
</c:choose>

<%@include file="includes/footer.jsp" %>

<script>
    function sortBy(sortBy){
        let sortByElement = document.getElementById("sortBy");
        sortByElement.value = sortBy;
        let submit = document.getElementById("sortForm");
        submit.submit();
    }
</script>