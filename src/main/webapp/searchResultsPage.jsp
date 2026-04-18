

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
        <div class="resultTableDiv"><table class="resultTable">
            <tr>
                <th onClick="sortBy('TalentName')">Name</th>
                <th class="wideTableColumn" onClick="sortBy('TalentDescription')">Talent Description</th>
                <th onClick="sortBy('ActionType')">Action Type</th>
                <th onClick="sortBy('ActionCost')">Action Cost</th>
                <th onClick="sortBy('FocusCost')">Focus Cost</th>
                <th onClick="sortBy('InvestitureCost')">Invest. Cost</th>
                <th onClick="sortBy('BranchName')">Talent Branch</th>
                <th onClick="sortBy('HeroicPathName')">Heroic Path</th>
                <th onClick="sortBy('RadiantOrderName')">Radiant Path</th>
                <th class="wideTableColumn" onClick="sortBy('FlavorText')">Flavor Text</th>
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
        </table></div></form>
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