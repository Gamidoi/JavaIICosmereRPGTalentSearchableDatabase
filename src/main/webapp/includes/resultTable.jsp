
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
        <c:choose >
            <c:when test="${userName != null}">
                <th>Add&nbspto Character</th>
            </c:when>
        </c:choose>
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
            <c:choose >
                <c:when test="${userName != null}">
                    <td><form action="addTalent"><input type="hidden" value="${talent.primaryKey}" name="addTalentIDToCharacter" /><input type="submit" value="add" /></form></td>
                </c:when>
            </c:choose>
        </tr>
    </c:forEach>
</table></div>