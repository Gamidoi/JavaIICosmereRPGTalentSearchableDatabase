
<%@include file="includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@include file="includes/talentDescriptionSearchbars.jsp" %>
<c:choose>
    <c:when test="${talentResults.size() > 0}">
        <div class="resultTableDiv"><table class="resultTable">
            <tr>
                <th>Name</th>
                <th class="wideTableColumn">Talent Description</th>
                <th>Action Type</th>
                <th>Action Cost</th>
                <th>Focus Cost</th>
                <th>Invest. Cost</th>
                <th>Talent Branch</th>
                <th>Heroic Path</th>
                <th>Radiant Path</th>
                <th class="wideTableColumn">Flavor Text</th>
            </tr>
            <c:forEach var="talent" items="${talentResults}">
                <tr>
                    <td>${talent.name}</td>
                    <td>${talent.description}</td>
                    <td>${talent.actionType}</td>
                    <td>${talent.actionCost > 0 ? talent.actionCost : ""}</td>
                    <td>${talent.focusCost > 0 ? talent.focusCost : ""}</td>
                    <td>${talent.investitureCost > 0 ? talent.investitureCost : ""}</td>
                    <td>${talent.branch}</td>
                    <td>${talent.path}</td>
                    <c:choose>
                        <c:when test = "${talent.radiantPath1 != null && talent.radiantPath2 != null}">
                            <td>${talent.radiantPath1}, ${talent.radiantPath2}</td>
                        </c:when>
                        <c:when test = "${talent.radiantPath1 != null}">
                            <td>${talent.radiantPath1}</td>
                        </c:when>
                        <c:otherwise><td></td></c:otherwise>
                    </c:choose>
                    <td>${talent.flavorText}</td>
                </tr>
            </c:forEach>
        </table></div>
    </c:when>
    <c:otherwise>
        <h1>The Search returned no results</h1>
    </c:otherwise>
</c:choose>
<%@include file="includes/footer.jsp" %>

