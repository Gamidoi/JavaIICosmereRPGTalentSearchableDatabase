<%@include file="includes/header.jsp" %>

<h1>Player: ${userName}</h1>
<form id="updateForm" action="update" method="post">
    <label>Character Name: <input onchange="update()" type="text" name="name" value="${character.name}" /></label>
    <br>
    <label>Character Level: <input onchange="update()" type="number" name="level" value="${character.level}" /></label>
    <br>
    <label>Current HP: <input onchange="update()" type="number" name="currHP" value="${character.currHP}" /> / <input onchange="update()" type="number" name="maxHP" value="${character.maxHP}" /></label>
    <br>
    <h2>Stats:</h2>
    <label>Strength: <input onchange="update()" type="number" name="strength" value="${character.strength}" /></label><br>
    <label>Speed: <input onchange="update()" type="number" name="speed" value="${character.speed}" /></label><br>
    <label>Intellect: <input onchange="update()" type="number" name="intellect" value="${character.intellect}" /></label><br>
    <label>Willpower: <input onchange="update()" type="number" name="willpower" value="${character.willpower}" /></label><br>
    <label>Awareness: <input onchange="update()" type="number" name="awareness" value="${character.awareness}" /></label><br>
    <label>Presence: <input onchange="update()" type="number" name="presence" value="${character.presence}" /></label>
</form>


<%@include file="includes/footer.jsp" %>
<script>
    function update(){
        let submit = document.getElementById("updateForm");
        submit.submit();
    }
</script>