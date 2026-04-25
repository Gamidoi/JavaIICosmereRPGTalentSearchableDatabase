<%@include file="includes/header.jsp" %>

<h1>Player: ${userName}</h1>
<form id="switchCharacters" action="switch" method="post">
    <input type="hidden" id="newCharacterID" name="newCharacterID" value="${character.characterID}">
    <div style="display: flex; flex-direction: row; width: 100%; margin-bottom: 10px;">
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
    <label>Current HP: <input onchange="update()" type="number" name="currHP" value="${character.currHP}" max="500"/> / <input onchange="update()" type="number" name="maxHP" value="${character.maxHP}"  max="500"/></label>
    <br>
    <h2>Stats:</h2>
    <label>Strength: <input onchange="update()" type="number" name="strength" value="${character.strength}"  max="10"/></label><br>
    <label>Speed: <input onchange="update()" type="number" name="speed" value="${character.speed}"  max="10"/></label><br>
    <label>Intellect: <input onchange="update()" type="number" name="intellect" value="${character.intellect}"  max="10"/></label><br>
    <label>Willpower: <input onchange="update()" type="number" name="willpower" value="${character.willpower}"  max="10"/></label><br>
    <label>Awareness: <input onchange="update()" type="number" name="awareness" value="${character.awareness}"  max="10"/></label><br>
    <label>Presence: <input onchange="update()" type="number" name="presence" value="${character.presence}"  max="10"/></label>
</form>
<form action="delete" method="post">
    <input type="submit" value="Delete Character" />
</form>


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
</script>