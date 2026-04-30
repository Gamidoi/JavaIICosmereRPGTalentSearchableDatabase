<%@include file="includes/header.jsp" %>

<h1>Player: ${userName}</h1>
<form id="switchCharacters" action="switch" method="post">
    <input type="hidden" id="newCharacterID" name="newCharacterID" value="${character.characterID}">
    <div style="display: flex; flex-direction: row; width: 100%; margin-bottom: 10px;overflow-x: auto;">
        <p class="characterSelector" onclick="switchCharacters(0)">+New Character+</p>
        <c:forEach var="otherCharacters" items="${allUserCharacters}">
            <p class="characterSelector" onclick="switchCharacters(${otherCharacters.getValue()})">${otherCharacters.getKey()}</p>
        </c:forEach>
    </div>
</form>
<form id="updateForm" action="update" method="post">
    <label>Character Name: <input onchange="update()" type="text" name="name" value="${character.name}" /></label>
    <br>
    <label>Character Level: <input onchange="update()" type="number" name="level" value="${character.level}" max="30"/></label>
    <br>
    <label>Current HP: <input onchange="update()" type="number" name="currHP" value="${character.currHP}" max="500" class="hpBar"/> / <input onchange="update()" type="number" name="maxHP" value="${character.maxHP}"  max="500" class="hpBar" /></label>
    <br>
    <h2>Stats:</h2>
    <div class="statAndDefenseBoxesDIV">
        <div class="statAndDefenseBoxes">
            <p class="defense">Physical Defense: ${10 + character.speed + character.strength}</p>
            <div class="pairedStatsDiv">
                <label>Strength:<br><input class="statInputBox" onchange="update()" type="number" name="strength" value="${character.strength}"  max="10"/></label>
                <label>Speed:<br><input class="statInputBox" onchange="update()" type="number" name="speed" value="${character.speed}"  max="10"/></label>
            </div>
        </div>
        <div class="statAndDefenseBoxes">
            <p class="defense">Cognitive Defense: ${10 + character.intellect + character.willpower}</p>
            <div class="pairedStatsDiv">
                <label>Intellect:<br><input class="statInputBox" onchange="update()" type="number" name="intellect" value="${character.intellect}"  max="10"/></label>
                <label>Willpower:<br><input class="statInputBox" onchange="update()" type="number" name="willpower" value="${character.willpower}"  max="10"/></label>
            </div>
        </div>
        <div class="statAndDefenseBoxes">
            <p class="defense">Spiritual Defense: ${10 + character.awareness + character.presence}</p>
            <div class="pairedStatsDiv">
                <label>Awareness:<br><input class="statInputBox" onchange="update()" type="number" name="awareness" value="${character.awareness}"  max="10"/></label>
                <label>Presence:<br><input class="statInputBox" onchange="update()" type="number" name="presence" value="${character.presence}"  max="10"/></label>
            </div>
        </div>
    </div>
    <div class="pairedStatsDiv">
        <label>Inventory:<br><textarea onchange="update()" name="inventory" class="inventoryBox">${character.inventory}</textarea></label>
    </div>
</form>
<p onclick="confirmDelete()" id="deleteP" class="toggleViewMore">+ Delete Character?</p>
<div style="display: none" id="deleteButtonDiv" class="deleteCharacterBox">
    <p>Are you sure you want to delete this character? This cannot be undone!</p>
    <form action="delete" method="post">
        <input type="submit" value="Confirm" />
    </form>
</div>
<c:choose>
    <c:when test="${talentResults.size() > 0}">
            <%@include file="includes/resultTable.jsp" %>
    </c:when>
    <c:otherwise>
        <h1>${character.name} has no talents currently</h1>
    </c:otherwise>
</c:choose>

<%@include file="includes/footer.jsp" %>
<script>
    function update(){
        let submit = document.getElementById("updateForm");
        submit.submit();
    }
    function switchCharacters(charID){
        let newCharacterID = document.getElementById("newCharacterID");
        newCharacterID.value = charID;
        let submit = document.getElementById("switchCharacters");
        submit.submit();
    }
    function confirmDelete(){
        let deleteP = document.getElementById("deleteP");
        deleteP.innerHTML = deleteP.innerHTML === "+ Delete Character?" ? "- Delete Character?" : "+ Delete Character?";
        let deleteDiv = document.getElementById("deleteButtonDiv");
        deleteDiv.style.display = deleteDiv.style.display === "none" ? "block" : "none";
    }
</script>