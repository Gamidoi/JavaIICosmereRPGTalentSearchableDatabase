
<%@include file="includes/header.jsp" %>

    <h1><%= "Welcome to the Cosmere RPG!" %></h1>
    <br/>
    <form  action="search" method="post">
        <label>Search in Talent Description:
            <input type="text" name="descriptionSearch"/>
        </label>
        <br/>
        <label>Search in Talent Name:
            <input type="text" name="nameSearch"/>
        </label>
        <br/>
        <label>Search in Talent Flavor Text:
            <input type="text" name="flavorSearch"/>
        </label>
        <br/>
        <input type="submit" value="Search" />
    </form>
    <br/>

<%@include file="includes/footer.jsp" %>
