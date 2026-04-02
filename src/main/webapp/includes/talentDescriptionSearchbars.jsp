
<br/>
<form  action="search" method="post">
    <label>Search in Talent Description:
        <input type="text" name="descriptionSearch"/>
    </label>
    <br/>
    <p onClick="toggleSearchView()" id="viewToggleButton" class="toggleViewMore">More Search Options ( + )</p>
    <div id="moreSearchOptions" style="display: none">
        <label>Search in Talent Name:
            <input type="text" name="nameSearch"/>
        </label>
        <br/>
        <label>Search in Talent Flavor Text:
            <input type="text" name="flavorSearch"/>
        </label>
        <br/>
    </div>
    <br>
    <input type="submit" value="Search" />
</form>

<script>
    function toggleSearchView(){
        let extraSearchFields = document.getElementById("moreSearchOptions");
        extraSearchFields.style.display = extraSearchFields.style.display === "none" ? "block" : "none";
        let viewToggleButton = document.getElementById("viewToggleButton");
        viewToggleButton.innerHTML = viewToggleButton.innerHTML === "More Search Options ( + )" ? "More Search Options ( - )" : "More Search Options ( + )"
    }
</script>
<br/>
